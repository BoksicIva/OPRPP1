package hr.fer.zemris.java.gui.calc.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
/**
 * Implementation of binary operation button that can be inverted
 * @author Iva
 *
 */
public class BinaryOperationInvButton extends JButton implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private DoubleBinaryOperator operation;
	private String sign;
	private DoubleBinaryOperator operationInv;
	private String signInv;
	private JCheckBox checkBoxInv;
	private CalcModel calcModel;
	private DoubleBinaryOperator resultOperation;
	
	/**
	 * Constructo of class
	 * @param operation
	 * @param sign
	 * @param operationInv
	 * @param signInv
	 * @param checkBoxInv
	 * @param calcModel
	 */
	public BinaryOperationInvButton(DoubleBinaryOperator operation, String sign, DoubleBinaryOperator operationInv,
			String signInv,JCheckBox checkBoxInv ,CalcModel calcModel) {
		this.operation = operation;
		this.sign = sign;
		this.operationInv = operationInv;
		this.signInv = signInv;
		this.checkBoxInv=checkBoxInv;
		this.calcModel=calcModel;
		reset();
		
		this.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				//calcModel.setPendingBinaryOperation(resultOperation);
				if (calcModel.isActiveOperandSet()) {
					//calcModel.setActiveOperand(calcModel.getValue());
					//calcModel.setPendingBinaryOperation(resultOperation);
					double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
					calcModel.setValue(result);
					calcModel.clearActiveOperand();
				}
				calcModel.setActiveOperand(calcModel.getValue());
				calcModel.freezeValue();
				calcModel.setPendingBinaryOperation(resultOperation);
				calcModel.clear();
				
			}
		});
		checkBoxInv.addActionListener(this);
	}
	
	/**
	 * Funtion for setting operation that should be used based on checkbox
	 */
	private void  reset() {
		if(checkBoxInv.isSelected())	{
			resultOperation=operationInv;
			this.setText(signInv);
		}else {
			resultOperation=operation;
			this.setText(sign);
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		reset();
	}
	
	

}
