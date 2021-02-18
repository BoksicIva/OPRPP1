package hr.fer.oprpp1.custom.collections;

public class ObjectStack<O>{
	private ArrayIndexedCollection<O> collection;
	/**
	 * Method pushes value on top of stack
	 * @param value
	 */
	
	public ObjectStack() {
		collection = new ArrayIndexedCollection<O>();
	}
	public void push(O value) {
		collection.add(value);
	}
	
	/**
	 * Method removes last value from the stack
	 * @return last value that is removes from stack
	 */
	public O pop() {
		O last=(O) collection.get(collection.size()-1);
		if(last==null) throw new EmptyStackException();
		collection.remove(collection.size()-1);
		return last;
	}
	
	/**
	 * Method gets last value from stack but does not remove it
	 * @return last value of the stack
	 */
	public O peek() {
		O last= (O) collection.get(collection.size()-1);
		if(last==null) throw new EmptyStackException();
		return last;
	}
	
	/**
	 * Method removes all elements from stack
	 */
	public void clear() {
		this.clear();
	}
	
	
}
