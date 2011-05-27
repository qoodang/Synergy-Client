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
package org.synergy.common.screens;


import org.synergy.base.*;
import org.synergy.injection.*;
import android.graphics.*;

public class PlatformIndependentScreen implements ScreenInterface {

    private PlatformScreenInterface screen;
    private boolean isPrimary;
    private boolean enabled;
    private boolean entered;
    private boolean screenSaverSync;

    // TODO KeyModiferMask
    boolean fakeInput;



    public PlatformIndependentScreen (PlatformScreenInterface platformScreen) {
        this.screen = platformScreen;
        this.isPrimary = platformScreen.isPrimary ();
        this.enabled = false;
        this.entered = this.isPrimary;
        this.screenSaverSync = true;
        this.fakeInput = false;
        assert screen != null;

        resetOptions ();
        
        /*
        try {
        	robot = new Robot ();
        } catch (AWTException e) {
        	e.printStackTrace ();
        }
		*/
        Log.debug ("opened display");
        
        
    }

    @Override
	public void finalize () throws Throwable {
        if (enabled) {
            disable ();
        }

        assert !enabled;
        assert (entered == isPrimary);

        Log.debug ("closed display");
    }

    public void enable () {
        assert !enabled;

        screen.updateKeyMap ();
        screen.updateKeyState ();
        screen.enable ();

        if (isPrimary) {
            enablePrimary ();
        } else {
            enableSecondary ();
        }

        enabled = true;
    }

    public void disable () {
        assert enabled;

        if (!isPrimary && entered) {
            leave ();
        } else if (isPrimary && !entered) {
            enter (0);
        } 

        screen.disable ();

        if (isPrimary) {
            disablePrimary ();
        } else {
            disableSecondary ();
        }

        enabled = false;
    }

    public void enter (int toggleMask) {
        assert (entered == false);
        Log.info ("entering screen");

        // Now on screen
        entered = true;

        screen.enter ();
        if (isPrimary) {
            enterPrimary ();
        } else {
            enterSecondary (toggleMask);
        }
    }

    public boolean leave () {
        assert (entered == true);
        Log.info ("leaving screen");

        if (!screen.leave ()) {
            return false;
        } 

        if (isPrimary) {
            leavePrimary ();
        } else {
            leaveSecondary ();
        }

        // make sure our idea of clipboard ownership is correct
        screen.checkClipboards ();

        // now not on screen
        entered = false;

        return true;
    }

    public void reconfigure (Integer activeSides) {
    	// only needed for the server
        //assert isPrimary;
        //screen.reconfigure (activeSides);
    }

	public void warpCursor (Integer x, Integer y) {
        // I think this is only needed for the server
        //assert isPrimary;
        //screen.warpCursor (x, y);
    }

    public boolean setClipboard () {
        // TODO Implement
        return false;
    }
	
	public boolean checkClipboard () {
        // TODO Implement
        return false;
    }
	
	public void openScreensaver () {
        // TODO Implement
    }
	
	public void closeScreensaver () {
        // TODO Implement
    }
	
	public void screenSaver (boolean activate) {
        // TODO Implement
    }
	
	public void resetOptions () {
        // TODO Implement
    }
	
	public void setOptions () {
        // TODO Implement
    }
	
	public void setSequenceNumber (Integer sequenceNumber) {
        // TODO Implement
    }
	
	public boolean isPrimary () {
        // TODO Implement
        return true;
    }

    /**
      * @param keyEventID A VK_ defined in KeyEvent
      */
    public void keyDown (int keyEventID, int mask) {
    	Log.debug ("Key down: " + keyEventID);
    	
    	
    	//Injection.getInstance ().testKey ();
    	
    	//Injection.getInstance ().move_mouse (180, 180);
    	
    	screen.fakeKeyDown(keyEventID, mask);
    }

    /**
      * @param keyEventID A VK_ defined in KeyEvent
      */
    public void keyUp (int keyEventID, int mask) {
        // TODO move to screen implementation
        //robot.keyRelease (keyEventID);
    	Injection.keyup (keyEventID, mask);
    }
    
    public void keyRepeat (int keyEventID, int mask) {
    }


    public void mouseDown (int buttonID) {
    	// TODO handle multiple button presses better...
        // TODO move to screen implementation

    	
     /*   int buttons = 0;

     
        switch (buttonID) {
        case 1:
            buttons = InputEvent.BUTTON1_MASK;
            break;
        case 2:
            buttons = InputEvent.BUTTON2_MASK;
            break;
        case 3:
        default:
            buttons = InputEvent.BUTTON3_MASK;
            break;
        }*/
        
       

        screen.fakeMouseButton (buttonID, true);
        //robot.mousePress (buttons);
    }

    public void mouseUp (int buttonID) {
    	// TODO handle multiple button releases
        // TODO move to screen implementation
        int buttons = 0;

        /*
        switch (buttonID) {
        case 1:
            buttons = InputEvent.BUTTON1_MASK;
            break;
        case 2:
            buttons = InputEvent.BUTTON2_MASK;
            break;
        case 3:
        default:
            buttons = InputEvent.BUTTON3_MASK;
            break;
        }
        */
        screen.fakeMouseButton (buttonID, false);
        
    	//robot.mouseRelease (buttons);
    }

    public void mouseMove (int x, int y) {
        screen.fakeMouseMove (x, y);

        //moveThread.mouseMove (new Point (x, y));
    	
    	// This seems way too slow...
        //robot.mouseMove (x, y);
    }

    public void mouseRelativeMove (int x, int y) {
    	screen.fakeRelativeMouseMove (x, y);
    	//Injection.getInstance ().moveMouse (x, y);
    }

    public void mouseWheel () {
    }


    public Object getEventTarget () {
		return screen;
	}
	
	public boolean getClipboard () {
		// TODO: Implement
		return false;
	}
	
	public Rect getShape () {
		return screen.getShape ();
	}
	
	public Point getCursorPos () {
        // TODO: Implement
		return new Point (0, 0);
	}

    private void enablePrimary () {
        // TODO screensave

        // claim screen changed sized
        EventQueue.getInstance ().addEvent (new Event (EventType.SHAPE_CHANGED, getEventTarget ()));
    }

    private void enableSecondary () {
        // TODO assume primary has all clipboards
        
        // disable the screen save if sychronization is enabled
        if (screenSaverSync) {
            screen.openScreensaver (false);
        }
    }

    private void disablePrimary () {
        // done with screen saver
        screen.closeScreensaver ();
    }

    private void disableSecondary () {
        // done with screen saver
        screen.closeScreensaver ();
    }

    private void enterPrimary () {
        // do nothing
    }

    private void enterSecondary (Integer toggleMask) {
        // do nothing
    }

    private void leavePrimary () { 
        // we don't track keys while on the primary screen so update our
        //  idea of them now.  This is to update the state of the toggle modifiers
        screen.updateKeyState ();
    }

    private void leaveSecondary () {
        // release any keys we think are still down
        screen.fakeAllKeysUp ();
    }

	
	public boolean checkClipboards () {
		// TODO Auto-generated method stub
		return false;
	}

	public void openScreensaver (boolean notify) {
		// TODO Auto-generated method stub
		
	}

	public Integer getErrorEvent () {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getShapeChangedEvent () {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getClipboardGrabbedEvent () {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getSuspendEvent () {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getResumeEvent () {
		// TODO Auto-generated method stub
		return null;
	}
	
}
