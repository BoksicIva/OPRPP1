package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;
/**
 * Class that represents state of turtle in Coordinate system
 * @author Iva
 *
 */
public class TurtleState {
	private Vector2D currentPosition;
	private Vector2D direction;
	private Color color;
	private double effectiveLength;
	
	public TurtleState() {
	}
	
	
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double effectiveLength) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.effectiveLength = effectiveLength;
	}
	/**
	 * Method makes new instance -> copy of current TurtleState
	 * @return instance of copied state
	 */
	public TurtleState copy() {
		TurtleState copyTurtleState=new TurtleState();
		copyTurtleState.setCurrentPosition(currentPosition.copy());;
		copyTurtleState.setDirection(direction.copy());
		copyTurtleState.setColor(color);
		copyTurtleState.setEffectiveLength(effectiveLength);
		return copyTurtleState;
	}

	/**
	 * Getter for currentPosition
	 * @return currentPosition
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Setter for currentPosition 
	 * @param currentPosition value that is set to currentPosition
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Getter for direction
	 * @return direction of turtle
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Setter for direction
	 * @param direction value that is set to TurstleState variable direction
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Getter for color
	 * @return color of turtle
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * Setter for color
	 * @param color value that is set to TurstleState variable color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for effectiveLength
	 * @return effectiveLength of turtle
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

	/**
	 * Setter for effectiveLength
	 * @param effectiveLength value that is set to TurstleState variable effectiveLength
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}
	
	
	

}
