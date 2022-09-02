package controller;

import java.util.*;

import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.logic.MVCModelo;
import model.logic.TravelTime;
import model.logic.PruebaColaPrioridad;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo */
	private MVCModelo modelo;

	/* Instancia de la Vista */
	private MVCView view;
	private MaxColaCP<TravelTime> datosCola = new MaxColaCP<>();

	private MaxHeapCP<TravelTime> datosHeap = new MaxHeapCP<>(0);

	// Crear la vista y el modelo del proyecto
	public Controller() {
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String[] datos;
		String N = "";
		String horaI = "";
		String horaF = "";

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				modelo.loadTravelTimes();
				break;
			case 2:
				System.out.println(
						"--------- \nDar un número entero para el tamaño de la cola de prioridad, y un rango de horas; dando primero hora inicial y después hora final (e.g., 20,3,6");
				datos = lector.next().split(",");
				N = datos[0];
				horaI = datos[1];
				horaF = datos[2];
				long startTime1 = System.currentTimeMillis();
				datosCola = modelo.tiempoPromViajesCola(N, horaI, horaF);
				long endTime1 = System.currentTimeMillis();
				long duration1 = endTime1 - startTime1;
				long startTime2 = System.currentTimeMillis();
				datosHeap = modelo.tiempoPromViajesHeap(N, horaI, horaF);
				long endTime2 = System.currentTimeMillis();
				long duration2 = endTime2 - startTime2;
				int j1 = datosCola.darTamano();
				int k1 = datosHeap.darTamano();
				System.out.println("Resultado con MaxColaCP: " + "\n---------");
				if (datosCola.isEmpty()) {
					System.out.println("No hay viajes en el rango de horas dado");
				} else {
					TravelTime actual1 = datosCola.eliminarMax();
					for (int i = 0; i < j1; i++) {
						System.out.println(actual1.darIdOrigen() + " " + actual1.darIdDestino() + " "
								+ actual1.darHoraPromedio() + " " + actual1.darTiempoPromedio() + "\n---------");
						actual1 = datosCola.eliminarMax();
					}
				}
				System.out.println("Tiempo de ejecucion MaxColaCP: " + duration1 + " milisegundos\n");
				System.out.println("Resultado con MaxHeapCP: " + "\n---------");
				if (datosHeap.isEmpty()) {
					System.out.println("No hay viajes en el rango de horas dado");
				} else {
					TravelTime actual2 = datosHeap.eliminarMax();
					for (int i = 0; i < k1; i++) {
						System.out.println(actual2.darIdOrigen() + " " + actual2.darIdDestino() + " "
								+ actual2.darHoraPromedio() + " " + actual2.darTiempoPromedio() + "\n---------");
						actual2 = datosHeap.eliminarMax();
					}
				}
				System.out.println("Tiempo de ejecucion MaxHeapCP: " + duration2 + " milisegundos\n");
				break;
			case 3:
				System.out.println("--------- \nDar un numero de datos para generar una muestra aleatoria de viajes");
				N = lector.next();
				PruebaColaPrioridad PCP = new PruebaColaPrioridad(modelo.generarMuestra(Integer.parseInt(N)));
				long startTime3 = System.currentTimeMillis();
				PCP.agregarCola();
				long endTime3 = System.currentTimeMillis();
				long duration3 = endTime3 - startTime3;
				long startTime4 = System.currentTimeMillis();
				PCP.agregarHeap();
				long endTime4 = System.currentTimeMillis();
				long duration4 = endTime4 - startTime4;
				long startTime5 = System.currentTimeMillis();
				PCP.sacarCola();
				long endTime5 = System.currentTimeMillis();
				long duration5 = endTime5 - startTime5;
				long startTime6 = System.currentTimeMillis();
				PCP.sacarHeap();
				long endTime6 = System.currentTimeMillis();
				long duration6 = endTime6 - startTime6;

				System.out.println("Agregar Cola: " + duration3 + "ms\n" + "Agregar Heap: " + duration4 + "ms\n"
						+ "Sacar Cola: " + duration5 + "ms\n" + "Sacar Heap: " + duration6 + "ms");
				break;
			case 4:
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
