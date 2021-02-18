package hr.fer.oprpp1.hw02.prob1;
/**
 * Class token represents each token that is generated during lexical analysis
 * @author Iva
 *
 */
public class Token {
	/**
	 * type of Token
	 */
	private TokenType type;
	/**
	 * Value of Token
	 */
	private String value;
	
	public Token(TokenType type, String value) {
		this.type=type;
		this.value=value;
	} 
	/**
	 * Getter for variable value
	 * @return value of Token
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Getter for variable type
	 * @return type of Token
	 */
	public TokenType getType() {
		return type;
	}


}
