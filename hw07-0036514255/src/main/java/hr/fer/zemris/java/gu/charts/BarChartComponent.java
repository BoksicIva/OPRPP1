package hr.fer.zemris.java.gu.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

public class BarChartComponent extends JComponent{
	private static final long serialVersionUID = 1L;
	private BarChart barChart;
	
	public static final int gap= 70;
	 public static final int gap10 = 10;
	 public static final int gap5 = 5;
	 
	 // size of start coordinate length
	 public static final int ORIGIN_COORDINATE_LENGHT = 6;
	 
	 // distance of coordinate strings from axis
	 public static final int descriptionGep = 20;
	
	/**
	 * Constructor of class
	 * @param barChar is used to set class variable
	 */
	public BarChartComponent(BarChart barChar) {
		super();
		this.barChart = barChar;
		
	}

	/**
	 * Getter of barChart
	 * @return barChart
	 */
	public BarChart getBarChar() {
		return barChart;
	}

	/**
	 * Setter of barChart
	 * @param barChar
	 */
	public void setBarChar(BarChart barChar) {
		this.barChart = barChar;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	
	  super.paintComponent(g);
	  
	  Graphics2D g2 = (Graphics2D) g;
	  
	  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	    RenderingHints.VALUE_ANTIALIAS_ON);
	  
	  // x-axis
	  g2.drawLine(gap, getHeight()-gap,
			  getWidth()-gap,  getHeight()-gap);
	  
	  
	  // y-axis
	 g2.drawLine(gap,  getHeight()-gap,
			 gap,gap);
	  
	  // x-axis arrow
	  g2.drawLine(getWidth()-gap - gap10,
			  getHeight()-gap - gap5,
			  getWidth()-gap, getHeight()-gap);
	  g2.drawLine(getWidth()-gap - gap10,
			  getHeight()-gap + gap5,
			  getWidth()-gap, getHeight()-gap);
	  
	  // y-axis arrow
	  g2.drawLine(gap - gap5,
	     gap + gap10,
	     gap , gap);
	  g2.drawLine(gap + gap5, 
	     gap + gap10,
	     gap , gap);
	  
	  // draw start Point
	  g2.drawString("(0, 0)", gap-gap/2,
			     getHeight()-gap + descriptionGep);
	  
	  // setup for font use
	  FontMetrics fm = g.getFontMetrics();
	  int widthOfStringX = fm.stringWidth(barChart.getxDescription());
	  int widthOfStringY = fm.stringWidth(barChart.getyDescription());
		
	  // inserting description under x axis
	  g2.drawString(barChart.getxDescription(), 
			     getWidth()/2-widthOfStringX/2, 
			     getHeight() - gap/2+10);
	  
	  
	  // changing coordinate system
	  AffineTransform at = new AffineTransform();
	  AffineTransform defaultAt = g2.getTransform();
	  at.rotate(-Math.PI / 2);
	  g2.setTransform(at);
	  
	  // inserting description next to y axis
	  g2.drawString(barChart.getyDescription(), -((getHeight()+2*gap)/2-widthOfStringY/2), gap-gap/2) ;
	  
	  
	  g2.setTransform(defaultAt);
	  
	  int lengthX=(getWidth()-2*gap- gap5)/barChart.getElements().size()-3;
	  int lengthY=(getHeight()-2*gap- gap10)/((barChart.getMaxY()-barChart.getMinY()/barChart.getStep()));
	  
	  // draw x-axis numbers and lines
	  for(int i = 1; i <= barChart.getElements().size(); i++) {
	   g2.setColor(Color.ORANGE);
	   g2.drawLine(gap + (i * lengthX),
	     getHeight()-gap + gap5,
	     gap + (i * lengthX),
	     gap);
	   g2.setColor(Color.BLACK);
	   g2.drawString(Integer.toString(i), 
	     gap + (i * lengthX)-lengthX/2,
	     getHeight()-gap + descriptionGep);
	  }
	  
	  // draw y-axis numbers and lines
	  for(int i = barChart.getMinY(); i <= barChart.getMaxY(); i+=barChart.getStep()) {
	   
	   if(i!=0)
		   g2.setColor(Color.ORANGE);
	  if(i==0)
		  continue;
	   g2.drawLine(gap - gap5,
			   getHeight()-gap- (i * lengthY), 
	     getWidth()-gap + gap5,
	     getHeight()-gap- (i * lengthY));
	   g2.setColor(Color.BLACK);
	   g2.drawString(Integer.toString(i), 
			     gap  - descriptionGep, 
			     getHeight()-gap- (i * lengthY)+3);
	  }
	  
	  
	  // inserting bars
	  g2.setColor(Color.ORANGE);
	  for(int i = 0; i < barChart.getElements().size(); i++) {
		  g2.fill3DRect(gap +1+ (i * lengthX),
				  getHeight()-gap -lengthY*barChart.getElements().get(i).getY(),
				  lengthX,
				  lengthY*barChart.getElements().get(i).getY(),true);
	  }
	 
	  // dot at centar of coordinate system
	  g2.fillOval(
		        70- (ORIGIN_COORDINATE_LENGHT / 2), 
		        getHeight()-70 - (ORIGIN_COORDINATE_LENGHT / 2),
			    ORIGIN_COORDINATE_LENGHT, ORIGIN_COORDINATE_LENGHT);
	
	}

	
}
