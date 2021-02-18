package hr.fer.oprpp1.hw01;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for ComplexNumber class
 * @author Iva
 *
 */
public class ComplexNumberTest {


	@Test
	public void testFromReal() {
		ComplexNumber result=ComplexNumber.fromReal(1);
		ComplexNumber expected=new ComplexNumber(1,0);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testFromImaginary() {
		ComplexNumber result=ComplexNumber.fromImaginary(1);
		ComplexNumber expected=new ComplexNumber(0,1);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testFromMagnitudaAndAngle() {
		ComplexNumber result=ComplexNumber.fromMagnitudeAndAngle(1, Math.PI);
		ComplexNumber expected=new ComplexNumber(-1,0);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse() {
		ComplexNumber result=ComplexNumber.parse("-1-3i");
		ComplexNumber expected=new ComplexNumber(-1,-3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse2() {
		ComplexNumber result=ComplexNumber.parse("-3i");
		ComplexNumber expected=new ComplexNumber(0,-3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse3() {
		ComplexNumber result=ComplexNumber.parse("-1");
		ComplexNumber expected=new ComplexNumber(-1,0);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse4() {
		ComplexNumber result=ComplexNumber.parse("1+3i");
		ComplexNumber expected=new ComplexNumber(1,3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse5() {
		ComplexNumber result=ComplexNumber.parse("+1-3i");
		ComplexNumber expected=new ComplexNumber(+1,-3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testGetReal() {
		ComplexNumber result=new ComplexNumber(-1,-3);
		double resultReal=result.getReal();
		double expected=-1.0;
		
		Assertions.assertEquals(expected, resultReal);
	}
	
	@Test
	public void testGetImaginary() {
		ComplexNumber result=new ComplexNumber(-1,-3);
		double resultImag=result.getImaginary();
		double expected=-3.0;
		
		Assertions.assertEquals(expected, resultImag);
	}
	
	@Test
	public void testGetMagnitude() {
		ComplexNumber result=new ComplexNumber(-1,-3);
		double resultMag=result.getMagnitude();
		double expected=Math.sqrt(Math.pow(result.getReal(), 2)+Math.pow(result.getImaginary(), 2));
		
		Assertions.assertEquals(expected, resultMag);
	}
	
	@Test
	public void testAngle() {
		ComplexNumber result=new ComplexNumber(-1,-3);
		double resultAngle=result.getAngle();
		double expected=Math.PI+1.249045772;
		
		Assertions.assertTrue(Math.abs(resultAngle-expected) < 1E-5);
	}
	
	@Test
	public void testAdd() {
		ComplexNumber c1= new ComplexNumber(1,-5);
		ComplexNumber c2= new ComplexNumber(4,3);
		ComplexNumber result= c1.add(c2);
		ComplexNumber expected=new ComplexNumber(5,-2);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testSub() {
		ComplexNumber c1= new ComplexNumber(1,-5);
		ComplexNumber c2= new ComplexNumber(4,3);
		ComplexNumber result= c1.sub(c2);
		ComplexNumber expected=new ComplexNumber(-3,-8);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testMul() {
		ComplexNumber c1= new ComplexNumber(1,-5);
		ComplexNumber c2= new ComplexNumber(4,3);
		ComplexNumber result= c1.mul(c2);
		ComplexNumber expected=new ComplexNumber(19,-17);
		
		Assertions.assertEquals(expected, result);
	}
	

	@Test
	public void testDiv() {
		ComplexNumber c1= new ComplexNumber(1,-5);
		ComplexNumber c2= new ComplexNumber(4,3);
		ComplexNumber result= c1.div(c2);
		ComplexNumber expected=new ComplexNumber(-11.0/25,-23.0/25);
		
		Assertions.assertEquals(expected, result);
	}
	

	@Test
	public void testPow() {
		ComplexNumber c1= new ComplexNumber(1,-5);
		ComplexNumber result= c1.power(3);
		ComplexNumber expected=new ComplexNumber(-74,110);
		
		Assertions.assertEquals(expected, result);
	}
	
	public void testRoot() {
		ComplexNumber c1= new ComplexNumber(-74,110);
		ComplexNumber result= c1.root(3)[2];
		ComplexNumber expected=new ComplexNumber(1,-5);
		
		Assertions.assertEquals(expected, result);
	}
	
	public void testToString() {
		ComplexNumber c1= new ComplexNumber(-1,-5);
		String result= c1.toString();
		String expected="-1-5i";
		
		Assertions.assertEquals(expected, result);
	}
	
	
}
