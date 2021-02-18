package hr.fer.zemris.math;

import static java.lang.Math.hypot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class representing implementation of complex number
 * @author Iva
 *
 */
public class Complex {
	
	public static final Complex ZERO = new Complex(0,0); 
	public static final Complex ONE = new Complex(1,0); 
	public static final Complex ONE_NEG = new Complex(-1,0); 
	public static final Complex IM = new Complex(0,1); 
	public static final Complex IM_NEG = new Complex(0,-1); 
	private double real;
	private double imaginary;
	
	public Complex() {
		real=0;
		imaginary=0;
	} 
	
	/**
	 * Constructor for complex number
	 * @param re is stored into private class variable real
	 * @param im is stored into private class variable imaginary
	 */
	public Complex(double re, double im) {
		real=re;
		imaginary=im;
	}
	
	
	/**
	 * Method gets real part of complex number
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Method creates complex number from real one
	 * @param real number that is turned into real part of complex number
	 * @return complex number 
	 */
	public static Complex fromReal(double real) {
		return new Complex(real,0);
	}
	
	/**
	 * Method creates complex number from imaginary one
	 * @param imaginary number that is turned into imaginary part of complex number
	 * @return complex number
	 */
	public static Complex fromImaginary(double imaginary) {
		return new Complex(0,imaginary);
	}
	
	/**
	 * Method gets imaginary part of complex number
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	
	/**
	 * Method gets module from complex number
	 * @return module
	 */
	public double module() {
		return hypot(this.real,this.imaginary); 
	}
	
	
	/**
	 * Method multiply two complex number
	 * @param c complex number is multiplied with this
	 * @return new complex number as result of multiplication
	 */
	public Complex multiply(Complex c) {
		double newReal=this.real*c.real-this.imaginary*c.imaginary;
		double newImaginary=this.imaginary*c.real+this.real*c.imaginary;
		return new Complex(newReal,newImaginary);
	}
	
	
	/**
	 * Method divides two complex numbers
	 * @param c complex number that this is divided by
	 * @return new complex number as result of division
	 */
	public Complex divide(Complex c) {
		c.imaginary=-c.imaginary;
		Complex newNumb=this.multiply(c);
		double divide=Math.pow(c.real, 2)+Math.pow(c.imaginary, 2);
		newNumb.real=newNumb.real*1.0/divide;
		newNumb.imaginary=newNumb.imaginary*1.0/divide;
		return newNumb;
	}
	
	/**
	 * Method adds two complex numbers
	 * @param c complex number that needs to be added to this
	 * @return new complex number -> this+c
	 */
	public Complex add(Complex c) {
		return new Complex(this.real+c.real,this.imaginary+c.imaginary);
	}
	
	/**
	 * Method subducts two complex numbers
	 * @param c complex number that needs to be subduct from this
	 * @return new complex number 
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real-c.real,this.imaginary-c.imaginary);
	}
	
	
	/**
	 * Method returns negative of complex number
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-this.real,-this.imaginary);
	}
	
	/**
	 * Method calculate complex number powers n
	 * @param n exponent ,non-negative integer
	 * @return new complex number -> this^n
	 */ 
	public Complex power(int n) {
		if(n<0) throw new IllegalArgumentException();
		return Complex.fromMagnitudeAndAngle(Math.pow(this.module(),n), this.angle()*n);
	}
	
	
	/**
	 * Method calculate nth root 
	 * @param n representing root, n is positive integer
	 * @return list of n ComplexNumbers as result of root method
	 */
	// returns n-th root of this, n is positive integer 
	public List<Complex> root(int n) {
		if(n<=0) throw new IllegalArgumentException();
		List<Complex> rootList=new ArrayList<>();
		for(int i=0;i<n;i++) {
			rootList.add(Complex.fromMagnitudeAndAngle(Math.pow(this.module(), 1.0/(n*1.0)),
					                                 (this.angle()+2*Math.PI*i)/(n*1.0)));
		}
		return Collections.unmodifiableList(rootList);
	}

	/**
	 * Method creates complex number from magnitude and angle
	 * @param magnitude -> double value
	 * @param angle in radians
	 * @return new complex number with real and imaginary part
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude,double angle) {
		return new Complex(magnitude*Math.cos(angle),magnitude*Math.sin(angle));
	}
	
	/**
	 * Method converses String into class Complex
	 * @param s String representing complex number
	 * @return conversed complex number
	 * @throws NumberFormatException if given string is not representing complex number
	 */
	public static Complex parse(String s) {
		s=s.replaceAll("\\s","");
		int firstPlus=s.indexOf("+");
		int firstMinus=s.indexOf("-");
		int lastPlus=s.lastIndexOf("+");
		int lastMinus=s.lastIndexOf("-");
		int iSign=s.indexOf("i");
		String[] el=s.split("\\+|\\-");
		Double first=0.0,second=0.0;
		String firstString="",secondString="";
		
		if((firstPlus!=-1 && firstMinus!=-1)&&(Math.abs(firstMinus-firstPlus)==1 
			|| Math.abs(lastMinus-lastPlus)==1 
			|| Math.abs(lastMinus-firstMinus)==1 || Math.abs(lastPlus-firstMinus)==1))
			throw new NumberFormatException();
		
		if(el[0]=="" && el.length==3) {
			firstString=el[1];
		    secondString=el[2];
		}else if(el[0]!="" && el.length==2) {
			firstString=el[0];
		    secondString=el[1];
		}else if(el[0]!="" && iSign==-1) {
			firstString=el[0];
		}else if(iSign==-1) {
			firstString=el[1];
		}else if(el[0]!=""){
			secondString=el[0];
		}else {
			secondString=el[1];
		}
		
		if(firstString!="" && secondString!="") {
			 first=Complex.isDouble(firstString);
			 if(secondString.length()==1 && secondString.indexOf("i")!=-1) {
				 	second=1.0;
			 }else {
			 		second=Complex.isDouble(secondString.substring(1, secondString.length()));
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
			 		second=Complex.isDouble(secondString.substring(1, secondString.length()));
			 }
			if(s.charAt(0)=='-') {
				second=second*(-1);
			}
		}else if( iSign==-1) {
			first=Complex.isDouble(firstString);
			if(s.charAt(0)=='-') {
				first=first*(-1);
			}
		}
	    return new Complex(first,second);	
	}
	
	/**
	 * Method checks if given value can be modified into Double
	 * @param val value that needs to be checked
	 * @return val parsed into double
	 */
	private static double isDouble(Object val) {
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
	 * Method calculate angle depending on sign of real and complex number
	 * @return angle in radians
	 */
	public double angle() {
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
	
	@Override
	public String toString() {
	    String s= "("+ this.real;
	    s+=(this.imaginary >= 0? "+i"+Math.abs(this.imaginary) : "-i"+Math.abs(this.imaginary)) ;
	    s+=")";
	    return s;
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
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(this.imaginary-other.imaginary) > 1E-8)
			return false;
		if (Math.abs(this.real-other.real) > 1E-10)
			return false;
		return true;
	}
	
	
	
	
	
	


}
