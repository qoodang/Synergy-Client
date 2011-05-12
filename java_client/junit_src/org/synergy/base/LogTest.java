package org.synergy.base;

import org.junit.Test;
import org.junit.Assert.*;


public class LogTest {

	@Test
	public void testOutput () {
		
		Log.debug ("Debug");
		Log.debug1 ("Test Debug1");
		Log.info ("Test Info");
		
		Log.setLogLevel (Log.Level.INFO);
		Log.debug ("Test Debug");
		Log.debug1("Test Debug1");
		Log.info ("Test Info");
		
	}
	
}
