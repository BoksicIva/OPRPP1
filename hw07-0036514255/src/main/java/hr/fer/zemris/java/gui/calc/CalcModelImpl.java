package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {
	private boolean editable=true;
	private boolean sign=true;
	private String insertedDigits="";
	private double value=0;
	private String frozenValue=null;
	private double activeOperand;
	private DoubleBinaryOperator pendingBinaryOperation;
	private boolean activeOperandSet=false;
	private List<CalcValueListener> calcValueListeners= new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		calcValueListeners= new ArrayList<>(calcValueListeners);
		calcValueListeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		calcValueListeners= new ArrayList<>(calcValueListeners);
		calcValueListeners.remove(l);
	}
	
	public String toString() {
		if(hasFrozenValue()) 
			return frozenValue;
		if(Double.compare(value, Double.POSITIVE_INFINITY)==0)
			return sign? "Infinity": "-Infinity";
		if(Double.compare(value, Double.NaN)==0)
			return "Nan";
		if(insertedDigits=="")
			return sign? "0": "-0";
		return sign? insertedDigits: "-"+insertedDigits;
		//return  insertedDigits;
		
	}

	@Override
	public double getValue() {
		return sign==true? value: value*(-1);
	}

	@Override
	public void setValue(double value) {
		if(value<0) 
			sign=false;
		else
			sign=true;
		this.value=Math.abs(value);
		insertedDigits=Double.toString(this.value);
		editable=false;
		
		for(CalcValueListener listener : calcValueListeners) 
			listener.valueChanged(this);
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		this.value=0;
		this.editable=true;
		this.insertedDigits="";
		this.sign=true;
		
		for(CalcValueListener listener : calcValueListeners) 
			listener.valueChanged(this);
	}

	@Override
	public void clearAll() {
		this.frozenValue=null;
		this.pendingBinaryOperation=null;
		clearActiveOperand();
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable()) throw new CalculatorInputException("Calculator is not editable!");
		if(sign)
			sign=false;
		else 
			sign=true;
		frozenValue=null;
		for(CalcValueListener listener : calcValueListeners) 
			listener.valueChanged(this);
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable()) throw new CalculatorInputException("Calculator is not editable!");
		if(insertedDigits.contains(".")) throw new CalculatorInputException("Decimal dot already exists in inserted number.");
		if(insertedDigits=="") 	throw new CalculatorInputException("Dot cannot be first character of number");
		insertedDigits+=".";
		frozenValue=null;
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable()) throw new CalculatorInputException("Calculator is not editable!");
		if(digit<0 || digit > 9) throw new IllegalArgumentException("Digit is not in range [0-9]");
		String s=insertedDigits+digit;
		if(isNumber(s)) {
			value=Double.parseDouble(s);
		}else {
			throw new CalculatorInputException("Number is not parsable to double.");
		}
		
		if((insertedDigits.equals("0") && digit!=0)) {
			insertedDigits=digit+"";
		}else if((insertedDigits.equals("0") && digit==0)) {
			insertedDigits="0";
		} else {
		insertedDigits=s;	
		}
		frozenValue=null;
		for(CalcValueListener listener : calcValueListeners) 
			listener.valueChanged(this);
	}
	
	private  boolean isNumber(Object val) {
	    if (val == null) {
	    	throw new NumberFormatException();
	    }
	    try {
	        Double number = Double.parseDouble((String) val);
	        if(Double.isInfinite(number)) return false;
	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }   
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) throw new IllegalStateException("Operand is not setted.");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand=activeOperand;
		this.activeOperandSet=true;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperandSet=false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingBinaryOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingBinaryOperation=op;
		freezeValue();
	}

	@Override
	public void freezeValue() {
		frozenValue=toString();
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenValue!="" && frozenValue!=null;
	}

}
