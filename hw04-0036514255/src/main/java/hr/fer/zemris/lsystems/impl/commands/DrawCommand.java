package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class DrawCommand is implementation of Command interface, draw line when execute method is called
 * @author Iva
 *
 */
public class DrawCommand implements Command{
	private double step;
	
	/**
	 * Constructor for DrawCommand
	 * @param step value is set to DrawCommand variable step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	/**
	 * Method draw line by calling method drawLine with new parameters
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state=ctx.getCurrentState();
		Vector2D newPosition=state.getCurrentPosition().added(state.getDirection().scaled(step*state.getEffectiveLength()));
		painter.drawLine(state.getCurrentPosition().getX(), state.getCurrentPosition().getY(), newPosition.getX(), newPosition.getY(), state.getColor(), (float) state.getEffectiveLength());
		state.setCurrentPosition(newPosition);
	}
	
	
	
	
}
