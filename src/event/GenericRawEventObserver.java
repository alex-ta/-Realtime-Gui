package event;
import java.util.*;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.time.CurrentTimeSpanEvent;




public class GenericRawEventObserver implements Observer  {
	
	// nothing changed here
	
	private EPServiceProvider epService;
	private long lastTimestamp = 0;
	private long speedupfactor = 300000000;
	
	private long timeSerializionOffset = 0;
	
	public GenericRawEventObserver(EPServiceProvider epService) {
		this.epService = epService;
	}	

	public GenericRawEventObserver(EPServiceProvider epService, long speedupfactor) {
		this.epService = epService;
		this.speedupfactor = speedupfactor;
		if(speedupfactor == -1) speedupfactor = 100000000;
	}		
	
	public void update(Observable o, Object arg) {
		
		TaxiTripRawEvent event = (TaxiTripRawEvent) arg;					
		long eventTimeInMillis = event.getLongTime();
		
				
		if(lastTimestamp != 0) { //advance time
						
			if(lastTimestamp == eventTimeInMillis) {
				timeSerializionOffset++;
			} else {
				timeSerializionOffset = 0;
			}			

			//workaround for unsorted replay files
			if(lastTimestamp > eventTimeInMillis) {
				eventTimeInMillis = lastTimestamp;
			}
												
			try {
				Thread.sleep((eventTimeInMillis - lastTimestamp) / speedupfactor);
			} catch (Exception e) {System.err.println("Thread.sleep does not work :-( " + e.toString() + ", " + e.getMessage());}
			
			//update time						
			CurrentTimeSpanEvent timeEvent = new CurrentTimeSpanEvent(eventTimeInMillis+timeSerializionOffset);
			epService.getEPRuntime().sendEvent(timeEvent);
							
			
			//send event
			epService.getEPRuntime().sendEvent(arg);
			
			lastTimestamp = eventTimeInMillis;
		
		} else { //init time
			
			lastTimestamp = eventTimeInMillis;
			
		}
		
		
	}	
	
}
