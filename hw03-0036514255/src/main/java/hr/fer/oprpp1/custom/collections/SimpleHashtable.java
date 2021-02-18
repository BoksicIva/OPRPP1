package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	private TableEntry<K,V>[]  table;
	private int size;
	private int modificationCount;
	
	/**
	 * Class that represents elements of SimpleHashtable class
	 * @author Iva
	 *
	 * @param <K> used for key
	 * @param <V> used for value
	 */
	public static class TableEntry<K,V> {
		private K key;
		private V value;
		@SuppressWarnings("rawtypes")
		private TableEntry next;
		
		/**
		 * Constructor for TableEntry
		 * @param key is new key of entry
		 * @param value is new value of entry
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		/**
		 * Getter method for key
		 * @return key of object
		 */
		public K getKey() {
			return key;
		}
		/**
		 * Getter method for value
		 * @return value of object
		 */
		public V getValue() {
			return value;
		}
		/**
		 * Setter method for value
		 * sets value to given value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		/**
		 * returns string representation of entry like "key=value" 
		 */
		@Override
		public String toString() {
			return  key + "=" + value ;
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof TableEntry))
				return false;
			TableEntry other = (TableEntry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
		
		
		
	}
	/**
	 * Class IteratorImpl is implementation of Iterator for SimpleHashtable class
	 * @author Iva
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		/**
		 * stored modification count
		 */
		private int modificationCountIterator;
		/**
		 * position of current element 
		 */
		private int index;
		/**
		 * number of returned elements by iteration
		 */
		private int returned;
		/**
		 * current entry 
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * next entry used when entry is removed
		 */
		private TableEntry<K, V> nextEntry;
		
		public IteratorImpl() {
			this.modificationCountIterator = SimpleHashtable.this.modificationCount;
			this.index = -1;
			this.returned=0;
			
		}
		/**
		 * Method checks if there is more elements fot iteration
		 * return true if does,otherwise false
		 */
		@Override
		public	boolean hasNext() {
			return returned<size;
		}
		
		@Override
		public SimpleHashtable.TableEntry<K,V> next() {
			checkForModification();
			if(hasNext()) {
				if(nextEntry!=null) {
					currentEntry=nextEntry;
					returned++;
					nextEntry=null;
					return currentEntry;
				}
				else {
					currentEntry=calculateNext();
					returned++;
					return currentEntry;
				}
			}
			throw new NoSuchElementException();
		}
		/**
		 * Method calculate next entry for iteration
		 * @return next entry
		 */
		@SuppressWarnings("unchecked")
		private SimpleHashtable.TableEntry<K,V> calculateNext(){
			if(currentEntry!= null && currentEntry.next!=null) {
				currentEntry=currentEntry.next;
				return currentEntry;
			}else {
				for(int i=index+1;i<table.length;i++) {
					if(table[i]!=null) {
						index=i;
						currentEntry=table[i];
						return currentEntry;
					}
				}
				
			}
			return null;
		}
		
		@Override
		public void remove() {
			if(currentEntry==null)
				throw new IllegalStateException();
			TableEntry<K, V> oldEnty=currentEntry;
			nextEntry=calculateNext();
			SimpleHashtable.this.remove(oldEnty.key);
			modificationCountIterator=SimpleHashtable.this.modificationCount;
			returned--;
			currentEntry=null;

		}
		/**
		 * Method checks if there was modification outside class over hashtable
		 */
		private void checkForModification() {
            if (modificationCountIterator !=  SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException();
        }
	}

	/**
	 * Constructor that makes new table with capacity of 16;
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.table=(TableEntry<K,V>[]) new TableEntry[16];
	}
	/**
	 * Constructor that makes new table with capacity first equal or greater power of number 2;
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) throw new IllegalArgumentException();
		int newCapacity=(int) Math.pow(2,Math.ceil((Math.log(capacity)/Math.log(2))));
		this.table=(TableEntry<K,V>[])new TableEntry[newCapacity];
	}
	/**
	 * Method puts given entry in hashtable. If table already contains key new value is stored for that key, otherwise
	 * method makes new TableEntry<K,V> with parameters key and value and stores it.
	 * @param key represents key
	 * @param value represents value
	 * @return old value of TableEntry if key already existed,otherwise null 
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		duplicateIfNeeded();
		if(key==null) throw new NullPointerException();
		V oldValue;
		int index=Math.abs(key.hashCode())%table.length;
		if(containsKey(key)) {
				TableEntry<K,V> entry=table[index];
				if(entry.key.equals(key)) {
					oldValue=entry.value;
					entry.value=value;
					return oldValue;
				}else {
					while(entry.next!=null) {
						if(entry.key.equals(key)) {
							oldValue=entry.value;
							entry.value=value;
							return oldValue;
						}
						entry=entry.next;
					}
				}
			
		}else {
			TableEntry<K,V> te=new TableEntry<K,V>(key, value);
		    add(te,index);
		    size++;
		    modificationCount++;
		    return null;
		}	
		return null;
	}
	/**
	 * Method adds new entry at given index
	 * @param newEntry object that needs to be added to hashtable
	 * @param index position where it should be added
	 */
	@SuppressWarnings("unchecked")
	private void add(TableEntry<K,V> newEntry,int index) {
		if(table[index]==null) {
			table[index]=newEntry;
		}else {
			TableEntry<K,V> entry=table[index];
			if(entry!=null) {
				while(entry.next!=null) {
					entry=entry.next;
				}
			}
			entry.next=newEntry;
		}
	}
	/**
	 * Method duplicate array table condition is true 
	 * Condition : 75% of table is filed 
	 */
	@SuppressWarnings("unchecked")
	private void duplicateIfNeeded() {
		if(size*1.0/table.length >= 0.75) {
			TableEntry<K,V>[] newTable=(TableEntry<K,V>[]) new TableEntry[table.length*2];
			TableEntry<K,V>[] oldTable=this.toArray();
			clear();
			size=0;
			table=newTable;
			for(int i=0;i<oldTable.length;i++) {
				put(oldTable[i].key,oldTable[i].value);
			}
		}
	}
	
	
	/**
	 * Method get object value by its key
	 * @param key key that is searched in hashtable
	 * @return value of found object with same key
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		int index=Math.abs(key.hashCode())%table.length;
		TableEntry<K,V> entry=table[index];
		while(entry!=null) {
			if(entry.key.equals(key)) {
				return entry.value;
			}
			entry=entry.next;	
		}
		return null;
		
	}
	
	/**
	 * Method returns size of elements stored in hashtable
	 * @return
	 */
	public int size() {
		return size;
	}
	/**
	 * Method checks if collection has given key 
	 * @param key key that is checked
	 * @return true if collection contains key, otherwise false
	 */
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) {
		if(key==null)
			return false;
		int index=Math.abs(key.hashCode())%table.length;
		TableEntry<K,V> entry=table[index];
		if(entry==null)
			return false;
		if(entry.key.equals(key)) {
			return true;
		}else {
			if(entry!=null) {
				while(entry.next!=null) {
					entry=entry.next;
					if(entry.key.equals(key)) {
						return true;
					}
					
				}
			}
		}
		
		return false;	
	}
	/**
	 * Method checks if collection has given value 
	 * @param value value that is checked
	 * @return true if collection contains value, otherwise false
	 */
	@SuppressWarnings("unchecked")
	public boolean containsValue(Object value) {
		for(int i=0;i<table.length;i++) {
			TableEntry<K,V> entry=table[i];
			if(entry!=null) {
				if(entry.value.equals(value)) {
					return true;
				}
				while(entry.next!=null) {
					entry=entry.next;
					if(entry.value.equals(value)) {
						return true;
					}	
				}
			}
		}
		return false;	
	}
	
	/**
	 * Method removes object by its key
	 * @param key key of object that should be removed
	 * @return null if key is not found,otherwise returns value of removed object
	 */
	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		if(!containsKey(key))
			return null;
		V value;
		int index=Math.abs(key.hashCode())%table.length;
		TableEntry<K,V> entry=table[index];
		TableEntry<K,V> previousentry;
		if(entry.key.equals(key)) {
			value=entry.value;
			table[index]=entry.next;
			size--;
			modificationCount++;
			return value;
		}else {
			if(entry!=null) {
				while(entry.next!=null) {
					previousentry=entry;
					entry=entry.next;
					if(entry.key.equals(key)) {
						value=entry.value;
						entry=entry.next;
						previousentry.next=entry;
						size--;
						modificationCount++;
						return value;
					}
					
				}
			}
		}
		return null;
	}
	/**
	 * Method checks if array table is empty
	 * @return true if it is,otherwise false
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	/**
	 * Method returns string of table elements by specific form
	 */
	@SuppressWarnings("unchecked")
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("[");
		int index;
		boolean first=true;
		for(int i=0;i<table.length;i++) {
			index=1;
			TableEntry<K,V> entry=table[i];
			if(entry!=null) {
				if(first) {
					first=false;
				}else {
					sb.append(", ");
				}
				sb.append(entry.toString());
				while(entry.next!=null) {
					entry=entry.next;
					sb.append(", "+entry.toString());
					
				}
		    }
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * Method makes new array and stores elements of hashtable inside it from first one to last without list
	 * @return new array
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray(){
		TableEntry<K,V>[] newTable=(TableEntry<K, V>[]) new TableEntry[size];
		int index=0;
		for(int i=0;i<table.length;i++) {
			TableEntry<K,V> entry=table[i];
			if(entry!=null) {
			newTable[index++]=entry;
				while(entry.next!=null) {
					entry=entry.next;
					newTable[index++]=entry;
				}
			}
		}
		return newTable;
			
	}
	/**
	 * Method clears all table slots
	 */
	public void clear() {
        for (int i = 0; i < table.length; ++i)
            table[i] = null;
	}
	/**
	 * Method makes new IteratorImpl and makes hashtable iterable
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		return new IteratorImpl();
	}
	
}
