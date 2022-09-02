import uniandes.cupi2.collections.listaEncadenada.ListaEncadenada;

public class Main {

	public static void main(String[] args) {

		System.out.println("Se crea una lista con la palabra 'ala'");
		ListaEncadenada<Character> lista = new ListaEncadenada<>();
		lista.insertarCabeza('a');
		lista.insertarCabeza('l');
		lista.insertarCabeza('a');
		
		System.out.println("¿Es palindrome?: " + lista.esPalindrome());
		System.out.println();
				
		System.out.println("Se crea una lista vacia");
		lista = new ListaEncadenada<>();
		System.out.println("¿Es palindrome?: " + lista.esPalindrome());
		System.out.println();
		
		System.out.println("Se crea una lista con la palabra 'vaca'");
		lista = new ListaEncadenada<>();
		lista.insertarCabeza('v');
		lista.insertarCabeza('a');
		lista.insertarCabeza('c');
		lista.insertarCabeza('a');
		
		System.out.println("¿Es palindrome?: " + lista.esPalindrome());
		System.out.println();
	}

}
