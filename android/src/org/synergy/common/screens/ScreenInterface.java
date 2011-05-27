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

import android.graphics.*;

public interface ScreenInterface {

	public Object getEventTarget ();

	public boolean getClipboard ();
	
	public Rect getShape ();
	
	public Point getCursorPos ();
	
	public void enable ();
	
	public void disable ();
	
	public void enter (int toggleMask);
	
	public boolean leave ();
	
	public boolean setClipboard ();
	
	public boolean checkClipboards ();
	
	public void openScreensaver (boolean notify);
	
	public void closeScreensaver ();
	
	public void screenSaver (boolean activate);
	
	public void resetOptions ();
	
	public void setOptions ();
	
	public void setSequenceNumber (Integer sequenceNumber);
	
	public boolean isPrimary ();
	
	public void keyDown (int keyEventID, int mask);

	public void keyUp (int keyEventID, int mask);

	public void keyRepeat (int keyEventID, int mask);

	public void mouseDown (int buttonID);

	public void mouseUp (int buttonID);

	public void mouseMove (int x, int y);

	public void mouseRelativeMove (int x, int y);

	public void mouseWheel ();

}
