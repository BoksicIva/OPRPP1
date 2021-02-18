package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class representing implementation of complex number with roots and constant
 * @author Iva
 *
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;
	
	/**
	 * Constructor for class ComplexRootedPolynomial
	 * @param constant of rooted polynomial complex number
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant=constant;
		this.roots=new Complex[roots.length];
		int index=0;
		for(var root:roots) {
			this.roots[index++]=root;
		}
		
	}
	
	/**
	 * Method computes polynomial value at given point z 
	 * @param z complex number for which polynomial value is calculated
	 * @return complex number representing value of polynomial value
	**/
	public Complex apply(Complex z) {
		Complex polynomial=this.constant;
		Complex sub;
		for(Complex root: this.roots) {
			sub=z.sub(root);
			polynomial=polynomial.multiply(sub);
		}
		return polynomial;
	}
	
	/**
	 * Method converts this representation to ComplexPolynomial type 
	 * @return new ComplexPolynomial object
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial newComplexPolynomial=new ComplexPolynomial(constant);
		for(int i=0;i<roots.length;i++) {
			ComplexPolynomial nextToMultyply=new ComplexPolynomial( roots[i].negate(),Complex.ONE);
			newComplexPolynomial=newComplexPolynomial.multiply(nextToMultyply);
		}
		return newComplexPolynomial;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(constant.toString());
		for(int i=0;i<roots.length;i++) {
			sb.append("*");
			sb.append("(z-");
			sb.append(roots[i].toString());
			sb.append(")");
		}
		return sb.toString();
	}
	
	/**
	 * Method finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1 ; first root has index 0, second index 1, etc
	 * @param z
	 * @param treshold 
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index=0;
		for(Complex root:roots) {
			if(z.sub(root).module() < treshold)
				return index;
			index++;
		}
		return -1;
	}
	
	/**
	 * Method from List of Complex numbers makes new instance of ComplexRootedPolynomial which roots are elements of list
	 * @param lines list of complex numbers
	 * @return new ComplexRootedPolynomial with roots that are given in list of complex numbers
	 */
	public static ComplexRootedPolynomial toComplexRootedPolynomial(List<Complex> lines) {
		Complex[] roots=new Complex[lines.size()];
		int index=0;
		for(Complex line:lines) {
			roots[index++]=line;
		}
		return new ComplexRootedPolynomial(Complex.ONE, roots);
	}

	

}
