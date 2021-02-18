package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
/**
 * Interface Command used for all forms of commands,they need to have implementation of execute command depending of command
 * @author Iva
 *
 */
public interface Command {
	
	void execute(Context ctx,Painter painter);

}
