package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
	
	private char[] data;      // ulazni tekst 
	private Token token;      // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	
	
	
	public Lexer(String text) { 
		this.data=text.toCharArray();
		this.currentIndex=0;
	}
	
	/**
	 * Method start generating next token when it is called
	 * @return token
	 */
	public Token nextToken() { 
		generateToken();
		return token;
	}
	
	
	/**
	 * Getter method for variable token, does not start generating next token when it is called
	 * @return token
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * Setter method for variable state
	 * @param state
	 */
	
	/**
	 * Method generates token and saves it inside variable "token"
	 */
	public void generateToken() {
		if(token!=null && token.getType()==TokenType.EOR) {
			 throw new LexerException();
	    }
		String operators= "<>=!";
		TokenType type = TokenType.EOR;
		String last="";
		
		while(currentIndex<data.length) {
			if(data[currentIndex]!=' '&& data[currentIndex]!='\t' && data[currentIndex]!='\r' && data[currentIndex]!='\n') {
				break;
			}else {
				currentIndex++;
			}
		}
		if(currentIndex<data.length) {
			if(Character.isLetter(data[currentIndex])) {
				type=TokenType.WORD;
				while(currentIndex<data.length && Character.isLetter(data[currentIndex])) {
					last+=data[currentIndex++];
				}
			}else if(data[currentIndex] =='\"') {
				type=TokenType.STRING;
				currentIndex++;
				while(currentIndex<data.length && data[currentIndex]!='\"') {
						last+=data[currentIndex];
						currentIndex++;
						}
						
				currentIndex++;
		    }else if(operators.indexOf(data[currentIndex])!=-1 ){
		    	last+=data[currentIndex++];
		    	if(operators.indexOf(data[currentIndex])!=-1 )
		    		last+=data[currentIndex++];
		    	type=TokenType.OPERATOR;
		    }
		}
		if(last=="") {
			token=new Token(type,null);
		}else {
			token=new Token(type,last);
		}
		
	}

}
