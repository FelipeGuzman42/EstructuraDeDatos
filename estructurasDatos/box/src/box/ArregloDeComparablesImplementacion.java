package box;

import java.util.Iterator;


/**
 * Arreglo con 3 elementos comparables
 */
public class ArregloDeComparablesImplementacion<T extends Comparable<T>> implements ArregloDeComparables<T>{

	private T[] arreglo;
	private int size;
	
	public ArregloDeComparablesImplementacion() {
		//OJO!!!! La siguiente linea no funcionaria
		//arreglo = (T[])new Object[3];
		
		arreglo = (T[])new Comparable[3];
		size = 0;
	}

	@Override
	public void add(T elem) {
		if(size < 3) {
			arreglo[size] = elem;
			size ++;
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ContainerComparableArrayIterator();
	}

	private class ContainerComparableArrayIterator implements Iterator<T>{

		private int count = 0;
		@Override
		public boolean hasNext() {
			return count < size;
		}

		@Override
		public T next() {
			if(count < size) {
				count++;
				return arreglo[count - 1];
			}
			return null;
		}
		
	}

}
