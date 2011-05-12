package org.synergy.common.keys;

import java.awt.event.KeyEvent;

import org.junit.Test;
import static org.junit.Assert.*;


public class KeyTranslatorTest {

	@Test
	public void testKeyTranslation () {
		
		assertEquals (KeyEvent.VK_A, KeyTranslator.translateKey (65));
		assertEquals (KeyEvent.VK_A, KeyTranslator.translateKey (97));
		assertEquals (KeyEvent.VK_Z, KeyTranslator.translateKey (90));
		assertEquals (KeyEvent.VK_Z, KeyTranslator.translateKey (122));
		
	}
	
	@Test
	public void testkeyEventToString () {
		int a = KeyEvent.VK_A;
		int z = KeyEvent.VK_Z;
		int bs = KeyEvent.VK_BACK_SPACE;
		
		assertEquals ("VK_A", KeyTranslator.keyEventToString (a));
		assertEquals ("VK_Z", KeyTranslator.keyEventToString (z));
		assertEquals ("VK_BACK_SPACE", KeyTranslator.keyEventToString (bs));
		
	}
	
}
