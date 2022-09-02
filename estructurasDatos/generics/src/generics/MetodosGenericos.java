package generics;

public class MetodosGenericos {
	public static <K, V> void imprimirTupla(Tupla<K, V> tupla) {
		System.out.println("{");
		System.out.println("\tvalor1:" + tupla.get1() + ",");
		System.out.println("\tvalor2:" + tupla.get2());
		System.out.println("}");
	}
	
	public static double valorAbsolutoDeContenido(Contenedor<? extends Number> contenedor) {
		Number numero = contenedor.get();
		double valor = numero.doubleValue();
		if(valor < 0) {
			return -valor;
		}
		else {
			return valor;
		}
	}
}
