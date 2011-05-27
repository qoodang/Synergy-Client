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
package org.synergy.common.screens;

import org.synergy.base.Log;
import org.synergy.injection.Injection;

import android.graphics.Rect;

public class BasicScreen implements PlatformScreenInterface{
	
	// Keep track of the mouse cursor since I cannot find a way of
	//  determining the current mouse position
	private int mouseX = -1;
	private int mouseY = -1;
	
	// Screen dimensions
	private int width;
	private int height;
	
    private int count = 0;
	

    public BasicScreen () {
    	mouseX = -1;
    	mouseY = -1;
    }

    /**
     * Set the shape of the screen -- set from the initializing activity
     * @param width
     * @param height
     */
    public void setShape (int width, int height) {
    	this.width = width;
    	this.height = height;
    }
    
    public Rect getShape () {
    	return new Rect (0, 0, width, height);
    }
    
	public void updateKeyMap () {
		// TODO Auto-generated method stub
		
	}

	
	public void updateKeyState () {
		// TODO Auto-generated method stub
		
	}

	
	public void setHalfDuplexMask (Integer keyModifierMask) {
		// TODO Auto-generated method stub
		
	}

	
	public void fakeAllKeysUp () {
		// TODO Auto-generated method stub
		
	}

	
	public void fakeCtrlAltDel () {
		// TODO Auto-generated method stub
		
	}
	
	public void fakeKeyDown (int id, int mask) {
		//keyThread.keyDown(id, mask);
		//keyThread.keyUp(id, mask);
		
		Injection.keydown(id, mask);
		
		// This stops keys from repeating...but it doesn't seem like the correct thing to do
		//Injection.keyup(id, mask);
	}
	
	public void fakeKeyUp (int id, int mask) {
		Injection.keyup(id, mask);
	}

	public void fakeMouseButton (int buttonID, boolean press) {
		// TODO Auto-generated method stub
		if (press) {
			Injection.mousedown (buttonID);
		} else {
			Injection.mouseup (buttonID);
		}
	}

	
	public final void fakeMouseMove (int x, int y) {
		count++;
        Log.debug2 ("fakeMouseMove: " + x + ", " + y);
//        if (count % 3 != 0) {
//        	// Because my G1 phone is so slow... I'm skipping every other mouse movement
//        	return;
//        }
  
        
        if (mouseX < 0 || mouseY < 0) {
        	// initial mouse coordinates unknown
        	// So my hack to "determine" the mouse position is to just shove
        	//  the mouse cursor to the top left screen. Then keep track of
        	//  all the relative mouse movements. 
        	Injection.movemouse (-width, -height);
        	mouseX = 0;
        	mouseY = 0;
        }
        
        int dx = x - mouseX;
    	int dy = y - mouseY;
    	
    	//injection.moveMouseRel (dx, dy);
    	  	Injection.movemouse (dx, dy);
        
    	// Adjust known cursor position
        mouseX += dx;
        mouseY += dy;
        
        if (mouseX < 0) 
        	mouseX = 0;
        if (mouseX > width) 
        	mouseX = width;
        if (mouseY < 0) 
        	mouseY = 0;
        if (mouseY > height)
        	mouseY = height;
	}

	
	public void fakeRelativeMouseMove (int x, int y) {
		Injection.movemouse (x, y);
	}

	
	public void fakeMouseWheel (int x, int y) {
		// TODO Auto-generated method stub
		
	}
	

	
	public void enable () {
		// TODO Auto-generated method stub
		
	}

	public void disable () {
		// TODO Auto-generated method stub
		
	}

	
	public void enter () {
		// TODO Auto-generated method stub
		
	}

	
	public boolean leave () {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void checkClipboards () {
		// TODO Auto-generated method stub
		
	}

	
	public void openScreensaver (boolean notify) {
		// TODO Auto-generated method stub
		
	}

	
	public void closeScreensaver () {
		// TODO Auto-generated method stub
		
	}

	
	public void screensaver (boolean activate) {
		// TODO Auto-generated method stub
		
	}

	
	public void resetOptions () {
		// TODO Auto-generated method stub
		
	}

	
	public void setSequenceNumber (int seqNum) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean isPrimary () {
		// TODO Auto-generated method stub
		return false;
	}

}
