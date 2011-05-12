package org.synergy.base;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;


public class EventQueueTimerTest {

	@Test
	public void testBasicTimer () {
		try {
			EventQueueTimer timer = new EventQueueTimer (2.0, false, this, 
					new EventJobInterface() {
						public void run (Event event) {
							System.out.println ("Event fired");
						}
					});
			
			Event event = new Event ();
			Event eventFromQueue = EventQueue.getInstance ().getEvent (event, -1.0);
			System.out.println ("Got Event: " + eventFromQueue.getType ());		
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
	
}
