package chartsupport;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

public class Console extends DataListener{

	/**
	 * Console implementation
	 * @author alex
	 * 
	 * prints out every event passed to the DataListener 
	 * 
	 * */
	
	
	@Override
	void update(EventBean event, String[] names, EventType eType) {
		for(String name: names){
		    	System.out.println(name+" = "+event.get(name));
		     }
	}
}
