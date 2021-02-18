package hr.fer.oprpp1.hw04.db;
/**
 * Class ConditionalExpression represents one query request 
 * @author Iva
 *
 */
public class ConditionalExpression {
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comperisonOperator;

	/**
	 * Constructor for ConditionalExpression 
	 * @param fieldGetter
	 * @param stringLiteral
	 * @param comperisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comperisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comperisonOperator = comperisonOperator;
	}

	/**
	 * Getter for fieldGetter variable
	 * @return fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for stringLiteral variable
	 * @return stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for comperisonOperator variable
	 * @return comperisonOperator
	 */
	public IComparisonOperator getComperisonOperator() {
		return comperisonOperator;
	}


	

}
