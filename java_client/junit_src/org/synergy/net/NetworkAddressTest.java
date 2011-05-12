package org.synergy.net;

import org.junit.Test;

import static org.junit.Assert.fail;


public class NetworkAddressTest {

	@Test
	public void resolveTest () {
		NetworkAddress addr = new NetworkAddress ("127.0.0.1", 24800);
		
		try {
			addr.resolve ();
			
			System.out.println (addr.getHostname ());
			System.out.println (addr.getAddress());
			System.out.println (addr);
		} catch (Exception e) {
			fail ("Failed: " + e);
		}
		
	}
	
}
