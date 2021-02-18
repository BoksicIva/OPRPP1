package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
/**
 * Tests for ArrayIndexedCollection class
 * @author Iva
 *
 */


public class ArrayIndexedCollectionTest {

	@Test
	public void testArrayIndexedCollection() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		int resultSize=collection.size();
		int expected=0;
		
		
		Assertions.assertEquals(expected, resultSize);
	}
	
	@Test
	public void testArrayIndexedCollection2() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testArrayIndexedCollection3() {	
		assertThrows(NullPointerException.class, ()-> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testArrayIndexedCollection4() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection(3);
		ArrayIndexedCollection collection2=new ArrayIndexedCollection(collection,2);
		int resultSize=collection.size();
		int expected=0;
		
		
		Assertions.assertEquals(expected, resultSize);
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		collection.add(2);
		collection.add(4);
		int resultSize=collection.size();
		int expected=2;
		Assertions.assertEquals(expected, resultSize);
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		collection.add(2);
		collection.add(4);
		Object resultSize=collection.get(0);
		int expected=2;
		Assertions.assertEquals(expected, (Integer)resultSize);
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		collection.add(2);
		collection.add(4);
		collection.clear();
		int result=collection.size();
		int expected=0;
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		collection.add(2);
		collection.add(4);
		collection.insert(3,1);
		Object result=collection.get(1);
		int expected=3;
		Assertions.assertEquals(expected, (Integer)result);
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		collection.add(2);
		collection.add(4);
		collection.insert(3,1);
		Object result=collection.indexOf(3);
		int expected=1;
		Assertions.assertEquals(expected, (Integer)result);
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection collection=new ArrayIndexedCollection();
		collection.add(2);
		collection.add(4);
		collection.insert(3,1);
		collection.remove(1);
		Object result=collection.indexOf(3);
		int expected=-1;
		Assertions.assertEquals(expected, (Integer)result);
	}
	
	
}
