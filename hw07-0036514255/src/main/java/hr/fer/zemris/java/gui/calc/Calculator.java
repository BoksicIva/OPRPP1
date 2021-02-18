package hr.fer.zemris.java.gui.calc;


import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.button.ActivateFunkcionButton;
import hr.fer.zemris.java.gui.calc.button.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.button.BinaryOperationInvButton;
import hr.fer.zemris.java.gui.calc.button.NumberButton;
import hr.fer.zemris.java.gui.calc.button.UnaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;
	private boolean Inv=false;
	Stack<Double> stack = new Stack<>();
	private List<UnaryOperationButton> unaryOperationButtonsTrig=new ArrayList<>();
	private List<UnaryOperationButton> unaryOperationButtons=new ArrayList<>();
	private BinaryOperationInvButton binaryOperationInvButton;
	private String[] binaryOperandsSing= {"/","*","-","+"};
	private DoubleBinaryOperator[]  binaryOperandsImpl= {
		 (x, y) -> { return x / y; },
		 (x, y) -> { return x * y; },
		 (x, y) -> { return x - y; },
		 (x, y) -> { return x + y; }
	};
	
	private String[][] unaryOperandsSingTrig= {
			{"sin","arcsin"},
			{"cos","arccos"},
			{"tan","arctan"},
			{"ctg","arcctg"},
			};
	private DoubleUnaryOperator[][]  unaryOperandsImplTrig= {
			 {(x) -> { return Math.sin(x);} , (x) -> { return Math.asin(x);}   },
			 {(x) -> { return Math.cos(x);} , (x) -> { return Math.cos(x); }   },
			 {(x) -> { return Math.tan(x);} , (x) -> { return Math.atan(x);}   },
			 {(x) -> { return 1/Math.tan(x);} , (x) -> { return Math.atan(Math.tan(x)); }  },             
		};
	
	private String[][] unaryOperandsSing= {
			{"1/x","1/x"},
			{"log","10^x"},
			{"ln","e^x"},
			};
	private DoubleUnaryOperator[][]  unaryOperandsImpl= {
			 {(x) -> { return 1/x;} , (x) -> { return 1/x;}   },
			 {(x) -> { return Math.log10(x);} , (x) -> { return Math.pow(10,x); }   },
			 {(x) -> { return Math.log(x);} , (x) -> { return Math.pow(Math.E,x);}   },    
		};
	
	private DoubleBinaryOperator[]  binaryOperationInv= {
			 (x,y) -> { return Math.pow(x, y);} , (x,y) -> { return Math.pow(x, 1.0/y);}     
		};
	
	private String[] binaryOperationInvsSing= {
			"x^n","x^(1/n)"
			};
	
	
	/**
	 * Constructor of class
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setSize(500,500);
		initGUI();
		pack();
	}
	
	/**
	 * Method initialize GUI over component
	 */
	private void initGUI() {
		Container cp=getContentPane();
		cp.setLayout(new CalcLayout(4));
		cp.setFont(cp.getFont().deriveFont(30f));
		CalcModel calcModel=new CalcModelImpl();
		
		cp.add(l("0",calcModel), new RCPosition(1,1));
		
		

		

		JCheckBox checkBoxInv=new JCheckBox("Inv");
		cp.add(checkBoxInv, new RCPosition(5,7));
		
		checkBoxInv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Inv=checkBoxInv.isSelected();  
				for(UnaryOperationButton button:unaryOperationButtonsTrig)
					checkBoxInv.addActionListener(button);
				for(UnaryOperationButton button:unaryOperationButtons)
					checkBoxInv.addActionListener(button);
				checkBoxInv.addActionListener(binaryOperationInvButton);
			}
		
         });  
		
		
		cp.add(new NumberButton(0,calcModel), new RCPosition(5,3));
		int number=1;
		for(int i=4;i>1;i--) {
			for(int j=3;j<6;j++) {
				cp.add(new NumberButton(number,calcModel), new RCPosition(i,j));
				number++;
			}
		}
		
		cp.add(new ActivateFunkcionButton("+ / -",(() -> calcModel.swapSign())),new RCPosition(5, 4));
		cp.add(new ActivateFunkcionButton(".",(() -> calcModel.insertDecimalPoint())),new RCPosition(5, 5));
		
		cp.add(new ActivateFunkcionButton("=",(() -> {
												if (calcModel.isActiveOperandSet()) {
												double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
												calcModel.setValue(result);
												calcModel.clearActiveOperand();
											}
											calcModel.freezeValue();
											calcModel.setActiveOperand(calcModel.getValue());
											calcModel.clear();})),				
				new RCPosition(1, 6));
		
		
		
		cp.add(new ActivateFunkcionButton("clr",(() -> calcModel.clear())),new RCPosition(1, 7));
		cp.add(new ActivateFunkcionButton("reset",(() -> calcModel.clearAll())),new RCPosition(2, 7));
		cp.add(new ActivateFunkcionButton("push",(() -> stack.push(calcModel.getValue()))),new RCPosition(3, 7));
		cp.add(new ActivateFunkcionButton("pop",(() -> calcModel.setValue(stack.pop()))),new RCPosition(4, 7));
		
		
		for(int i=2;i<=5;i++) {
			cp.add(new BinaryOperationButton(binaryOperandsImpl[i-2],
											binaryOperandsSing[i-2], calcModel),new RCPosition(i,6));
		}
		
		binaryOperationInvButton =new BinaryOperationInvButton(binaryOperationInv[0],
				                            binaryOperationInvsSing[0]
											,binaryOperationInv[1],
											binaryOperationInvsSing[1],
											checkBoxInv , calcModel);
		cp.add(binaryOperationInvButton,new RCPosition(5,1));
	
		for(int i=2;i<5;i++) {
			UnaryOperationButton unaryButton=new UnaryOperationButton(unaryOperandsImpl[i-2][0],
																	  unaryOperandsSing[i-2][0],
																	  unaryOperandsImpl[i-2][1],
																	  unaryOperandsSing[i-2][1],
																	  checkBoxInv, calcModel);
			cp.add(unaryButton,new RCPosition(i, 1));
			unaryOperationButtons.add(unaryButton);
		}
		
		for(int i=2;i<=5;i++) {
			UnaryOperationButton unaryButton=new UnaryOperationButton(unaryOperandsImplTrig[i-2][0], 
																	unaryOperandsSingTrig[i-2][0],
																	unaryOperandsImplTrig[i-2][1],
																	unaryOperandsSingTrig[i-2][1],
																	checkBoxInv , calcModel);
			cp.add(unaryButton,new RCPosition(i, 2));
			unaryOperationButtonsTrig.add(unaryButton);
		}
	
		
		
	}
	
	private JLabel l(String text,CalcModel calcModel) {
		JLabel l = new JLabel(text,SwingUtilities.RIGHT);
		l.setFont(l.getFont().deriveFont(30f));
		l.setBackground(Color.YELLOW);
		l.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		l.setOpaque(true);
		calcModel.addCalcValueListener(new CalcValueListener() {
	
				@Override
				public void valueChanged(CalcModel model) {
					l.setText(model.toString());;
				}
		});
		return l;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
		new Calculator().setVisible(true);
		});
		
	}


}
