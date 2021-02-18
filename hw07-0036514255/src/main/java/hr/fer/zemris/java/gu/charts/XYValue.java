package hr.fer.zemris.java.gu.charts;

/**
 * Class represents 
 * @author Iva
 *
 */
public class XYValue {
	private int x;
	private int y;
	
	/**
	 * Constructor of class
	 * @param x int value
	 * @param y int value
	 */
	public XYValue(int x, int y) {

		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter of x value
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter of y value
	 * @return y
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof XYValue))
			return false;
		XYValue other = (XYValue) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
	
}
