package hr.fer.zemris.java.gu.charts;

import java.util.List;
/**
 * Class represents BarChart implementation
 * @author Iva
 *
 */
public class BarChart {
	private List<XYValue> elements;
	private String xDescription;
	private String yDescription;
	private int minY;
	private int maxY;
	private int step;
	
	/**
	 * Constructor of class
	 * @param elements list of bars that need to be shown in bar chart
	 * @param xDescription description below x axis
	 * @param yDescription description below y axis
	 * @param minY minimal y that needs to be shown on y axis
	 * @param maxY maximal y that need to be shown on y axis
	 * @param step interval between each two dots on y axis
	 */
	public BarChart(List<XYValue> elements, String xDescription, String yDescription, int minY, int maxY, int step) {
		if(minY<0) throw new IllegalArgumentException("Minimal y value cannot be negative.");
		if(minY>= maxY)  throw new IllegalArgumentException("Minimal y value cannot be grater than maximal y value.");
		
		while((maxY-minY)% step !=0)
			maxY++;
		
		for(XYValue element : elements) {
			if(element.getY()<minY) throw new IllegalArgumentException("Y value of element cannot be less than minimal y");
		}
		this.elements = elements;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.step = step;
	}

	/**
	 * Getter of list of elements that needs to be shown on bar chart
	 * @return
	 */
	public List<XYValue> getElements() {
		return elements;
	}

   /**
    * Getter of  xDescription value
    * @return xDescription
    */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter of  yDescription value
	 * @return yDescription
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter of minY value
	 * @return minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Getter of maxY value
	 * @return maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Getter of step value
	 * @return step
	 */
	public int getStep() {
		return step;
	}
	


}
