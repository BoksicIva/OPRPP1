package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

/*
 * Class ArrayIndexedCollection represents collection of A stored in array 
 * @author Iva
 *
 */
public class ArrayIndexedCollection<A> implements List<A>{
	/**
	 * number of elements stored in array
	 */
	private int size; 
	/**
	 * array of elements
	 */
	private A[] elements;
	/**
	 * long value used to check if some modification in array happened
	 */
	private long modificationCount=0;
	
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection (int initialCapacity ) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Value size of collection is less then 1.");
		this.elements=(A[])new Object[initialCapacity];
		this.size=0;
	}
	
	/**
	 * Constructor sets size of array up to initalCapcity
	 */
	public ArrayIndexedCollection(Collection<? extends A> other) {
		this(other,0);
	}
	
	/**
	 * Constructor sets size of array up to initalCapcity or size of collection other
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection (Collection<? extends A> other, int initialCapacity ) {
		if(other==null) throw new NullPointerException();
		int newSize=initialCapacity>other.size() ? initialCapacity :other.size();
		this.elements=(A[]) new Object[newSize];
		this.size=0;
		addAll(other);
	}
	
	/**
	 * Method for size of current objects in collection
	 * @return number of objects in collection
	 */
	public int size() {
		return this.size;
	}
	
	public int getSizeOfArray() {
		return elements.length;
	}
	/**
	 * Method adds given object into collection
	 * @param value represents object that should be added into collection
	 * @throws NullPointerException when value is null
	 */
	public void add(A value) {
		if(value == null) throw new NullPointerException();
		if(this.elements.length>this.size) {
			elements[size]=value;
			this.size++;
			this.modificationCount++;
		}else {
			@SuppressWarnings("unchecked")
			A[] newElements=(A[])new Object[size*2];
			for(int i=0;i<this.size;i++) {
				newElements[i]=elements[i];
			}			
			newElements[size]=value;
			this.size++;
			this.elements= newElements;
			this.modificationCount++;
		}
	}
	
	/**
	 * Method gets object on specific position
	 * @param index location of object
	 * @return the object that is stored in backing array at position index
	 */
	@Override
	public A get(int index) {
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		return elements[index];
	}
	
	/**
	 * Method removes all elements from collection
	 */
	@Override
	public void clear() {
		for(int i =0;i<this.size;i++) {
			elements[i]=null;
		}		
		this.size=0;
		this.modificationCount++;
	}
	
	/**
	 * Method inserts value on specific position inside collection
	 * @param value 
	 * @param position
	 * @throws IndexOutOfBounce
	 */
	@Override
	public void insert(A value,int position) {
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		A prevValue = value;
		for(int i=position;i<this.size;i++) {
			A tmp = prevValue;
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
	@Override
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
	@Override
	public void remove(int index) {
		if(index<0 || index>=this.size) throw new IndexOutOfBoundsException();
		for(int i=index;i<size-1;i++) {
			elements[i]=elements[i+1];
		}
		elements[this.size-1]=null;
		this.size=this.size-1;
		this.modificationCount--;
	}

	/**
	 * Method checks if there is a Object with value value inside collection
	 * returns true if does, otherwise false
	 */
	@Override
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
	@Override
	public boolean remove(Object value) {
		try {
			remove(indexOf(value));
		}catch(IndexOutOfBoundsException e){
			return false;
		}
		return true;
	}

	/**
	 * Method allocates new array with size equals to the size of this collections, fills it with collection content and returns the array.
	 */
	@Override
	public A[] toArray() {
		A[] newArray=elements.clone();
		return newArray;
	}

	
	
	/**
	 * Private static class ArrayElementsGetter which implements ElementsGetter interface
	 * and has implementation of getNextElement and hasNextElement methods
	 * @author Iva
	 *
	 */
	private static class ArrayElementsGetter<A> implements ElementsGetter<A>{
		private int  index;
		private int savedSize;
		private long savedModificationCount;
		private ArrayIndexedCollection<A> collection;
		
		public ArrayElementsGetter(ArrayIndexedCollection<A> arrayCollection) {
			this.collection=arrayCollection;
			this.index=0;
			this.savedSize=arrayCollection.size;
			this.savedModificationCount=arrayCollection.modificationCount;
		}
		
		/**
		 * Method returns element from saved index and remembers new index as index++
		 * @throws IndexOutOfBoundsException if array has no more elements
		 */
		@Override
		public A getNextElement() {
			if(hasNextElement())
				return collection.elements[index++];
			else
				throw new IndexOutOfBoundsException("Collection has no more elements inside.");
			
		}

		/**
		 * Method checks if there is more unused elements in array and if there was no modification inside array
		 * @throws ConcurrentModificationException if modification happened
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Collextion has been modified.");
			}else {
				if(index >= savedSize)
					return false;
				return true;
			}
		}
		
	}
	@Override
	public ElementsGetter<A> createElementsGetter() {
		return new ArrayElementsGetter<A>(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + (int) (modificationCount ^ (modificationCount >>> 32));
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ArrayIndexedCollection))
			return false;
		@SuppressWarnings("unchecked")
		ArrayIndexedCollection<A> other = (ArrayIndexedCollection<A>) obj;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (modificationCount != other.modificationCount)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	
	
	
	
	
}
