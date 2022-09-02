package model.data_structures;

import java.util.Iterator;

public class HashTableSeparateChaining<K, V extends Comparable<K>> implements IHashTable<K, V> {
	
//La mayor parte del código fue tomado de la implementación hecha en GitHub por Robert Sedgewick y Kevin Wayne.
	
	private static final double loadFactor = 5.0;

	private int n; 
	private int m;
	private int rehashes = 0;
	private SequentialSearch<K, V>[] st;

	public HashTableSeparateChaining(int m) {
		this.m = m;
		st = (SequentialSearch<K, V>[]) new SequentialSearch[m];
		for (int i = 0; i < m; i++)
			st[i] = new SequentialSearch<K, V>();
	} 

	private void resize(int chains) {
		HashTableSeparateChaining<K, V> temp = new HashTableSeparateChaining<K, V>(chains);
		for (int i = 0; i < m; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.m  = temp.m;
		this.n  = temp.n;
		this.st = temp.st;
	}

	private int hash(K key) {
		int result = (key.hashCode()& 0x7fffffff)%m;
		return result;
	} 

	public int darTamano() {
		return n;
	}
	public int darTamanoMax() {
		return m;
	}
	
	public double cargaF() {
		double carga = (1.0 * n) / m;
		return carga;
	}
	
	public int darRehashes() {
		return rehashes;
	}


	public boolean isEmpty() {
		if (n == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean contains(K key) {
		return get(key) != null;
	} 

	public V get(K key) {
		int i = hash(key);
		return st[i].get(key);
	} 

	public void put(K key, V val) {
		if (val == null) {
			delete(key);
			return;
		}
		if (n >= 10*m) resize(2*m);
		int i = hash(key);
		if (!st[i].contains(key)) n++;
		st[i].put(key, val);
		
		double carga = (1.0 * n) / m;
		if (carga > loadFactor)
			rehash();
	} 

	public V delete(K key) {
		V eliminado = get(key);
		
		int i = hash(key);
		if (st[i].contains(key)) n--;
		st[i].delete(key);
		return eliminado;
	} 
	
	private void rehash() {
		SequentialSearch<K, V>[] temp = st;
		rehashes++;
		st = (SequentialSearch<K, V>[]) new SequentialSearch[numRehash()];
		for (int i = 0; i < m; i++)
			st[i] = new SequentialSearch<K, V>();
		Iterator it = keys().iterator();
		K key;
		V value;
		while (it.hasNext()) {
			key = (K) it.next();
			value = get(key);
			this.put(key, value);
		}
	}
	
	public boolean esPrimoMayor(int num) {
		boolean es = true;
		if (num > m) {
			for (int i = 1; i < num; i++) {
				if ((i != 1) && (i != num) && ((num%i)==0)){
					es = false;
				}
			}
		}
		return es;
	}
	
	public int numRehash() {
		int retorno = 0;
		for (int i = 1; i < m; i++) {
			int num = 2*m + i;
			if (esPrimoMayor(num)) {
				retorno = num;
				return num;
			}
		}
		return retorno;
	}

	public Iterable<K> keys() {
		Queue<K> queue = new Queue<K>();
		for (int i = 0; i < m; i++) {
			if(st[i] != null) {
			for (K key : st[i].keys())
				queue.enqueue(key);
			}
		}
		return queue;
	}
}
