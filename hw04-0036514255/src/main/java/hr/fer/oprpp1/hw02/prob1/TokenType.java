package hr.fer.oprpp1.hw02.prob1;
/**
 * Types of token
 * @author Iva
 *
 */
public enum TokenType {
	/**
	 * EOR end of row 
	 */
	EOR, 
	/**
	 * WORD is all that contains isLetter chars
	 */
	WORD,
	/**
	 * word inside quotes
	 */
	STRING,
	/**
	 * one of supported operators
	 */
	OPERATOR
}
