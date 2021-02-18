package hr.fer.oprpp1.math;
/**
 * Class Vector2D represents vector in 2D and gives methods for modificate vectors
 * @author Iva
 *
 */
public class Vector2D {
	/**
	 * value of vector on x-axis
	 */
	private double x;
	/**
	 * value of vector on y-axis
	 */
	private double y;
	/**
	 * Constructor for vector
	 * @param x - sets vector value x to given value
	 * @param y - sets vector value y to given value
	 */
	public Vector2D(double x, double y) {
		this.x=x;
		this.y=y;
	}
	/**
	 * Getter for x value
	 * @return x value of vector
	 */
	public double getX() {
		return x;
	}
	/**
	 * Getter for y value
	 * @return y value of vector
	 */
	public double getY() {
		return y;
	}
	/**
	 * Method adds vector and given vector
	 * @param offset represents given vector that needs to be added to this
	 */
	public void add(Vector2D offset) {
		this.x=this.x+offset.x;
		this.y=this.y+offset.y;
	}
	/**
	 * Method makes copy of vector and adds vector to copy not modifying vector of class
	 * @param offset vector that is added to copy
	 * @return value of add function of two vectors
	 */
	public Vector2D added(Vector2D offset) {
		Vector2D newVector=copy();
		newVector.add(offset);
		return newVector;
	}
	/**
	 * Method rotate vector for given angle
	 * @param angle for which is vector rotates
	 */
	public void rotate(double angle) {
		double newX=Math.cos(angle)*this.x-Math.sin(angle)*this.y;
		double newY=Math.sin(angle)*this.x+Math.cos(angle)*this.y;
		this.x=newX;
		this.y=newY;
	}
	/**
	 * Method makes copy of vector and rotate copy of  vector not modifying vector of class
	 * @param angle for which is vector rotates
	 * @return value of rotated vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector=copy();
		newVector.rotate(angle);
		return newVector;
	}
	/**
	 * Method multiply vector with scaler
	 * @param scaler double value that multiply vector
	 */
	public void scale(double scaler) {
		this.x=this.x*scaler;
		this.y=this.y*scaler;
	}
	/**
	 * Method makes copy of vector and multiply copy with scaler
	 * @param scaler double value that multiply vector
	 * @return value of multiply vector
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector=copy();
		newVector.scale(scaler);
		return newVector;
	}
	/**
	 * Method makes copy of vector 
	 * @return reference to new vector with same parameters
	 */
	public Vector2D copy() {
		Vector2D newVector=new Vector2D(x, y);
		return newVector;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		if (Math.abs(this.x-other.x) > 1E-8)
			return false;
		if (Math.abs(this.y-other.y) > 1E-8)
			return false;
		return true;
	}
	
	
	

}
