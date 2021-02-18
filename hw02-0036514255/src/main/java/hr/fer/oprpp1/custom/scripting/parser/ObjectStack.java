package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class ObjectStack {
	ArrayIndexedCollection arrayCollection;
	/**
	 * Method pushes value on top of stack
	 * @param value
	 */
	public void push(Object value) {
		arrayCollection.add(value);
	}
	
	/**
	 * Method removes last value from the stack
	 * @return last value that is removes from stack
	 */
	public Object pop() {
		Object last=arrayCollection.get(arrayCollection.size()-1);
		if(last==null) throw new EmptyStackException();
		arrayCollection.remove(arrayCollection.size()-1);
		return last;
	}
	
	/**
	 * Method gets last value from stack but does not remove it
	 * @return last value of the stack
	 */
	public Object peek() {
		Object last= arrayCollection.get(arrayCollection.size()-1);
		if(last==null) throw new EmptyStackException();
		return last;
	}
	
	/**
	 * Method removes all elements from stack
	 */
	public void clear() {
		arrayCollection.clear();
	}
	
	/**
	 * Method return size of stack
	 * @return number of elements on stack
	 */
	public int size() {
		return arrayCollection.size()
;	}
	
	/**
	 * Method check if collections contains objects
	 * @return true if not contains object, otherwise returns false
	 */
	 public boolean isEmpty() {
		return size()==0;
	}
	
	
}
