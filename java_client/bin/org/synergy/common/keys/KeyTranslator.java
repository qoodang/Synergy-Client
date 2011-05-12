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
package org.synergy.common.keys;

import java.awt.event.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public abstract class KeyTranslator {

    private static int [] translationTable = new int [0xFFFF];
    private static Map <Integer, String> keyEventTranslationTable;

    static {
        // Set all to unknown
        for (int i = 0; i < translationTable.length; i++) {
            translationTable [i] = KeyEvent.VK_UNDEFINED;
        }

        // Set alphas
        //  From API: VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) -> (65 - 90) 
        //  That's A-Z, a-z is (97 - 122) and should also be mapped to VK_A
        final int NUM_ALPHA = KeyEvent.VK_Z - KeyEvent.VK_A + 1;
        final int START_A = 65;
        final int START_a = 97;
        for (int i = 0; i < NUM_ALPHA; i++) {
            translationTable [i + START_A] = KeyEvent.VK_A + i;
            translationTable [i + START_a] = KeyEvent.VK_A + i;
        }

        // Set digits
        for (int i = 0; i < 10; i++) {
            translationTable [48 + i] = KeyEvent.VK_0 + i;
        }

        // Punctuation
        translationTable [8] = KeyEvent.VK_BACK_SPACE;
        translationTable [9] = KeyEvent.VK_TAB;
        translationTable [32] = KeyEvent.VK_SPACE;

        // Straight from KeyTypes.h in the Synergy source
        // TODO: Translate everything else...


        // TTY functions
        translationTable [0xEF08] = KeyEvent.VK_BACK_SPACE;
        translationTable [0xEF09] = KeyEvent.VK_TAB;
//        translationTable [0xEF0A] = KeyEvent.VK_LINE_FEED;
        translationTable [0xEF0B] = KeyEvent.VK_CLEAR;
        translationTable [0xEF0D] = KeyEvent.VK_ENTER;
        translationTable [0xEF13] = KeyEvent.VK_PAUSE;
        translationTable [0xEF14] = KeyEvent.VK_SCROLL_LOCK;
        //translationTable [0xEF15] = KeyEvent.VK_SYSREQ; 
        translationTable [0xEF1B] = KeyEvent.VK_ESCAPE;
        //translationTable [0xEF23] = KeyEvent.VK_HENKAN;
        //translationTable [0xEF26] = KeyEvent.VK_HANGULKANA;
        translationTable [0xEF27] = KeyEvent.VK_HIRAGANA;
        //translationTable [0xEF2A] = KeyEvent.VK_ZENKAKU;
        //translationTable [0xEF2A] = KeyEvent.VK_HANJA;     // Two EF2As?
        translationTable [0xEFFF] = KeyEvent.VK_DELETE;

        // cursor control
        translationTable [0xEF50] = KeyEvent.VK_HOME;
        translationTable [0xEF51] = KeyEvent.VK_LEFT;
        translationTable [0xEF52] = KeyEvent.VK_UP;
        translationTable [0xEF53] = KeyEvent.VK_RIGHT;
        translationTable [0xEF54] = KeyEvent.VK_DOWN;
        translationTable [0xEF55] = KeyEvent.VK_PAGE_UP;
        translationTable [0xEF56] = KeyEvent.VK_PAGE_DOWN;
        translationTable [0xEF57] = KeyEvent.VK_END;
        //translationTable [0xEF58] = KeyEvent.VK_BEGIN;

        // misc functions
        /*
translationTable [0xEF60] = ; // kKeySelect
translationTable [0xEF61] = ; // kKeyPrint
translationTable [0xEF62] = ; // kKeyExecute
translationTable [0xEF63] = ; // kKeyInsert
translationTable [0xEF65] = ; // kKeyUndo
translationTable [0xEF66] = ; // kKeyRedo
translationTable [0xEF67] = ; // kKeyMenu
translationTable [0xEF68] = ; // kKeyFind
translationTable [0xEF69] = ; // kKeyCancel
translationTable [0xEF6A] = ; // kKeyHelp
translationTable [0xEF6B] = ; // kKeyBreak
translationTable [0xEF7E] = ; // kKeyAltGr
translationTable [0xEF7F] = ; // kKeyNumLock

translationTable [0xEF80] = ; // kKeyKP_Space
translationTable [0xEF89] = ; // kKeyKP_Tab
translationTable [0xEF8D] = ; // kKeyKP_Enter
translationTable [0xEF91] = ; // kKeyKP_F1
translationTable [0xEF92] = ; // kKeyKP_F2
translationTable [0xEF93] = ; // kKeyKP_F3
translationTable [0xEF94] = ; // kKeyKP_F4
translationTable [0xEF95] = ; // kKeyKP_Home
translationTable [0xEF96] = ; // kKeyKP_Left
translationTable [0xEF97] = ; // kKeyKP_Up
translationTable [0xEF98] = ; // kKeyKP_Right
translationTable [0xEF99] = ; // kKeyKP_Down
translationTable [0xEF9A] = ; // kKeyKP_PageUp
translationTable [0xEF9B] = ; // kKeyKP_PageDown
translationTable [0xEF9C] = ; // kKeyKP_End
translationTable [0xEF9D] = ; // kKeyKP_Begin
translationTable [0xEF9E] = ; // kKeyKP_Insert
translationTable [0xEF9F] = ; // kKeyKP_Delete
translationTable [0xEFBD] = ; // kKeyKP_Equal
translationTable [0xEFAA] = ; // kKeyKP_Multiply
translationTable [0xEFAB] = ; // kKeyKP_Add
translationTable [0xEFAC] = ; // kKeyKP_Separator
translationTable [0xEFAD] = ; // kKeyKP_Subtract
translationTable [0xEFAE] = ; // kKeyKP_Decimal
translationTable [0xEFAF] = ; // kKeyKP_Divide
translationTable [0xEFB0] = ; // kKeyKP_0
translationTable [0xEFB1] = ; // kKeyKP_1
translationTable [0xEFB2] = ; // kKeyKP_2
translationTable [0xEFB3] = ; // kKeyKP_3
translationTable [0xEFB4] = ; // kKeyKP_4
translationTable [0xEFB5] = ; // kKeyKP_5
translationTable [0xEFB6] = ; // kKeyKP_6
translationTable [0xEFB7] = ; // kKeyKP_7
translationTable [0xEFB8] = ; // kKeyKP_8
translationTable [0xEFB9] = ; // kKeyKP_9
*/

translationTable [0xEFBE] = KeyEvent.VK_F1; 
translationTable [0xEFBF] = KeyEvent.VK_F2; 
translationTable [0xEFC0] = KeyEvent.VK_F3; 
translationTable [0xEFC1] = KeyEvent.VK_F4; 
translationTable [0xEFC2] = KeyEvent.VK_F5; 
translationTable [0xEFC3] = KeyEvent.VK_F6; 
translationTable [0xEFC4] = KeyEvent.VK_F7; 
translationTable [0xEFC5] = KeyEvent.VK_F8; 
translationTable [0xEFC6] = KeyEvent.VK_F9; 
translationTable [0xEFC7] = KeyEvent.VK_F10; 
translationTable [0xEFC8] = KeyEvent.VK_F11; 
translationTable [0xEFC9] = KeyEvent.VK_F12; 
translationTable [0xEFCA] = KeyEvent.VK_F13; 
translationTable [0xEFCB] = KeyEvent.VK_F14; 
translationTable [0xEFCC] = KeyEvent.VK_F15; 
translationTable [0xEFCD] = KeyEvent.VK_F16; 
translationTable [0xEFCE] = KeyEvent.VK_F17; 
translationTable [0xEFCF] = KeyEvent.VK_F18; 
translationTable [0xEFD0] = KeyEvent.VK_F19; 
translationTable [0xEFD1] = KeyEvent.VK_F20; 
translationTable [0xEFD2] = KeyEvent.VK_F21; 
translationTable [0xEFD3] = KeyEvent.VK_F22; 
translationTable [0xEFD4] = KeyEvent.VK_F23; 
translationTable [0xEFD5] = KeyEvent.VK_F24; 
//translationTable [0xEFD6] = KeyEvent.VK_F25; 
//translationTable [0xEFD7] = KeyEvent.VK_F26; 
//translationTable [0xEFD8] = KeyEvent.VK_F27; 
//translationTable [0xEFD9] = KeyEvent.VK_F28; 
//translationTable [0xEFDA] = KeyEvent.VK_F29; 
//translationTable [0xEFDB] = KeyEvent.VK_F30; 
//translationTable [0xEFDC] = KeyEvent.VK_F31; 
//translationTable [0xEFDD] = KeyEvent.VK_F32; 
//translationTable [0xEFDE] = KeyEvent.VK_F33; 
//translationTable [0xEFDF] = KeyEvent.VK_F34; 
//translationTable [0xEFE0] = KeyEvent.VK_F35; 

// modifiers
translationTable [0xEFE1] = KeyEvent.VK_SHIFT | KeyEvent.KEY_LOCATION_LEFT; 
translationTable [0xEFE2] = KeyEvent.VK_SHIFT | KeyEvent.KEY_LOCATION_RIGHT;
//translationTable [0xEFE3] = ; // kKeyControl_L
//translationTable [0xEFE4] = ; // kKeyControl_R
//translationTable [0xEFE5] = ; // kKeyCapsLock
//translationTable [0xEFE6] = ; // kKeyShiftLock
//translationTable [0xEFE7] = ; // kKeyMeta_L
//translationTable [0xEFE8] = ; // kKeyMeta_R
//translationTable [0xEFE9] = ; // kKeyAlt_L
//translationTable [0xEFEA] = ; // kKeyAlt_R
//translationTable [0xEFEB] = ; // kKeySuper_L
//translationTable [0xEFEC] = ; // kKeySuper_R
//translationTable [0xEFED] = ; // kKeyHyper_L
//translationTable [0xEFEE] = ; // kKeyHyper_R


        // Others


        setupKeyEventTranslationTable ();
    }



    /**
      * Translate a key code from Synergy to a KeyEvent.VK_
     */
    public static int translateKey (final int keyCode) {
        return translationTable [keyCode];
    }


    

    /**
      * A utility function that will take an integer value
      *  and convert it to the string representation of
      *  a KeyEvent.VK_
      *
      * Not sure if this will be useful -- maybe for debugging
      */
    public static String keyEventToString (final int keyEvent) throws IllegalArgumentException {
        if (keyEventTranslationTable.containsKey (Integer.valueOf (keyEvent))) {
            return keyEventTranslationTable.get (Integer.valueOf (keyEvent));
        } else {
            throw new IllegalArgumentException ("Key not found: " + keyEvent); 
        } 

    }

    /**
      * Set up a KeyEvent to String map
      */
    private static void setupKeyEventTranslationTable () {
        keyEventTranslationTable = new HashMap <Integer, String> ();

        keyEventTranslationTable.put (KeyEvent.KEY_FIRST, "KEY_FIRST");
        keyEventTranslationTable.put (KeyEvent.KEY_LAST, "KEY_LAST");
        keyEventTranslationTable.put (KeyEvent.KEY_LOCATION_LEFT, "KEY_LOCATION_LEFT");
        keyEventTranslationTable.put (KeyEvent.KEY_LOCATION_NUMPAD, "KEY_LOCATION_NUMPAD");
        keyEventTranslationTable.put (KeyEvent.KEY_LOCATION_RIGHT, "KEY_LOCATION_RIGHT");
        keyEventTranslationTable.put (KeyEvent.KEY_LOCATION_STANDARD, "KEY_LOCATION_STANDARD");
        keyEventTranslationTable.put (KeyEvent.KEY_LOCATION_UNKNOWN, "KEY_LOCATION_UNKNOWN");
        keyEventTranslationTable.put (KeyEvent.KEY_PRESSED, "KEY_PRESSED");
        keyEventTranslationTable.put (KeyEvent.KEY_RELEASED, "KEY_RELEASED");
        keyEventTranslationTable.put (KeyEvent.KEY_TYPED, "KEY_TYPED");
        keyEventTranslationTable.put (KeyEvent.VK_0, "VK_0");
        keyEventTranslationTable.put (KeyEvent.VK_1, "VK_1");
        keyEventTranslationTable.put (KeyEvent.VK_2, "VK_2");
        keyEventTranslationTable.put (KeyEvent.VK_3, "VK_3");
        keyEventTranslationTable.put (KeyEvent.VK_4, "VK_4");
        keyEventTranslationTable.put (KeyEvent.VK_5, "VK_5");
        keyEventTranslationTable.put (KeyEvent.VK_6, "VK_6");
        keyEventTranslationTable.put (KeyEvent.VK_7, "VK_7");
        keyEventTranslationTable.put (KeyEvent.VK_8, "VK_8");
        keyEventTranslationTable.put (KeyEvent.VK_9, "VK_9");
        keyEventTranslationTable.put (KeyEvent.VK_A, "VK_A");
        keyEventTranslationTable.put (KeyEvent.VK_ACCEPT, "VK_ACCEPT");
        keyEventTranslationTable.put (KeyEvent.VK_ADD, "VK_ADD");
        keyEventTranslationTable.put (KeyEvent.VK_AGAIN, "VK_AGAIN");
        keyEventTranslationTable.put (KeyEvent.VK_ALL_CANDIDATES, "VK_ALL_CANDIDATES");
        keyEventTranslationTable.put (KeyEvent.VK_ALPHANUMERIC, "VK_ALPHANUMERIC");
        keyEventTranslationTable.put (KeyEvent.VK_ALT, "VK_ALT");
        keyEventTranslationTable.put (KeyEvent.VK_ALT_GRAPH, "VK_ALT_GRAPH");
        keyEventTranslationTable.put (KeyEvent.VK_AMPERSAND, "VK_AMPERSAND");
        keyEventTranslationTable.put (KeyEvent.VK_ASTERISK, "VK_ASTERISK");
        keyEventTranslationTable.put (KeyEvent.VK_AT, "VK_AT");
        keyEventTranslationTable.put (KeyEvent.VK_B, "VK_B");
        keyEventTranslationTable.put (KeyEvent.VK_BACK_QUOTE, "VK_BACK_QUOTE");
        keyEventTranslationTable.put (KeyEvent.VK_BACK_SLASH, "VK_BACK_SLASH");
        keyEventTranslationTable.put (KeyEvent.VK_BACK_SPACE, "VK_BACK_SPACE");
        keyEventTranslationTable.put (KeyEvent.VK_BRACELEFT, "VK_BRACELEFT");
        keyEventTranslationTable.put (KeyEvent.VK_BRACERIGHT, "VK_BRACERIGHT");
        keyEventTranslationTable.put (KeyEvent.VK_C, "VK_C");
        keyEventTranslationTable.put (KeyEvent.VK_CANCEL, "VK_CANCEL");
        keyEventTranslationTable.put (KeyEvent.VK_CAPS_LOCK, "VK_CAPS_LOCK");
        keyEventTranslationTable.put (KeyEvent.VK_CIRCUMFLEX, "VK_CIRCUMFLEX");
        keyEventTranslationTable.put (KeyEvent.VK_CLEAR, "VK_CLEAR");
        keyEventTranslationTable.put (KeyEvent.VK_CLOSE_BRACKET, "VK_CLOSE_BRACKET");
        keyEventTranslationTable.put (KeyEvent.VK_CODE_INPUT, "VK_CODE_INPUT");
        keyEventTranslationTable.put (KeyEvent.VK_COLON, "VK_COLON");
        keyEventTranslationTable.put (KeyEvent.VK_COMMA, "VK_COMMA");
        keyEventTranslationTable.put (KeyEvent.VK_COMPOSE, "VK_COMPOSE");
        keyEventTranslationTable.put (KeyEvent.VK_CONTROL, "VK_CONTROL");
        keyEventTranslationTable.put (KeyEvent.VK_CONVERT, "VK_CONVERT");
        keyEventTranslationTable.put (KeyEvent.VK_COPY, "VK_COPY");
        keyEventTranslationTable.put (KeyEvent.VK_CUT, "VK_CUT");
        keyEventTranslationTable.put (KeyEvent.VK_D, "VK_D");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_ABOVEDOT, "VK_DEAD_ABOVEDOT");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_ABOVERING, "VK_DEAD_ABOVERING");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_ACUTE, "VK_DEAD_ACUTE");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_BREVE, "VK_DEAD_BREVE");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_CARON, "VK_DEAD_CARON");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_CEDILLA, "VK_DEAD_CEDILLA");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_CIRCUMFLEX, "VK_DEAD_CIRCUMFLEX");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_DIAERESIS, "VK_DEAD_DIAERESIS");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_DOUBLEACUTE, "VK_DEAD_DOUBLEACUTE");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_GRAVE, "VK_DEAD_GRAVE");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_IOTA, "VK_DEAD_IOTA");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_MACRON, "VK_DEAD_MACRON");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_OGONEK, "VK_DEAD_OGONEK");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_SEMIVOICED_SOUND, "VK_DEAD_SEMIVOICED_SOUND");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_TILDE, "VK_DEAD_TILDE");
        keyEventTranslationTable.put (KeyEvent.VK_DEAD_VOICED_SOUND, "VK_DEAD_VOICED_SOUND");
        keyEventTranslationTable.put (KeyEvent.VK_DECIMAL, "VK_DECIMAL");
        keyEventTranslationTable.put (KeyEvent.VK_DELETE, "VK_DELETE");
        keyEventTranslationTable.put (KeyEvent.VK_DIVIDE, "VK_DIVIDE");
        keyEventTranslationTable.put (KeyEvent.VK_DOLLAR, "VK_DOLLAR");
        keyEventTranslationTable.put (KeyEvent.VK_DOWN, "VK_DOWN");
        keyEventTranslationTable.put (KeyEvent.VK_E, "VK_E");
        keyEventTranslationTable.put (KeyEvent.VK_END, "VK_END");
        keyEventTranslationTable.put (KeyEvent.VK_ENTER, "VK_ENTER");
        keyEventTranslationTable.put (KeyEvent.VK_EQUALS, "VK_EQUALS");
        keyEventTranslationTable.put (KeyEvent.VK_ESCAPE, "VK_ESCAPE");
        keyEventTranslationTable.put (KeyEvent.VK_EURO_SIGN, "VK_EURO_SIGN");
        keyEventTranslationTable.put (KeyEvent.VK_EXCLAMATION_MARK, "VK_EXCLAMATION_MARK");
        keyEventTranslationTable.put (KeyEvent.VK_F, "VK_F");
        keyEventTranslationTable.put (KeyEvent.VK_F1, "VK_F1");
        keyEventTranslationTable.put (KeyEvent.VK_F10, "VK_F10");
        keyEventTranslationTable.put (KeyEvent.VK_F11, "VK_F11");
        keyEventTranslationTable.put (KeyEvent.VK_F12, "VK_F12");
        keyEventTranslationTable.put (KeyEvent.VK_F13, "VK_F13");
        keyEventTranslationTable.put (KeyEvent.VK_F14, "VK_F14");
        keyEventTranslationTable.put (KeyEvent.VK_F15, "VK_F15");
        keyEventTranslationTable.put (KeyEvent.VK_F16, "VK_F16");
        keyEventTranslationTable.put (KeyEvent.VK_F17, "VK_F17");
        keyEventTranslationTable.put (KeyEvent.VK_F18, "VK_F18");
        keyEventTranslationTable.put (KeyEvent.VK_F19, "VK_F19");
        keyEventTranslationTable.put (KeyEvent.VK_F2, "VK_F2");
        keyEventTranslationTable.put (KeyEvent.VK_F20, "VK_F20");
        keyEventTranslationTable.put (KeyEvent.VK_F21, "VK_F21");
        keyEventTranslationTable.put (KeyEvent.VK_F22, "VK_F22");
        keyEventTranslationTable.put (KeyEvent.VK_F23, "VK_F23");
        keyEventTranslationTable.put (KeyEvent.VK_F24, "VK_F24");
        keyEventTranslationTable.put (KeyEvent.VK_F3, "VK_F3");
        keyEventTranslationTable.put (KeyEvent.VK_F4, "VK_F4");
        keyEventTranslationTable.put (KeyEvent.VK_F5, "VK_F5");
        keyEventTranslationTable.put (KeyEvent.VK_F6, "VK_F6");
        keyEventTranslationTable.put (KeyEvent.VK_F7, "VK_F7");
        keyEventTranslationTable.put (KeyEvent.VK_F8, "VK_F8");
        keyEventTranslationTable.put (KeyEvent.VK_F9, "VK_F9");
        keyEventTranslationTable.put (KeyEvent.VK_FINAL, "VK_FINAL");
        keyEventTranslationTable.put (KeyEvent.VK_FIND, "VK_FIND");
        keyEventTranslationTable.put (KeyEvent.VK_FULL_WIDTH, "VK_FULL_WIDTH");
        keyEventTranslationTable.put (KeyEvent.VK_G, "VK_G");
        keyEventTranslationTable.put (KeyEvent.VK_GREATER, "VK_GREATER");
        keyEventTranslationTable.put (KeyEvent.VK_H, "VK_H");
        keyEventTranslationTable.put (KeyEvent.VK_HALF_WIDTH, "VK_HALF_WIDTH");
        keyEventTranslationTable.put (KeyEvent.VK_HELP, "VK_HELP");
        keyEventTranslationTable.put (KeyEvent.VK_HIRAGANA, "VK_HIRAGANA");
        keyEventTranslationTable.put (KeyEvent.VK_HOME, "VK_HOME");
        keyEventTranslationTable.put (KeyEvent.VK_I, "VK_I");
        keyEventTranslationTable.put (KeyEvent.VK_INPUT_METHOD_ON_OFF, "VK_INPUT_METHOD_ON_OFF");
        keyEventTranslationTable.put (KeyEvent.VK_INSERT, "VK_INSERT");
        keyEventTranslationTable.put (KeyEvent.VK_INVERTED_EXCLAMATION_MARK, "VK_INVERTED_EXCLAMATION_MARK");
        keyEventTranslationTable.put (KeyEvent.VK_J, "VK_J");
        keyEventTranslationTable.put (KeyEvent.VK_JAPANESE_HIRAGANA, "VK_JAPANESE_HIRAGANA");
        keyEventTranslationTable.put (KeyEvent.VK_JAPANESE_KATAKANA, "VK_JAPANESE_KATAKANA");
        keyEventTranslationTable.put (KeyEvent.VK_JAPANESE_ROMAN, "VK_JAPANESE_ROMAN");
        keyEventTranslationTable.put (KeyEvent.VK_K, "VK_K");
        keyEventTranslationTable.put (KeyEvent.VK_KANA, "VK_KANA");
        keyEventTranslationTable.put (KeyEvent.VK_KANA_LOCK, "VK_KANA_LOCK");
        keyEventTranslationTable.put (KeyEvent.VK_KANJI, "VK_KANJI");
        keyEventTranslationTable.put (KeyEvent.VK_KATAKANA, "VK_KATAKANA");
        keyEventTranslationTable.put (KeyEvent.VK_KP_DOWN, "VK_KP_DOWN");
        keyEventTranslationTable.put (KeyEvent.VK_KP_LEFT, "VK_KP_LEFT");
        keyEventTranslationTable.put (KeyEvent.VK_KP_RIGHT, "VK_KP_RIGHT");
        keyEventTranslationTable.put (KeyEvent.VK_KP_UP, "VK_KP_UP");
        keyEventTranslationTable.put (KeyEvent.VK_L, "VK_L");
        keyEventTranslationTable.put (KeyEvent.VK_LEFT, "VK_LEFT");
        keyEventTranslationTable.put (KeyEvent.VK_LEFT_PARENTHESIS, "VK_LEFT_PARENTHESIS");
        keyEventTranslationTable.put (KeyEvent.VK_LESS, "VK_LESS");
        keyEventTranslationTable.put (KeyEvent.VK_M, "VK_M");
        keyEventTranslationTable.put (KeyEvent.VK_META, "VK_META");
        keyEventTranslationTable.put (KeyEvent.VK_MINUS, "VK_MINUS");
        keyEventTranslationTable.put (KeyEvent.VK_MODECHANGE, "VK_MODECHANGE");
        keyEventTranslationTable.put (KeyEvent.VK_MULTIPLY, "VK_MULTIPLY");
        keyEventTranslationTable.put (KeyEvent.VK_N, "VK_N");
        keyEventTranslationTable.put (KeyEvent.VK_NONCONVERT, "VK_NONCONVERT");
        keyEventTranslationTable.put (KeyEvent.VK_NUM_LOCK, "VK_NUM_LOCK");
        keyEventTranslationTable.put (KeyEvent.VK_NUMBER_SIGN, "VK_NUMBER_SIGN");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD0, "VK_NUMPAD0");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD1, "VK_NUMPAD1");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD2, "VK_NUMPAD2");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD3, "VK_NUMPAD3");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD4, "VK_NUMPAD4");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD5, "VK_NUMPAD5");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD6, "VK_NUMPAD6");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD7, "VK_NUMPAD7");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD8, "VK_NUMPAD8");
        keyEventTranslationTable.put (KeyEvent.VK_NUMPAD9, "VK_NUMPAD9");
        keyEventTranslationTable.put (KeyEvent.VK_O, "VK_O");
        keyEventTranslationTable.put (KeyEvent.VK_OPEN_BRACKET, "VK_OPEN_BRACKET");
        keyEventTranslationTable.put (KeyEvent.VK_P, "VK_P");
        keyEventTranslationTable.put (KeyEvent.VK_PAGE_DOWN, "VK_PAGE_DOWN");
        keyEventTranslationTable.put (KeyEvent.VK_PAGE_UP, "VK_PAGE_UP");
        keyEventTranslationTable.put (KeyEvent.VK_PASTE, "VK_PASTE");
        keyEventTranslationTable.put (KeyEvent.VK_PAUSE, "VK_PAUSE");
        keyEventTranslationTable.put (KeyEvent.VK_PERIOD, "VK_PERIOD");
        keyEventTranslationTable.put (KeyEvent.VK_PLUS, "VK_PLUS");
        keyEventTranslationTable.put (KeyEvent.VK_PREVIOUS_CANDIDATE, "VK_PREVIOUS_CANDIDATE");
        keyEventTranslationTable.put (KeyEvent.VK_PRINTSCREEN, "VK_PRINTSCREEN");
        keyEventTranslationTable.put (KeyEvent.VK_PROPS, "VK_PROPS");
        keyEventTranslationTable.put (KeyEvent.VK_Q, "VK_Q");
        keyEventTranslationTable.put (KeyEvent.VK_QUOTE, "VK_QUOTE");
        keyEventTranslationTable.put (KeyEvent.VK_QUOTEDBL, "VK_QUOTEDBL");
        keyEventTranslationTable.put (KeyEvent.VK_R, "VK_R");
        keyEventTranslationTable.put (KeyEvent.VK_RIGHT, "VK_RIGHT");
        keyEventTranslationTable.put (KeyEvent.VK_RIGHT_PARENTHESIS, "VK_RIGHT_PARENTHESIS");
        keyEventTranslationTable.put (KeyEvent.VK_ROMAN_CHARACTERS, "VK_ROMAN_CHARACTERS");
        keyEventTranslationTable.put (KeyEvent.VK_S, "VK_S");
        keyEventTranslationTable.put (KeyEvent.VK_SCROLL_LOCK, "VK_SCROLL_LOCK");
        keyEventTranslationTable.put (KeyEvent.VK_SEMICOLON, "VK_SEMICOLON");
        keyEventTranslationTable.put (KeyEvent.VK_SEPARATER, "VK_SEPARATER");
        keyEventTranslationTable.put (KeyEvent.VK_SEPARATOR, "VK_SEPARATOR");
        keyEventTranslationTable.put (KeyEvent.VK_SHIFT, "VK_SHIFT");
        keyEventTranslationTable.put (KeyEvent.VK_SLASH, "VK_SLASH");
        keyEventTranslationTable.put (KeyEvent.VK_SPACE, "VK_SPACE");
        keyEventTranslationTable.put (KeyEvent.VK_STOP, "VK_STOP");
        keyEventTranslationTable.put (KeyEvent.VK_SUBTRACT, "VK_SUBTRACT");
        keyEventTranslationTable.put (KeyEvent.VK_T, "VK_T");
        keyEventTranslationTable.put (KeyEvent.VK_TAB, "VK_TAB");
        keyEventTranslationTable.put (KeyEvent.VK_U, "VK_U");
        keyEventTranslationTable.put (KeyEvent.VK_UNDEFINED, "VK_UNDEFINED");
        keyEventTranslationTable.put (KeyEvent.VK_UNDERSCORE, "VK_UNDERSCORE");
        keyEventTranslationTable.put (KeyEvent.VK_UNDO, "VK_UNDO");
        keyEventTranslationTable.put (KeyEvent.VK_UP, "VK_UP");
        keyEventTranslationTable.put (KeyEvent.VK_V, "VK_V");
        keyEventTranslationTable.put (KeyEvent.VK_W, "VK_W");
        keyEventTranslationTable.put (KeyEvent.VK_X, "VK_X");
        keyEventTranslationTable.put (KeyEvent.VK_Y, "VK_Y");
        keyEventTranslationTable.put (KeyEvent.VK_Z , "VK_Z");

    }
}
