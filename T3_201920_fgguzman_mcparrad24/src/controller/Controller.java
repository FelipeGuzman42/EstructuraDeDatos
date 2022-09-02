package controller;

import java.util.*;

import model.data_structures.Queue;
import model.logic.MVCModelo;
import model.logic.UBERTrips;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo */
	private MVCModelo modelo;

	/* Instancia de la Vista */
	private MVCView view;

	private Comparable<UBERTrips>[] clus = null;
	
	private Comparable<UBERTrips>[] clusFinal = null;
			
	//Crear la vista y el modelo del proyecto
	public Controller() {
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		int num = 0, j = 0, k = 0;
		String dato = "";
		String[] datos;
		String hora = "";

		while (!fin) {
			view.printMenu();
			
			int option = lector.nextInt();
			switch (option) {
			case 1:
				modelo.CVSLector();
				num = modelo.totalViajesTrimestre();
				System.out.println("Viajes totales: " + num + "\n---------");
				System.out.println(
						"Primer viaje del trimestre: " + Arrays.toString(modelo.primerElemento()) + "\n---------");
				System.out.println(
						"Ultimo viaje del trimestre: " + Arrays.toString(modelo.ultimoElemento()) + "\n---------");
				break;

			case 2:
				System.out.println("--------- \nDar una hora entera de [0-23] (e.g., 1)");
				hora = lector.next();
				clus = modelo.cluster(Integer.parseInt(hora));
				System.out.println("Total de viajes: " + clus.length);
				break;

			case 3:
				System.out.println("--------- \nOrdenar los viajes por ShellSort");
				long startTime = System.currentTimeMillis();// medici�n tiempo actual
				clusFinal = modelo.ordenarViajesShellSort(clus);
				long endTime = System.currentTimeMillis();// medici�n tiempo actual
				long duration = endTime -startTime;// duracion de ejecucion del algoritmo
				System.out.println("Tiempo de ordenamiento ShellSort: " + duration + " milisegundos\n"
						+ "Primeros 10 viajes: \n");
				j = clus.length;
				if (j >= 10) {
				for (int i = 0; i < 10; i++) {
					System.out.println(clus[i] + "\n---------");
				}
				System.out.println("Ultimos 10 viajes: \n");
				for (int i = j; i > j-10; i--) {
					System.out.println(clus[i-1] + "\n---------");
				}
				}
				else {
					for (int i = 0; i < j; i++) {
						System.out.println(clus[i] + "\n---------");
					}
					System.out.println("Ultimos 10 viajes: \n");
					for (int i = j-1; i >= 0; i--) {
						System.out.println(clus[i] + "\n---------");
					}
				}
				break;
			case 4:
				System.out.println("--------- \nOrdenar los viajes por MergeSort");
				long startTime1 = System.currentTimeMillis();// medici�n tiempo actual
				clusFinal = modelo.ordenarViajesMergeSort(clus);
				long endTime1 = System.currentTimeMillis();// medici�n tiempo actual
				long duration1 = endTime1 -startTime1;// duracion de ejecucion del algoritmo
				System.out.println("Tiempo de ordenamiento MergeSort: " + duration1 + " milisegundos\n"
						+ "Primeros 10 viajes: \n");
				j = clus.length;
				if (j >= 10) {
					for (int i = 0; i < 10; i++) {
						System.out.println(clus[i] + "\n---------");
					}
					System.out.println("Ultimos 10 viajes: \n");
					for (int i = j; i > j-10; i--) {
						System.out.println(clus[i-1] + "\n---------");
					}
					}
					else {
						for (int i = 0; i < j; i++) {
							System.out.println(clus[i] + "\n---------");
						}
						System.out.println("Ultimos 10 viajes: \n");
						for (int i = j-1; i >= 0; i--) {
							System.out.println(clus[i] + "\n---------");
						}
					}
				break;
			case 5:
				System.out.println("--------- \nOrdenar los viajes por QuickSort");
				long startTime2 = System.currentTimeMillis();// medici�n tiempo actual
				clusFinal = modelo.ordenarViajesQuickSort(clus);
				long endTime2 = System.currentTimeMillis();// medici�n tiempo actual
				long duration2 = endTime2 -startTime2;// duracion de ejecucion del algoritmo
				System.out.println("Tiempo de ordenamiento QuickSort: " + duration2 + " milisegundos\n"
						+ "Primeros 10 viajes: \n");
				j = clus.length;
				if (j >= 10) {
					for (int i = 0; i < 10; i++) {
						System.out.println(clus[i] + "\n---------");
					}
					System.out.println("Ultimos 10 viajes: \n");
					for (int i = j; i > j-10; i--) {
						System.out.println(clus[i-1] + "\n---------");
					}
					}
					else {
						for (int i = 0; i < j; i++) {
							System.out.println(clus[i] + "\n---------");
						}
						System.out.println("Ultimos 10 viajes: \n");
						for (int i = j-1; i >= 0; i--) {
							System.out.println(clus[i] + "\n---------");
						}
					}
				break;
			case 6:
				System.out.println("--------- \n Hasta pronto !! \n---------");
				lector.close();
				fin = true;
				break;
			default:
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}
}
