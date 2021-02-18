package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**
 * Class ColorCommand is implementation of Command interface, sets color
 * @author Iva
 *
 */

public class ColorCommand implements Command{
	private Color color;
	
	
	/**
	 * Constructor for ColorCommand
	 * @param color value is set to ColorCommand variable color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}


	/**
	 * Method sets new color to current state of turtle
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
		
	}

}
