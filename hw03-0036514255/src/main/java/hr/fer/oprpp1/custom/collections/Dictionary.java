package hr.fer.oprpp1.custom.collections;

/**
 * Class that represents collection of elements make of inner class DictionaryElement
 * @author Iva
 *
 * @param <K> parameter for key
 * @param <V> parameter for value
 */
public class Dictionary<K,V> {
	
	private ArrayIndexedCollection<DictionaryElement<K,V>> elements;
	
	/**
	 * Private class that represents elements make of key and value
	 * @author Iva
	 *
	 * @param <K> parameter for key
	 * @param <V> parameter for value
	 */
	private static class DictionaryElement<K,V>{
		private K key;
		private V value;
		/**
		 * Constructor for DictionaryElement
		 * @param key sets attribute key to its value
		 * @param value sets attribute value to its value
		 */
		public DictionaryElement(K key, V value) {
			if(key==null ) throw new NullPointerException();
			this.key = key;
			this.value = value;
		}
		
	}
	/**
	 * Constructor that makes new instance of ArrayIndexedCollection for class
	 */
	public Dictionary() {
		this.elements=new ArrayIndexedCollection<>();
	}

	/** 
	 * Constructor makes given instance of ArrayIndexedCollection as elements
	 * @param elements  given instance of ArrayIndexedCollection
	 */
	public Dictionary(ArrayIndexedCollection<DictionaryElement<K, V>> elements) {
		this.elements = elements;
	}
	
	/**
	 * Method check if class contains objects
	 * @return true if not contains object, otherwise returns false
	 */
	public boolean isEmpty() {
		return elements.size()==0;
	}
	/**
	 * Method for size of current objects in class
	 * @return number of objects in ArrayIndexedCollection elements
	 */
	public int size() {
		return elements.size();
	}
	/**
	 * Removes all elements from collection
	 */
	public void clear() {
		elements.clear();
	}
	/**
	 * Method adds new object in collection if given key is not found in collection or
	 * stores new value for existing key
	 * @param key 
	 * @param value 
	 * @return null if key wasnt found or old value for key
	 */
	public V put(K key, V value) {
		if(key== null) throw new NullPointerException();
		V oldValue=get(key);
		if(oldValue!=null) {
			elements.get(indexOfKey(key)).value=value;
		}else {
		DictionaryElement<K, V> de=new DictionaryElement<>(key, value);
		elements.add(de);
		}
		return oldValue;
	}
	/**
	 * Method get value for given key 
	 * @param key that is searched in collection
	 * @return value for given key if it exists in collection ,otherwise return null
	 */
	public V get(Object key) {
		int index=indexOfKey(key);
		if(index!= -1)
			return elements.get(index).value;
		return null;
		
	}
	/**
	 * Method returns index of given key
	 * @param key  that is searched in collection
	 * @return index if key is found, otherwise -1
	 */
	private int indexOfKey(Object key) {
		for(int i=0;i<size();i++) {
			if(elements.get(i).key.equals(key))
				return i;
		}
		return -1;
	}
	/**
	 * Method removes element from collection
	 * @param key of object that needs to be removes
	 * @return value of removes object
	 */
	public V remove(Object key) {
		V value=get(key);
		elements.remove(indexOfKey(key));
		return value;
	}

}
