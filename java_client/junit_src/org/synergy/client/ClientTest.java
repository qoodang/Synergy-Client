package org.synergy.client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.synergy.base.Log;
import org.synergy.base.Log.Level;
import org.synergy.common.screens.BasicScreen;
import org.synergy.common.screens.PlatformIndependentScreen;
import org.synergy.common.screens.PlatformScreenInterface;
import org.synergy.net.DataSocketInterface;
import org.synergy.net.NetworkAddress;
import org.synergy.net.SocketFactoryInterface;
import org.synergy.net.TCPSocketFactory;


public class ClientTest {

	@Test
	public void testConnect () {
		try {
			Log.setLogLevel(Level.DEBUG5);
			
			SocketFactoryInterface socketFactory = new TCPSocketFactory();
			NetworkAddress serverAddress = new NetworkAddress ("tabasco", 24800);
			serverAddress.resolve ();
		
			BasicScreen basicScreen = new BasicScreen();
			
			PlatformIndependentScreen screen = new PlatformIndependentScreen(basicScreen);
			
			Client client = new Client ("Test", serverAddress, socketFactory, null, screen);
			client.connect ();
			
		} catch (Exception e) {
			e.printStackTrace();
			fail ("Failed: " + e);
		}
		
		/*
		 * 2010-10-08T22:39:31 WARNING: n: 4
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,168
2010-10-08T22:39:31 WARNING: n: 11
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,168
2010-10-08T22:39:31 WARNING: n: 4096
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,126
2010-10-08T22:39:31 WARNING: n: 26
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,134
2010-10-08T22:39:31 WARNING: n: 4096
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,126
2010-10-08T22:39:31 WARNING: n: 0
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,134
2010-10-08T22:39:31 WARNING: n: 4
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,168
2010-10-08T22:39:31 WARNING: n: 4
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,168
2010-10-08T22:39:31 WARNING: n: 4096
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,126
2010-10-08T22:39:31 WARNING: n: 22
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,134
2010-10-08T22:39:31 WARNING: n: 4096
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,126
2010-10-08T22:39:31 WARNING: n: 0
	/home/shaun/workspace/synergy/lib/net/CTCPSocket.cpp,134

		 */
		
	}
	
}
