package controller;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import model.data_structures.Edge;
import model.data_structures.MaxPQ;
import model.data_structures.MinPQ;
import model.logic.Info;
import model.logic.MVCModelo;
import view.MVCView;
import model.logic.Queue;

public class Controller {

	/* Instancia del Modelo */
	private MVCModelo modelo;

	/* Instancia de la Vista */
	private MVCView view;

	private MaxPQ<Integer> maxPQ;

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
		String dato = "";
		String[] datos;
		String retorno = "";

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				modelo.CVSLector();
				modelo.TXTLector();
				System.out.println("Total vértices: " + modelo.totalVertices() + "\n---------");
				System.out.println("Total arcos: " + modelo.totalArcos() + "\n---------");
				System.out.println("Total componentes conexos: " + modelo.totalCC() + "\n---------");
				System.out.println("Los números de vértices de los componentes conexos más grandes del grafo son: ");
				maxPQ = modelo.comps();
				int tamanoC = maxPQ.size();
				if (tamanoC < 5) {
					for (int i = 0; i < tamanoC; i++) {
						System.out.println("Número de vértices: " + maxPQ.delMax());
					}
				} else {
					for (int i = 0; i < 5; i++) {
						System.out.println("Número de vértices: " + maxPQ.delMax());
					}
				}
				modelo.crearJSON();
				//modelo.JSONLector();
				modelo.generarHTML(false);
				break;
			case 2:
				System.out.println("Para retornar el camino de mínimo costo, elija uno de los siguientes criterios: ");
				System.out.println("1. Menor tiempo promedio según Uber en la ruta");
				System.out.println("2. Menor distancia Harversine");
				int op = lector.nextInt();
				System.out.println(
						"Ingrese dos localizaciones geográficas: La de la localización de origen y la de la localización de destino (latitudOrigen,longitudOrigen,latitudDestino,longitudDestino)");
				String line = lector.next();
				datos = line.split(",");
				if (op == 1) {
					Iterable<Edge> rta = modelo.tiempoMinimo(Double.valueOf(datos[0]), Double.valueOf(datos[1]),
							Double.valueOf(datos[2]), Double.valueOf(datos[3]));
					Queue<Edge> camino = new Queue<Edge>();
					for (Edge arco : rta) {
						camino.enqueue(arco);
					}
					Queue<Integer> vertices = new Queue<Integer>();
					int tam = camino.size();
					System.out.println("El camino a seguir es el siguiente: ");
					retorno = "= [";
					for (int i = 0; i < tam; i++) {
						Edge edge = camino.dequeue();
						int ver1 = edge.either();
						int ver2 = edge.other(ver1);
						Info infor1 = modelo.infoVertex(ver1);
						double lng1 = infor1.getLng();
						double lat1 = infor1.getLat();
						Info infor2 = modelo.infoVertex(ver2);
						double lng2 = infor2.getLng();
						double lat2 = infor2.getLat();
						System.out.println("Arco entre el vértice: " + ver1 + " con longitud " + lng1 + " y latitud"
								+ lat1 + ", y el vértice: " + ver2 + " con longitud " + lng2 + " y latitud " + lat2);
						retorno += "{lat: "+lat1+",lng: "+lng1+"},\r\n"+
							"{lat: "+lat2+",lng: "+lng2+"},\r\n";
						Queue<Integer> copia = vertices;
						boolean yaEsta1 = false;
						boolean yaEsta2 = false;
						for (int j = 0; j < vertices.size(); j++) {
							yaEsta1 = false;
							yaEsta2 = false;
							if (vertices.size() == 0) {
								vertices.enqueue(ver1);
								vertices.enqueue(ver2);
							} else {
								int vActual = copia.dequeue();
								if (vActual == ver1) {
									yaEsta1 = true;
								}
								if (vActual == ver2) {
									yaEsta2 = true;
								}
							}
						}
						if (!yaEsta1) {
							vertices.enqueue(ver1);
						}
						if (!yaEsta2) {
							vertices.enqueue(ver2);
						}
					}
					retorno += "];\r\n";
					HTML(2, retorno);
					System.out.println(
							"El costo mínimo (menor tiempo promedio) del camino es: " + modelo.tiempoMinimoPromedio());
					System.out.println(
							"La distancia estimada (Haversine) en km del camino es: " + modelo.distanciaEstimada());
					System.out.println("El número de vertices en el camino es: " + vertices.size());
				} else if (op == 2) {
					Iterable<Edge> rta = modelo.distanciaMinima(Double.valueOf(datos[0]), Double.valueOf(datos[1]),
							Double.valueOf(datos[2]), Double.valueOf(datos[3]));
					Queue<Edge> camino = new Queue<Edge>();
					for (Edge arco : rta) {
						camino.enqueue(arco);
					}
					Queue<Integer> vertices = new Queue<Integer>();
					int tam = camino.size();
					System.out.println("El camino a seguir es el siguiente: ");
					retorno = "= [";
					for (int i = 0; i < tam; i++) {
						Edge edge = camino.dequeue();
						int ver1 = edge.either();
						int ver2 = edge.other(ver1);
						Info infor1 = modelo.infoVertex(ver1);
						double lng1 = infor1.getLng();
						double lat1 = infor1.getLat();
						Info infor2 = modelo.infoVertex(ver2);
						double lng2 = infor2.getLng();
						double lat2 = infor2.getLat();
						System.out.println("Arco entre el vértice: " + ver1 + " con longitud " + lng1 + " y latitud"
								+ lat1 + ", y el vértice: " + ver2 + " con longitud " + lng2 + " y latitud " + lat2);
						retorno += "{lat: "+lat1+",lng: "+lng1+"},\r\n"+
								"{lat: "+lat2+",lng: "+lng2+"},\r\n";
						Queue<Integer> copia = vertices;
						boolean yaEsta1 = false;
						boolean yaEsta2 = false;
						for (int j = 0; j < vertices.size(); j++) {
							yaEsta1 = false;
							yaEsta2 = false;
							if (vertices.size() == 0) {
								vertices.enqueue(ver1);
								vertices.enqueue(ver2);
							} else {
								int vActual = copia.dequeue();
								if (vActual == ver1) {
									yaEsta1 = true;
								}
								if (vActual == ver2) {
									yaEsta2 = true;
								}
							}
						}
						if (!yaEsta1) {
							vertices.enqueue(ver1);
						}
						if (!yaEsta2) {
							vertices.enqueue(ver2);
						}
					}
					retorno += "];\r\n";
					HTML(2, retorno);
					System.out.println(
							"El costo mínimo (menor tiempo promedio) del camino es: " + modelo.tiempoMinimoPromedio());
					System.out.println(
							"La distancia estimada (Haversine) en km del camino es: " + modelo.distanciaEstimada());
					System.out.println("El número de vertices en el camino es: " + vertices.size());
				}
				break;
			case 3:
				System.out.println("Ingrese el número de vértices que desea buscar:");
				int vertices = lector.nextInt();
				MinPQ<Info> verts = modelo.verticesMenorVelocidad(vertices);
				int tam = verts.size();
				int numCC = 0;
				Queue<Integer> vertsComps = new Queue<Integer>();
				for (int i = 0; i < tam; i++) {
					Info infor = verts.delMin();
					int ver = infor.getID();
					double lng = infor.getLng();
					double lat = infor.getLat();
					System.out.println("Vértice " + ver + ": Con longitud " + lng + " y latitud " + lat);
					retorno = "= [{lat: "+lat+",lng: "+lng+"},\r\n";
					Iterable<Integer> compsCon = modelo.getCC(ver);
					if (numCC == 0) {
						System.out.println("El primer componente conexo esta compuesto por los siguientes vértices: ");
						for (Integer vActual : compsCon) {
							vertsComps.enqueue(vActual);
							System.out.println("El vértice con el ID: " + vActual);
							Info inf = modelo.infoVertex(vActual);
							retorno += "{lat: "+inf.getLat()+",lng: "+inf.getLng()+"},\r\n";
						}
						retorno += "];\r\n";
						HTML(2, retorno);
						numCC++;
					} else {
						boolean yaEsta = false;
						for (Integer vActual : compsCon) {
							for (Integer vert : vertsComps) {
								if (vActual == vert) {
									yaEsta = true;
								}
							}
						}
						if (!yaEsta) {
							System.out.println(
									"El siguiente componente conexo esta compuesto por los siguientes vértices ");
							retorno = "=[";
							for (Integer vActual : compsCon) {
								vertsComps.enqueue(vActual);
								System.out.println("El vértice con el ID: " + vActual);
								Info inf = modelo.infoVertex(vActual);
								retorno += "{lat: "+inf.getLat()+",lng: "+inf.getLng()+"},\r\n" +
								"{lat: "+inf.getLat()+",lng: "+inf.getLng()+"},\r\n";
							}
							retorno += "];\r\n";
							HTML(3, retorno);
							numCC++;
						}
					}
					System.out.println(
							"El número de componentes conexos (subgrafos) que se definen entre estos vértices es: "
									+ numCC);
				}
				break;
			case 4:
				System.out.println("Para retornar el MST, elija uno de los siguientes algoritmos: ");
				System.out.println("1. Prim");
				System.out.println("2. Kruskal");
				int ops = lector.nextInt();
				if (ops == 1) {
					long startTime = System.currentTimeMillis();// medici�n tiempo actual
					Iterable<Edge> mst = modelo.MSTDistanciaPrim();
					long endTime = System.currentTimeMillis();// medici�n tiempo actual
					long duration = endTime - startTime;// duracion de ejecucion del algoritmo
					Queue<Edge> camino = new Queue<Edge>();
					for (Edge arco : mst) {
						camino.enqueue(arco);
					}
					Queue<Integer> vers = new Queue<Integer>();
					int tama = camino.size();
					System.out.println("El tamaño del árbol es: " + tama);
					retorno = "= [";
					for (int i = 0; i < tama; i++) {
						Edge edge = camino.dequeue();
						int ver1 = edge.either();
						int ver2 = edge.other(ver1);
						Info infor1 = modelo.infoVertex(ver1);
						double lng1 = infor1.getLng();
						double lat1 = infor1.getLat();
						Info infor2 = modelo.infoVertex(ver2);
						double lng2 = infor2.getLng();
						double lat2 = infor2.getLat();
						System.out.println("Arco entre el vértice: " + ver1 + " con longitud " + lng1 + " y latitud "
								+ lat1 + ", y el vértice: " + ver2 + " con longitud " + lng2 + " y latitud " + lat2);
						retorno += "{lat: "+lat1+",lng: "+lng1+"},\r\n"+
								"{lat: "+lat2+",lng: "+lng2+"},\r\n"
							+ "];\r\n";
							HTML(2, retorno);
						Queue<Integer> copia = vers;
						boolean yaEsta1 = false;
						boolean yaEsta2 = false;
						for (int j = 0; j < vers.size(); j++) {
							yaEsta1 = false;
							yaEsta2 = false;
							if (vers.size() == 0) {
								vers.enqueue(ver1);
								vers.enqueue(ver2);
							} else {
								int vActual = copia.dequeue();
								if (vActual == ver1) {
									yaEsta1 = true;
								}
								if (vActual == ver2) {
									yaEsta2 = true;
								}
							}
						}
						if (!yaEsta1) {
							vers.enqueue(ver1);
						}
						if (!yaEsta2) {
							vers.enqueue(ver2);
						}
					}
					retorno += "];\r\n";
					HTML(2, retorno);
					System.out.println(
							"El tiempo que le toma al algoritmo para encontrar la solución en ms es: " + duration);
					System.out.println("El costo total (distancia en Km) del árbol es: " + modelo.costMinEnKm());
					System.out.println("El número de vertices en el mst es: " + vers.size());
				} else if (ops == 2) {
					long startTime = System.currentTimeMillis();// medici�n tiempo actual
					Iterable<Edge> rta = modelo.MSTDistanciaKruskal();
					long endTime = System.currentTimeMillis();// medici�n tiempo actual
					long duration = endTime - startTime;// duracion de ejecucion del algoritmo
					Queue<Edge> mst = new Queue<Edge>();
					for (Edge arco : rta) {
						mst.enqueue(arco);
					}
					Queue<Integer> vers = new Queue<Integer>();
					int tama = mst.size();
					System.out.println("El tamaño del árbol es: " + tama);
					retorno = "= [";
					for (int i = 0; i < tama; i++) {
						Edge ed = mst.dequeue();
						int ver1 = ed.either();
						int ver2 = ed.other(ver1);
						Info infor1 = modelo.infoVertex(ver1);
						double lng1 = infor1.getLng();
						double lat1 = infor1.getLat();
						Info infor2 = modelo.infoVertex(ver2);
						double lng2 = infor2.getLng();
						double lat2 = infor2.getLat();
						System.out.println("Arco entre el vértice: " + ver1 + " con longitud " + lng1 + " y latitud "
								+ lat1 + ", y el vértice: " + ver2 + " con longitud " + lng2 + " y latitud " + lat2);
						retorno += "{lat: "+lat1+",lng: "+lng1+"},\r\n"+
								"{lat: "+lat2+",lng: "+lng2+"},\r\n";
						Queue<Integer> copia = vers;
						boolean yaEsta1 = false;
						boolean yaEsta2 = false;
						for (int j = 0; j < vers.size(); j++) {
							yaEsta1 = false;
							yaEsta2 = false;
							if (vers.size() == 0) {
								vers.enqueue(ver1);
								vers.enqueue(ver2);
							} else {
								int vActual = copia.dequeue();
								if (vActual == ver1) {
									yaEsta1 = true;
								}
								if (vActual == ver2) {
									yaEsta2 = true;
								}
							}
						}
						if (!yaEsta1) {
							vers.enqueue(ver1);
						}
						if (!yaEsta2) {
							vers.enqueue(ver2);
						}
					}
					retorno += "];\r\n";
					HTML(2, retorno);
					System.out.println(
							"El tiempo que le toma al algoritmo para encontrar la solución en ms es: " + duration);
					System.out.println("El costo total (distancia en Km) del árbol es: " + modelo.costMinEnKm());
					System.out.println("El número de vertices en el mst es: " + vers.size());
				}
				break;
			case 5:
				System.out.println(
						"Ingrese la localización geográfica de origen y el tiempo en segundos (longitud,latitud,tiempo)");
				dato = lector.next();
				datos = dato.split(",");
				System.out.println(
						"Los vértices alcanzables en el tiempo dado en segundos desde el vértice con la localización geográfica dada son: ");
				Queue<Integer> alcanzables = modelo.verticesAlcanzables(Double.valueOf(datos[0]),
						Double.valueOf(datos[1]), Double.valueOf(datos[2]));
				int tamano = alcanzables.size();
				retorno = "= [{lat: "+datos[0]+",lng: "+datos[1]+"},\r\n";
				for (int i = 0; i < tamano; i++) {
					int ver = alcanzables.dequeue();
					Info infor = modelo.infoVertex(modelo.keyToInteger().get(ver));
					double lng = infor.getLng();
					double lat = infor.getLat();
					System.out.println("Vértice " + ver + ": Con longitud " + lng + " y latitud " + lat);
					retorno += "{lat: "+lat+",lng: "+lng+"},\r\n";
				}
				retorno += "];\r\n";
				HTML(2, retorno);
				break;
			case 6:
				modelo.grafoSimplificado();
				System.out.println("Total vértices: " + modelo.totalVerticesGrafoZonas() + "\n---------");
				System.out.println("Total arcos: " + modelo.totalArcosGrafoZonas() + "\n---------");
				modelo.generarHTML(true);
				break;
			case 7:
				System.out.println("Ingrese dos zonas: La de origen y la de destino (zonaOrigen,zonaDestino)");
				String lineas = lector.next();
				datos = lineas.split(",");
				long startTime = System.currentTimeMillis();// medici�n tiempo actual
				Iterable<Edge> rta = modelo.MSTDijkstra(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]));
				long endTime = System.currentTimeMillis();// medici�n tiempo actual
				long duration = endTime - startTime;// duracion de ejecucion del algoritmo
				Queue<Edge> costoMin = new Queue<Edge>();
				for (Edge arco : rta) {
					costoMin.enqueue(arco);
				}
				int tama = costoMin.size();
				System.out.println("El camino a seguir es el siguiente: ");
				retorno = "= [";
				for (int i = 0; i < tama; i++) {
					Edge edge = costoMin.dequeue();
					int ver1 = edge.either();
					int ver2 = edge.other(ver1);
					Info infor1 = modelo.infoVertex(modelo.keyToInteger().get(ver1));
					double lng1 = infor1.getLng();
					double lat1 = infor1.getLat();
					Info infor2 = modelo.infoVertex(modelo.keyToInteger().get(ver2));
					double lng2 = infor2.getLng();
					double lat2 = infor2.getLat();
					System.out.println("Arco entre la zona: " + ver1 + " con longitud " + lng1 + " y latitud" + lat1
							+ ", y la zona: " + ver2 + " con longitud " + lng2 + " y latitud " + lat2);
					retorno += "= [{lat: "+lat1+",lng: "+lng1+"},\r\n"+
							"{lat: "+lat2+",lng: "+lng2+"},\r\n";
				}
				retorno += "];\r\n";
				HTML(2, retorno);
				System.out.println(
						"El costo mínimo total, que es menor tiempo promedio desde la zona de origen hasta la zona destino reportado por el archivo de resolución semanal de Uber, es: "
								+ modelo.tiempoMinimoPromedioZonas());
				System.out
						.println("El tiempo que le toma al algoritmo para encontrar la solución en ms es: " + duration);
				break;
			case 8:
				System.out.println("Ingrese una zona");
				int lin = lector.nextInt();
				long startTime1 = System.currentTimeMillis();// medici�n tiempo actual
				Iterable<Edge> camino = modelo.caminoMasDistante(lin);
				long endTime1 = System.currentTimeMillis();// medici�n tiempo actual
				long duration1 = endTime1 - startTime1;// duracion de ejecucion del algoritmo
				Queue<Edge> arcos = new Queue<Edge>();
				for (Edge e : camino) {
					arcos.enqueue(e);
				}
				int ta = arcos.size();
				retorno = "= [";
				for (int i = 0; i < ta; i++) {
					Edge arco = arcos.dequeue();
					int ver1 = arco.either();
					int ver2 = arco.other(ver1);
					Info infor1 = modelo.infoVertex(modelo.keyToInteger().get(ver1));
					int movID1 = infor1.getMOVEMENT_ID();
					Info infor2 = modelo.infoVertex(modelo.keyToInteger().get(ver2));
					int movID2 = infor2.getMOVEMENT_ID();
					System.out.println(
							"Arco entre la zona con MOVEMENT_ID " + movID1 + ", y la zona con MOVEMENT_ID " + movID2);
					retorno += "{lat: "+infor1.getLat()+",lng: "+infor1.getLng()+"},\r\n"+
							"{lat: "+infor2.getLng()+",lng: "+infor2.getLng()+"},\r\n";
				}
				retorno += "];\r\n";
				HTML(2, retorno);
				System.out.println(
						"El tiempo que le toma al algoritmo para encontrar la solución en ms es: " + duration1);
				System.out.println("El número total de arcos es: " + arcos.size());
				break;
			case 9:
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

	public void HTML(int op, String datos) {
		String hex = "";
		int a = 0xFF0000,b = 0x100;
		File file = new File("./data/Mapa"+op+".html");
		try {
		// creates the file
		file.createNewFile();
		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);
		// Writes the content to the file
		writer.write("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"  <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\r\n" + 
				"  <meta charset=\"utf-8\">\r\n" + 
				"  <title>Simple Polylines</title>\r\n" + 
				"  <style>\r\n" + 
				"    #map {\r\n" + 
				"      height: 100%;\r\n" + 
				"    }\r\n" + 
				"    html,\r\n" + 
				"    body {\r\n" + 
				"      height: 100%;\r\n" + 
				"      margin: 0;\r\n" + 
				"      padding: 0;\r\n" + 
				"    }\r\n" + 
				"  </style>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"  <div id=\"map\"></div>\r\n" + 
				"  <script>\r\n" + 
				"    function initMap() {\r\n" + 
				"      var map = new google.maps.Map(document.getElementById('map'), {\r\n" + 
				"        zoom: 10,\r\n" + 
				"        center: {\r\n" + 
				"          lat: 4.585891,\r\n" + 
				"          lng: -74.046699\r\n" + 
				"        },\r\n" + 
				"        mapTypeId: 'roadmap'\r\n" + 
				"      });\r\n" + 
				"	var line;\r\n" + 
				"        var path;\r\n" + 
				"	var GrafoC;\r\n"
				+ "   var Grafo;\r\n"); 
		switch (op) {
		case 2:
			writer.write("          GrafoC "+datos);
			writer.write("     Grafo = new google.maps.Polyline({\r\n" + 
					"          path: GrafoC,\r\n" + 
					"          geodesic: true,\r\n"+
					"          strokeColor: '#");
			a = a + b;
			hex = Integer.toHexString(a);
			writer.write(hex+"',\r\n" + 
					"          strokeOpacity: 1.0,\r\n" + 
					"          strokeWeight: 2\r\n" + 
					"        });\r\n" + 
					"        Grafo.setMap(map);\r\n");
			break;
		case 3:
			writer.write("          GrafoC "+datos);
			writer.write("     Grafo = new google.maps.Polyline({\r\n" + 
					"          path: GrafoC,\r\n" + 
					"          geodesic: true,\r\n"+
					"          strokeColor: '#");
			a = a + b + 0xF00000;
			hex = Integer.toHexString(a);
			writer.write(hex+"',\r\n" + 
					"          strokeOpacity: 1.0,\r\n" + 
					"          strokeWeight: 2\r\n" + 
					"        });\r\n" + 
					"        Grafo.setMap(map);\r\n");
			break;
		default:
			break;
		}
		writer.write( 
				"    }\r\n" + 
				"  </script>\r\n" + 
				"  <script async defer src=\"https://maps.googleapis.com/maps/api/js?key=&callback=initMap\">\r\n" + 
				"  </script>\r\n" + 
				"</body>\r\n" + 
				"\r\n" + 
				"</html>");
		writer.flush();
		writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
