/*
 * synergy -- mouse and keyboard sharing utility
 * Copyright (C) 2010 Shaun Patterson
 * Copyright (C) 2010 The Synergy Project
 * Copyright (C) 2009 The Synergy+ Project
 * Copyright (C) 2002 Chris Schoeneman
 * 
 * This package is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * found in the file COPYING that should have accompanied this file.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.synergy.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.synergy.base.Event;
import org.synergy.base.EventJobInterface;
import org.synergy.base.EventQueue;
import org.synergy.base.EventQueueTimer;
import org.synergy.base.EventType;
import org.synergy.base.Log;
import org.synergy.common.keys.KeyTranslator;

import org.synergy.io.msgs.ClipboardDataMessage;
import org.synergy.io.msgs.ClipboardMessage;
import org.synergy.io.msgs.InfoAckMessage;
import org.synergy.io.msgs.InfoMessage;
import org.synergy.io.msgs.KeyDownMessage;
import org.synergy.io.msgs.KeyRepeatMessage;
import org.synergy.io.msgs.KeyUpMessage;
import org.synergy.io.msgs.LeaveMessage;
import org.synergy.io.msgs.MessageType;
import org.synergy.io.msgs.MouseDownMessage;
import org.synergy.io.msgs.MouseMoveMessage;
import org.synergy.io.msgs.MouseRelMoveMessage;
import org.synergy.io.msgs.MouseUpMessage;
import org.synergy.io.msgs.MouseWheelMessage;
import org.synergy.io.msgs.QueryInfoMessage;
import org.synergy.io.msgs.ResetOptionsMessage;
import org.synergy.io.msgs.ScreenSaverMessage;
import org.synergy.io.msgs.SetOptionsMessage;
import org.synergy.io.Stream;
import org.synergy.io.msgs.EnterMessage;
import org.synergy.io.msgs.KeepAliveMessage;
import org.synergy.io.msgs.MessageHeader;

public class ServerProxy {
    private static final double KEEP_ALIVE_UNTIL_DEATH = 3.0;
    private static final double KEEP_ALIVE_RATE = 3.0;


	/**
	 * Enumeration and Interface for parsing function
	 */
	private enum Result {
		OKAY, UNKNOWN, DISCONNECT
	};

	// To define what should parse and process messages
	private interface Parser {
		public Result parse () throws IOException;
	}

	private Client client;
	private Stream stream;

	private Integer seqNum;

	private boolean compressMouse;
	private boolean compressMouseRelative;
	private Integer xMouse;
	private Integer yMouse;
	private Integer dxMouse;
	private Integer dyMouse;

	private Parser parser;

	private boolean ignoreMouse;

	// TODO KeyModifierTable

	private double keepAliveAlarm;
	private EventQueueTimer keepAliveAlarmTimer;

	private DataInputStream din;
	private DataOutputStream dout;

	public ServerProxy (Client client, Stream stream) {
		this.client = client;
		this.stream = stream;

		this.seqNum = 0;
		this.compressMouse = false;
		this.compressMouseRelative = false;
		this.xMouse = 0;
		this.yMouse = 0;
		this.dxMouse = 0;
		this.dyMouse = 0;
		this.ignoreMouse = false;

		this.keepAliveAlarm = 0.0;
		this.keepAliveAlarmTimer = null;

		assert (client != null);
		assert (stream != null);

		// TODO Key modifier table

		// handle data on stream
		EventQueue.getInstance ().adoptHandler (EventType.STREAM_INPUT_READY, stream.getEventTarget (),
				new EventJobInterface () {
					public void run (Event event) {
						handleData ();
					}
				});

		// send heartbeat
        setKeepAliveRate (KEEP_ALIVE_RATE);

		parser = new Parser () {
			public Result parse () throws IOException {
				return parseHandshakeMessage ();
			}
		};

	}

	protected enum EResult {
		OKAY, UNKNOWN, DISCONNECT;
	}

    /**
      * Handle messages before handshake is complete
      */
	protected Result parseHandshakeMessage () throws IOException {
		// Read the header
		MessageHeader header = new MessageHeader (din);
		Log.debug1 ("Received Header: " + header);

		switch (header.getType ()) {
		case QINFO:
			//queryInfo (new QueryInfoMessage ());
			queryInfo ();
			break;
		case CINFOACK:
			infoAcknowledgment ();
			break;
		case DSETOPTIONS:
			SetOptionsMessage setOptionsMessage = new SetOptionsMessage (header, din);

			setOptions (setOptionsMessage);

			// handshake is complete
			Log.debug ("Handshake is complete");
			parser = new Parser () {
				public Result parse () throws IOException {
					return parseMessage ();
				}
			};

			client.handshakeComplete ();
			break;
		case CRESETOPTIONS:
			resetOptions (new ResetOptionsMessage (din));
			break;
		default:
			return Result.UNKNOWN;
		}
		
		return Result.OKAY;
	}

    /**
      * Handle messages after the handshack is complete
      */
	protected Result parseMessage () throws IOException {
		// Read the header
		MessageHeader header = new MessageHeader (din);
		Log.debug1 ("Received Header: " + header);

        switch (header.getType ()) {
        // Okay.. why can't I do
        //case MouseMoveMessage.MESSAGE_TYPE:
        // switch to a bunch of if statements?
        case DMOUSEMOVE:
            short x = din.readShort ();
    		short y = din.readShort ();

            //Log.debug ("x: " + x + ", y:" + y);
            client.mouseMove ((int)x, (int) y);

			//mouseMove (new MouseMoveMessage (din));
            // I think this'll be too slow
            break;

        case DMOUSERELMOVE:
            mouseRelativeMove (new MouseRelMoveMessage (din));
            break;

        case DMOUSEWHEEL:
			mouseWheel (new MouseWheelMessage (din));
            break;

		case DKEYDOWN:
            // 97:8192:38  -- lowercase a
            // 61409 - left shift
            // 65:8193:38 -- uppercase A

		    keyDown (new KeyDownMessage (din));
            break;

		case DKEYUP:
			keyUp (new KeyUpMessage (din));
            break;
		
        case DKEYREPEAT:
			keyRepeat (new KeyRepeatMessage (din));
            break;

        case DMOUSEDOWN:
            mouseDown (new MouseDownMessage (din));
            break;

        case DMOUSEUP:
            mouseUp (new MouseUpMessage (din));
            break;


		case CKEEPALIVE:
			// echo keep alives and reset alarm
            new KeepAliveMessage ().write (dout);
			resetKeepAliveAlarm ();
			break;

		case CNOOP:
			// accept and discard no-op
			break;

        case CENTER:
			enter (new EnterMessage (header, din));
			break;

        case CLEAVE:
			leave (new LeaveMessage (din));
			break;

		case CCLIPBOARD:
			grabClipboard (new ClipboardMessage (din));
			break;

		case CSCREENSAVER:
			screensaver (new ScreenSaverMessage (din));
			break;

		case QINFO:
			//queryInfo (new QueryInfoMessage (din));
			queryInfo ();
			break;

		case CINFOACK:
			//infoAcknowledgment (new InfoAckMessage (din));
			infoAcknowledgment ();
			
			break;

        case DCLIPBOARD:
			setClipboard (new ClipboardDataMessage (header, din));
			break;

		case CRESETOPTIONS:
			resetOptions (new ResetOptionsMessage (din));
			break;

        case DSETOPTIONS:
			SetOptionsMessage setOptionsMessage = new SetOptionsMessage (header, din);
			setOptions (setOptionsMessage);
			break;

		case CCLOSE:
			// server wants us to hangup
			Log.debug1 ("recv close");
			// client.disconnect (null);
			return Result.DISCONNECT;

        case EBAD:
			Log.error ("server disconnected due to a protocol error");
			// client.disconnect("server reported a protocol error");
			return Result.DISCONNECT;

        default: 
			return Result.UNKNOWN;
		}

		return Result.OKAY;

	}

	private void flushCompressMouse () {
	}

	private void handleData () {
		Log.debug ("handle data called");

		try {
			this.din = new DataInputStream (stream.getInputStream ());
			// this.dout = new DataOutputStream (stream.getOutputStream ());
			// this.oout = new ObjectOutputStream (stream.getOutputStream());

			while (true) {
				switch (parser.parse ()) {
				case OKAY:
					break;
				case UNKNOWN:
					Log.error ("invalid message from server");
					return;
				case DISCONNECT:
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace ();
			// TODO
		}

		// TODO flushCompressedMouse ();
	}

	// private void sendInfo (

	private void resetKeepAliveAlarm () {
        if (keepAliveAlarmTimer != null) {
            keepAliveAlarmTimer.cancel ();
            keepAliveAlarmTimer = null;
        }
        
        if (keepAliveAlarm > 0.0) {
            keepAliveAlarmTimer = new EventQueueTimer (keepAliveAlarm, true, this, 
                    new EventJobInterface () {
                        public void run (Event event) {
                            handleKeepAliveAlarm ();
                        }});
        }
	}

	private void setKeepAliveRate (double rate) {
        keepAliveAlarm = rate * KEEP_ALIVE_UNTIL_DEATH;
        resetKeepAliveAlarm ();
	}

    private void handleKeepAliveAlarm () {
        Log.note ("server is dead");
        client.disconnect ("server is not responding");
    }


	// TODO translateKey

	private void handleData (Event event, Object buffer) {
		// handle messages until there are no more. first read message code
		Log.debug ("handle data");
	}

	private void queryInfo () {
		ClientInfo info = new ClientInfo (client.getShape (), client.getCursorPos ());
		sendInfo (info);
	}

	private void sendInfo (ClientInfo info) {
		try {
			dout = new DataOutputStream (stream.getOutputStream ());
            
			new InfoMessage (info.getScreenPosition ().x,
                            info.getScreenPosition ().y,
                            info.getScreenPosition ().width,
                            info.getScreenPosition ().height,
                            info.getCursorPos ().x,
                            info.getCursorPos ().y).write (dout);
            
		} catch (Exception e) {
			// TODO
			e.printStackTrace ();
		}
	}

	private void infoAcknowledgment () {
		Log.debug ("recv info acknowledgment");

		ignoreMouse = false;
	}

	public void onInfoChanged () {
		// ignore mouse motion until we receive acknowledgement of our info
		// changed message
		ignoreMouse = true;

		// send info update
		queryInfo ();
	}

	private void enter (EnterMessage enterMessage) {
		Log.debug1 ("Screen entered: " + enterMessage);

        compressMouse = false;
        compressMouseRelative = false;
        dxMouse = 0;
        dyMouse = 0;
        seqNum = enterMessage.getSequenceNumber ();

        client.enter (enterMessage);


	}

	private void leave (LeaveMessage leaveMessage) {
		Log.debug1 ("Screen left: " + leaveMessage);
	}

	private void keyUp (KeyUpMessage keyUpMessage) {
        Log.debug1 (keyUpMessage.toString ());
        
        try {
            int keyEventID = KeyTranslator.translateKey (keyUpMessage.getID ());

            Log.debug ("Key Released: " + KeyTranslator.keyEventToString (keyEventID));

            // forward
            client.keyUp (keyEventID);
        } catch (Exception e) {
        }
	}

    private void keyDown (KeyDownMessage keyDownMessage) {
        Log.debug (keyDownMessage.toString ());

        // lowercase a key
        // Key Down:97:8192:38
        
        // get mouse up to date
  //      flushCompressedMouse ();

//        Log.error ("Key Value: " + keyDownMessage.getKey

        // Translate to a KeyEvent 
        try {
            int keyEventID = KeyTranslator.translateKey (keyDownMessage.getID ());

            Log.debug ("Key Pressed: " + KeyTranslator.keyEventToString (keyEventID));

            // forward
            client.keyDown (keyEventID);
        } catch (Exception e) {
        }
    }

	private void keyRepeat (KeyRepeatMessage keyRepeatMessage) {
        Log.debug1 (keyRepeatMessage.toString ());
        
        
        try {
            int keyEventID = KeyTranslator.translateKey (keyRepeatMessage.getID ());

            Log.debug ("Key Pressed: " + KeyTranslator.keyEventToString (keyEventID));

            // forward
            client.keyRepeat (keyEventID);
        } catch (Exception e) {
        }
	}

	private void mouseDown (MouseDownMessage mouseDownMessage) {
        Log.debug (mouseDownMessage.toString ());

        client.mouseDown (mouseDownMessage.getButtonID ());
	}

	private void mouseUp (MouseUpMessage mouseUpMessage) {
        Log.debug (mouseUpMessage.toString ());

        client.mouseUp (mouseUpMessage.getButtonID ());
	}


    /*
	private void mouseMove (MouseMoveMessage mouseMoveMessage) {
		Log.debug1 (mouseMoveMessage.toString ());

        client.mouseMove (mouseMoveMessage);
	}
    */

	private void mouseRelativeMove (MouseRelMoveMessage mouseRelMoveMessage) {
	}

	private void mouseWheel (MouseWheelMessage mouseWheelMessage) {
	}

	private void resetOptions (ResetOptionsMessage resetOptionsMessage) {
	}

	private void setOptions (SetOptionsMessage setOptionsMessage) {
	}

	private void screensaver (ScreenSaverMessage screenSaverMessage) {
	}

	private void grabClipboard (ClipboardMessage clipboardMessage) {
	}

	private void setClipboard (ClipboardDataMessage clipboardDataMessage) {
		Log.debug1 ("Setting clipboard: " + clipboardDataMessage);
	}

}
