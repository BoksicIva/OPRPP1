package hr.fer.oprpp1.hw01.demo;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class ComplexDemo {
	
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1]; 
		System.out.println(c3);
		
		
		/*ComplexNumber c4 = ComplexNumber.parse("-1-3i");
		System.out.println(c4);
		/*ComplexNumber c4 = new ComplexNumber(4, -1);
		System.out.println(c4.getAngle());
		ComplexNumber c5 = new ComplexNumber(-1, 1);
		System.out.println(c1.power(3));
		ComplexNumber c6 = new ComplexNumber(1,-1);
		System.out.println(c6.getAngle());
		ComplexNumber c7 = new ComplexNumber(-1, -1);
		System.out.println(ComplexNumber.fromMagnitudeAndAngle(2, 1.57));
		System.out.println(c1.getAngle());
		System.out.println(c1.power(3));*/

		
	}

}
