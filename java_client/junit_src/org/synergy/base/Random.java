package org.synergy.base;

import org.junit.Test;


public class Random {

	public void outputParamTest (StringBuffer test) {
		test.append ("Test");
	}
	
	public void outputParamTestWithNew (StringBuffer test) {
		if (test == null) {
			test = new StringBuffer ("");
		}
		test.append ("Test");
	}
	
	public void setIntValue (Integer a) {
		a = 10;
	}
	
	public void swap (Integer a, Integer b) {
		Integer c = b;
		b = a;
		a = c;
	}
	
	@Test
	public void random () {
		
		Integer a = 0;
		Integer b = 1;
		
		swap (a, b);
		
		System.out.println ("a: " + a + "  b: " + b);
		
		setIntValue(a);
		System.out.println ("a: " + a + "  b: " + b);
		
		StringBuffer test = new StringBuffer ("test");
		outputParamTest (test);
		
		System.out.println (test.toString ());
		
		
		
		
		//StringBuffer test2 = null;
		//outputParamTestWithNew(test2);
		//System.out.println (test2.toString ());
	}
	
	
}
