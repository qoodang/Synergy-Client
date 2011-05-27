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
package org.synergy.io;

public class ProtocolUtil {

	/**
	 * @param stream
	 * @param str1 Expected string
	 * @param short1 returned short
	 * @param short2 returned short
	 * @throws IOException
	 */
	/*public static void read (Stream stream, String str1, short short1, short short2) throws IOException {
		DataInputStream din = new DataInputStream (stream.getInputStream ());
		
		// Read the packet size
		int packetSize = din.readInt ();
		Log.debug5 ("Packet Size: " + packetSize);
		
		// Now read the bytes
		byte readBytes [] = new byte [packetSize];
		din.read (readBytes, 0, packetSize);
		ByteBuffer byteBuffer = ByteBuffer.wrap(readBytes);
		
		readString (byteBuffer, str1);
		
		short1 = byteBuffer.getShort();
		short2 = byteBuffer.getShort();
		
		System.out.println ("short1: " + short1);
	} *
	
	private static void readString (DataInputStream din, String str1) {
		ByteBuffer byteBuffer
		
		byte stringText [] = new byte [str1.length ()];
		byteBuffer.get (stringText, 0, str1.length());
		String readString = new String (stringText);
		
		assert (readString.equals(str1));
	} */
}
