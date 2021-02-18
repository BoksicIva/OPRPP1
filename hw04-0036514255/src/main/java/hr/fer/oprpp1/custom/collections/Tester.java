package hr.fer.oprpp1.custom.collections;
/**
 * Interface used for testing objects
 * @author Iva
 *
 */
public interface Tester<T> {
	/**
	 * Method that tests objects 
	 * @param obj object that needs to be checked
	 * @return true if obj passes test,otherwise false
	 */
	boolean test(T obj);
}
