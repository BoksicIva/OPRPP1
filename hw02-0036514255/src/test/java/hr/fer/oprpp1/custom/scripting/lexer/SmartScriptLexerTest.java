package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw02.prob1.Lexer;

public class SmartScriptLexerTest {
	
	
	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}
	@Test
	void testText() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n");

		lexer.nextToken();
		assertEquals(SmartScriptTokenType.TEXT,lexer.getToken().getType());
		assertEquals("This is sample text.\r\n",lexer.getToken().getValue());
		
	}
	
	@Test
	void testForloopVariable() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $} \r\n");

		lexer.nextToken();
		lexer.nextToken();
		assertEquals(SmartScriptTokenType.VARIABLE,lexer.getToken().getType());
		assertEquals("i",lexer.getToken().getValue());
		
	}
	
	@Test
	void testFoorLoop2() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i-1.35bbb\"1\" $}");

		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "FOR"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.DOUBLE, -1.35), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "bbb"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "1"), lexer.nextToken());

		assertEquals(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	void testEscapingInEchoTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");

		assertEquals(new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows "), lexer.nextToken());
		assertEquals(SmartScriptTokenType.TAG_NAME, lexer.nextToken().getType());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \"Long\" Smith"), lexer.nextToken());


		assertEquals(new SmartScriptToken(SmartScriptTokenType.TEXT, "."), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	void testFoorLoop3() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= i i * @sin  \"0.000\" @decfmt $}");

		assertEquals(SmartScriptTokenType.TAG_NAME, lexer.nextToken().getType());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.OPERATOR, '*'), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.FUNKCTION, "sin"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "0.000"), lexer.nextToken());
		assertEquals(new SmartScriptToken(SmartScriptTokenType.FUNKCTION, "decfmt"), lexer.nextToken());

		assertEquals(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	

}
