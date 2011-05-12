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

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;



import org.synergy.base.Event;
import org.synergy.base.EventQueue;
import org.synergy.base.EventType;
import org.synergy.base.Log;
import org.synergy.base.Log.Level;
import org.synergy.base.exceptions.InvalidMessageException;
import org.synergy.common.screens.BasicScreen;
import org.synergy.common.screens.PlatformIndependentScreen;
import org.synergy.net.NetworkAddress;
import org.synergy.net.SocketFactoryInterface;
import org.synergy.net.TCPSocketFactory;

public class ClientApp {

    /**
      * Print the command line usage
      */
    private void printUsage () {
        System.out.println ("Usage");
    }

	private void run (String [] args) {
		
		try {
            String ipAddress = "";
            String hostname = null;

            // Quick parse of the command line arguments
            try {
                Queue <String> parsedArgs = new LinkedList <String> (Arrays.asList (args));
                while (parsedArgs.size () > 0) {
                    String argument = parsedArgs.remove ();

                    if (argument.equals ("--help") || argument.equals ("-h")) {
                        printUsage ();
                        return;
                    } else if (argument.equals ("--debug") || argument.equals ("-d")) {
                        String nextArgument = parsedArgs.remove ();
                        if (nextArgument == null) {
                            printUsage ();
                            return;
                        } 
                        Log.setLogLevel (Level.valueOf (nextArgument));
                        Log.print ("Debug level set to: " + Log.getLogLevel ());

                    } else if (argument.equals ("--name") || argument.equals ("-n")) {
                        String nextArgument = parsedArgs.remove ();
                        if (nextArgument == null) {
                            printUsage ();
                            return;
                        } 
                        Log.print ("Setting hostname to: " + nextArgument);
                        hostname = nextArgument;
                    } else {
                        ipAddress = argument;
                    }
                }
            } catch (Exception e) {
                printUsage ();
                return;
            }

			SocketFactoryInterface socketFactory = new TCPSocketFactory();
			NetworkAddress serverAddress = new NetworkAddress (ipAddress, 24800);
			serverAddress.resolve ();

			BasicScreen basicScreen = new BasicScreen();

			PlatformIndependentScreen screen = new PlatformIndependentScreen(basicScreen);

            // Grab the hostname
            try { 
                if (hostname == null) {
                    InetAddress addr = InetAddress.getLocalHost(); 
                    byte[] ipAddr = addr.getAddress(); 
                    hostname = addr.getHostName(); 
                }
            } catch (UnknownHostException e) { 
                e.printStackTrace ();
                throw new IllegalArgumentException ("Unable to obtain hostname");
            } 

			Client client = new Client (hostname, serverAddress, socketFactory, null, screen);
			client.connect ();
			
			mainLoop ();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void mainLoop () { 
	    // TODO Socket Multiplexer

		try {
	        Event event = new Event ();
	        event = EventQueue.getInstance ().getEvent (event, -1.0);
	        Log.note ("Event grabbed");
	        while (event.getType () != EventType.QUIT) {
	            EventQueue.getInstance ().dispatchEvent (event);
	            // TODO event.deleteData ();
	            event = EventQueue.getInstance ().getEvent (event, -1.0);
	            Log.note ("Event grabbed");
	        }
		} catch (InvalidMessageException e) {
			e.printStackTrace ();
		}

        // close down
        // TODO

	}
	
	public static void main (String [] args) {
		System.out.println ("Starting client...");
		
		ClientApp app = new ClientApp ();
		
		app.run (args);
	}
}
