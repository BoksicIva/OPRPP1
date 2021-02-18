package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
/**
 * Class represents each node given while parsing document, all other classes extends this class
 * @author Iva
 *
 */
public class Node {
	private ArrayIndexedCollection collection;
	
	public ArrayIndexedCollection getCollection() {
		return collection;
	}
	/**
	 * Method adds child to node building tree
	 */
	public void addChildNode(Node child) {
		if(collection==null)
			collection=new ArrayIndexedCollection();
		
		collection.add(child);
	}
	/**
	 * method returns number of children
	 * @return
	 */
	public int numberOfChildren() {
		return collection.size();
	}
	/**
	 * Method gets child at given index
	 * @param index of wanted child
	 * @return child 
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collection == null) ? 0 : collection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (collection == null) {
			if (other.collection != null)
				return false;
		} else if (!collection.equals(other.collection))
			return false;
		return true;
	}

	
}
