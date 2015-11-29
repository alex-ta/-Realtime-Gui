package chart;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import chartsupport.Chart;

public class LineChart extends Chart{
	
	/**
	 * LineChart implementation
	 * @author alex
	 * 
	 * component contains the swing component which gets added to the JFrame
	 * series and dataset manage the passed data
	 * */
	
	private JComponent component;
	private final HashMap<String,XYSeries> series;
	private final XYSeriesCollection dataset;
	public LineChart(String title, String xAxis, String yAxis){
		  series = new HashMap<String,XYSeries>();
		  dataset = new XYSeriesCollection( );   

		  
		 /**create a new Chart Object with empty data*/ 
		  JFreeChart chart = ChartFactory.createXYLineChart(
	         title, 
	         xAxis,
	         yAxis, 
	         dataset,
	         PlotOrientation.VERTICAL, 
	         true, true, false);
		/**add chart to a Panel which gets displayed on a JFrame*/
	    this.component = new ChartPanel(chart);
	    /** set the preferredsize*/
	    this.component.setPreferredSize(new Dimension(600, 300));
	    
	}
	
	public void addSeries(String name){
		/**adds a new dataseries to the chart*/
		XYSeries s = new XYSeries(name);
		/**
		 * Set the max amount from of points
		 * the implementations works a queue
		 * but internal resource managment throws
		 * Indexoutofbounds
		 */
		s.setMaximumItemCount(1000);
		series.put(name, s);
		dataset.addSeries(s);
	}
	
	public void addData(String serie, double x, double y){
		/**add a point to the chart*/
		series.get(serie).add(x,y);		
		// throws exception
	}
	
	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	@Override
	public void repaint(String string, double y, long x) {
		/**gets triggered on the chart*/
		if(!series.containsKey(string)){
			addSeries(string);
		}
		addData(string,x,y);
	}

	@Override
	public Component getChart() {
		/**returns the Component added to the JFrame*/
		return component;
	}

}
