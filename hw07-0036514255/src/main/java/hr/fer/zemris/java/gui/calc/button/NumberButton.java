package hr.fer.zemris.java.gui.calc.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
/**
 * Implementation of button on digits on calculator
 * @author Iva
 *
 */
public class NumberButton extends JButton{
	private static final long serialVersionUID = 1L;
	private int digit;
	
	public NumberButton(int digit, CalcModel calcModel) {
	this.digit=digit;
	this.setFont(this.getFont().deriveFont(30f));
	this.setText(Integer.toString(digit));
	this.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			calcModel.insertDigit(digit);
			
		}
	});
	}
	
	/**
	 * getter of digit
	 * @return digit
	 */
	public int getDigit() {
		return digit;
	}
	
}
