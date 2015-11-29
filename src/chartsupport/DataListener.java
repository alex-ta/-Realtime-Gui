package chartsupport;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.UpdateListener;

public abstract class DataListener implements UpdateListener{
	
	/**
	 * DataListener implementation
	 * @author alex
	 * 
	 * abstract class which gives basic support on the
	 * UpdateListener Interface, such as the validation 
	 * and looping.
	 * */
	
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvent) {
		if(newEvents == null) {
			return;
		}
		
		this.clear();
		
		for(int e = 0; e < newEvents.length; e++) {
			EventBean event = newEvents[e];
	        EventType eType = event.getEventType();
	        String[] names = eType.getPropertyNames();
	        if(names.length >= 0) {
	        	update(event,names,eType);
	        } else {
	        	return;
	        }	
	 	      	             
		}        
	}
	
	/**Methods that get called in the update Methode*/
	abstract void update(EventBean event, String[] names, EventType eType);
	protected void clear(){};
}
