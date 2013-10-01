package adts;

import java.util.Iterator;

/**
 * @author fmartins
 *
 * @param <E>
 * 
 * A list of elements E that allows new elements to 
 * be added only at the end of the list.
 */
public interface QList<E> extends Iterable<E> {

	/**
	 * @return the number of the elements in the list
	 */
	int size();
	
	/**
	 * @param e the element to be added
	 * @throws UnsupportedOperationException
	 * 
	 * Adds an element at the end of the list
	 */
	void add (E e) throws UnsupportedOperationException;
	
	/**
	 * @param i the position of the element to return
	 * @return the element at position i
	 * 
	 * @requires 0 <= i < size()
	 * 
	 * Return the element at position i
	 */
	E get (int i);
	
	/**
	 * @return  an iterator over the elements in this list in proper sequence.
	 */
	Iterator<E> iterator(); 
}

