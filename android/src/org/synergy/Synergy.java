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
package org.synergy;

import java.net.*;
import java.util.*;

import org.synergy.base.*;
import org.synergy.client.*;
import org.synergy.common.screens.*;
import org.synergy.injection.*;
import org.synergy.net.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Synergy extends Activity {
	
	public native String stringFromJNI ();
	
	static {
		System.loadLibrary ("synergy-jni");
	}
	
	private class TestThread extends Thread {
		public TestThread () {}
		
		public void run () {
			try {
				// Time to beat: 5084634146
				//               1090853659
				//               10670030487
				// 25647164635
				// time to beat 5992560976
				//              4538536585
				//              4223170732
				
				// 13647621951
				// 11021951219
				// 9618536585
				// 9566280485
				// 9564817073
				
				// 11277804878
				// 11149207317
				// 10941067073
				// 10863384146
				// 10970182926
				// 10670030487
				long start = System.nanoTime ();
				System.out.println ("--- START: " + start);
				
				for (int i = 0; i < 1000000; i++) {
					Injection.movemouse (0, 0);
				}
				
				long end = System.nanoTime ();
				System.out.println ("--- TIME: " + (end - start));

				
			} catch (Exception e) {
				e.printStackTrace ();
			}
			
		}
	}
	
	private class MainLoopThread extends Thread {
		public MainLoopThread () { 
			
		}
		
		public void run () {
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
				
			} catch (Exception e) {
				e.printStackTrace ();
			}
		}
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        final Button testButton = (Button) findViewById (R.id.test);
        final Button connectButton = (Button) findViewById (R.id.connect);
        //모토믹스 : 키보드 없음, 트랙볼지원
        //에뮬레이터 : 쿼티키보드, 트랙볼지원
        int a = getResources().getConfiguration().navigation;
        int b = getResources().getConfiguration().keyboard;
        //아이피 확인하기
        try {
        	  Enumeration<NetworkInterface> en =
        	    NetworkInterface.getNetworkInterfaces(); 
        	  while(en.hasMoreElements()) {
        	         NetworkInterface interf = en.nextElement();
        	         Enumeration<InetAddress> ips = interf.getInetAddresses();
        	              while (ips.hasMoreElements()) {
        	                InetAddress inetAddress = ips.nextElement();
        	                if (!inetAddress.isLoopbackAddress()) {
        	                       String addr =  inetAddress.getHostAddress().toString();
        	                       System.out.println(addr);
        	             }
        	      }
        	  }
        	  } catch (SocketException ex) {
        	      
        	  }
        testButton.setOnClickListener (new View.OnClickListener() {
			public void onClick (View arg) {
				testConnection ();
			}
		});
        
        connectButton.setOnClickListener (new View.OnClickListener() {
			public void onClick (View arg) {
				connect ();
			}
		});       
        	
        Log.setLogLevel (Log.Level.INFO);
        
        Log.debug ("Client starting....");

        try {
			Injection.startInjection ();
		} catch (Exception e) {
			// TODO handle exception
		}
    }
    
    private void testConnection () {
    	new TestThread ().start ();
    }
    
    private void connect () {
    	EditText clientNameText = (EditText) findViewById (R.id.clientName);
    	EditText ipText = (EditText) findViewById (R.id.ipAddress);
    	EditText outputText = (EditText) findViewById (R.id.output);
    	
        try {
        	SocketFactoryInterface socketFactory = new TCPSocketFactory();
       	   	NetworkAddress serverAddress = new NetworkAddress (ipText.getText ().toString (), 24800);
        	serverAddress.resolve ();

        	BasicScreen basicScreen = new BasicScreen();
        	
        	Display display = getWindowManager ().getDefaultDisplay ();
        	basicScreen.setShape (display.getWidth (), display.getHeight ());
        	
        	
        	PlatformIndependentScreen screen = new PlatformIndependentScreen(basicScreen);

        	// Grab the hostname -- hmm it's always localhost? No good
        	/*
        	String hostname = "";
            try { 
                InetAddress addr = InetAddress.getLocalHost(); 
                byte[] ipAddr = addr.getAddress(); 
                hostname = addr.getHostName(); 
            } catch (UnknownHostException e) { 
                e.printStackTrace ();
                throw new IllegalArgumentException ("Unable to obtain hostname");
            } */
            
        	String hostname = clientNameText.getText ().toString ();
            Log.debug ("Hostname: " + hostname);
            
			Client client = new Client (hostname, serverAddress, socketFactory, null, screen);
			client.connect ();

			new MainLoopThread ().start ();
        } catch (Exception e) {
        	e.printStackTrace();
        	outputText.setText ("Connection failed");
        }
    }
}