package generics;

public class ContenedorNumero<K extends Number> {
	private K valor;
	
	public K get() {
		return valor;
	}
	
	public void set(K valor) {
		this.valor = valor;
	}
}
