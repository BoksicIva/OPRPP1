package hr.fer.zemris.java.gui.calc.button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
/**
 * Implementation of class for buttons that apply unary operations
 * @author Iva
 *
 */
public class UnaryOperationButton extends JButton implements ActionListener{
	private static final long serialVersionUID = 1L;
	private DoubleUnaryOperator operation;
	private String sign;
	private DoubleUnaryOperator operationInv;
	private DoubleUnaryOperator resultOperation;
	private String signInv;
	private JCheckBox checkBoxInv;
	
	
	public UnaryOperationButton(DoubleUnaryOperator operation, String sign, DoubleUnaryOperator operationInv,
			String signInv, JCheckBox checkBoxInv,CalcModel calcModel) {
		this.operation = operation;
		this.sign = sign;
		this.operationInv = operationInv;
		this.signInv = signInv;
		this.checkBoxInv=checkBoxInv;
		reset();
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 reset() ;
				double result=resultOperation.applyAsDouble(calcModel.getValue());
				calcModel.clear();
				calcModel.setValue(result);
			}
		});		
		checkBoxInv.addActionListener(this);
	}
	
	/**
	 * Mehod used to reset text on button and result operation based on checkbox
	 */
	public void  reset() {
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
