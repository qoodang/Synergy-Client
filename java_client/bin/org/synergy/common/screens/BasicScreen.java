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

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.synergy.base.Log;

public class BasicScreen implements PlatformScreenInterface {

    Robot robot;

    private class MouseMoveThread extends Thread {
    	private static final int MAX_SIZE = 26;
    	
        private BlockingQueue <Point> mouseMovements =
            new LinkedBlockingQueue <Point>(MAX_SIZE);

        public void run () {
        	Log.debug ("MoveMouseThread started");
        	
            try {
                while (true) {
                    Point nextPoint = mouseMovements.take ();
                    robot.mouseMove (nextPoint.x, nextPoint.y);
                } 

            } catch (InterruptedException ex) {
            	ex.printStackTrace ();
                Thread.currentThread ().interrupt ();
            }
        }

        public void mouseMove (Point nextPoint) {
        	try {
                /**
                  * Erase all the mouse movements if the user
                  *  makes a lot of quick mouse movements...
                 */
        		if (mouseMovements.size () >= MAX_SIZE) {
        			/*for (int i = 0; i < MAX_SIZE / 2; i++) {
        				mouseMovements.remove ();
        			}*/
        			//System.out.println ("CLEAR");
        			mouseMovements.clear ();
        		}
        		mouseMovements.put (nextPoint);
        	} catch (InterruptedException ex) {
        		// Just ignore this mouse movement
        	}
        }
    }
    MouseMoveThread moveThread;

    public BasicScreen () {
       moveThread = new MouseMoveThread ();
       moveThread.start ();

       try {
           robot = new Robot ();
       } catch (AWTException e) {
           e.printStackTrace ();
       }
    }


	@Override
	public void updateKeyMap () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateKeyState () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHalfDuplexMask (Integer keyModifierMask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fakeAllKeysUp () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fakeCtrlAltDel () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fakeMouseButton (int buttonID, boolean press) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fakeMouseMove (int x, int y) {
        Log.debug2 ("fakeMouseMove: " + x + ", " + y);
        moveThread.mouseMove (new Point (x, y));
	}

	@Override
	public void fakeRelativeMouseMove (int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fakeMouseWheel (int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean leave () {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkClipboards () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openScreensaver (boolean notify) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeScreensaver () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screensaver (boolean activate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetOptions () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSequenceNumber (int seqNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPrimary () {
		// TODO Auto-generated method stub
		return false;
	}

}
