package chartsupport;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;


public class ChartTrendListener extends DataListener{
	
	/**
	 * ChartTrendListener implementation
	 * @author alex
	 * 
	 * Data gets displayed as Trend,
	 * a triangle will be painted.
	 * */
	
	
	private String up,down,equal;
	/**size of the triangle*/
	private int size = 30;
	private JComponent component;
	private String trend;    
	public ChartTrendListener(){
		super();
		this.component = new JComponent(){
			/**
			 * implementation of a JComponent, updating the
			 * paintComponent which holds the triangle painting
			 */
			private static final long serialVersionUID = 1L;

			@Override
			    public void paintComponent(Graphics g) {
			    	drawTrend(g,trend);
			    }
			};

	}
 
	public void drawTrend(Graphics gx, String trends){
		/**
		 * Method which draws the current trend.
		 * */
    	int size = 10;
		int next = (size-1)*2;
		if(trends == null){
			return;
		}
		
		switch(trends){
		case "UP":
			drawUp(gx,next+100,20);
			break;
		case "DOWN":
			drawDown(gx,next+100,20);
			break;
		default :
			drawEquals(gx,next+100+size/2,20);
			break;
		}
    	
    }
    
    
    public void drawUp(Graphics gx,int startX,int startY){
    	/**
    	 * Draws a Triangle pointing to the top
    	 * */
    	Graphics2D g = (Graphics2D) gx.create();
    	Polygon p = new Polygon();
    	p.addPoint(startX, startY);
    	p.addPoint(startX-size, startY+size);
    	p.addPoint(startX+size, startY+size);
    	p.addPoint(startX, startY);
    	g.fillPolygon(p);
    }
    
    public void drawDown(Graphics gx,int startX,int startY){
    	/**
    	 * Draws a Triangle pointing to the bottom
    	 * */
    	Graphics2D g = (Graphics2D) gx.create();
    	Polygon p = new Polygon();
    	p.addPoint(startX, startY+size);
    	p.addPoint(startX-size, startY);
    	p.addPoint(startX+size, startY);
    	p.addPoint(startX, startY+size);
    	g.fillPolygon(p);
    }
    
    public void drawEquals(Graphics gx,int startX,int startY){
    	/**
    	 * Draws a Triangle pointing to the right
    	 * */
    	Graphics2D g = (Graphics2D) gx.create();
    	Polygon p = new Polygon();
    	p.addPoint(startX, startY);
    	p.addPoint(startX-size, startY+size);
    	p.addPoint(startX-size, startY-size);
    	p.addPoint(startX, startY);
    	g.fillPolygon(p);
    }
	
	
	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getEqual() {
		return equal;
	}

	public void setEqual(String equal) {
		this.equal = equal;
	}

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	@Override
	void update(EventBean event, String[] names, EventType eType) {
	    /**setting the trend variable to the event Trend value*/
		trend = event.get("Trend")+"";
		component.repaint();
	}
}
