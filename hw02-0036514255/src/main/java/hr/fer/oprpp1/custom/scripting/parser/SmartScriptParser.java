package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
/**
 * Class SmartScriptParser is used for parsing documents
 * @author Iva
 *
 */
public class SmartScriptParser {
	private SmartScriptLexer lexer;
	private DocumentNode documentNode;
	
	public SmartScriptParser(String documentBody) {
		this.lexer=new SmartScriptLexer(documentBody);
		parse();
	}
	
	public SmartScriptLexer getLexer() {
		return lexer;
	}

	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	
	/**
	 * Method parses document using lexer
	 */
	public void parse() {
		ObjectStack stack=new ObjectStack();
		stack.push(new DocumentNode());
		try {
		while(true) {
			SmartScriptToken token=lexer.nextToken();
			
			if(token.getType() == SmartScriptTokenType.TEXT) {
				TextNode textNode=new TextNode((String)token.getValue());
				((Node) stack.peek()).addChildNode(textNode);
			}else if(token.getType() == SmartScriptTokenType.TAG_NAME) {
				String tagName=(String)token.getValue();
				switch(tagName.toLowerCase()) {
				case "for":{
					token=lexer.nextToken();
					ArrayIndexedCollection array = new ArrayIndexedCollection();
					ForLoopNode forNode;
					while(token.getType()!=SmartScriptTokenType.TEXT ) {
						if (token.getType() == SmartScriptTokenType.EOF) 
							throw new SmartScriptParserException();
						if (token.getType() == SmartScriptTokenType.INTEGER) {
							ElementConstantInteger elementInt=new ElementConstantInteger((Integer) token.getValue());
							array.add(elementInt);
						}
						else if (token.getType() == SmartScriptTokenType.DOUBLE) {
							ElementConstantDouble elementDouble=new ElementConstantDouble((Double) token.getValue());
							array.add(elementDouble);
						}
						else if (token.getType() == SmartScriptTokenType.STRING) {
							ElementString elementString=new ElementString((String) token.getValue());
							array.add(elementString);
						}else if (token.getType() == SmartScriptTokenType.VARIABLE) {
							ElementVariable elementVariable=new ElementVariable((String) token.getValue());
							array.add(elementVariable);
						}
						token=lexer.nextToken();
					
					}
					if (!(array.get(0) instanceof ElementVariable)) {
						throw new SmartScriptParserException();
					}
					if (array.size()!= 3 || array.size()!= 4) {
						throw new SmartScriptParserException();
					}
					if (array.size() == 3) {
						forNode= new ForLoopNode((ElementVariable) array.get(0), (Element) array.get(1), (Element) array.get(2), null);
					}else {
						forNode= new ForLoopNode((ElementVariable) array.get(0), (Element) array.get(1), (Element) array.get(2), (Element) array.get(3));
					}
					((Node) stack.peek()).addChildNode(forNode);
					stack.push(forNode);
					break;
				}
				case "end":{
					if (stack.isEmpty()) {
						throw new SmartScriptParserException();
					}

					stack.pop();
					break;
					
				}
				case "=" : {
					token=lexer.nextToken();
					ArrayIndexedCollection array = new ArrayIndexedCollection();
					EchoNode echoNode;
					while(token.getType()!=SmartScriptTokenType.TEXT ) {
						if (token.getType() == SmartScriptTokenType.EOF) 
							throw new SmartScriptParserException();
						if (token.getType() == SmartScriptTokenType.INTEGER) {
							ElementConstantInteger elementInt=new ElementConstantInteger((Integer) token.getValue());
							array.add(elementInt);
						}
						else if (token.getType() == SmartScriptTokenType.DOUBLE) {
							ElementConstantDouble elementDouble=new ElementConstantDouble((Double) token.getValue());
							array.add(elementDouble);
						}
						else if (token.getType() == SmartScriptTokenType.STRING) {
							ElementString elementString=new ElementString((String) token.getValue());
							array.add(elementString);
						}else if (token.getType() == SmartScriptTokenType.VARIABLE) {
							ElementVariable elementVariable=new ElementVariable((String) token.getValue());
							array.add(elementVariable);
						}else if (token.getType() == SmartScriptTokenType.OPERATOR) {
							ElementOperator operator=new ElementOperator((String) token.getValue());
							array.add(operator);
						}else if (token.getType() == SmartScriptTokenType.FUNKCTION) {
							ElementFunction function=new ElementFunction((String) token.getValue());
							array.add(function);
						}else {
							throw new SmartScriptParserException();
						}
						token=lexer.nextToken();
					}
					Element[] elements=(Element[]) new Object[array.size()];
					for(int i=0;i<array.size();i++) {
						elements[i]=(Element) array.get(i);
					}
					echoNode=new EchoNode(elements);
					((Node) stack.peek()).addChildNode(echoNode);
					stack.push(echoNode);
					
					break;
				} 
				default:
					throw new SmartScriptParserException();
				}
				
			}
			
			
			
			
			else if(token.getType()==SmartScriptTokenType.EOF) {
				break;
			}
		}
		if (stack.size() != 1) {
			throw new SmartScriptParserException();
		}
		documentNode = (DocumentNode) stack.pop();
		}catch(Exception e) {
			throw new SmartScriptParserException();
		}
	}

}
