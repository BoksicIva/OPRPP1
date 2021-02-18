package hr.fer.oprpp1.hw04.db;
/**
 * Interface for filtering data
 * @author Iva
 *
 */
public interface IFilter {
	
	public boolean accepts(StudentRecord record);
}
