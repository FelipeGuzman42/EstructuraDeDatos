package controller;

import java.util.*;

import model.logic.MVCModelo;
import model.logic.TravelTime;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo */
	private MVCModelo modelo;

	/* Instancia de la Vista */
	private MVCView view;
	
	// Crear la vista y el modelo del proyecto
	public Controller() {
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				modelo.CVSLector();
				System.out.println("Numero de viajes cargados del primer archivo: " + modelo.darTamanoL(1) + "\n");
				System.out.println("Primer viaje cargado: " + "\n");
				String[] primero = modelo.darPrimero(1).split("-");
				System.out.println("Trimestre: " + primero[0] + "\n" + "ID Zona de Origen: " + primero[1] + "\n" + "ID Zona Destino: " + primero[2] + "\n");
				System.out.println("Ultimo viaje cargado: " + "\n");
				String[] ultimo = modelo.darUltimo(1).split("-");
				System.out.println("Trimestre: " + ultimo[0] + "\n" + "ID Zona de Origen: " + ultimo[1] + "\n" + "ID Zona Destino: " + ultimo[2] + "\n");
				System.out.println("Numero de viajes cargados del segundo archivo: " + modelo.darTamanoL(2) + "\n");
				System.out.println("Primer viaje cargado: " + "\n");
				String[] primero2 = modelo.darPrimero(2).split("-");
				System.out.println("Trimestre: " + primero2[0] + "\n" + "ID Zona de Origen: " + primero2[1] + "\n" + "ID Zona Destino: " + primero2[2] + "\n");
				System.out.println("Ultimo viaje cargado: " + "\n");
				String[] ultimo2 = modelo.darUltimo(2).split("-");
				System.out.println("Trimestre: " + ultimo2[0] + "\n" + "ID Zona de Origen: " + ultimo2[1] + "\n" + "ID Zona Destino: " + ultimo2[2] + "\n");
				System.out.println("Numero de viajes cargados del tercer archivo: " + modelo.darTamanoL(3) + "\n");
				System.out.println("Primer viaje cargado: " + "\n");
				String[] primero3 = modelo.darPrimero(3).split("-");
				System.out.println("Trimestre: " + primero3[0] + "\n" + "ID Zona de Origen: " + primero3[1] + "\n" + "ID Zona Destino: " + primero3[2] + "\n");
				System.out.println("Ultimo viaje cargado: " + "\n");
				String[] ultimo3 = modelo.darUltimo(3).split("-");
				System.out.println("Trimestre: " + ultimo3[0] + "\n" + "ID Zona de Origen: " + ultimo3[1] + "\n" + "ID Zona Destino: " + ultimo3[2] + "\n"); 
				break;
			case 2:
				System.out.println("Realizar consulta con: " + "\n");
				System.out.println("1. Tabla de Hash Linear Probing");
				System.out.println("2. Tabla de Hash Separate Chaining");
				int n = Integer.parseInt(lector.next());
				System.out.println("Dar el trimestre, seguido del id de la zona de origen, seguido del id de la zona destino (e.g. 3,2,4)");
				String[] ids = lector.next().split(",");
				TravelTime[] viajes;
				if (n == 1) {
					viajes = modelo.viajesLinProb(Integer.parseInt(ids[0]), ids[1], ids[2]);
					if (viajes==null) {
						System.out.println("No existen viajes que cumplan con los criterios dados.");
					}
					else {
						for (int i = 0; i < viajes.length; i++) {
							if (viajes[i] != null) {
								System.out.println(viajes[i].darValor());
							}
						}
					}
				}
				else if (n == 2) {
					viajes = modelo.viajesSepCh(Integer.parseInt(ids[0]), ids[1], ids[2]);
					if (viajes==null) {
						System.out.println("No existen viajes que cumplan con los criterios dados.");
					}
					else {
						for (int i = 0; i < viajes.length; i++) {
							if (viajes[i] != null) {
								System.out.println(viajes[i].darValor());
							}
						}
					}
				}
				break;
			case 3:
				System.out.println("Información sobre HashTable Linear Probing");
				System.out.println("Numero de duplas del primer archivo: " + modelo.darTamanoL(1) + "\n");
				System.out.println("Tamano inicial del primer archivo: 31\n");
				System.out.println("Tamano final del primer archivo: " + modelo.darTamanoMaxL(1) + "\n");
				System.out.println("Factor de carga del primer archivo: " + modelo.darFactorCargaL(1) + "\n");
				System.out.println("Numero de Rehashes del primer archivo: " + modelo.darRehashesL(1) + "\n\n");

				System.out.println("Numero de duplas del segundo archivo: " + modelo.darTamanoL(2) + "\n");
				System.out.println("Tamano inicial del segundo archivo: 31\n");
				System.out.println("Tamano final del segundo archivo: " + modelo.darTamanoMaxL(2) + "\n");
				System.out.println("Factor de carga del segundo archivo: " + modelo.darFactorCargaL(2) + "\n");
				System.out.println("Numero de Rehashes del segundo archivo: " + modelo.darRehashesL(2) + "\n\n");
				
				System.out.println("Numero de duplas del tercer archivo: " + modelo.darTamanoL(3) + "\n");
				System.out.println("Tamano inicial del tercer archivo: 31\n");
				System.out.println("Tamano final del tercer archivo: " + modelo.darTamanoMaxL(3) + "\n");
				System.out.println("Factor de carga del tercer archivo: " + modelo.darFactorCargaL(3) + "\n");
				System.out.println("Numero de Rehashes del tercer archivo: " + modelo.darRehashesL(3) + "\n\n");
				break;
			case 4:
				System.out.println("Información sobre HashTable Separate Chaining");
				System.out.println("Numero de duplas del primer archivo: " + modelo.darTamanoS(1) + "\n");
				System.out.println("Tamano inicial del primer archivo: 31\n");
				System.out.println("Tamano final del primer archivo: " + modelo.darTamanoMaxS(1) + "\n");
				System.out.println("Factor de carga del primer archivo: " + modelo.darFactorCargaS(1) + "\n");
				System.out.println("Numero de Rehashes del primer archivo: " + modelo.darRehashesS(1) + "\n\n");

				System.out.println("Numero de duplas del segundo archivo: " + modelo.darTamanoS(2) + "\n");
				System.out.println("Tamano inicial del segundo archivo: 31\n");
				System.out.println("Tamano final del segundo archivo: " + modelo.darTamanoMaxS(2) + "\n");
				System.out.println("Factor de carga del segundo archivo: " + modelo.darFactorCargaS(2) + "\n");
				System.out.println("Numero de Rehashes del segundo archivo: " + modelo.darRehashesS(2) + "\n\n");
				
				System.out.println("Numero de duplas del tercer archivo: " + modelo.darTamanoS(3) + "\n");
				System.out.println("Tamano inicial del tercer archivo: 31\n");
				System.out.println("Tamano final del tercer archivo: " + modelo.darTamanoMaxS(3) + "\n");
				System.out.println("Factor de carga del tercer archivo: " + modelo.darFactorCargaS(3) + "\n");
				System.out.println("Numero de Rehashes del tercer archivo: " + modelo.darRehashesS(3) + "\n\n");
				break;
			case 5:
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
