package model.data_structures;

public class MaxHeapCP<T extends Comparable<T>> implements IMaxHeapCP<T> {

	private T[] heap;
	private int tamano;
	private int tamanoMax;

	public MaxHeapCP(int tamanoMax) {
		this.tamanoMax = tamanoMax;
		this.tamano = 0;
		heap = (T[]) new Comparable[tamanoMax + 1];
	}

	private int padre(int pos) {
		return (pos) / 2;
	}

	private int hijoIzq(int pos) {
		return (2 * pos);
	}

	private int hijoDer(int pos) {
		return (2 * pos) + 1;
	}

	private void intercambio(int x, int y) {
		T copia = heap[x];
		heap[x] = heap[y];
		heap[y] = copia;
	}

	private void sink(int pos) {
		int izq = hijoIzq(pos), mayor = pos, der = hijoDer(pos);
		if((izq < tamano) && (heap[izq].compareTo(heap[pos]) > 0)) {
			mayor = izq;
		}
		if((der < tamano) && (heap[der].compareTo(heap[pos]) > 0)) {
			mayor = der;
		}
		if(mayor != pos) {
			intercambio(pos, mayor);
			sink(mayor);
		}
	}

	private void swim(int pos) {
		if((pos > 1) && (heap[padre(pos)].compareTo(heap[pos]) < 0)) {
			intercambio(pos, padre(pos));
			swim(padre(pos));
		}
	}
	
	private void resize(int capacity) {
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 1; i <= tamano; i++) {
            temp[i] = heap[i];
        }
        heap = temp;
    }

	public void insertar(T dato) {
		if (tamano == heap.length - 1) resize(2 * heap.length);
		heap[++tamano] = dato;
		int prueba = tamano;
		swim(prueba);
	}

	public int darTamano() {
		return tamano;
	}

	public T getMax() {
		return heap[1];
	}
	
	public T getIndex(int N) {
		return heap[N];
	}

	public T eliminarMax() {
		T retorno = heap[1];
		heap[1] = heap[tamano];
		heap[tamano--] = null;
		sink(1);
		return retorno;
	}

	public boolean isEmpty() {
		if (tamano == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public T getLast() {
		T retorno = heap[tamano];
		return retorno;
	}
}