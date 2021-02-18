package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class ComplexTest {

	
	
	@Test
	public void testParse() {
		Complex result=Complex.parse("-1-i3");
		Complex expected=new Complex(-1,-3);
		
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testParse2() {
		Complex result=Complex.parse("-i3");
		Complex expected=new Complex(0,-3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse3() {
		Complex result=Complex.parse("-1");
		Complex expected=new Complex(-1,0);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse4() {
		Complex result=Complex.parse("1+i3");
		Complex expected=new Complex(1,3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testParse5() {
		Complex result=Complex.parse("+1-i3");
		Complex expected=new Complex(+1,-3);
		
		Assertions.assertEquals(expected, result);
	}
	
	
	
	@Test
	public void testGetMagnitude() {
		Complex result=new Complex(-1,-3);
		double resultMag=result.module();
		double expected=Math.sqrt(Math.pow(-1, 2)+Math.pow(-3, 2));
		
		Assertions.assertEquals(expected, resultMag);
	}
	
	
	@Test
	public void testAdd() {
		Complex c1= new Complex(1,-5);
		Complex c2= new Complex(4,3);
		Complex result= c1.add(c2);
		Complex expected=new Complex(5,-2);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testSub() {
		Complex c1= new Complex(1,-5);
		Complex c2= new Complex(4,3);
		Complex result= c1.sub(c2);
		Complex expected=new Complex(-3,-8);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testMul() {
		Complex c1= new Complex(1,-5);
		Complex c2= new Complex(4,3);
		Complex result= c1.multiply(c2);
		Complex expected=new Complex(19,-17);
		
		Assertions.assertEquals(expected, result);
	}
	
	
	@Test
	public void testDiv() {
		Complex c1= new Complex(1,-5);
		Complex c2= new Complex(4,3);
		Complex result= c1.divide(c2);
		Complex expected=new Complex(-11.0/25,-23.0/25);
		
		Assertions.assertEquals(expected, result);
	}
	
	
	@Test
	public void testPow() {
		Complex c1= new Complex(1,-5);
		Complex result= c1.power(3);
		Complex expected=new Complex(-74,110);
		
		Assertions.assertEquals(expected, result);
	}
	
	public void testRoot() {
		Complex c1= new Complex(-74,110);
		Complex result= c1.root(3).get(2);
		Complex expected=new Complex(1,-5);
		
		Assertions.assertEquals(expected, result);
	}
	
	public void testToString2() {
		Complex c1= new Complex(-1,-5);
		String result= c1.toString();
		String expected="(-1-i5)";
		
		Assertions.assertEquals(expected, result);
	}
	
	
	
	@Test
	public void testFromRealShouldConstructComplexWithImaginaryPartEqualsZero() {
		Complex expected = new Complex(2.0, 0);
		assertEquals(expected, Complex.fromReal(2.0));
	}
	
	@Test
	public void testFromImaginaryShouldConstructComplexWithRealPartEqualsZero() {
		Complex expected = new Complex(0, 2.0);
		assertEquals(expected, Complex.fromImaginary(2.0));
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		Complex[] expected = new Complex[] { new Complex(1.0, 1.0),
				new Complex(-1.0, 1.0), new Complex(-1.0, -1.0),
				new Complex(1.0, -1.0), };

		Complex[] actual = new Complex[] {
				Complex.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI / 4),
				Complex.fromMagnitudeAndAngle(Math.sqrt(2), 3 * Math.PI / 4),
				Complex.fromMagnitudeAndAngle(Math.sqrt(2), 5 * Math.PI / 4),
				Complex.fromMagnitudeAndAngle(Math.sqrt(2), 7 * Math.PI / 4) };

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testComplexParseShouldReturnNewComplexOnValidFormat() {
		String[] array = new String[] { "351", "-317", "3.51", "-3.17", "i351", "-i317", "i3.51",
				"-i3.17", "i", "-1", "1", "-i", "+1", "+i", "-2.71-i3.15", "31+i24", "-1-i",
				"+2.71", "+2.71+i3.15" };

		Complex[] expected = new Complex[] { 
				new Complex(351, 0),
				new Complex(-317, 0), 
				new Complex(3.51, 0), 
				new Complex(-3.17, 0),
				new Complex(0, 351), 
				new Complex(0, -317), 
				new Complex(0, 3.51),
				new Complex(0, -3.17), 
				new Complex(0, 1), 
				new Complex(-1, 0),
				new Complex(1, 0), 
				new Complex(0, -1), 
				new Complex(1, 0),
				new Complex(0, 1), 
				new Complex(-2.71, -3.15), 
				new Complex(31, 24),
				new Complex(-1, -1), 
				new Complex(2.71, 0),
				new Complex(2.71, 3.15) };

		for (int i = 0; i < array.length; i++) {
			assertEquals(expected[i], Complex.parse(array[i]));
		}
	}
	
	@Test
	public void testComplexParseShouldThrowOnInvalidFormatAndNullValue() {

		String[] array = new String[] {  "i-3.17", "--2.71",
				"-2.71+-3.15i", "+2.71-+3.15i", "-+2.71" };
		
		for(int i=0; i < array.length; i++) {
			final String number = array[i];
			assertThrows(NumberFormatException.class, ()-> Complex.parse(number));
		}
		
		assertThrows(NullPointerException.class, ()-> Complex.parse(null));
	}
	
	@Test
	public void testGetRealAndGetImaginaryMustReturnRealAndImaginaryPart() {
		Complex cpx = new Complex(5.0, 7.0);
		
		assertEquals(5.0, cpx.getReal());
		assertEquals(7.0, cpx.getImaginary());
	}
	
	@Test
	public void testGetAngleReturnsOnlyPositiveResultBetweenZeroAndTwoPi() {
		Complex[] array = new Complex[] {
				new Complex(1.0, 1.0),
				new Complex(-1.0, 1.0), 
				new Complex(-1.0, -1.0),
				new Complex(1.0, -1.0)};
		
		for(int i = 0; i < array.length; i++) {
			if(!(array[i].angle() >= 0 && array[i].angle() <= 2 * Math.PI))
				fail("Angle must be between 0 and 2PI but was " + array[i].angle());
		}
	}
	
	@Test
	public void testGetMagnitudeAndGetAngle() {
		Complex[] array = new Complex[] { new Complex(1.0, 1.0),
				new Complex(-2.0, 2.0), new Complex(-3.0, -3.0),
				new Complex(4.0, -4.0), };
		
		for(int i = 0; i < array.length; i++) {
			assertEquals(Math.sqrt(2 * Math.pow((i + 1), 2)), array[i].module());
			assertEquals((2 * i + 1) * Math.PI / 4, array[i].angle());
		}
	}
	
	@Test
	public void testAddSubMulAndDivShouldThrowOnNullValue() {
		assertThrows(NullPointerException.class, () -> new Complex(1, 1).add(null));
		assertThrows(NullPointerException.class, () -> new Complex(1, 1).sub(null));
		assertThrows(NullPointerException.class, () -> new Complex(1, 1).multiply(null));
		assertThrows(NullPointerException.class, () -> new Complex(1, 1).divide(null));
	}
	
	@Test
	public void testAddComplexs() {
		assertEquals(new Complex(-1.0, 3.0),
				new Complex(1.0, 1.0).add(new Complex(-2.0, 2.0)));
	}
	
	@Test
	public void testSubComplexs() {
		assertEquals(new Complex(3.0, -1.0),
				new Complex(1.0, 1.0).sub(new Complex(-2.0, 2.0)));
	}
	
	@Test
	public void testMulComplexs() {
		assertEquals(new Complex(-3.0, 1.0),
				new Complex(1.0, 1.0).multiply(new Complex(-1.0, 2.0)));
	}
	
	@Test
	public void testDivComplexs() {
		assertEquals(new Complex(0.2, -0.6),
				new Complex(1.0, 1.0).divide(new Complex(-1.0, 2.0)));
	}
	
	@Test
	public void testPowerOfComplexs() {
		assertEquals(new Complex(-4.0, -4.0),
				new Complex(1.0, 1.0).power(5));
	}
	
	/*@Test
	public void testRootOfComplexs() {
		List<Complex> roots = new ArrayList<>();
		roots.add(new Complex(1.272019649514069, 0.78615137775742));
		roots.add(new Complex(-1.27201964951407, -0.78615137775742));
		};
		
		assertEquals(roots, new Complex(1.0, 2.0).root(2));
	}
	*/
	
	@Test
	public void testPowerOfComplexShouldThrowOnNegative() {
		assertDoesNotThrow(()-> new Complex(1.0, 1.0).power(0));
		assertThrows(IllegalArgumentException.class, ()-> new Complex(1.0, 1.0).power(-1));
	}
	
	@Test
	public void testRootOfComplexShouldThrowOnNegativeOrZero() {
		assertThrows(IllegalArgumentException.class, ()-> new Complex(1.0, 1.0).root(0));
		assertThrows(IllegalArgumentException.class, ()-> new Complex(1.0, 1.0).root(-1));
	}

	@Test
	public void testToString() {
		Complex first = new Complex(1, 1);
		Complex second = new Complex(1, -1);
		
		assertEquals("(1.0+i1.0)", first.toString());
		assertEquals("(1.0-i1.0)", second.toString());
	}
	
	@Test
	public void testEquals() {
		assertEquals(true, new Complex(1, 1).equals(new Complex(1, 1)));
		assertEquals(false, new Complex(1, 1).equals(new Complex(-1, 1)));
		assertEquals(false, new Complex(1, 1).equals(new Complex(1, -1)));
	}
	
}
