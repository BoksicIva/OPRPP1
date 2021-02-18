package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class LinkedListIndexCollection is variation of class Collection
 * @author Iva
 *
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * Class for implementation of list nodes with previous and next element
	 * @author Iva
	 *
	 */
	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object storage;
		public ListNode(Object val) {
            this.storage = val;
        }
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	private long modificationCount=0;
	
	/**
	 * Default constructor create empty collection 
	 */
	public LinkedListIndexedCollection() {
		this.first=null;
		this.last=null;
		this.size=0;
	}
	
	/**
	 * Constructor uses other collection to make new one 
	 * @param other
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}
	
	/**
	 * Method giving size of collection that is stored
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Method adds the given object into this collection at the end of collection
	 * @throws NullPointerExveption
	 */
	public void add(Object value) {
		if(value==null) throw new NullPointerException();
		ListNode newNode=new ListNode(value);
        newNode.storage=value;
        newNode.next=null;
        newNode.previous=last;
        if(last==null){
            first=newNode;
        }else {
            last.next=newNode;
        }
        last=newNode;
		this.size++;
		this.modificationCount++;
	}
	
	/**
	 * Method returns the object that is stored in linked list at position index
	 * @param index position of wanted object
	 * @return value of wanted object 
	 */
	public Object get(int index) {
		ListNode current;
		if(index <0 || index >=this.size()) throw new IndexOutOfBoundsException();
		if(index <= this.size/2) {
			current=this.first;
			for(int i=0;i<index;i++) {
				current=current.next;
				}
		}else {
			current=this.last;
			for(int i=this.size-1;i>index;i--) {
				current=current.previous;
			}
			
		}
		return current.storage;
	}
	
	/**
	 * Removes all elements from the collection
	 */
	public void clear() {
		this.first=null;
		this.last=null;
		this.size=0;
		this.modificationCount++;
	}
	
	/**
	 * Method inserts the given value at given position
	 * @param value that needs to be inserted
	 * @param position place where value needs to be inserted inside collection
	 */
	public void insert(Object value,int position) {
		ListNode newFirst=this.first;
		int oldSize=this.size;
		this.first=null;
		this.last=null;
		if(position <0 || position >oldSize) throw new IndexOutOfBoundsException();
		if(position==oldSize) {
			add(value);
		}else {
			this.size=0;
			for(int i=0;i<oldSize;i++) {
				if(i==position) {
					add(value);
				}else {
					add(newFirst.storage);
					newFirst=newFirst.next;
				}
				
			}
			add(newFirst.storage);
		}
		this.modificationCount++;
	}
	
	/**
	 * Method searches the collection and returns the index of first occurrence of value
	 * @param value that needs to be found
	 * @return first index of found value
	 */
	public int indexOf(Object value) {
		ListNode current=this.first;
		for(int i=0;i<this.size;i++) {
			if(current.storage==value) {
				return i;
			}
			current=current.next;
		}
		return -1;
	
	}
	
	/**
	 * Method removes element at given index inside collection
	 * @param index position of element that needs to be removed
	 */
	public void remove(int index) {
		ListNode current=this.first;
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		for(int i=0;i<index;i++) {
			current=current.next;
		}
		if (index == 0) {
	        first = current.next;
	    } else {
	        current.previous.next = current.next;
	    }
	    this.size=this.size--;
		this.modificationCount++;
	}

	/**
	 * Method checks if there is a Object with value value inside collection
	 * returns true if does, otherwise false
	 */
	public boolean contains(Object value) {
		if(indexOf(value)!=-1)
			return true;
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
	 * Method makes new array with size of LinkedListIndexColection and fills is with collection values
	 * @return new array
	 */
	public Object[] toArray() {
		ListNode newHead=first;
		Object[] array=new Object[size];
		for(int i =0;i<size;i++) {
			array[i]=newHead.storage;
			newHead=newHead.next;
		}
		return array;
	}

	/**
	 * Static class used for implementation of method createElementsGetter
	 * @author Iva
	 *
	 */
	private static class ListElementsGetter implements ElementsGetter{
		private LinkedListIndexedCollection listCollection;
		private ListNode first;
		private long savedModificationCount;
		private int usedElements;
		
		public ListElementsGetter(LinkedListIndexedCollection collection) {
			this.listCollection=collection;
			this.first=collection.first;
			this.savedModificationCount=collection.modificationCount;
			this.usedElements=collection.size;
		}
		
		@Override
		public Object getNextElement() {
			if(hasNextElement()) {
				ListNode element=first;
				first=first.next;
				usedElements--;
				return element.storage;
			}else
				throw new NoSuchElementException("Collection has no more elements inside.");
		}

		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != listCollection.modificationCount) {
				throw new ConcurrentModificationException("Collextion has been modified.");
			}else {
				if(usedElements <= 0)
					return false;
				return true;
			}
		}
		
		
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new ListElementsGetter(this);
	}

	@Override
	public String toString() {
		return "LinkedListIndexedCollection [size=" + size + ", first=" + first + ", last=" + last
				+ ", modificationCount=" + modificationCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + (int) (modificationCount ^ (modificationCount >>> 32));
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LinkedListIndexedCollection))
			return false;
		LinkedListIndexedCollection other = (LinkedListIndexedCollection) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (modificationCount != other.modificationCount)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	
	
	

}
