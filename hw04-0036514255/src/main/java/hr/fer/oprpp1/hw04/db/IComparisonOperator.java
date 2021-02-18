package hr.fer.oprpp1.hw04.db;
/**
 * Interface for operators accepted in query
 * @author Iva
 *
 */
public interface IComparisonOperator {
	
	public boolean satisfied(String value1,String value2);
}
