package reader;
import java.util.Calendar;
import java.util.Observable;

import chartsupport.SourceInterface;
import event.TaxiTripRawEvent;

import java.io.*;


public class TaxiTripFileReplay extends Observable implements SourceInterface {

	/**
	 * TaxiTripFileReplay implementation
	 * 
	 * implemnts SourceInterface, so the Data can be stopped or continued.
	 * */
	
	private String path;
	private boolean run = false;
	
	
	public TaxiTripFileReplay(String path) {
		this.path = path;
	}
	
	@Override
	public void run() {
		
    	Calendar calStart = Calendar.getInstance();
        long startTime = calStart.getTimeInMillis();		
		
        
		try {
			FileReader fr = new FileReader(this.path); 
			BufferedReader br = new BufferedReader(fr);
			br.readLine(); //skip first row
			String line;
				
			while((line = br.readLine()) != null) { 
				// ignore comments
				if(!line.startsWith("%")) {
	 				String[] attributes = line.split(",");
	 				//no nullcheck was added, 
	 				//Code is to slow.
	 				
	 				TaxiTripRawEvent rawEvent = new TaxiTripRawEvent(attributes);
					setChanged();
					notifyObservers(rawEvent);
					if(!run){
						synchronized(this){
							this.wait();
						}
					}
				}
			}			
			fr.close();
			
	    	Calendar calEnd = Calendar.getInstance();
	        long endTime = calEnd.getTimeInMillis();
	        
	        System.out.println("processing time = " + (endTime - startTime) + " milliseconds");
		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	@Override
	public void setRunning(boolean running){
		if(running){
			synchronized(this){
				this.notify();
			}
		}
		run = running;
	}

}
