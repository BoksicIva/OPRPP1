package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
/**
 * Class representing implementation of complex number with roots
 * @author Iva
 *
 */
public class ComplexPolynomial {
	private Complex[] factors;
	
	/**
	 * Constructor for ComplexPolynomial class
	 * @param factors of Complex number
	 */
	public ComplexPolynomial(Complex ...factors) {
		int index=0;
		this.factors=new Complex[factors.length];
		for(var factor:factors) {
			this.factors[index++]=factor;
		}
	}
	
	/**
	 * Method returns order of this polynom;
	 * eg. For (7+2i)z^3+2z^2+5z+1 returns 3 
	 * @return
	 */
	public short order() {
		return (short) (factors.length-1);
	}
	
	/**
	 * Method computes a new polynomial this*p
	 * @param p
	 * @return
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int oldLength=factors.length;
		int pLength=p.factors.length;
		int newLength=oldLength+pLength-1;
		Complex[] newFactors=new Complex[newLength];
		for(int i=0;i<newLength;i++) {
			newFactors[i]=Complex.ZERO;
		}
		
		for(int i=0;i<oldLength;i++) {
			for(int j=0;j<pLength;j++) {
				newFactors[i+j]=newFactors[i+j].add(factors[i].multiply(p.factors[j]));
			}
		}
		 return new ComplexPolynomial(newFactors);
	}
	
	
	
	/**
	 * Method computes first derivative of this polynomial; for example, for 
	 *  (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5 
	 * @return new ComplexPolynomial contains factors of first derivative
	 */
	public ComplexPolynomial derive() {
		Complex[] newComplexPolynomal=new Complex[this.factors.length-1];
		for(int i=1;i<factors.length;i++) {
			Complex cn=new Complex(i,0);
			newComplexPolynomal[i-1]=factors[i].multiply(cn);
		}
		return new ComplexPolynomial(newComplexPolynomal);
	}
	
	/**
	 * Method computes polynomial value at given point z 
	 * @param z given Complex number for which polynomial value should be calculated
	 * @return polynomial value of z 
	 */
	public Complex apply(Complex z) {
		Complex mul;
		Complex polynomial=Complex.ZERO;
		int index=0;
		for(Complex factor: this.factors) {
			mul=z.power(index).multiply(factor);
			polynomial=polynomial.add(mul);
			index++;
		}
		return polynomial;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=factors.length-1;i>0;i--) {
			sb.append(factors[i].toString());
			sb.append("*");
			sb.append("z^"+i);
			sb.append("+");
		}
		sb.append(factors[0].toString());
		return sb.toString();
	} 

}
