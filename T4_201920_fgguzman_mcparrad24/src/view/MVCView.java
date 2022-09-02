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
		System.out.println("1. Realizar carga de los tiempos de viaje");
		System.out.println("2. Consultar tiempos de viajes promedio mas largos en un rango definito de horas.");
		System.out.println("3. Prueba de eficiencia");
		System.out.println("4. Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}

	public void printModelo(MVCModelo modelo) {
		/*view.printMenu();
		long startTime = System.currentTimeMillis();// medici�n tiempo actual
		this.algoritmoOrdenarXXXXX(viajes_A_Ordenar);
		long endTime = System.currentTimeMillis();// medici�n tiempo actual
		long duration = endTime -startTime;// duracion de ejecucion del algoritmo
		view.printMensage("Tiempo de ordenamiento XXXXX: " + duration + " milisegundos");*/
	}
}
