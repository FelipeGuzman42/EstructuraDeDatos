package box;

import java.util.Iterator;

public class ContainerArray<T> implements Container<T> {

	private Box<T>[] arreglo;
	private int size;
	
	public ContainerArray() {
		arreglo = (Box<T>[]) new Box[3];
		size = 0;
	}

	@Override
	public void add(T elem) {
		if(size < 3) {
			Box<T> box = new BoxValue<T>(); 
			box.put(elem);
			arreglo[size] = box;
			size ++;
		}
	}

	
	@Override
	public Iterator<T> iterator() {
		return new ContainerArrayIterator();
	}

	private class ContainerArrayIterator implements Iterator<T>{

		private int count = 0;
		@Override
		public boolean hasNext() {
			return count < size;
		}

		@Override
		public T next() {
			if(count < size) {
				count++;
				return arreglo[count - 1].get();
			}
			return null;
		}
		
	}
	
}
