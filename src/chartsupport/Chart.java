package chartsupport;
import java.awt.Component;
import java.util.HashMap;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

public abstract class Chart extends DataListener{
	
	/**
	 * Chart implementation
	 * @author alex
	 * 
	 * Data gets processed, so it can be displayed in a Chart.
	 * */
	
	/**Time gets converted into an intern Time ticker*/
	private HashMap<String,Long> count = new HashMap<String, Long>();	
	
	protected Point parsePoint(String k,String v, String time) {
		/**
		 * Methode that parses the input Stream to real Data.
		 * Exceptions get catched.
		 * Default values get set.
		 * */
		
		long counter = 0L;
		
		if(time != ""){
			try{
				counter = Long.parseLong(time);
			}catch(Exception e){
				// Numberformat Exception
			}
		}else{
			if(count.get(k) != null){
				counter += 1+count.get(k);
			}
			count.put(k,counter);
		}
		
		double value = 0;
		try{
			value = Double.parseDouble(v);
		}catch(Exception e){
			// Numberformat Exception
		}
		
		Point p = new Point(value,counter);
		return p;
	}
	
	protected class Point{
		/**
		 * ValueObject that can hold an x and y value.
		 * */
		public Point (double y, long x){
			this.y =y;
			this.x =x;
		}
		
		double y;
		long x;
		public double getY() {
			return y;
		}
		public long getX() {
			return x;
		}
	}


	@Override
	protected void update(EventBean event, String[] names, EventType eType) {
		/**
		 * Implementation preparing the Data
		 * */
		if(names.length >= 0) {
         	for(int i = 0; i < names.length; i++) {
        		Point p = parsePoint(names[i],event.get(names[i])+"","");
        		repaint(names[i], p.getY(), p.getX());
        	}
		}
	}

	/**
	 * Method that gets called with single line Data
	 * */
	public abstract void repaint(String string, double y, long x);

	/**
	 * Method that gets called from the gui
	 * */
	public abstract Component getChart();
}
