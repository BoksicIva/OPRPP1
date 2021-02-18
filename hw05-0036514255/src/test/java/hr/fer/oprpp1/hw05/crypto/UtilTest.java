package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHexToByteReturnsCorrectArray() {
		assertArrayEquals(new byte [] {1, -82, 34}, Util.hextobyte("01aE22"));
		assertArrayEquals(new byte [] {}, Util.hextobyte(""));
	}
	
	@Test
	public void testByteToHexReturnsCorrectString() {
		assertEquals("01ae22", Util.bytetohex(new byte [] {1, -82, 34}));
		assertEquals("", Util.bytetohex(new byte [] {}));
	}
	
	
	@Test
	public void testHexToByteThrows() {
		assertThrows(IllegalArgumentException.class, ()->{Util.hextobyte("01aE2");});
		assertThrows(IllegalArgumentException.class, ()->{Util.hextobyte("01aZ22");});
		assertThrows(NullPointerException.class, ()->{Util.hextobyte(null);});
	}
	
	@Test
	public void testByteToHexThrows() {
		assertThrows(NullPointerException.class, ()->{Util.bytetohex(null);});
	}
}
