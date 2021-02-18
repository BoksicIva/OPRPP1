package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Tests for LinkedListCollection class
 * @author Iva
 *
 */
public class LinkedListCollectionTest {
	
	@Test
	public void testAddullPointerException() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, ()-> list.add(null));
	}
	
	@Test
	public void testAdd() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		list.add(4);
		int expected=0;
		int result=list.indexOf(4);
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testGet() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		list.add(4);
		list.add(7);
		list.add(9);
		list.add(10);
		int expected=10;
		Object result=list.get(3);
		Assertions.assertEquals(expected,(Integer) result);
	}
	
	@Test
	public void testGetIndexOutOfBoundsException() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()-> list.get(-1));
	}
	
	@Test
	public void testClear() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		list.add(4);
		list.add(7);
		list.add(9);
		list.clear();
		assertThrows(IndexOutOfBoundsException.class, ()-> list.get(1));
	}
	
	@Test
	public void testInsert() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		list.add(4);
		list.add(7);
		list.add(9);
		list.insert(6, 0);
		int expected=6;
		Object result=list.get(0);
		Assertions.assertEquals(expected,(Integer) result);
	}
	
	@Test
	public void testIndexOf() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		list.add(4);
		list.add(7);
		list.add(9);
		list.insert(6, 1);
		int expected=3;
		Object result=list.indexOf(9);
		Assertions.assertEquals(expected,(Integer) result);
	}
	
	@Test
	public void testRemove() {	
		LinkedListIndexedCollection list=new LinkedListIndexedCollection();
		list.add(4);
		list.add(7);
		list.add(9);
		list.insert(6, 1);
		list.remove(2);
		int expected=9;
		Object result=list.get(2);
		Assertions.assertEquals(expected,(Integer) result);
	}

}
