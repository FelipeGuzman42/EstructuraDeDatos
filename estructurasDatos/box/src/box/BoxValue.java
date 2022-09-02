package box;

import java.util.Iterator;

public class BoxValue<T> implements Box<T>{

	private T value;
	
	@Override
	public void put(T o) {
		value = o;
	}

	@Override
	public void delete() {
		value = null;
	}

	@Override
	public T get() {
		return value;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new BoxValueIterator();
	}
	
	private class BoxValueIterator implements Iterator<T>{
		private boolean primeraIteracion = true;
		
		@Override
		public boolean hasNext() {
			return primeraIteracion && (value != null);
		}

		@Override
		public T next() {
			if(primeraIteracion) {
				primeraIteracion = false;
				return value;
			}
			return null;
		}
		
	}

}
