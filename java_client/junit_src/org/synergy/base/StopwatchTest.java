package org.synergy.base;

import static org.junit.Assert.*;
import org.junit.Test;


public class StopwatchTest {

	@Test
	public void testMilliSec () {
		System.out.println ((double)System.currentTimeMillis() / 1000.0);
	}
	
	@Test 
	public void generalTest () {
		try {	
			Stopwatch stopwatch = new Stopwatch (true);
			
			Thread.sleep (1000);
			System.out.println (stopwatch.getTime());
			
			Thread.sleep (1000);
			System.out.println (stopwatch.getTime());
		} catch (Exception e) {
			fail ("Failed: " + e);
		}
		
	}
}
