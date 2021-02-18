package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw02.prob1.Lexer;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;
/**
 * Implementation of parser for query expressions
 * @author Iva
 *
 */
public class QueryParser {
	private List<ConditionalExpression> query;
	/**
	 * Constructor for Query parser, gets string representing row of query request and parses it into ConditionalExpresions
	 * @param row
	 */
	public QueryParser(String row) {
		query=new ArrayList<ConditionalExpression>();
		Lexer lexer=new Lexer(row);
		while(true) {
			parse(lexer.nextToken(),lexer.nextToken(),lexer.nextToken());
			Token next=lexer.nextToken();
			if(next.getType()==TokenType.EOR)
				break;
			else if(next.getValue().toLowerCase().equals("and"))
				continue;
			else 
				throw new IllegalArgumentException("Query request is not valid");
		}
	}
	/**
	 * Method parse tokens and checks if order of token is correct and if token value is also correct
	 * @param getter Token for getter in query
	 * @param operator Token for operator in query
	 * @param string Token for string  literal in query
	 */
	private void parse(Token getter,Token operator,Token string) {
		IFieldValueGetter fieldGetter;
		IComparisonOperator comperisonOperator;
		if(getter.getType()==TokenType.WORD) {
			switch(getter.getValue()) {
			case "firstName":  fieldGetter=FieldValueGetter.FIRST_NAME;
							   break;
			case "lastName":   fieldGetter=FieldValueGetter.LAST_NAME;
			   				   break;
			case "jmbag":	   fieldGetter=FieldValueGetter.JMBAG;
			   				   break;
			default: throw new IllegalArgumentException("Wrong name for value getter!");
			}
		}else {
			throw new IllegalArgumentException("FieldGetter in not word!");
		}
		if(operator.getType()==TokenType.WORD || operator.getType()==TokenType.OPERATOR) {
			switch(operator.getValue()) {
			case "<":  		   comperisonOperator= ComparisonOperators.LESS;
							   break;
			case "<=":   	   comperisonOperator= ComparisonOperators.LESS_OR_EQUAL;
			   				   break;
			case ">":	   	   comperisonOperator= ComparisonOperators.GREATER;
			   				   break;
			case ">=":	   	   comperisonOperator= ComparisonOperators.GREATER_OR_EQUAL;
			   				   break;
			case "=":	   	   comperisonOperator= ComparisonOperators.EQUALS;
							   break;  			
			case "!=":	   	   comperisonOperator= ComparisonOperators.NOT_EQUALS;
			   				   break;	
			case "LIKE":	   comperisonOperator= ComparisonOperators.LIKE;
			   				   break;
			default: throw new IllegalArgumentException("Wrong combination for operator!");
			}
		}else {
			throw new IllegalArgumentException("Operator cant be string literal");
		}
		String stringLiteral=null;
		if(string.getType()==TokenType.STRING) {
			stringLiteral=string.getValue().toString();
		}else {
			throw new IllegalArgumentException("String literal in not inside quotes");
		}
		ConditionalExpression ce=new ConditionalExpression(fieldGetter, stringLiteral, (IComparisonOperator) comperisonOperator);
		query.add(ce);
		
	}
	/**
	 * Method returns true if query was of of the form jmbag="xxx" (i.e. it must have only one comparison, on attribute jmbag, 
	 * and operator must be equals). We will call queries of this form direct queries. String getQueriedJMBAG();
	 * @return
	 */
	public boolean isDirectQuery() {
		if(query.size()==1 && query.get(0).getComperisonOperator().equals(ComparisonOperators.EQUALS) && query.get(0).getFieldGetter().equals(FieldValueGetter.JMBAG) ) 
			return true;
		return false;
	}
	/**
	 * Method must return the string representing jmbag for 
	 * which was given in equality comparison in direct query. 
	 * If the query was not a direct one, method must throw IllegalStateException.
	 * @return
	 */
	public String getQueriedJMBAG() {
		if(isDirectQuery()) 
			return query.get(0).getStringLiteral();
		throw new IllegalStateException("Query is not direct query!");
	}
	/**
	 * For all queries, this method returns a list of conditional expressions from query; 
	 * @return
	 */
	public List<ConditionalExpression> getQuery(){
		List<ConditionalExpression> newQuery=new ArrayList<>();
		newQuery.addAll(query);
		return newQuery;
	}

}
