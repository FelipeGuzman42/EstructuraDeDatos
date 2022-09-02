package view;

import model.logic.MVCModelo;

public class MVCView {
	/**
	 * Metodo constructor
	 */
	public MVCView() {

	}
	/**
	 * Muestra el menu en consola
	 */
	public void printMenu() {
		System.out.println("1. Realizar carga de datos y crear archivo JSON");
		System.out.println("2. Encontrar el camino de costo mínimo para un viaje entre dos localizaciones geográficas");
		System.out.println("3. Determinar n vértices con menor velocidad promedio en la ciudad");
		System.out.println("4. Calcular un MST con criterio distancia para el componente conexo más grande");
		System.out.println("5. Encontrar los vértices alcanzables en un tiempo T a partir de una localización geográfica de origen");
		System.out.println("6. Construir un nuevo grafo no dirigido de las zonas Uber");
		System.out.println("7. Encontrar el camino de costo mínimo basado en el tiempo promedio entre una zona de origen y una zona destino sobre el grafo hecho en la opción anterior");
		System.out.println("8. Encontrar los caminos de menor longitud (cantidad de arcos) a partir de una zona de origen");
		System.out.println("9. Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}

	public void printModelo(MVCModelo modelo) {
		// TODO implementar
	}
}
