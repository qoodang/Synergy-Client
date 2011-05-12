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
package org.synergy.client;

import java.awt.Point;
import java.awt.Rectangle;

public class ClientInfo {

	Rectangle screenPosition;
	Point cursorPos;

	public ClientInfo(Rectangle screenPosition, Point cursorPos) {
		this.screenPosition = screenPosition;
		this.cursorPos = cursorPos;
	}

	public Rectangle getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(Rectangle screenPosition) {
		this.screenPosition = screenPosition;
	}

	public Point getCursorPos() {
		return cursorPos;
	}

	public void setCursorPos(Point cursorPos) {
		this.cursorPos = cursorPos;
	}
}
