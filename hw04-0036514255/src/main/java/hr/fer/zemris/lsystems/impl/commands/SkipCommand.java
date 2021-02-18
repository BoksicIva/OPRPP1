package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class SkipCommand is implementation of Command interface, skip drawing line and sets current position of turtle to new position when execute method is called
 * @author Iva
 *
 */
public class SkipCommand implements Command{
	private double step;
	
	
	/**
	 * Constructor for SkipCommand class 
	 * @param step value is set to class variable step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}


	/**
	 * Method skip drawing line and sets current position of turtle to new position
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state=ctx.getCurrentState();
		Vector2D newPosition=state.getCurrentPosition().added(state.getDirection().scaled(step*state.getEffectiveLength()));
		state.setCurrentPosition(newPosition);
		
	}

}
