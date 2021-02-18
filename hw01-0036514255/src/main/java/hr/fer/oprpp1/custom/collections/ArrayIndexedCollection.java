package hr.fer.oprpp1.custom.collections;
/*
 * Class ArrayIndexedCollection represents collection stored in array 
 * @author Iva
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	private int size;
	private Object[] elements;
	
	/**
	 * Constructor sets size of array up to 16
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Constructor sets size of array up to initalCapcity
	 * @throws IllegalArgumentException when given capacity is less than 1
	 */
	public ArrayIndexedCollection (int initialCapacity ) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Value size of collection is less then 1.");
		this.elements=new Object[initialCapacity];
		this.size=0;
	}
	
	/**
	 * Constructor sets size of array up to initalCapcity
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other,0);
	}
	
	/**
	 * Constructor sets size of array up to initalCapcity or size of collection other
	 */
	public ArrayIndexedCollection (Collection other, int initialCapacity ) {
		if(other==null) throw new NullPointerException();
		int newSize=initialCapacity>other.size() ? initialCapacity :other.size();
		this.elements=new Object[newSize];
		this.size=0;
		super.addAll(other);
	}
	
	/**
	 * Method for size of current objects in collection
	 * @return number of objects in collection
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Method adds given object into collection
	 * @param value represents object that should be added into collection
	 * @throws NullPointerException when value is null
	 */
	public void add(Object value) {
		if(value == null) throw new NullPointerException();
		if(this.elements.length>this.size) {
			elements[size]=value;
			this.size++;
		}else {
			Object[] newElements=new Object[size*2];
			for(int i=0;i<this.size;i++) {
				newElements[i]=elements[i];
			}			
			newElements[size]=value;
			this.size++;
			this.elements=newElements;
		}
	}
	
	/**
	 * Method gets object on specific position
	 * @param index location of object
	 * @return the object that is stored in backing array at position index
	 */
	public Object get(int index) {
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		return elements[index];
	}
	
	/**
	 * Method removes all elements from collection
	 */
	public void clear() {
		for(int i =0;i<this.size;i++) {
			elements[i]=null;
		}		
		this.size=0;
	}
	
	/**
	 * Method inserts value on specific position inside collection
	 * @param value 
	 * @param position
	 * @throws IndexOutOfBounce
	 */
	public void insert(Object value,int position) {
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		Object prevValue = value;
		for(int i=position;i<this.size;i++) {
			Object tmp = prevValue;
	        prevValue = elements[i];
	        elements[i] = tmp;
		}
		this.add(prevValue);
	}
	
	/**
	 * Method for giving first position of given value
	 * @param value variable that needs to be found in collection
	 * @return position of value variable
	 */
	public int indexOf(Object value) {
		for(int i=0;i<this.size;i++) {
			if(elements[i].equals(value))
				return i;
		}
		return -1;
	}
	
	/**
	 * MethodRemoves element at specified index from collection. 
	 * @param index of element that should be removed
	 */
	public void remove(int index) {
		if(index<0 || index>=this.size) throw new IndexOutOfBoundsException();
		for(int i=index;i<size-1;i++) {
			elements[i]=elements[i+1];
		}
		elements[this.size-1]=null;
		this.size=this.size-1;
	}
	
	/**
	 * Method checks if there is a Object with value value inside collection
	 * returns true if does, otherwise false
	 */
	public boolean contains(Object value) {
		for(int i =0;i<size;i++) {
			if(elements[i]==value)
				return true;
		}
		return false;
	}

	/**
	 * Method removes value inside collection on first position where it is found
	 * returns true if value is removed, otherwise false
	 */
	public boolean remove(Object value) {
		try {
			remove(indexOf(value));
		}catch(IndexOutOfBoundsException e){
			return false;
		}
		return true;
	}

	/**
	 * Method returns elements because it is already array with collection values
	 */
	public Object[] toArray() {
		Object[] newElements=elements.clone();
		return newElements;
	}
	
	/**
	 * Method for each element of collection calls process() method
	 * @param processor
	 */
	public void forEach(Processor processor) {
		if(processor==null) throw new  NullPointerException();
		for(int i=0;i<size;i++) {
			processor.process(elements[i]);
		}
	}

	
	
}
