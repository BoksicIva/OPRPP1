package hr.fer.zemris.java.gui.calc.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class BinaryOperationButton extends JButton {
	private static final long serialVersionUID = 1L;
	private DoubleBinaryOperator operation;
	private String sign;
	
	/**
	 * Constructor of class
	 * @param operation DoubleBinaryOperator that is used over inserted values in calculator
	 * @param sign text over button
	 * @param calcModel model used for applying functions
	 */
	public BinaryOperationButton(DoubleBinaryOperator operation, String sign,CalcModel calcModel) {
		this.operation = operation;
		this.sign = sign;
		this.setText(sign);
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (calcModel.isActiveOperandSet()) {
					double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
					calcModel.setValue(result);
					calcModel.clearActiveOperand();
				}
				calcModel.freezeValue();
				calcModel.setPendingBinaryOperation(operation);
				calcModel.setActiveOperand(calcModel.getValue());
				calcModel.clear();
			}
		});

	}

	
	/**
	 * Getter of sign value
	 * @return sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Getter of operation value
	 * @return operation
	 */
	public DoubleBinaryOperator getOperation() {
		return operation;
	}
	
	
	

}
