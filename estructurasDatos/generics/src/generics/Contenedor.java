package generics;

public class Contenedor<K> {
	private K valor;
	
	public K get() {		
		return valor;
	}
	
	public void set(K valor) {
		this.valor = valor;
	}
}
