package controller;

import java.util.*;

import model.data_structures.HashTableLinearProbing;
//import model.data_structures.Queue;
import model.logic.MVCModelo;
import view.MVCView;
import model.logic.Queue;

public class Controller {

	/* Instancia del Modelo */
	private MVCModelo modelo;

	/* Instancia de la Vista */
	private MVCView view;

	/**
	 * Crear la vista y el modelo del proyecto
	 */
	public Controller() {
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String eleccion = "";
		int j = 0;
		String dato = "";
		String[] datos;
		String N = "";
		Queue<String> dat = new Queue<>();
		HashTableLinearProbing<String, String> rta = new HashTableLinearProbing<>(0);

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				modelo.JSONLector();
				modelo.CSVLector();
				modelo.TXTLector();
				System.out.println("Total viajes por hora del primer trimestre: " + modelo.totalHora1() + "\n---------");
				System.out.println("Total viajes por semana del primer trimestre: " + modelo.totalSemana1() + "\n---------");
				System.out.println("Total viajes por mes del primer trimestre: " + modelo.totalMes1() + "\n---------");
				System.out.println("Total viajes por hora del segundo trimestre: " + modelo.totalHora2() + "\n---------");
				System.out.println("Total viajes por semana del segundo trimestre: " + modelo.totalSemana2() + "\n---------");
				System.out.println("Total viajes por mes del segundo trimestre: " + modelo.totalMes2() + "\n---------");
				System.out.println("Total de zonas: " + modelo.totalZonas() + "\n---------");
				System.out.println("Total de nodos: " + modelo.totalNodos() + "\n---------");
				break;
			case 2:
				System.out.println("--------- \nIngresar el numero de letras con las que quiere realizar la consulta: ");
				N = lector.next();
				dat = modelo.letrasMasFrecuentes(Integer.parseInt(N));
				for(int i = 0; i<dat.size();i++) {
					System.out.println(""+dat.dequeue());
				}
				break;
			case 3:
				System.out.println("--------- \nIngresar una latitud y una longitud. (eg. -4.123, 74.987)");
				dato = lector.next();
				datos = dato.split(",");
				dat = modelo.buscarLatitudLongitud(Double.parseDouble(datos[0]), Double.parseDouble(datos[1]));
				for(int i = 0; i<dat.size();i++) {
					System.out.println(""+dat.dequeue());
				}
				break;
			case 4:
				System.out.println("--------- \nHacer la consulta con:");
				System.out.println("1. Tiempos de viaje que estan en un rango y que son del primero trimestre del 2018");
				System.out.println("2. Tiempos de viaje que salen de una zona dada y a una hora dada");
				System.out.println("3. Tiempos de viaje que llegan a una zona dada y en un rango de horas");
				eleccion = lector.next();
				if (eleccion.equals("1")) {
					System.out.println("--------- \nIngresar un rango de tiempos promedio de viaje en segundos [limite_bajo,limite_alto]");
					dato = lector.next();
					datos = dato.split(",");
					dat = modelo.tiempoPromRangoMes(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]));
					for(int i = 0; i<dat.size();i++) {
						System.out.println(""+dat.dequeue());
					}
				}
				else if (eleccion.equals("2")) {
					System.out.println("--------- \nIngresar el ID de la zona de origen y una hora (eg. 245,7)");
					dato = lector.next();
					datos = dato.split(",");
					dat = modelo.viajesFranja(datos[0], datos[1]);
					for(int i = 0; i<dat.size();i++) {
						System.out.println(""+dat.dequeue());
					}
				}
				else if (eleccion.equals("3")) {
					System.out.println("--------- \nIngresar el ID de la zona destino y un rango de horas (eg. 994,2,6), donde 2 es el limite bajo del rango y 6 el limite alto del rango");
					dato = lector.next();
					datos = dato.split(",");
					dat = modelo.tiempoPromViajesHora(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]));
					for(int i = 0; i<dat.size();i++) {
						System.out.println(""+dat.dequeue());
					}
				}
				break;
			case 5:
				System.out.println("--------- \nIngresar el numero de zonas con las que quiere realizar la consulta: ");
				N = lector.next();
				dat = modelo.viajesAlNorte(Integer.parseInt(N));
				for(int i = 0; i<dat.size();i++) {
					System.out.println(""+dat.dequeue());
				}
				break;
			case 6:
				System.out.println("--------- \nIngresar una latitud y una longitud. (eg. -4.123, 74.987)");
				dato = lector.next();
				datos = dato.split(",");
				dat = modelo.mismaLatitudLongitud(Double.parseDouble(datos[0]), Double.parseDouble(datos[1]));
				for(int i = 0; i<dat.size();i++) {
					System.out.println(""+dat.dequeue());
				}
				break;
			case 7:
				System.out.println("--------- \nIngresar un rango de desviaciones estandar [limite_bajo,limite_alto]");
				dato = lector.next();
				datos = dato.split(",");
				dat = modelo.desviacionEstandarRangoMes(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]));
				for(int i = 0; i<dat.size();i++) {
					System.out.println(""+dat.dequeue());
				}
				break;
			case 8:
				System.out.println("--------- \nIngresar el numero de zonas priorizadas con las que quiere realizar la consulta: ");
				N = lector.next();
				dat = modelo.zonasFronterizas(Integer.parseInt(N));
				for(int i = 0; i<dat.size();i++) {
					System.out.println(""+dat.dequeue());
				}
				break;
			case 9:
				System.out.println("--------- \nGrafica ASCII: ");
				rta = modelo.graficaASCII();
				Iterator<String> it = rta.keys().iterator();
				String llaveActual = (String) it.next();
				while(it.hasNext()) {
					System.out.println(llaveActual+"|"+rta.get(llaveActual));
					llaveActual = (String) it.next();
				}
				break;
			case 10:
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
