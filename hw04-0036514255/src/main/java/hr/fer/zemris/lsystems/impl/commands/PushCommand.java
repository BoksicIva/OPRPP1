package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**
 * Class PushCommand is implementation of Command interface, push state to stack in class Context 
 * @author Iva
 *
 */
public class PushCommand implements Command{
	/**
	 * Method pushes copy of TurtleState state to Context stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
		
	}

}
