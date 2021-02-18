package hr.fer.oprpp1.custom.collections;

/**
 * Class LinkedListIndexCollection is variation of class Collection
 * @author Iva
 *
 */
public class LinkedListIndexedCollection extends Collection{
	
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
		super.addAll(other);
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
		if(position==0) {
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
	}
	
	/**
	 * Mwthod searches the collection and returns the index of first occurrence of value
	 * @param value that needs to be found
	 * @return first index of found value
	 */
	public int indexOf(Object value) {
		ListNode current=this.first;
		for(int i=0;i<this.size;i++) {
			if(current.storage.equals(value)) {
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
	 * Method for each element of collection calls process() method
	 * @param processor
	 */
	public void forEach(Processor processor) {
		if(processor==null) throw new  NullPointerException();
		ListNode newHead=first;
		for(int i=0;i<size;i++) {
			processor.process(newHead.storage);
			newHead=newHead.next;
		}
	}
	

}
