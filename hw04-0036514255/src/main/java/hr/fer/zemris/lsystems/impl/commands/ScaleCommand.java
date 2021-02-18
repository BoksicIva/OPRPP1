package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Class ScaleCommand is implementation of Command interface, scales effective length for TurtleState class with factor value
 * @author Iva
 *
 */
public class ScaleCommand implements Command{
	private double factor;
	
	/**
	 * Constructor for ScaleCommand class
	 * @param factor value is set to class variable factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Method cales effective length for TurtleState class with factor value
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state=ctx.getCurrentState();
		state.setEffectiveLength(state.getEffectiveLength()*factor);
		
	}

	

}
