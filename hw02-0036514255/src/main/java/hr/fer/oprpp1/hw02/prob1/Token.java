package hr.fer.oprpp1.hw02.prob1;
/**
 * Class token represents each token that is generated during lexical analysis
 * @author Iva
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	public Token(TokenType type, Object value) {
		this.type=type;
		this.value=value;
	} 
	
	public Object getValue() {
		return value;
	}
	
	
	public TokenType getType() {
		return type;
	}


}
