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

import org.synergy.common.keys.KeyStateInterface;

import android.graphics.Rect;

public interface PlatformScreenInterface extends SecondaryScreenInterface, KeyStateInterface {
    void enable();

    void disable();

    void enter();

    boolean leave();
    
    Rect getShape ();

    //boolean setClipboard(ClipboardID id,  IClipboard*);

    void checkClipboards();

    void openScreensaver(boolean notify);

    void closeScreensaver();

    void screensaver(boolean activate);

    void resetOptions();

    //void setOptions( COptionsList& options);

    void setSequenceNumber(int seqNum);

    boolean isPrimary() ;
}
