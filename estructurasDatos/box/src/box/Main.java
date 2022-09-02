package box;

import java.util.ArrayList;
import java.util.List;

/**
 * Para más documentación mirar:
 * https://docs.oracle.com/javase/tutorial/java/generics/index.html
 */

public class Main {

	public static void main(String[] args) {
				
		System.out.println("-------------------------------");
		System.out.println("Probando Box");
		System.out.println("-------------------------------");

		Box<String> box = new BoxValue<>();
		box.put("Hola");
		for(String s: box) {
			System.out.println(s);
		}
		System.out.println(box.get());
		
		//Lo siguiente no es valido:
		//box.put(14);

		System.out.println("-------------------------------");
		System.out.println("Probando ContainerArray");
		System.out.println("-------------------------------");

		Container<Integer> c = new ContainerArray<Integer>();
		c.add(1);
		c.add(2);
		c.add(3);
		for(Integer i: c) {
			System.out.println(i);
		}
		
				
		System.out.println("-------------------------------");
		System.out.println("Probando ContainerArray 2");
		System.out.println("-------------------------------");
		
		ArrayList<Box<Integer>> a = new ArrayList<Box<Integer>>();
		BoxValue b; 
		b = new BoxValue<Integer>();
		b.put(1);
		a.add(b);
		
		b = new BoxValue<Integer>();
		b.put(2);
		a.add(b);
		
		b = new BoxValue<Integer>();
		b.put(3);
		a.add(b);
		
		for(Box<Integer> bb: a) {
			Integer i = bb.get();
			System.out.println(i);
		}
		
		
		System.out.println("-------------------------------");
		System.out.println("Probando Arreglo de Comparables");
		System.out.println("-------------------------------");
		
		ArregloDeComparables<String> ar = new ArregloDeComparablesImplementacion<>();
		ar.add("1");
		ar.add("2");
		for(String s: ar) {
			System.out.println(s);
		}
		
		
		
	}

}
