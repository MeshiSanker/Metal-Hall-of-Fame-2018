package Ex2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BandsArrayList implements List<Band> {
	private Band arr[];
	private int size;
	public static final int INITIAL_CAPACITY = 16;

	public BandsArrayList(Band data[]) {
		arr = data;
		size = data.length;
	}

	private boolean isFull() {
		return size == arr.length;
	}

	@Override
	public boolean add(Band band) {
		if (band == null)
			return false;

		if (isFull())
			arr = Arrays.copyOf(arr, arr.length * 2 + 1);

		arr[size++] = band;
		return true;
	}

	@Override
	public void add(int index, Band band) {
		ensureCapacity();
		// Move the elements to the right after the specified index
		for (int i = size - 1; i >= index; i--)
			arr[i + 1] = arr[i];
		// Insert new element to data[index]
		arr[index] = band;
		// Increase size by 1
		size++;
	}

	/** Create a new larger array, double the current size + 1 */
	private void ensureCapacity() {
		if (size >= arr.length) {
			Band[] newArr = (Band[]) (new Object[size * 2 + 1]);
			System.arraycopy(arr, 0, newArr, 0, size);
			arr = newArr;
		}
	}

	@Override
	public boolean addAll(Collection<? extends Band> collection) {
		for (Band element : collection) {
			add(element);
		}
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Band> collection) {
		int i=index;
		for(Band element: collection){
			add(i,element);
			i++;
		}
		return false;
	}

	@Override
	public void clear() {
		arr = (Band[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	@Override
	public boolean contains(Object band) {
		return indexOf(band) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> iter = c.iterator();

		while (iter.hasNext()) {
			Object elem = iter.next();
			if (!contains(elem)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Band get(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException();
		
		return arr[index];
	}

	@Override
	public int indexOf(Object obj) {
		for (int i = 0; i < size; i++) {
			if (arr[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<Band> iterator() {
		return new ArrayIterator();
	}

	@Override
	public int lastIndexOf(Object band) {
		for (int i = size - 1; i >= 0; i--)
			if (band.equals(arr[i]))
				return i;
		return -1;
	}

	@Override
	public ListIterator<Band> listIterator() {
		return new ArrayListIterator(0);
	}

	@Override
	public ListIterator<Band> listIterator(int index) {
		return new ArrayListIterator(index);
	}

	@Override
	public boolean remove(Object band) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(band)) {
				arr[i] = null;
				return true;
			}

		}
		return false;
	}

	@Override
	public Band remove(int index) {
		
		Band band = arr[index];
		
		for (int i = index; i < size - 1; i++) {
			arr[i] = arr[i + 1];
		}
		if(isFull())
			arr[arr.length-1]=null;
		
		if(index== size-1)
			index=0;

		size--;

		return band;
	}

	@Override
	public boolean removeAll(Collection<?> bands) {
		for (Object b : bands){
			remove(b);
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> bands) {
		for (Object b : bands){
			add((Band) b);
		}		
		return false;
	}

	@Override
	public Band set(int index, Band band) {
		arr[index] = band;
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<Band> subList(int start, int end) {
		List<Band> bandsSubList = new LinkedList<Band>();

		for (int i = (start - 1); i < end; i++)
			bandsSubList.add(arr[i]);

		return bandsSubList;
	}

	@Override
	public Object[] toArray() {
		//Making a new array so return an array with a new size and without nulls
		Band array[]= new Band[size];
//		for(int i =0; i<size ; i++){
//			array[i]=arr[i];
//		}
		
		array = Arrays.copyOf(arr, size);
		return array;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return null;
	}

	public class ArrayListIterator implements ListIterator<Band> {
		protected int index = 0;

		public ArrayListIterator(int index) {
			if (index > 0)
				this.index = (index - 1);
			else
				this.index = 0;
		}

		@Override
		public boolean hasPrevious() {
			return index > 0;
		}

		@Override
		public boolean hasNext() {
			return index < size-1;
		}

		@Override
		public int nextIndex() {
			if (hasNext()) {
				index++;
				return index;

			} else {
				index = 0;
				return index;
			}
		}

		@Override
		public int previousIndex() {
			if (hasPrevious()) {
				index--;
				return index;
			} 
			else {
				index=size -1;
				return index;
			}
		}

		@Override
		public Band next() {
			return arr[nextIndex()];
		}

		@Override
		public Band previous() {
			return arr[previousIndex()];
		}

		@Override
		public void add(Band e) {
			BandsArrayList.this.add(index, e);
		}

		@Override
		public void remove() {
			BandsArrayList.this.remove(index);
			previous();
			
		}

		@Override
		public void set(Band e) {
			arr[index] = e;
		}

	}

	public class ArrayIterator implements Iterator<Band> {
		protected int index = 0;

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public Band next() {
			if (!hasNext())
				return arr[0];

			return arr[index++];
		}

	}

}
