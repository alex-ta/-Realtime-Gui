package chart;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import chartsupport.Chart;

public class PieChart extends Chart{

	/**
	 * PieChart implementation
	 * @author alex
	 * 
	 * component contains the swing component which gets added to the JFrame
	 * dataset manage the passed data
	 * */
	
	private JComponent component;
	private final DefaultPieDataset dataset;

	
	
	
	public PieChart(String title){
		  dataset = new DefaultPieDataset( );   
		  /**create a new Chart Object with empty data*/ 
		  JFreeChart chart = ChartFactory.createPieChart(
	         title, 
	         dataset,
	         true, true, false);
		  /**add chart to a Panel which gets displayed on a JFrame*/
	      this.component = new ChartPanel(chart);
	      /** set the preferredsize*/
	      this.component.setPreferredSize(new Dimension(600, 300));
	    
	}
	
	public void addData(String serie, double v){
		/**adds data to a series or creats the series*/
		dataset.setValue(serie, v);
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
		addData(string,y);
	}

	@Override
	public Component getChart() {
		/**returns the Component added to the JFrame*/
		return component;
	}
}
