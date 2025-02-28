package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**
 * Class PopCommand is implementation of Command interface, pops state from Context
 * @author Iva
 *
 */
public class PopCommand implements Command{
	/**
	 * Method pops state from Context stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
