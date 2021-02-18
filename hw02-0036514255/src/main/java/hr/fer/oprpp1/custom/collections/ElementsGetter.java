package hr.fer.oprpp1.custom.collections;
/**
 * Interface ElementsGetter has two methods hasNextElement and getNexElement and inside classes ArrayIndexedCollection and 
 * LinkedListIndexedCollextion private class implement this interface
 * @author Iva
 *
 */
public interface ElementsGetter {
	
	/**
	 * Method checks if there is more elements inside collection
	 * @return true if does,otherwise false
	 */
	public boolean hasNextElement();
	
	/**
	 * Method gets next element of collection
	 * @return element of collection
	 */
	public Object getNextElement();
	
	
	/**
	 * Method calls process function over all remaining element of collection
	 * @param p Processor from which is implementation of funtion process
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
