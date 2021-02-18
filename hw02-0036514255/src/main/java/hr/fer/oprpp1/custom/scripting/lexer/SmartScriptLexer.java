package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 * Class SmartScriptLexer is implementation of lexer used for third task
 * @author Iva
 *
 */
public class SmartScriptLexer {
	
	private char[] data;      // ulazni tekst 
	private SmartScriptToken token;      // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private SmartScriptLexerState state;
	private int notGenerated=0;

	
	public SmartScriptLexer(String text) { 
		this.data=text.toCharArray();
		this.currentIndex=0;
		this.state=SmartScriptLexerState.BASIC;
	}
	
	/**
	 * Method calls funtion generateToken which saves generated token into private lokal variable token
	 * @return token variable
	 */
	public SmartScriptToken nextToken() { 
		generateToken();
		return token;
	}
	/**
	 * Getter method for variable token
	 * @return token
	 */
	public SmartScriptToken getToken() {
		return token;
	}
	/**
	 * Setter method for state variable
	 * @param state variable of type SmartScriptLexerState
	 */
	public void setState(SmartScriptLexerState state) {
		if(state== null) throw new SmartScriptLexerException();
		this.state=state;
	}
	/**
	 * Method generates token and saves it into variable token 
	 */
	public void generateToken() {
		if(token!=null && token.getType()==SmartScriptTokenType.EOF) {
			 throw new SmartScriptLexerException();
	    }
		notGenerated=0;
		if(state==SmartScriptLexerState.BASIC) 
			basicMode();	
	    if(notGenerated==0 && state==SmartScriptLexerState.TAG_MODE)
			tagMode();	
	   
	}	
			
	/**
	 * Method generates next token if is lexer in tag mode state
	 */
	public void tagMode() {
		SmartScriptTokenType type = SmartScriptTokenType.EOF;
		String last="";
		char symbol=0;
		skip();
		if(currentIndex<data.length) {
		if(currentIndex+1<data.length && data[currentIndex]=='{' && data[currentIndex+1]=='$') {
			currentIndex+=2;
			skip();
			while(currentIndex<data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex]=='=' )) {
				if(data[currentIndex]=='=') {
					currentIndex++;
					token=new SmartScriptToken(SmartScriptTokenType.TAG_NAME, '=');
					return;
				}else {
					last+=data[currentIndex++];
				}
			}
			token=new SmartScriptToken(SmartScriptTokenType.TAG_NAME, last);
			return;
		}else if(Character.isLetter(data[currentIndex]) ) {
			type=SmartScriptTokenType.VARIABLE;
			last+=data[currentIndex++];
			while(currentIndex<data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex]=='_' || (data[currentIndex] >='0' && data[currentIndex] <='9'))) {
				last+=data[currentIndex++];
			}
		}else if(currentIndex+1<data.length && data[currentIndex]=='$' && data[currentIndex+1]=='}') {
			currentIndex+=2;	
			state=SmartScriptLexerState.BASIC;
			if(last!="")
				token=new SmartScriptToken(type, last);
			else
				basicMode();
			return;
		}else if(data[currentIndex]=='@') {
			currentIndex++;
			if(Character.isLetter(data[currentIndex]) ) {
				type=SmartScriptTokenType.FUNKCTION;
				last+=data[currentIndex++];
				while(currentIndex<data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex]=='_' || (data[currentIndex] >='0' && data[currentIndex] <='9'))) {
					last+=data[currentIndex++];
				}
			}else {
				throw new SmartScriptLexerException("Function name not valid");
			}
	  }else if(data[currentIndex] >='0' && data[currentIndex] <='9') {
		  type=SmartScriptTokenType.DOUBLE;
		  int numOfDots=0;
		  last+=data[currentIndex++];
			while(currentIndex<data.length && ((data[currentIndex] >='0' && data[currentIndex] <='9') || (data[currentIndex]=='.'))) {
				if(data[currentIndex]=='.' && numOfDots==0) {
					last+=data[currentIndex++];
					numOfDots=1;
					}else if(data[currentIndex]!='.'){
					last+=data[currentIndex++];	
					}else {
					  break;
					}
				}
			if(numOfDots==0)
				type=SmartScriptTokenType.INTEGER;
	  }else if(data[currentIndex] =='-' && currentIndex+1<data.length && (data[currentIndex+1] >='0' && data[currentIndex+1] <='9')) {
		  type=SmartScriptTokenType.DOUBLE;
		  int numOfDots=0;
		  last+=data[currentIndex++];
			while(currentIndex<data.length && ((data[currentIndex] >='0' && data[currentIndex] <='9') || (data[currentIndex]=='.'))) {
				if(data[currentIndex]=='.' && numOfDots==0) {
					last+=data[currentIndex++];
					numOfDots++;
					}else if(data[currentIndex]!='.') {
					last+=data[currentIndex++];	
					}else {
					   break;
					}
				}
			if(numOfDots==0)
				type=SmartScriptTokenType.INTEGER;
	  }else if(data[currentIndex]=='-' || data[currentIndex]=='+' || data[currentIndex]=='/' || data[currentIndex]=='*' || data[currentIndex]=='^') {
		  type=SmartScriptTokenType.OPERATOR;
		  symbol=data[currentIndex++];
	  }else if(data[currentIndex]=='\"') {
		  type=SmartScriptTokenType.STRING;
		  currentIndex++;
		  while(currentIndex<data.length) {
			  if( data[currentIndex]=='\\' ) {
				  switch (data[currentIndex + 1]) {
			        case '\\':
			        	last+=data[currentIndex + 1];
			        	break;
					case '"':
						last+=data[currentIndex + 1];
						break;
					case 'n':
						last+='\n';
						break;
					case 'r':
						last+='\r';
						break;
					case 't':
						last+='\t';
						break;
				default:
					throw new SmartScriptLexerException("Invalid escaping inside string.");
				  }
				  currentIndex+=2;
				}else if(data[currentIndex]=='\"' ){
					currentIndex++;
					break;
				}else {
				last+=data[currentIndex++];
				}
		  } 
	  }
	 }
		if(last=="" && symbol==0)
			token=new SmartScriptToken(type,null);
		else if(type==SmartScriptTokenType.DOUBLE) {
			try {
				double number=Double.parseDouble(last);
				token=new SmartScriptToken(type,number);
			}catch(Exception e) {
				throw new SmartScriptLexerException();
			}
		}else if(type==SmartScriptTokenType.INTEGER) {
			try {
				int number=Integer.parseInt(last);
				token=new SmartScriptToken(type,number);
			}catch(Exception e) {
				throw new SmartScriptLexerException();
			}
		}else if(type==SmartScriptTokenType.OPERATOR) {
			token=new SmartScriptToken(type,symbol);
		}else {
			token=new SmartScriptToken(type,last);
		}
	}
					
		
	/**
	 * Method used for skipping white spaces
	 */
	public void skip() {
		while(currentIndex<data.length) {
			if(data[currentIndex]!=' '&& data[currentIndex]!='\t' && data[currentIndex]!='\r' && data[currentIndex]!='\n') {
				break;
			}else {
				currentIndex++;
			}
		}
	}
   /**
    * Method that generate next token if lexer is in basic state	
    */
   public void basicMode() {
	   SmartScriptTokenType type = SmartScriptTokenType.EOF;
	   String last="";
		while(currentIndex<data.length) {
			type=SmartScriptTokenType.TEXT;
			if(data[currentIndex]=='\\' ) {
				if(currentIndex+1<data.length && (data[currentIndex+1]=='{' || data[currentIndex+1]=='\\')){
					last+=data[currentIndex+1];
					currentIndex+=2;
				}else {
					throw new SmartScriptLexerException();
				}
			}else if(data[currentIndex]=='{'){
				state=SmartScriptLexerState.TAG_MODE;
				if(last!="") {
					token=new SmartScriptToken(type, last);
					notGenerated=1;
				}else
					notGenerated=0;
				return;
			}
			else{
				last+=data[currentIndex++];
			}
		}
		if(last=="") 
			token=new SmartScriptToken(SmartScriptTokenType.EOF, null);
		else
			token=new SmartScriptToken(type, last);
		notGenerated=1;
     }
   
}
