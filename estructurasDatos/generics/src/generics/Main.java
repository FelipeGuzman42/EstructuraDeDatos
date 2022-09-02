package generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Para más documentación mirar:
 * https://docs.oracle.com/javase/tutorial/java/generics/index.html
 */

public class Main {

	public static void main(String[] args) {
				
		System.out.println("-------------------------------");
		System.out.println("Uso de un ContenedorObjeto");
		System.out.println("-------------------------------");

		ContenedorObjeto c = new ContenedorObjeto();
		c.set("Algun contenido");
		//Es necesario usar un cast cuando queramos recuperar los objetos
		String valor = (String)c.get();
		System.out.println("Cast exitoso");
		System.out.println("Valor: " + valor);
		System.out.println();

		try {
			int v = (int)c.get();
		}
		catch (Exception e){
			System.out.println("El cast Falló:");
			e.printStackTrace();
		}
		
		
		System.out.println("-------------------------------");
		System.out.println("Uso de un Contenedor<String>");
		System.out.println("-------------------------------");

		Contenedor<String> cs = new Contenedor<>();
		cs.set("Otro contenido");
		valor = cs.get();
		System.out.println("No se necseito hacer un cast");
		System.out.println("Valor: " + valor);

		
		System.out.println("-------------------------------");
		System.out.println("Uso de una Tupla<String, Integer>");
		System.out.println("-------------------------------");
		
		Tupla<String, Integer> t = new Tupla<>();
		t.set1("Valor 1");
		t.set2(new Integer(45));
		String valor1 = t.get1();
		Integer valor2 = t.get2();
		System.out.println("Valores de la tupla");
		System.out.println("valor1: "+ valor1);
		System.out.println("valor2: "+ valor2);

		System.out.println("-------------------------------");
		System.out.println("Uso del metodo Generico imprimirTupla");
		System.out.println("-------------------------------");
		MetodosGenericos.imprimirTupla(t);
		
		
		System.out.println("-------------------------------");
		System.out.println("Uso de ContenedorNumero");
		System.out.println("(Integer, Double, Float son sublases de Number)");
		System.out.println("-------------------------------");
		
		
		System.out.println("No se puede crear ContenedorNumero<String>");
		//ContenedorNumero<String> cn = new ContenedorNumero<String>();
		
		System.out.println("Se puede crear un contenedor ContenedorNumero<Number> ");
		ContenedorNumero<Number> cn1 = new ContenedorNumero<>();
		cn1.set(new Integer(32));
		Number v3 = cn1.get();
		System.out.println("Valor: " + v3);
		
		System.out.println("Se puede crear un contenedor ContenedorNumero<Integer> ");
		ContenedorNumero<Integer> cn2 = new ContenedorNumero<>();
		cn2.set(new Integer(32));
		Integer v4 = cn2.get();
		System.out.println("Valor: " + v4);

		System.out.println("Se puede crear un contenedor ContenedorNumero<Double> ");
		ContenedorNumero<Double> cn3 = new ContenedorNumero<>();
		cn3.set(new Double(32.0));
		Double v5 = cn3.get();
		System.out.println("Valor: " + v5);
		
		
		System.out.println("-------------------------------");
		System.out.println("Wildcards: ?");
		System.out.println("-------------------------------");
		Contenedor<Object> cont1 = new Contenedor<Object>();
		
		//Error
		System.out.println("No es valido esta sentencia: ");
		System.out.println("Contenedor<Object> cont2 = new Contenedor<Integer>();");
		//Contenedor<Object> cont2 = new Contenedor<Integer>();
		
		//Valido
		Contenedor<?> cont3 = new Contenedor<Object>();
		Contenedor<?> cont2 = new Contenedor<Number>();
		Contenedor<?> cont4 = new Contenedor<Integer>();

		System.out.println("-------------------------------");
		System.out.println("Wildcards: ? extends Number");
		System.out.println("-------------------------------");
		
		//Error
		System.out.println("No es valido esta sentencia: ");
		System.out.println("Contenedor<? extends Number> cont4 = new Contenedor<Object>();");
		//Contenedor<? extends Number> cont4 = new Contenedor<Object>();
		
		//Valido
		Contenedor<? extends Number> cont5 = new Contenedor<Number>();
		Contenedor<? extends Number> cont6 = new Contenedor<Integer>();
		
		
		System.out.println("-------------------------------");
		System.out.println("Wildcards: ? super Number");
		System.out.println("-------------------------------");
		
		
		//Valido
		Contenedor<? super Number> cont7 = new Contenedor<Object>();
		Contenedor<? super Number> cont8 = new Contenedor<Number>();
		
		//Error
		System.out.println("No es valido esta sentencia: ");
		System.out.println("Contenedor<? super Number> cont4 = new Contenedor<Integer>();");
		//Contenedor<? super Number> cont9 = new Contenedor<Integer>();
		
		System.out.println();
		System.out.println("-------------------------------");
		System.out.println("Metodo con <? extends Number> ");
		System.out.println("-------------------------------");
		
		System.out.println("valorAbsolutoDeContenido con  Contenedor<Number>");
		Contenedor<Number> cnn1 = new Contenedor<>();
		cnn1.set(new Integer(-51));
		double d = MetodosGenericos.valorAbsolutoDeContenido(cnn1);
		System.out.println(d);
		System.out.println();
		
		System.out.println("valorAbsolutoDeContenido con  Contenedor<Integer>");
		Contenedor<Integer> cnn2 = new Contenedor<>();
		cnn2.set(new Integer(-51));
		d = MetodosGenericos.valorAbsolutoDeContenido(cnn2);
		System.out.println(d);
		System.out.println();
		
		System.out.println("valorAbsolutoDeContenido con  Contenedor<Object>, NO FUNCIONA");
		Contenedor<Object> cnn3 = new Contenedor<>();
		cnn3.set(new Integer(-51));
		
		//d = MetodosGenericos.valorAbsolutoDeContenido(cnn3);
		//System.out.println(d);
		//System.out.println();
				
	}

}
