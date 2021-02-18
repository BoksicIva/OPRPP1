package hr.fer.zemris.java.gui.calc.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.ButtonFunction;
/**
 * Class represents implementation for all buttons that activates function of calcModel 
 * clr, reset, = , . , +/-
 * @author Iva
 *
 */

public class ActivateFunkcionButton extends JButton{
	private static final long serialVersionUID = 1L;
	private String sign;
	private ButtonFunction funtion;
	
	/**
	 * Constructor of class
	 * @param sign is used to set text over button
	 * @param funtion is used to activates all functions in calcModel
	 */
	public ActivateFunkcionButton(String sign, ButtonFunction funtion) {
		this.sign = sign;
		this.funtion = funtion;
		this.setText(sign);
		this.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						funtion.applyFuntion();
					}
				});
	}
	
	
	
	
	
	
	
}
