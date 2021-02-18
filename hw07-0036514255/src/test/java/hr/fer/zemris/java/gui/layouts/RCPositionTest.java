package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RCPositionTest {
	
	@Test
	public void ParseTestBasic() {
		RCPosition rc1 = RCPosition.parse("3,7");
		assertEquals(3, rc1.getRow());
		assertEquals(7, rc1.getColumn());

		RCPosition rc2 = RCPosition.parse("33 ,2");
		assertEquals(33, rc2.getRow());
		assertEquals(2, rc2.getColumn());

		RCPosition rc3 = RCPosition.parse(" 17 ,    9    ");
		assertEquals(17, rc3.getRow());
		assertEquals(9, rc3.getColumn());
	}

	@Test
	public void DoubleParametersParseTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("3.33,7");
		});
	}

	@Test
	public void LessThanRequiredParametersParseTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("7");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("7,");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("7,  ");
		});
	}

	@Test
	public void MoreThanRequiredParamtersParseTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("3.33,7,4.12");
		});
	}

	@Test
	public void InvalidInputParseTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("31a,7");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("3,,7");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("3-7");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition.parse("3  7");
		});
	}

}
