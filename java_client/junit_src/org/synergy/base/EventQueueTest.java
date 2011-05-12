package org.synergy.base;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.synergy.base.Event;
import org.synergy.net.DataSocketInterface;


public class EventQueueTest {

	@Test
	public void testAdoptHandler () {
		EventQueue.getInstance().adoptHandler (EventType.SOCKET_CONNECTED, this, 
				new EventJobInterface() {
					public void run(Event event) {
						System.out.println ("Test");
					}});
		
		EventJobInterface job = EventQueue.getInstance().getHandler(EventType.SOCKET_CONNECTED, this);
		assertNotNull (job);
			
		Event event = new Event (EventType.SOCKET_CONNECTED, this);

		
		EventQueue.getInstance().addEvent(event);
		
		EventQueue.getInstance().dispatchEvent (event);
	}
	
	@Test
	public void testAddEvent () {
		EventQueue.getInstance ().addEvent (new Event (EventType.STREAM_INPUT_READY));
		EventQueue.getInstance ().addEvent (new Event (EventType.SOCKET_CONNECTED));
		
		try {
			Event event = new Event ();
			event = EventQueue.getInstance ().getEvent (event, -1.0);
			Log.note (event.toString ());
			event = EventQueue.getInstance ().getEvent (event, -1.0);
			Log.note (event.toString ());
		} catch (Exception e) {
			e.printStackTrace ();
		}
		
	}
	
}
