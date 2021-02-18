package hr.fer.oprpp1.custom.collections;

/**
 * Interface Collection representing general collection of generic type C
 * @author Iva
 *
 */

public interface Collection<C> {
	
	
	/**
	 * Method check if collections contains objects
	 * @return true if not contains object, otherwise returns false
	 */
	default public boolean isEmpty() {
		return size()==0 ? true : false;
	}
	
	/**
	 * Method for size of current objects in collection
	 * @return number of objects in collection
	 */
	public int size();	
	/**
	 * Method adds given object into collection
	 * @param value represents object that should be added into collection
	 */
	public void add(C value);
	
	
	/**
	 * Method checks if there is a given values inside collection
	 * @param value object that is checked
	 * @return true if collection contains value, otherwise false
	 */
	public boolean contains(Object value) ;
	
	
	/**
	 * Method checks if there is given value inside collection and removes one occurrence from collection
	 * @param value object that is checked
	 * @return true if collection contains value, otherwise false
	 */
	public boolean remove(Object value);
	
	
	/**
	 * Method makes new array with size of collection and fills is with collection
	 * @return array of collection content
	 */
	 public Object[] toArray();
	
	/**
	 * Method for each element of collection calls process() method
	 * @param processor
	 */
	default public void forEach(Processor<? super C> processor) {
		ElementsGetter<C> eg=this.createElementsGetter();
		while(eg.hasNextElement()) {
			C element=(C) eg.getNextElement();
			processor.process(element);
			
		}
	}
	
	
	/**
	 * Method adds all elements of other collection into current collection
	 * @param other collection that needs to be stored
	 */
	default public void addAll(Collection<? extends C> other) {
		/**
		 * Local class which extends class Processor and has different implementation of process method 
		 * @author Iva
		 *
		 */
		class LocalProcessor implements Processor<C>{
			@Override
			/**
			 * adds value to collection
			 */
			public void process(C value) {
				add(value);
					
				}
			}
		LocalProcessor processor=new LocalProcessor();
		other.forEach(processor);
	}
	
	
	/**
	 * Removes all elements from collection
	 */
	public void clear();
	
	/**
	 * Method creates elements getter for collection
	 * @return
	 */
	public ElementsGetter<C> createElementsGetter();
	
	/**
	 * Method goes through Collection col and adds all elements to this collection that pass test function of
	 * given Object tester
	 * @param col Collection that is checked
	 * @param tester implementation of Test interface with valid test function
	 */
	default void addAllSatisfying(Collection<? extends C> col, Tester<? super C> tester) {
		ElementsGetter<? extends C> eg=col.createElementsGetter();
		while(eg.hasNextElement()) {
			C element= eg.getNextElement();
			if(tester.test(element))
				this.add(element);
		}
	}
	
}
