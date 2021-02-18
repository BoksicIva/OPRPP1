package hr.fer.oprpp1.custom.collections;
/**
 * Interface List extends Collection and it is used to define ArrayIndexedCollection and LikedListIndexedCollection
 * @author Iva
 *
 */
public interface List<L> extends Collection<L>{
	/**
	 * method gets elements at given index
	 * @param index position in List
	 * @return element at given position
	 */
	L get(int index);
	
	/**
	 * Method inserts element of type L at given position
	 * @param value element that needs to be inserted
	 * @param position in List where should be placed
	 */
	void insert(L value, int position);
	
	/**
	 * Method finds value in List 
	 * @param value that needs to be searched
	 * @return position of value in list
	 */
	int indexOf(Object value); 
	/**
	 * Method removes element at given index
	 * @param index position of element that needs to be removed
	 */
	void remove(int index); 


}
