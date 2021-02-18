package hr.fer.oprpp1.hw01;

import static java.lang.Math.hypot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Constructor for complex number
	 * @param real is stored into private class variable real
	 * @param imaginary is stored into private class variable imaginary
	 */
	public ComplexNumber(double real,double imaginary) {
		this.real=real;
		this.imaginary=imaginary;
	}
	
	/**
	 * Method creates complex number from real one
	 * @param real number that is turned into real part of complex number
	 * @return complex number 
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,0);
	}
	
	/**
	 * Method creates complex number from imaginary one
	 * @param imaginary number that is turned into imaginary part of complex number
	 * @return complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0,imaginary);
	}
	
	/**
	 * Method creates complex number from magnitude and angle
	 * @param magnitude -> double value
	 * @param angle in radians
	 * @return new complex number with real and imaginary part
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle),magnitude*Math.sin(angle));
	}
	
	/**
	 * Method converses String into class ComplexNumber
	 * @param s String representing complex number
	 * @return conversed complex number
	 * @throws NumberFormatException if given string is not representing complex number
	 */
	public static ComplexNumber parse(String s) {
		int firstPlus=s.indexOf("+");
		int firstMinus=s.indexOf("-");
		int lastPlus=s.lastIndexOf("+");
		int lastMinus=s.lastIndexOf("-");
		int iSign=s.indexOf("i");
		String[] el=s.split("\\+|\\-");
		Double first=0.0,second=0.0;
		String firstString="",secondString="";
		if((firstPlus!=-1 && firstMinus!=-1)&&(Math.abs(firstMinus-firstPlus)==1 || Math.abs(lastMinus-lastPlus)==1 ||Math.abs(lastMinus-firstMinus)==1 || Math.abs(lastPlus-firstMinus)==1))
			throw new NumberFormatException();
		if(el[0]=="" && el.length==3) {
			firstString=el[1];
		    secondString=el[2];
		}else if(el[0]!="" && el.length==2) {
			firstString=el[0];
		    secondString=el[1];
		}else if(iSign==-1) {
			firstString=el[1];
		}else if(el[0]!=""){
			secondString=el[0];
		}else {
			secondString=el[1];
		}
		
		if(firstString!="" && secondString!="") {
			 first=ComplexNumber.isDouble(firstString);
			 if(secondString.length()==1) {
				 	second=1.0;
			 }else {
			 		second=ComplexNumber.isDouble(secondString.substring(0, secondString.length()-1));
			 }
			 if(s.charAt(0)=='-') {
				 first=first*(-1);
			 }
			 if(lastPlus<lastMinus) {
				 second=second*(-1);
			}
		}else if( iSign!=-1) {
			if(secondString.length()==1) {
				 	second=1.0;
			 }else {
			 		second=ComplexNumber.isDouble(secondString.substring(0, secondString.length()-1));
			 }
			if(s.charAt(0)=='-') {
				second=second*(-1);
			}
		}else if( iSign==-1) {
			first=ComplexNumber.isDouble(firstString);
			if(s.charAt(0)=='-') {
				first=first*(-1);
			}
		}
	    return new ComplexNumber(first,second);	
	}
		
	/**
	 * Method checks if given value can be modified into Double
	 * @param val value that needs to be checked
	 * @return val parsed into double
	 */
	public static double isDouble(Object val) {
	    if (val == null) {
	    	throw new NumberFormatException();
	    }
	    try {
	        Double number = Double.parseDouble((String) val);
	        return number;
	    } catch (NumberFormatException nfe) {
	        throw new NumberFormatException();
	    }   
	}
	
	/**
	 * Method gets real part of complex number
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Method gets imaginary part of complex number
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Method gets magnitude from complex number
	 * @return magnitude
	 */
	public double getMagnitude() {
		return hypot(this.real,this.imaginary); 
	}
	
	/**
	 * Method calculate angle depending on sign of real and complex number
	 * @return angle in radians
	 */
	public double getAngle() {
		if(imaginary==0 && real >=0)
			return 0;
		if(imaginary==0 && real<0)
			return Math.PI;
		if(imaginary>0 && real ==0)
			return Math.PI/2;
		if(imaginary<0 && real==0)
			return 3.0/2.0*Math.PI;
		
		if(real<0 && imaginary <0)
			return Math.PI+Math.atan(imaginary/real);
		
		if(real>0 && imaginary >0)
			return Math.atan(imaginary/real);
		
		if(real<0 && imaginary >0)
			return Math.PI+Math.atan(imaginary/real);
		
		return 2*Math.PI+Math.atan(imaginary/real);
	}
	
	/**
	 * Method adds two complex numbers
	 * @param c complex number that needs to be added to this
	 * @return new complex number 
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real+c.real,this.imaginary+c.imaginary);
	}
	
	/**
	 * Method subducts two complex numbers
	 * @param c complex number that needs to be subduct from this
	 * @return new complex number 
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real-c.real,this.imaginary-c.imaginary);
	}
	
	/**
	 * Method multiply two complex number
	 * @param c complex number is multiplied with this
	 * @return new complex number as result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double newReal=this.real*c.real-this.imaginary*c.imaginary;
		double newImaginary=this.imaginary*c.real+this.real*c.imaginary;
		return new ComplexNumber(newReal,newImaginary);
	}
	
	/**
	 * Method divides two complex numbers
	 * @param c complex number that this is divided by
	 * @return new complex number as result of division
	 */
	public ComplexNumber div(ComplexNumber c) {
		c.imaginary=-c.imaginary;
		ComplexNumber newNumb=this.mul(c);
		double divide=Math.pow(c.real, 2)+Math.pow(c.imaginary, 2);
		newNumb.real=newNumb.real*1.0/divide;
		newNumb.imaginary=newNumb.imaginary*1.0/divide;
		return newNumb;
	}
	
	/**
	 * Method calculate complex number powers n
	 * @param n exponent
	 * @return new complex number
	 */
	public ComplexNumber power(int n) {
		return ComplexNumber.fromMagnitudeAndAngle(Math.pow(this.getMagnitude(),n), this.getAngle()*n);
	}
	
	/**
	 * Method calculate nth root 
	 * @param n representing root
	 * @return array of n ComplexNumbers as result of root method
	 */
	public ComplexNumber[] root(int n) {
		ComplexNumber[] cn=new ComplexNumber[n];
		for(int i=0;i<n;i++) {
			cn[i]=ComplexNumber.fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), 1.0/(n*1.0)),
					                                 (this.getAngle()+2*Math.PI*i)/(n*1.0));
		}
		return cn;
	}
	
	/**
	 * Method prints complex number with their sings and imaginary part  with imaginary "i"
	 */
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(this.real);
		if(this.imaginary>=0)
			sb.append("+");
		sb.append(this.imaginary);
		sb.append("i");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(this.imaginary-other.imaginary) > 1E-8)
			return false;
		if (Math.abs(this.real-other.real) > 1E-10)
			return false;
		return true;
	}
	
	
	
}
