package main;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;

import chartsupport.ChartTrendListener;
import chartsupport.Console;
import chart.LineChart;
import chart.PieChart;
import chart.PieChart2;
import chartsupport.TextFieldListener;
import event.GenericRawEventObserver;
import event.TaxiTripRawEvent;
import gui.EsperGUI;
import reader.TaxiTripFileReplay;
import querys.*;

/**
 * @author hziekow
 *
 */
public class MainControlledTimeTaxiSimulation{

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		System.out.println("enter your data path (/root/Exchange/NY-02.csv):\r\n ");
		Scanner s = new Scanner(System.in);
		String line = s.nextLine();
		s.close();
		TaxiTripFileReplay eventSource = new TaxiTripFileReplay(line);
		// erstellen einer gui
		EsperGUI gui = new EsperGUI(eventSource);
		
		Configuration configuration = new Configuration();
		// Event Registrieren als Class
		configuration.addEventType("TaxiTripRawEvent", TaxiTripRawEvent.class.getName());
		// Verarbeitung nicht in Echtzeit
		configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);			 				
		
		// setzten der Zeit
		initEngineTimeForReplay(epService, "2013-02-01 00:00:54:000");				
		// 3600 -> speedupfactor
		
		GenericRawEventObserver observer = new GenericRawEventObserver(epService,3600);
		eventSource.addObserver(observer);
		
		Query q = new Query(epService);
		gui.setQuery(q);
		
		//q.addQuery("select * from TaxiEvent", new Console());
		
		// adden der gui Componenten und der querys
		TextFieldListener text = new TextFieldListener();
		gui.addContainer(text.getComponent());
		q.addQueryQ1(text);

		LineChart chart = new LineChart("Q2","10 min since start","Miles per Hour");
		gui.addChart(chart);
		q.addQueryQ2(chart);
		
		PieChart chart2 = new PieChart("Q3");
		gui.addChart(chart2);
		q.addQueryQ3(chart2);
		
		ChartTrendListener charTrend = new ChartTrendListener();
		gui.addContainer(charTrend.getComponent());
		q.addQueryQ5(charTrend);
		//q.addQueryQ5(new Console());
		
		// bar diagram / piechart
		//q.addQueryQ10(new Console());
		
		
		PieChart2 chart3 = new PieChart2("Q10","vendorId","totalAmount");
		gui.addChart(chart3);
		q.addQueryQ10(chart3);
		
		
		LineChart chart4 = new LineChart("Q11", "10 min since start", "opponent count");
		gui.addChart(chart4);
		q.addQueryQ11(chart4);
		
		
		gui.repaint();
        
        
        Thread thread = new Thread(eventSource);
        thread.start();
    	
	}
	
	
	public static void initEngineTimeForReplay(EPServiceProvider epService, String startTime) {
		//Set the internal time to a defined time
		//Note: this should be done before registering the queries, because the "nextEvent" schedule will not
		//be initialized correctly otherwise
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        try {
        	Date date = sdfSource.parse(startTime);            
        	Calendar cal = Calendar.getInstance();
            cal.setTime(date);            
            //this lets the engine jump to a certain point in time and skips all scheduled timers
			CurrentTimeEvent timeEvent = new CurrentTimeEvent(cal.getTimeInMillis());
			epService.getEPRuntime().sendEvent(timeEvent);
        } catch (Exception e) {System.err.println(e.toString() + " " + e.getStackTrace());}		
	}
}
