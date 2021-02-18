package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Class RotateCommand is implementation of Command interface, rotates direction of turtle
 * @author Iva
 *
 */
public class RotateCommand implements Command{
	private double angle;
	
	/**
	 * Constructor for class RotateCommand
	 * @param angle value is set to class variable angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Method sets new direction for next call of drawLine method by rotating old direction for angle value
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle*Math.PI/180.0);
	}

}
