package org.synergy;

import java.awt.Point;
import java.awt.event.KeyEvent;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;
import org.synergy.base.Log;

public class PlayZone {

	@Test
	public void test1 () {
		// Hmm, anyone know how to get this to work?
        /*for (Field f : KeyEvent.class.getFields ()) {
        	System.out.println (f.getName () + "=" + f.getInt (KeyEvent.class));
            System.out.println (f);
        } */
		
        // Behold the power if Vim
        System.out.println ("KEY_FIRST=" + KeyEvent.KEY_FIRST);
        System.out.println ("KEY_LAST=" + KeyEvent.KEY_LAST);
        System.out.println ("KEY_LOCATION_LEFT=" + KeyEvent.KEY_LOCATION_LEFT);
        System.out.println ("KEY_LOCATION_NUMPAD=" + KeyEvent.KEY_LOCATION_NUMPAD);
        System.out.println ("KEY_LOCATION_RIGHT=" + KeyEvent.KEY_LOCATION_RIGHT);
        System.out.println ("KEY_LOCATION_STANDARD=" + KeyEvent.KEY_LOCATION_STANDARD);
        System.out.println ("KEY_LOCATION_UNKNOWN=" + KeyEvent.KEY_LOCATION_UNKNOWN);
        System.out.println ("KEY_PRESSED=" + KeyEvent.KEY_PRESSED);
        System.out.println ("KEY_RELEASED=" + KeyEvent.KEY_RELEASED);
        System.out.println ("KEY_TYPED=" + KeyEvent.KEY_TYPED);
        System.out.println ("VK_0=" + KeyEvent.VK_0);
        System.out.println ("VK_1=" + KeyEvent.VK_1);
        System.out.println ("VK_2=" + KeyEvent.VK_2);
        System.out.println ("VK_3=" + KeyEvent.VK_3);
        System.out.println ("VK_4=" + KeyEvent.VK_4);
        System.out.println ("VK_5=" + KeyEvent.VK_5);
        System.out.println ("VK_6=" + KeyEvent.VK_6);
        System.out.println ("VK_7=" + KeyEvent.VK_7);
        System.out.println ("VK_8=" + KeyEvent.VK_8);
        System.out.println ("VK_9=" + KeyEvent.VK_9);
        System.out.println ("VK_A=" + KeyEvent.VK_A);
        System.out.println ("VK_ACCEPT=" + KeyEvent.VK_ACCEPT);
        System.out.println ("VK_ADD=" + KeyEvent.VK_ADD);
        System.out.println ("VK_AGAIN=" + KeyEvent.VK_AGAIN);
        System.out.println ("VK_ALL_CANDIDATES=" + KeyEvent.VK_ALL_CANDIDATES);
        System.out.println ("VK_ALPHANUMERIC=" + KeyEvent.VK_ALPHANUMERIC);
        System.out.println ("VK_ALT=" + KeyEvent.VK_ALT);
        System.out.println ("VK_ALT_GRAPH=" + KeyEvent.VK_ALT_GRAPH);
        System.out.println ("VK_AMPERSAND=" + KeyEvent.VK_AMPERSAND);
        System.out.println ("VK_ASTERISK=" + KeyEvent.VK_ASTERISK);
        System.out.println ("VK_AT=" + KeyEvent.VK_AT);
        System.out.println ("VK_B=" + KeyEvent.VK_B);
        System.out.println ("VK_BACK_QUOTE=" + KeyEvent.VK_BACK_QUOTE);
        System.out.println ("VK_BACK_SLASH=" + KeyEvent.VK_BACK_SLASH);
        System.out.println ("VK_BACK_SPACE=" + KeyEvent.VK_BACK_SPACE);
        System.out.println ("VK_BRACELEFT=" + KeyEvent.VK_BRACELEFT);
        System.out.println ("VK_BRACERIGHT=" + KeyEvent.VK_BRACERIGHT);
        System.out.println ("VK_C=" + KeyEvent.VK_C);
        System.out.println ("VK_CANCEL=" + KeyEvent.VK_CANCEL);
        System.out.println ("VK_CAPS_LOCK=" + KeyEvent.VK_CAPS_LOCK);
        System.out.println ("VK_CIRCUMFLEX=" + KeyEvent.VK_CIRCUMFLEX);
        System.out.println ("VK_CLEAR=" + KeyEvent.VK_CLEAR);
        System.out.println ("VK_CLOSE_BRACKET=" + KeyEvent.VK_CLOSE_BRACKET);
        System.out.println ("VK_CODE_INPUT=" + KeyEvent.VK_CODE_INPUT);
        System.out.println ("VK_COLON=" + KeyEvent.VK_COLON);
        System.out.println ("VK_COMMA=" + KeyEvent.VK_COMMA);
        System.out.println ("VK_COMPOSE=" + KeyEvent.VK_COMPOSE);
        System.out.println ("VK_CONTROL=" + KeyEvent.VK_CONTROL);
        System.out.println ("VK_CONVERT=" + KeyEvent.VK_CONVERT);
        System.out.println ("VK_COPY=" + KeyEvent.VK_COPY);
        System.out.println ("VK_CUT=" + KeyEvent.VK_CUT);
        System.out.println ("VK_D=" + KeyEvent.VK_D);
        System.out.println ("VK_DEAD_ABOVEDOT=" + KeyEvent.VK_DEAD_ABOVEDOT);
        System.out.println ("VK_DEAD_ABOVERING=" + KeyEvent.VK_DEAD_ABOVERING);
        System.out.println ("VK_DEAD_ACUTE=" + KeyEvent.VK_DEAD_ACUTE);
        System.out.println ("VK_DEAD_BREVE=" + KeyEvent.VK_DEAD_BREVE);
        System.out.println ("VK_DEAD_CARON=" + KeyEvent.VK_DEAD_CARON);
        System.out.println ("VK_DEAD_CEDILLA=" + KeyEvent.VK_DEAD_CEDILLA);
        System.out.println ("VK_DEAD_CIRCUMFLEX=" + KeyEvent.VK_DEAD_CIRCUMFLEX);
        System.out.println ("VK_DEAD_DIAERESIS=" + KeyEvent.VK_DEAD_DIAERESIS);
        System.out.println ("VK_DEAD_DOUBLEACUTE=" + KeyEvent.VK_DEAD_DOUBLEACUTE);
        System.out.println ("VK_DEAD_GRAVE=" + KeyEvent.VK_DEAD_GRAVE);
        System.out.println ("VK_DEAD_IOTA=" + KeyEvent.VK_DEAD_IOTA);
        System.out.println ("VK_DEAD_MACRON=" + KeyEvent.VK_DEAD_MACRON);
        System.out.println ("VK_DEAD_OGONEK=" + KeyEvent.VK_DEAD_OGONEK);
        System.out.println ("VK_DEAD_SEMIVOICED_SOUND=" + KeyEvent.VK_DEAD_SEMIVOICED_SOUND);
        System.out.println ("VK_DEAD_TILDE=" + KeyEvent.VK_DEAD_TILDE);
        System.out.println ("VK_DEAD_VOICED_SOUND=" + KeyEvent.VK_DEAD_VOICED_SOUND);
        System.out.println ("VK_DECIMAL=" + KeyEvent.VK_DECIMAL);
        System.out.println ("VK_DELETE=" + KeyEvent.VK_DELETE);
        System.out.println ("VK_DIVIDE=" + KeyEvent.VK_DIVIDE);
        System.out.println ("VK_DOLLAR=" + KeyEvent.VK_DOLLAR);
        System.out.println ("VK_DOWN=" + KeyEvent.VK_DOWN);
        System.out.println ("VK_E=" + KeyEvent.VK_E);
        System.out.println ("VK_END=" + KeyEvent.VK_END);
        System.out.println ("VK_ENTER=" + KeyEvent.VK_ENTER);
        System.out.println ("VK_EQUALS=" + KeyEvent.VK_EQUALS);
        System.out.println ("VK_ESCAPE=" + KeyEvent.VK_ESCAPE);
        System.out.println ("VK_EURO_SIGN=" + KeyEvent.VK_EURO_SIGN);
        System.out.println ("VK_EXCLAMATION_MARK=" + KeyEvent.VK_EXCLAMATION_MARK);
        System.out.println ("VK_F=" + KeyEvent.VK_F);
        System.out.println ("VK_F1=" + KeyEvent.VK_F1);
        System.out.println ("VK_F10=" + KeyEvent.VK_F10);
        System.out.println ("VK_F11=" + KeyEvent.VK_F11);
        System.out.println ("VK_F12=" + KeyEvent.VK_F12);
        System.out.println ("VK_F13=" + KeyEvent.VK_F13);
        System.out.println ("VK_F14=" + KeyEvent.VK_F14);
        System.out.println ("VK_F15=" + KeyEvent.VK_F15);
        System.out.println ("VK_F16=" + KeyEvent.VK_F16);
        System.out.println ("VK_F17=" + KeyEvent.VK_F17);
        System.out.println ("VK_F18=" + KeyEvent.VK_F18);
        System.out.println ("VK_F19=" + KeyEvent.VK_F19);
        System.out.println ("VK_F2=" + KeyEvent.VK_F2);
        System.out.println ("VK_F20=" + KeyEvent.VK_F20);
        System.out.println ("VK_F21=" + KeyEvent.VK_F21);
        System.out.println ("VK_F22=" + KeyEvent.VK_F22);
        System.out.println ("VK_F23=" + KeyEvent.VK_F23);
        System.out.println ("VK_F24=" + KeyEvent.VK_F24);
        System.out.println ("VK_F3=" + KeyEvent.VK_F3);
        System.out.println ("VK_F4=" + KeyEvent.VK_F4);
        System.out.println ("VK_F5=" + KeyEvent.VK_F5);
        System.out.println ("VK_F6=" + KeyEvent.VK_F6);
        System.out.println ("VK_F7=" + KeyEvent.VK_F7);
        System.out.println ("VK_F8=" + KeyEvent.VK_F8);
        System.out.println ("VK_F9=" + KeyEvent.VK_F9);
        System.out.println ("VK_FINAL=" + KeyEvent.VK_FINAL);
        System.out.println ("VK_FIND=" + KeyEvent.VK_FIND);
        System.out.println ("VK_FULL_WIDTH=" + KeyEvent.VK_FULL_WIDTH);
        System.out.println ("VK_G=" + KeyEvent.VK_G);
        System.out.println ("VK_GREATER=" + KeyEvent.VK_GREATER);
        System.out.println ("VK_H=" + KeyEvent.VK_H);
        System.out.println ("VK_HALF_WIDTH=" + KeyEvent.VK_HALF_WIDTH);
        System.out.println ("VK_HELP=" + KeyEvent.VK_HELP);
        System.out.println ("VK_HIRAGANA=" + KeyEvent.VK_HIRAGANA);
        System.out.println ("VK_HOME=" + KeyEvent.VK_HOME);
        System.out.println ("VK_I=" + KeyEvent.VK_I);
        System.out.println ("VK_INPUT_METHOD_ON_OFF=" + KeyEvent.VK_INPUT_METHOD_ON_OFF);
        System.out.println ("VK_INSERT=" + KeyEvent.VK_INSERT);
        System.out.println ("VK_INVERTED_EXCLAMATION_MARK=" + KeyEvent.VK_INVERTED_EXCLAMATION_MARK);
        System.out.println ("VK_J=" + KeyEvent.VK_J);
        System.out.println ("VK_JAPANESE_HIRAGANA=" + KeyEvent.VK_JAPANESE_HIRAGANA);
        System.out.println ("VK_JAPANESE_KATAKANA=" + KeyEvent.VK_JAPANESE_KATAKANA);
        System.out.println ("VK_JAPANESE_ROMAN=" + KeyEvent.VK_JAPANESE_ROMAN);
        System.out.println ("VK_K=" + KeyEvent.VK_K);
        System.out.println ("VK_KANA=" + KeyEvent.VK_KANA);
        System.out.println ("VK_KANA_LOCK=" + KeyEvent.VK_KANA_LOCK);
        System.out.println ("VK_KANJI=" + KeyEvent.VK_KANJI);
        System.out.println ("VK_KATAKANA=" + KeyEvent.VK_KATAKANA);
        System.out.println ("VK_KP_DOWN=" + KeyEvent.VK_KP_DOWN);
        System.out.println ("VK_KP_LEFT=" + KeyEvent.VK_KP_LEFT);
        System.out.println ("VK_KP_RIGHT=" + KeyEvent.VK_KP_RIGHT);
        System.out.println ("VK_KP_UP=" + KeyEvent.VK_KP_UP);
        System.out.println ("VK_L=" + KeyEvent.VK_L);
        System.out.println ("VK_LEFT=" + KeyEvent.VK_LEFT);
        System.out.println ("VK_LEFT_PARENTHESIS=" + KeyEvent.VK_LEFT_PARENTHESIS);
        System.out.println ("VK_LESS=" + KeyEvent.VK_LESS);
        System.out.println ("VK_M=" + KeyEvent.VK_M);
        System.out.println ("VK_META=" + KeyEvent.VK_META);
        System.out.println ("VK_MINUS=" + KeyEvent.VK_MINUS);
        System.out.println ("VK_MODECHANGE=" + KeyEvent.VK_MODECHANGE);
        System.out.println ("VK_MULTIPLY=" + KeyEvent.VK_MULTIPLY);
        System.out.println ("VK_N=" + KeyEvent.VK_N);
        System.out.println ("VK_NONCONVERT=" + KeyEvent.VK_NONCONVERT);
        System.out.println ("VK_NUM_LOCK=" + KeyEvent.VK_NUM_LOCK);
        System.out.println ("VK_NUMBER_SIGN=" + KeyEvent.VK_NUMBER_SIGN);
        System.out.println ("VK_NUMPAD0=" + KeyEvent.VK_NUMPAD0);
        System.out.println ("VK_NUMPAD1=" + KeyEvent.VK_NUMPAD1);
        System.out.println ("VK_NUMPAD2=" + KeyEvent.VK_NUMPAD2);
        System.out.println ("VK_NUMPAD3=" + KeyEvent.VK_NUMPAD3);
        System.out.println ("VK_NUMPAD4=" + KeyEvent.VK_NUMPAD4);
        System.out.println ("VK_NUMPAD5=" + KeyEvent.VK_NUMPAD5);
        System.out.println ("VK_NUMPAD6=" + KeyEvent.VK_NUMPAD6);
        System.out.println ("VK_NUMPAD7=" + KeyEvent.VK_NUMPAD7);
        System.out.println ("VK_NUMPAD8=" + KeyEvent.VK_NUMPAD8);
        System.out.println ("VK_NUMPAD9=" + KeyEvent.VK_NUMPAD9);
        System.out.println ("VK_O=" + KeyEvent.VK_O);
        System.out.println ("VK_OPEN_BRACKET=" + KeyEvent.VK_OPEN_BRACKET);
        System.out.println ("VK_P=" + KeyEvent.VK_P);
        System.out.println ("VK_PAGE_DOWN=" + KeyEvent.VK_PAGE_DOWN);
        System.out.println ("VK_PAGE_UP=" + KeyEvent.VK_PAGE_UP);
        System.out.println ("VK_PASTE=" + KeyEvent.VK_PASTE);
        System.out.println ("VK_PAUSE=" + KeyEvent.VK_PAUSE);
        System.out.println ("VK_PERIOD=" + KeyEvent.VK_PERIOD);
        System.out.println ("VK_PLUS=" + KeyEvent.VK_PLUS);
        System.out.println ("VK_PREVIOUS_CANDIDATE=" + KeyEvent.VK_PREVIOUS_CANDIDATE);
        System.out.println ("VK_PRINTSCREEN=" + KeyEvent.VK_PRINTSCREEN);
        System.out.println ("VK_PROPS=" + KeyEvent.VK_PROPS);
        System.out.println ("VK_Q=" + KeyEvent.VK_Q);
        System.out.println ("VK_QUOTE=" + KeyEvent.VK_QUOTE);
        System.out.println ("VK_QUOTEDBL=" + KeyEvent.VK_QUOTEDBL);
        System.out.println ("VK_R=" + KeyEvent.VK_R);
        System.out.println ("VK_RIGHT=" + KeyEvent.VK_RIGHT);
        System.out.println ("VK_RIGHT_PARENTHESIS=" + KeyEvent.VK_RIGHT_PARENTHESIS);
        System.out.println ("VK_ROMAN_CHARACTERS=" + KeyEvent.VK_ROMAN_CHARACTERS);
        System.out.println ("VK_S=" + KeyEvent.VK_S);
        System.out.println ("VK_SCROLL_LOCK=" + KeyEvent.VK_SCROLL_LOCK);
        System.out.println ("VK_SEMICOLON=" + KeyEvent.VK_SEMICOLON);
        System.out.println ("VK_SEPARATER=" + KeyEvent.VK_SEPARATER);
        System.out.println ("VK_SEPARATOR=" + KeyEvent.VK_SEPARATOR);
        System.out.println ("VK_SHIFT=" + KeyEvent.VK_SHIFT);
        System.out.println ("VK_SLASH=" + KeyEvent.VK_SLASH);
        System.out.println ("VK_SPACE=" + KeyEvent.VK_SPACE);
        System.out.println ("VK_STOP=" + KeyEvent.VK_STOP);
        System.out.println ("VK_SUBTRACT=" + KeyEvent.VK_SUBTRACT);
        System.out.println ("VK_T=" + KeyEvent.VK_T);
        System.out.println ("VK_TAB=" + KeyEvent.VK_TAB);
        System.out.println ("VK_U=" + KeyEvent.VK_U);
        System.out.println ("VK_UNDEFINED=" + KeyEvent.VK_UNDEFINED);
        System.out.println ("VK_UNDERSCORE=" + KeyEvent.VK_UNDERSCORE);
        System.out.println ("VK_UNDO=" + KeyEvent.VK_UNDO);
        System.out.println ("VK_UP=" + KeyEvent.VK_UP);
        System.out.println ("VK_V=" + KeyEvent.VK_V);
        System.out.println ("VK_W=" + KeyEvent.VK_W);
        System.out.println ("VK_X=" + KeyEvent.VK_X);
        System.out.println ("VK_Y=" + KeyEvent.VK_Y);
        System.out.println ("VK_Z =" + KeyEvent.VK_Z );
	}
	
	@Test
	public void testDecodeMask () {
        int KeyModifierShift      = 0x0001;
        int KeyModifierControl    = 0x0002;
        int KeyModifierAlt        = 0x0004;
        int KeyModifierMeta       = 0x0008;
        int KeyModifierSuper      = 0x0010;
        int KeyModifierAltGr      = 0x0020;
        int KeyModifierCapsLock   = 0x1000;
        int KeyModifierNumLock    = 0x2000;
        int KeyModifierScrollLock = 0x4000;
		
		int mask = 8192; 
		
		int newMask = mask & ~(KeyModifierShift |
				KeyModifierControl |
				KeyModifierAlt |
				KeyModifierMeta |
				KeyModifierSuper);
		
		System.out.printf ("%x", mask);
		System.out.printf ("%x", newMask);
		
		int [] s_masks = 
		{ 
				0,
				KeyModifierShift,
				KeyModifierControl,
				KeyModifierAlt,
				KeyModifierMeta,
				KeyModifierSuper
		};
		
		//if ((mask & KeyModifierShift) != 0) {
		//	newMask |= s_masks []
		//}
	}
	
	private class MouseMoveThread extends Thread {
        private BlockingQueue <Point> mouseMovements =
            new LinkedBlockingQueue <Point>(1);

        public void run () {
        	Log.debug ("MoveMouseThread started");
        	
            try {
                while (true) {
                    Point nextPoint = mouseMovements.take ();
                    System.out.println ("Take x: " + nextPoint.x + " , y" + nextPoint.y);
                    Thread.sleep (5000);
                    //robot.mouseMove (nextPoint.x, nextPoint.y);
                } 

            } catch (InterruptedException ex) {
            	ex.printStackTrace ();
                Thread.currentThread ().interrupt ();
            }
        }

        public void mouseMove (Point nextPoint) {
        	try {
        		if (mouseMovements.size () >= 1) {
        			System.out.println ("Clearing");
        			mouseMovements.clear ();
        		}
        		mouseMovements.put (nextPoint);
                System.out.println ("Put x: " + nextPoint.x + " , y" + nextPoint.y);
        	} catch (InterruptedException ex) {
        		// Just ignore this mouse movement
        	}
        }
    }
	
	@Test
	public void testCapacity () {
		MouseMoveThread moveThread = new MouseMoveThread ();
		
		moveThread.start ();
		
		moveThread.mouseMove (new Point (1, 1));
		moveThread.mouseMove (new Point (1, 2));
		moveThread.mouseMove (new Point (1, 3));
		moveThread.mouseMove (new Point (1, 4));
		moveThread.mouseMove (new Point (1, 5));
		moveThread.mouseMove (new Point (1, 6));
		moveThread.mouseMove (new Point (1, 7));
		moveThread.mouseMove (new Point (1, 8));
		moveThread.mouseMove (new Point (1, 100));
		
		
	}
	
}
