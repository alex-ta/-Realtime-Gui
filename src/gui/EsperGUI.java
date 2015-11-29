package gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import chartsupport.Chart;
import chartsupport.SourceInterface;

import querys.Query;

public class EsperGUI {
	
	/**
	 * EsperGUI implementation
	 * @author alex
	 * 
	 * JFrame which displays the Charts.
	 * */
	
	private JFrame frame;
	private SourceInterface source;
	private Query query;
	private Container charts;
	
	public EsperGUI(final SourceInterface source){
		this.setSource(source);
		this.frame = new JFrame("JCHART2D & Esper");
		/** set the size of the Frame*/
		frame.setSize(800, 600);
		/** center the Frame*/
		frame.setLocationRelativeTo(null);
		
		/** closing the window & thread on closeevent*/
		frame.addWindowListener(
		    new WindowAdapter() {
		        public void windowClosing(WindowEvent e){
		            System.exit(0);
		        }
		    }
		);
		
		/**container holding the top button*/
		Container buttons = new Container();
		buttons.setLayout(new FlowLayout());
		
		
		
		final JButton startInput = new JButton("start Input");
		/**Button which provides action to start or stop the Datastream*/
		startInput.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(startInput.getText().equals("start Input")){
					startInput.setText("stop Input");
					source.setRunning(true);
				}else{
					startInput.setText("start Input");
					source.setRunning(false);
				}
			}
		});

		buttons.add(startInput);
		
		
		/**container holding the charts in a GridLayout*/
		charts = new Container();
		charts.setLayout(new GridLayout(3,2));
			
		/**adding the containers to the Frame*/
		frame.getContentPane().add(buttons,BorderLayout.NORTH);
		frame.getContentPane().add(charts, BorderLayout.CENTER);
		/**show frame*/
		frame.setVisible(true);
	}
	

	public void addChart(Chart chart, String qUery) {
		/**add a chart and a query to the Frame and Esper*/
		query.addQuery(qUery, chart);
		charts.add(chart.getChart());
	}
	
	public void addChart(Chart chart){
		/**add a Chart to the Frame*/
		charts.add(chart.getChart());
	}
	
	public void addContainer(Component text){
		/**add a Container to the Frame*/
		charts.add(text);
	}


	public Query getQuery() {
		return query;
	}


	public void setQuery(Query query) {
		this.query = query;
	}


	public SourceInterface getSource() {
		return source;
	}


	public void setSource(SourceInterface source) {
		this.source = source;
	}


	public void repaint() {
		/**set up the Frame when all components were added*/
		frame.pack();
		frame.setSize(800, 600);
		frame.repaint();
	}
	
	//
}
