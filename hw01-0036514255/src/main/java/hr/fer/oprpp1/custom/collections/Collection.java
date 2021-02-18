package hr.fer.oprpp1.custom.collections;

import hr.fer.oprpp1.custom.collections.Processor;

/**
 * Class Collection representing general collection of objects
 * @author Iva
 *
 */

public class Collection {
	
	/**
	 * Protected default constructor
	 */
	protected Collection() {}
	
	
	/**
	 * Method check if collections contains objects
	 * @return true if does not contains object, otherwise returns false
	 */
	public boolean isEmpty() {
		return size()==0;
	}
	
	/**
	 * Method for size of current objects in collection
	 * @return number of ocjects in collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Method adds given object into collection
	 * @param value represents object that should be added into collection
	 */
	public void add(Object value) {}
	
	
	/**
	 * Method checks if there is a given values inside collection
	 * @param value object that is checked
	 * @return true if collection contains value, otherwise false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	
	/**
	 * Method checks if there is given value inside collection and removes one occurrence from collection
	 * @param value object that is checked
	 * @return true if collection contains value, otherwise false
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	
	/**
	 * Method makes new array with size of collection and fills is with collection
	 * @return array of collection content
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method for each element of collection calls process() method
	 * @param processor
	 */
	public void forEach(Processor processor) {}
	
	
	/**
	 * Method adds all elements of other collection into current collection
	 * @param other collection that needs to be stored
	 */
	public void addAll(Collection other) {
		/**
		 * Local class which extends class Procesor and has difrent implementation of procces method 
		 * @author Iva
		 *
		 */
		class LocalProcessor extends Processor{
			@Override
			/**
			 * adds value to collection
			 */
			public void process(Object value) {
				add(value);
					
				}
			}
		LocalProcessor processor=new LocalProcessor();
		other.forEach(processor);
	}
	
	
	/**
	 * Removes all elements from collection
	 */
	public void clear() {}
	
	
	
}
