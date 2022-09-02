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
		System.out.println("1. Realizar carga de datos");
		System.out.println("2. Consultar viajes en una hora dada");
		System.out.println("3. Ordenar los viajes del 2 por ShellSort");
		System.out.println("4. Ordenar los viajes del 2 por MergeSort");
		System.out.println("5. Ordenar los viajes del 2 por QuickSort");
		System.out.println("6. Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}

	public void printModelo(MVCModelo modelo) {
		/*view.printMenu();
		long startTime = System.currentTimeMillis();// medición tiempo actual
		this.algoritmoOrdenarXXXXX(viajes_A_Ordenar);
		long endTime = System.currentTimeMillis();// medición tiempo actual
		long duration = endTime -startTime;// duracion de ejecucion del algoritmo
		view.printMensage("Tiempo de ordenamiento XXXXX: " + duration + " milisegundos");*/
	}
}
