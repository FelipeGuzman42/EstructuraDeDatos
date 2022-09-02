package ordenamientosBasicos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class Main {
	
	
	public static void main(String[] args) {
		
		Dinero lista1 [] = {new Dinero(10, 5), new Dinero(4, 5)}; 
		Dinero lista2 [] = {new Dinero(10, 5), new Dinero(4, 5)}; 
		Dinero lista3 [] = {new Dinero(10, 2), new Dinero(4, 5)}; 

		
		//Ordenar con .compareTo()
		Arrays.sort(lista1);
		System.out.println(Arrays.toString(lista1));

		//Ornder con Comparator
		Arrays.sort(lista2, new Dinero.ComparadorDolar());
		System.out.println(Arrays.toString(lista2));

		Arrays.sort(lista3, new Dinero.ComparadorCentavos());
		System.out.println(Arrays.toString(lista3));

		
	}
	

}
