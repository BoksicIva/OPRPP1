package hr.fer.oprpp1.custom.collections;
/**
 * Interface List extends Collection and it is used to define ArrayIndexedCollection and LikedListIndexedCollection
 * @author Iva
 *
 */
public interface List extends Collection{
	
	Object get(int index);
	
	void insert(Object value, int position);
	
	int indexOf(Object value); 
	
	void remove(int index); 


}
