package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * Parameterized class LinkedListIndexCollection implements interface List which implements interface collection
 * @author Iva
 *
 */
public class LinkedListIndexedCollection<L> implements List<L>{
	
	/**
	 * Class for implementation of list nodes with previous and next element
	 * @author Iva
	 *
	 */
	private static class ListNode<L>{
		ListNode<L> previous;
		ListNode<L> next;
		L storage;
		public ListNode(L val) {
            this.storage = val;
        }
	}
	
	private int size;
	private ListNode<L> first;
	private ListNode<L> last;
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
	public LinkedListIndexedCollection(Collection<? extends L> other) {
		addAll(other);
	}
	
	/**
	 * Method giving size of collection that is stored
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Method adds the given object into this collection at the end of collection
	 * @throws NullPointerExveption
	 */
	@Override
	public void add(L value) {
		if(value==null) throw new NullPointerException();
		ListNode<L> newNode=new ListNode<>(value);
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
	@Override
	public L get(int index) {
		ListNode<L> current;
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
	@Override
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
	@Override
	public void insert(L value,int position) {
		ListNode<L> newFirst=this.first;
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
	@Override
	public int indexOf(Object value) {
		ListNode<L> current=this.first;
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
	@Override
	public void remove(int index) {
		ListNode<L> current=this.first;
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		for(int i=0;i<index;i++) {
			current=current.next;
		}
		if (index == 0) {
	        first = current.next;
	    } else {
	        current.previous.next = current.next;
	    }
	    this.size--;
		this.modificationCount++;
	}

	/**
	 * Method checks if there is a Object with value value inside collection
	 * returns true if does, otherwise false
	 */
	@Override
	public boolean contains(Object value) {
		if(indexOf(value)!=-1)
			return true;
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
	 * Method makes new array with size of LinkedListIndexColection and fills is with collection values
	 * @return new array
	 */
	@Override
	public Object[] toArray() {
		ListNode<L> newHead=first;
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
	private static class ListElementsGetter<L> implements ElementsGetter<L>{
		private LinkedListIndexedCollection<L> listCollection;
		private ListNode<L> first;
		private long savedModificationCount;
		private int usedElements;
		
		public ListElementsGetter(LinkedListIndexedCollection<L> collection) {
			this.listCollection=collection;
			this.first= collection.first;
			this.savedModificationCount=collection.modificationCount;
			this.usedElements=collection.size;
		}
		
		@Override
		public L getNextElement() {
			if(hasNextElement()) {
				ListNode<? extends L> element=first;
				first=first.next;
				usedElements--;
				return element.storage;
			}else
				throw new IndexOutOfBoundsException("Collection has no more elements inside.");
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
	public ElementsGetter<L> createElementsGetter() {
		return new ListElementsGetter<L>(this);
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
		LinkedListIndexedCollection<?> other = (LinkedListIndexedCollection<?>) obj;
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
