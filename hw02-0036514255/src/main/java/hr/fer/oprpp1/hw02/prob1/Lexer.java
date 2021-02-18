package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
	
	private char[] data;      // ulazni tekst 
	private Token token;      // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;
	
	
	public Lexer(String text) { 
		this.data=text.toCharArray();
		this.currentIndex=0;
		this.state=LexerState.BASIC;
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
	public void setState(LexerState state) {
		if(state== null) throw new NullPointerException();
		this.state=state;
	}
	/**
	 * Method generates token and saves it insde variable "token"
	 */
	public void generateToken() {
		if(token!=null && token.getType()==TokenType.EOF) {
			 throw new LexerException();
	    }
		if(state==LexerState.BASIC) {
			long number;
			char symbol = 0;
			if(token!=null && token.getType()==TokenType.EOF) {
					 throw new LexerException();
			}
			TokenType type = TokenType.EOF;
			String last="";
			
			while(currentIndex<data.length) {
				if(data[currentIndex]!=' '&& data[currentIndex]!='\t' && data[currentIndex]!='\r' && data[currentIndex]!='\n') {
					break;
				}else {
					currentIndex++;
				}
			}
			if(currentIndex<data.length) {
				if(Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\') {
					type=TokenType.WORD;
					while(currentIndex<data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\' )) {
						if( data[currentIndex]=='\\' ) {
							if(currentIndex+1<data.length && (data[currentIndex+1]=='\\' || (data[currentIndex+1] >='0' && data[currentIndex+1] <='9'))) {
								last+=data[currentIndex+1];
								currentIndex+=2;
							}else {
								throw new LexerException();
							}
						}else {
						last+=data[currentIndex++];
						}
					}
				}else if(data[currentIndex] >='0' && data[currentIndex] <='9') {
					type=TokenType.NUMBER;
					last+=data[currentIndex++];
					while(currentIndex<data.length && ((data[currentIndex] >='0' && data[currentIndex] <='9') || (data[currentIndex]=='.'))) {
						if(data[currentIndex]=='.') {
							last+=data[currentIndex++];
							while(currentIndex<data.length && data[currentIndex] >='0' && data[currentIndex] <='9') {
								last+=data[currentIndex];
								currentIndex++;
							}
							token=new Token(type,last);
							return;
						}
						last+=data[currentIndex++];
					}
			    }else if((data[currentIndex] >='!' && data[currentIndex] <='.')|| (data[currentIndex] >=':' && data[currentIndex] <='@')||(data[currentIndex] >='[' && data[currentIndex] <='\'')){
			    	symbol=data[currentIndex++];
			    	type=TokenType.SYMBOL;
			    }
			}
			if(last=="" && symbol==0)
				token=new Token(type,null);
			else if(type==TokenType.NUMBER) {
				try {
					number=Long.parseLong(last);
					token=new Token(type,number);
				}catch(Exception e) {
					throw new LexerException();
				}
			}else if(type==TokenType.SYMBOL) {
				token=new Token(type,symbol);
			}else {
				token=new Token(type,last);
			}
		}else {
			TokenType type = TokenType.WORD;
			String last="";
			while(currentIndex<data.length) {
				if(data[currentIndex]!=' '&& data[currentIndex]!='\t' && data[currentIndex]!='\r' && data[currentIndex]!='\n') {
					break;
				}else {
					currentIndex++;
				}
			}
			if(currentIndex<data.length) {
				if(Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\' || (data[currentIndex] >='0' && data[currentIndex] <='9')) {
					type=TokenType.WORD;
					while(currentIndex<data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\' || (data[currentIndex] >='0' && data[currentIndex] <='9'))) {
						if(data[currentIndex]=='\\') {
							last+="\\";
							currentIndex++;
						}
						else	
							last+=data[currentIndex++];
						}
			    }else if(data[currentIndex]=='#') {
			    	state=LexerState.EXTENDED;
			    	token= new Token(TokenType.SYMBOL,'#');
			    	currentIndex++;
			    	return;
			    }
			}
			if(last=="")
				token= new Token(TokenType.EOF,null);
			else
			    token= new Token(type,last);	
			
		
	}
	}

}
