package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import model.data_structures.CC;
import model.data_structures.Edge;
import model.data_structures.Grafo;
import model.data_structures.LinearProbingHashST;
import model.data_structures.MaxPQ;
import model.data_structures.MinPQ;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	private Info infos;
	private Grafo<Integer, Info> datos = new Grafo<>(1), metodo;
	private Queue<Info> vertices = new Queue<>();
	private Queue<Edge> arcos = new Queue<>();
	private HashTableLP<String, String> linPr = new HashTableLP<>(31);
	private MaxPQ<Integer> maxPQ;
	private double minPromedio = 0;
	private double distanciaEstimada = 0;
	private double minPromedioZonas = 0;
	private double distanciaEstimadaZonas = 0;
	private double costoMinKM = 0;

	// CARGA DE INFORMACION
	/**
	 * Lector de los archivos
	 */
	public void CVSLector() {
		CSVReader reader = null;
		UberTrip carga = null;
		try {
			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-5-WeeklyAggregate.csv"));
			reader.readNext();
			for (String[] nextLine : reader) {
				carga = new UberTrip(nextLine, 1);
				linPr.put(carga.darLlave(), carga.darValor());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void TXTLector() {
		FileReader fr = null;
		BufferedReader br = null;
		String st;
		String[] a = new String[10];
		int i;
		try {
			fr = new FileReader("./data/bogota_verticesT.txt");
			br = new BufferedReader(fr);
			br.readLine();
			while ((st = br.readLine()) != null) {
				a = st.split(";");
				infos = new Info(a[0], a[1], a[2], a[3]);
				datos.addVertex(Integer.parseInt(a[0]), infos);
				vertices.enqueue(infos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fr = new FileReader("./data/bogota_arcosT.txt");
			br = new BufferedReader(fr);
			while ((st = br.readLine()) != null) {
				a = st.split(" ");
				st = a[0];
				for (i = 1; i < a.length; i++) {
					Info infor = datos.getInfoVertex(Integer.parseInt(st));
					Info inform = datos.getInfoVertex(Integer.parseInt(a[i]));
					if (inform == null || infor == null) {
						continue;
					}
					double dist = distance(infor.getLat(), infor.getLng(), inform.getLat(), inform.getLng());
					double temp = tiempo(infor.getMOVEMENT_ID(), inform.getMOVEMENT_ID());
					Edge ed = new Edge(Integer.parseInt(st), Integer.parseInt(a[i]), dist, temp, velocidad(dist, temp));
					datos.addEdge(ed.either(), ed.other(ed.either()), dist, temp, ed.weightVelocidad());
					arcos.enqueue(ed);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void JSONLector() {
		String path = "./data/grafo.json";
		JsonReader reader;
		int i = 0, id;
		double dat;
		try {
			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonElement e = elem.getAsJsonObject().get("grafo").getAsJsonObject().get("vertices");
			while (i < elem.getAsJsonObject().get("grafo").getAsJsonObject().get("arcos").getAsJsonArray().size()) {
				id = elem.getAsJsonObject().get("grafo").getAsJsonObject().get("arcos").getAsJsonArray().get(i)
						.getAsJsonObject().get("id_inicio").getAsInt();
				id = elem.getAsJsonObject().get("grafo").getAsJsonObject().get("arcos").getAsJsonArray().get(i)
						.getAsJsonObject().get("id_final").getAsInt();
				dat = elem.getAsJsonObject().get("grafo").getAsJsonObject().get("arcos").getAsJsonArray().get(i)
						.getAsJsonObject().get("distancia").getAsDouble();
				dat = elem.getAsJsonObject().get("grafo").getAsJsonObject().get("arcos").getAsJsonArray().get(i)
						.getAsJsonObject().get("tiempo").getAsDouble();
				dat = elem.getAsJsonObject().get("grafo").getAsJsonObject().get("arcos").getAsJsonArray().get(i)
						.getAsJsonObject().get("velocidad").getAsDouble();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int totalVertices() {
		return datos.V();
	}

	public int totalArcos() {
		return datos.E();
	}

	public int totalCC() {
		return datos.cc();
	}

	public MaxPQ<Integer> comps() {
		maxPQ = new MaxPQ<Integer>(totalCC());
		int[] compIds = datos.ids();
		CC calculatedCC = datos.calculatedComps();
		for (int i = 0; i < totalCC(); i++) {
			maxPQ.insert(calculatedCC.size(compIds[i]));
		}
		maxPQ.insert(5);
		return maxPQ;

	}

	// Crea un JSON para guardar el grafo
	public void crearJSON() {
		Queue<Info> copia = vertices;
		try {
			FileWriter file = new FileWriter("./data/grafo.json");
			file.write("{\r\n" + "    \"grafo\": ");
			while (!copia.isEmpty()) {
				Info f = copia.dequeue();
				Iterator<Integer> it = datos.adj(f.getID()).iterator();
				file.write("{\r\n" + "        \"vertices\": {\r\n" + "                \"id\": " + f.getID() + ",\r\n"
						+ "                \"longitud\": " + f.getLng() + ",\r\n" + "                \"latitud\": "
						+ f.getLat() + ",\r\n" + "                \"MOVEMENT_ID\": " + f.getMOVEMENT_ID() + "\r\n"
						+ "            }\r\n");
				while (it.hasNext()) {
					int a = it.next();
					file.write("        \"arcos\": [\r\n" + "            {\r\n" + "                \"id_inicio\": "
							+ f.getID() + ",\r\n" + "                \"id_final\": " + a + ",\r\n"
							+ "                \"distancia\": " + datos.getCostArcHarversine(f.getID(), a) + ",\r\n"
							+ "                \"tiempo\": " + datos.getCostArcTiempo(f.getID(), a) + ",\r\n"
							+ "                \"velocidad\": " + datos.getCostArcVelocidad(f.getID(), a) + "\r\n"
							+ "            },\r\n");
				}
				file.write("        ]\r\n");
			}
			file.write("    }\r\n" + "}");
			file.flush();
			file.close();
		} catch (IOException e) {

		}
	}

	/*
	 * 3A Inicial Dada una localizaci�n geogr�fica con latitud y longitud,
	 * encontrar el Id del V�rtice de la malla vial m�s cercano por distancia
	 * Haversine. 4.58299,-74.08921,4.58256,-74.08953
	 * 
	 * @param lat latitud, lng longitud
	 * 
	 * @return idMasCercano
	 */
	public int idMasCercano(double lat, double lng) {
		int a = 0, ret = 0;
		double latV, lngV, min = Double.POSITIVE_INFINITY, prueba;
		Queue<Info> copia = vertices;
		Info inf;
		Iterator<Integer> it;
		for (int i = 0; i < vertices.size(); i++) {
			inf = copia.dequeue();
			latV = (double) Math.round(inf.getLat() * 100000d) / 100000d;
			if (latV != lat)
				continue;
			lngV = (double) Math.round(inf.getLng() * 100000d) / 100000d;
			if (lngV != lng)
				continue;
			it = datos.adj(inf.getID()).iterator();
			while (it.hasNext()) {
				a = it.next();
				prueba = datos.getCostArcHarversine(i, a);
				if (min < prueba) {
					min = prueba;
					ret = a;
				}
			}
		}
		return ret;
	}

	/*
	 * 4A Encontrar el camino de costo m�nimo (menor tiempo promedio) para un
	 * viaje entre dos localizaciones geogr�ficas de la ciudad.
	 * 
	 * @param lat1 latitud inicial, lng1 longitud inicial, lat2 latitud destino,
	 * lng2 longitud destino
	 */
	public Iterable<Edge> tiempoMinimo(double lat1, double lng1, double lat2, double lng2) {
		int idVertOrigen = idMasCercano(lat1, lng1);
		int idVertDestino = idMasCercano(lat2, lng2);
		DijkstraTiempoPromedio caminoCostoMin = new DijkstraTiempoPromedio(datos.edgeWeightedGrap(),
				datos.keyToInteger().get(idVertOrigen));
		Iterable<Edge> caminoASeguir = caminoCostoMin.pathTo(datos.keyToInteger().get(idVertDestino));
		minPromedio = caminoCostoMin.distTo(datos.keyToInteger().get(idVertDestino));
		distanciaEstimada = caminoCostoMin.distHarv(datos.keyToInteger().get(idVertDestino));
		return caminoASeguir;
	}

	public double tiempoMinimoPromedio() {
		return minPromedio;
	}

	public double distanciaEstimada() {
		return distanciaEstimada;
	}

	/*
	 * 5A Determinar los n vertices con menor velocidad promedio
	 * 
	 * @param n numero de datos
	 */
	public MinPQ<Info> verticesMenorVelocidad(int n) {
		Queue<Info> copia = vertices;
		MinPQ<Info> organizar = new MinPQ<Info>();
		MinPQ<Info> rta = new MinPQ<Info>();
		for (int i = 0; i < vertices.size(); i++) {
			Info infoVert = copia.dequeue();
			Iterable<Edge> res = datos.arcosAdj(infoVert.getID());
			double velVert = 0;
			for (Edge arco : res) {
				velVert = velVert + arco.weightVelocidad();
			}
			infoVert.setVelocidad(velVert);
			organizar.insert(infoVert);
		}
		for (int i = 0; i < n; i++) {
			rta.insert(organizar.delMin());
		}
		return rta;
	}

	public Iterable<Integer> getCC(int vert) {
		Iterable<Integer> compCon = datos.getCC(datos.keyToInteger().get(vert));
		return compCon;
	}

	/*
	 * 6A Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia,
	 * utilizando el algoritmo de Prim, aplicado al componente conectado (subgrafo)
	 * m�s grande.
	 */
	public Iterable<Edge> MSTDistanciaPrim() {
		Queue<Integer> ver = new Queue<Integer>();
		Queue<Info> copia = vertices;
		metodo = new Grafo<>(1);
		int cont = 0, mayor = 0, indice = 0;
		Iterator<Integer> it;
		Info inf;
		for (int i = 0; i < vertices.size(); i++) {
			inf = copia.dequeue();
			it = datos.getCC(inf.getID()).iterator();
			while (it.hasNext()) {
				cont++;
				it.next();
			}
			if (mayor < cont) {
				mayor = cont;
				indice = inf.getID();
			}
			cont = 0;
		}
		it = datos.getCC(indice).iterator();
		while (it.hasNext()) {
			indice = it.next();
			metodo.addVertex(indice, datos.getInfoVertex(indice));
			ver.enqueue(indice);
		}
		for (int i = 0; i < ver.size(); i++) {
			mayor = ver.dequeue();
			it = datos.adj(mayor).iterator();
			while (it.hasNext()) {
				indice = it.next();
				metodo.addEdge(mayor, indice, datos.getCostArcHarversine(mayor, indice),
						datos.getCostArcTiempo(mayor, indice), datos.getCostArcVelocidad(mayor, indice));
			}
		}
		AlgoPrim ret = new AlgoPrim(metodo.edgeWeightedGrap());
		Iterable<Edge> retu = ret.edges();
		costoMinKM = ret.weight();
		return retu;
	}

	public double costMinEnKm() {
		return costoMinKM;
	}

	/*
	 * 7B Encontrar el camino de costo m�nimo (menor distancia Haversine) para un
	 * viaje entre dos localizaciones geogr�ficas de la ciudad.
	 * 
	 * @param lat1 latitud inicial, lng1 longitud inicial, lat2 latitud destino,
	 * lng2 longitud destino
	 */
	public Iterable<Edge> distanciaMinima(double lat1, double lng1, double lat2, double lng2) {
		int idVert1 = idMasCercano(lat1, lng1);
		int idVert2 = idMasCercano(lat2, lng2);
		DijkstraDistanciaHaversine caminoCostoMin = new DijkstraDistanciaHaversine(datos.edgeWeightedGrap(),
				datos.keyToInteger().get(idVert1));
		Iterable<Edge> caminoASeguir = caminoCostoMin.pathTo(datos.keyToInteger().get(idVert2));
		distanciaEstimada = caminoCostoMin.distTo(datos.keyToInteger().get(idVert2));
		minPromedio = caminoCostoMin.tiempoEstimado(datos.keyToInteger().get(idVert2));
		return caminoASeguir;
	}

	/*
	 * 8B A partir de las coordenadas de una localizaci�n geogr�fica de la
	 * ciudad (lat, lon) de origen, indique cu�les v�rtices son alcanzables para
	 * un tiempo T (en segundos) dado por el usuario.
	 * 
	 * @param lat latitud, lng longitud, T tiempo
	 */
	public Queue<Integer> verticesAlcanzables(double lat, double lng, double T) {
		Queue<Integer> a = new Queue<Integer>();
		int idVertOrigen = idMasCercano(lat, lng);
		Queue<Info> copia = vertices;
		DijkstraTiempoPromedio caminoCostoMin = new DijkstraTiempoPromedio(datos.edgeWeightedGrap(),
				datos.keyToInteger().get(idVertOrigen));
		for (int i = 0; i < vertices.size(); i++) {
			Info vertActual = copia.dequeue();
			int idVertActual = vertActual.getID();
			int keyVertActual = datos.keyToInteger().get(idVertActual);
			if (caminoCostoMin.distTo(keyVertActual) <= T) {
				a.enqueue(idVertActual);
			}
		}
		return a;
	}

	/*
	 * 9B Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia,
	 * utilizando el algoritmo de Kruskal, aplicado al componente conectado
	 * (subgrafo) m�s grande.
	 */
	public Iterable<Edge> MSTDistanciaKruskal() {
		Queue<Integer> ver = new Queue<Integer>();
		Queue<Info> copia = vertices;
		metodo = new Grafo<>(1);
		int cont = 0, mayor = 0, indice = 0;
		Iterator<Integer> it;
		Info inf;
		for (int i = 0; i < vertices.size(); i++) {
			inf = copia.dequeue();
			it = datos.getCC(inf.getID()).iterator();
			while (it.hasNext()) {
				cont++;
				it.next();
			}
			if (mayor < cont) {
				mayor = cont;
				indice = inf.getID();
			}
			cont = 0;
		}
		it = datos.getCC(indice).iterator();
		while (it.hasNext()) {
			indice = it.next();
			metodo.addVertex(indice, datos.getInfoVertex(indice));
			ver.enqueue(indice);
		}
		for (int i = 0; i < ver.size(); i++) {
			mayor = ver.dequeue();
			it = datos.adj(mayor).iterator();
			while (it.hasNext()) {
				indice = it.next();
				metodo.addEdge(mayor, indice, datos.getCostArcHarversine(mayor, indice),
						datos.getCostArcTiempo(mayor, indice), datos.getCostArcVelocidad(mayor, indice));
			}
		}
		AlgoKruskal ret = new AlgoKruskal(metodo.edgeWeightedGrap());
		Iterable<Edge> retu = ret.edges();
		costoMinKM = ret.weight();
		return retu;
	}

	/*
	 * 10C Crear otro grafo
	 * 
	 * @param n numero de datos
	 */
	public void grafoSimplificado() {
		metodo = new Grafo<>(1);
		Queue<Info> copia = vertices;
		Iterator<Edge> it = datos.edgeWeightedGrap().edges().iterator();
		Info inf, inf2;
		Edge ed;
		double tim = 0;
		int i;
		for (i = 0; i < copia.size(); i++) {
			inf = copia.dequeue();
			if (metodo.getInfoVertex(inf.getMOVEMENT_ID()) == null) {
				metodo.addVertex(inf.getMOVEMENT_ID(), inf);
				vertices = new Queue<Info>();
				vertices.enqueue(inf);
			}
		}
		while (it.hasNext()) {
			ed = it.next();
			inf = datos.getInfoVertex(ed.either());
			inf2 = datos.getInfoVertex(ed.other(ed.either()));
			if (metodo.getInfoVertex(ed.either()) == null || metodo.getInfoVertex(inf2.getMOVEMENT_ID()) == null) {
				continue;
			}
			inf = metodo.getInfoVertex(inf.getMOVEMENT_ID());
			inf2 = metodo.getInfoVertex(inf2.getMOVEMENT_ID());
			if (inf == null || inf2 == null) {
				continue;
			}
			tim = metodo.getCostArcTiempo(inf.getMOVEMENT_ID(), inf2.getMOVEMENT_ID());
			if (tim == -1) {
				metodo.addEdge(inf.getMOVEMENT_ID(), inf2.getMOVEMENT_ID(), ed.weightHarversine(), ed.weightTiempo(),
						ed.weightVelocidad());
			} else {
				tim = (tim + ed.weightTiempo()) / 2;
				metodo.setCostArc(inf.getMOVEMENT_ID(), inf2.getMOVEMENT_ID(), ed.weightHarversine(), tim,
						ed.weightVelocidad());
			}
		}
	}

	public int totalVerticesGrafoZonas() {
		return metodo.V();
	}

	public int totalArcosGrafoZonas() {
		return metodo.E();
	}

	/*
	 * 11C Calcular el camino de costo m�nimo (algoritmo de Dijkstra) basado en el
	 * tiempo promedio entre una zona de origen y una zona de destino sobre el grafo
	 * de zonas.
	 */
	public Iterable<Edge> MSTDijkstra(int zona1, int zona2) {
		DijkstraTiempoPromedio caminoCostoMin = new DijkstraTiempoPromedio(metodo.edgeWeightedGrap(),
				metodo.keyToInteger().get(zona1));
		Iterable<Edge> caminoASeguir = caminoCostoMin.pathTo(metodo.keyToInteger().get(zona1));
		minPromedioZonas = caminoCostoMin.distTo(metodo.keyToInteger().get(zona2));
		distanciaEstimadaZonas = caminoCostoMin.distHarv(metodo.keyToInteger().get(zona2));
		return caminoASeguir;
	}

	public double tiempoMinimoPromedioZonas() {
		return minPromedioZonas;
	}

	public double distanciaEstimadaZonas() {
		return distanciaEstimadaZonas;
	}

	/*
	 * 12C A partir de una zona origen, calcular los caminos de menor longitud
	 * (cantidad de arcos) a todas sus zonas alcanzables. De estos caminos,
	 * seleccionar el camino m�s largo
	 */
	public Iterable<Edge> caminoMasDistante(int zonaOrigen) {
		DijkstraDistanciaHaversine camino = new DijkstraDistanciaHaversine(datos.edgeWeightedGrap(), zonaOrigen);
		Queue<Integer> verts = new Queue<Integer>();
		AlgoPrim ret = new AlgoPrim(metodo.edgeWeightedGrap());
		Iterable<Edge> edges = ret.edges();
		for (Edge ed : edges) {
			int v1 = ed.either();
			int v2 = ed.other(v1);
			if (verts.size() == 0) {
				verts.enqueue(v1);
				verts.enqueue(v2);
			} else {
				Queue<Integer> copiaVerts = verts;
				boolean esta1 = false;
				boolean esta2 = false;
				for (int i = 0; i < verts.size(); i++) {
					int vActual = copiaVerts.dequeue();
					if (vActual == v1) {
						esta1 = true;
					}
					if (vActual == v2) {
						esta2 = true;
					}
				}
				if (!esta1) {
					verts.enqueue(v1);
				}
				if (!esta2) {
					verts.enqueue(v2);
				}
			}
		}
		int tam = verts.size();
		Queue<Integer> copiaVerts = verts;
		int numArcos = 0;
		int vertSeleccionado = 0;
		for (int i = 0; i < tam; i++) {
			int verActual = copiaVerts.dequeue();
			Iterable<Edge> arcosCamino = camino.pathTo(verActual);
			int numArcosCamino = 0;
			for (Edge e : arcosCamino) {
				numArcosCamino++;
			}
			if (numArcosCamino > numArcos) {
				numArcos = numArcosCamino;
				vertSeleccionado = verActual;
			}
		}
		for (int i = 0; i < tam; i++) {
			int verActual = copiaVerts.dequeue();
			Iterable<Edge> arcosCamino = camino.pathTo(verActual);
			int numArcosCamino = 0;
			for (Edge e : arcosCamino) {
				numArcosCamino++;
			}
			if (numArcosCamino == numArcos) {
				if (vertSeleccionado > verActual) {
					vertSeleccionado = verActual;
				}
			}
		}
		Iterable<Edge> rta = camino.pathTo(vertSeleccionado);
		return rta;
	}

	// Metodos adicionales

	private double distance(double startLat, double startLong, double endLat, double endLong) {

		double dLat = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (6371 * c); // <-- d
	}

	private double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	private double tiempo(int idInicio, int idFinal) {
		Iterator<String> it = linPr.keys().iterator();
		String llave = "";
		String valor = "";
		double tim = 0;
		while (it.hasNext()) {
			llave = it.next();
			valor = linPr.get(llave);
			String[] valores = valor.split(",");
			if (valores[0].equals(String.valueOf(idInicio)) && valores[1].equals(String.valueOf(idFinal))) {
				tim = (tim + Double.parseDouble(valores[3])) / 2;
			}
		}
		if (tim == 0) {
			if (idInicio == idFinal) {
				tim = 10;
			} else {
				tim = 100;
			}
		}
		return tim;
	}

	private double velocidad(double distancia, double tiempo) {
		return ((distancia / tiempo) * 100) / 100;
	}

	public void generarHTML(boolean t) {
		int a = 0xFF0000, b = 0x100;
		File file = new File("./data/Mapa.html");
		int i = 0, tam;
		Queue<Info> copia = vertices;
		tam = datos.V();
		if (t) tam = metodo.V();
		try {
			// creates the file
			file.createNewFile();
			// creates a FileWriter Object
			FileWriter writer = new FileWriter(file);
			// Writes the content to the file
			writer.write("<!DOCTYPE html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n"
					+ "  <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\r\n"
					+ "  <meta charset=\"utf-8\">\r\n" + "  <title>Simple Polylines</title>\r\n" + "  <style>\r\n"
					+ "    #map {\r\n" + "      height: 100%;\r\n" + "    }\r\n" + "    html,\r\n" + "    body {\r\n"
					+ "      height: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 0;\r\n" + "    }\r\n"
					+ "  </style>\r\n" + "</head>\r\n" + "<body>\r\n" + "  <div id=\"map\"></div>\r\n"
					+ "  <script>\r\n" + "    function initMap() {\r\n"
					+ "      var map = new google.maps.Map(document.getElementById('map'), {\r\n"
					+ "        zoom: 10,\r\n" + "        center: {\r\n" + "          lat: 4.585891,\r\n"
					+ "          lng: -74.046699\r\n" + "        },\r\n" + "        mapTypeId: 'roadmap'\r\n"
					+ "      });\r\n" + "	var line;\r\n" + "        var path;\r\n" + "	var GrafoC;\r\n"
					+ "   var Grafo;\r\n");
			while (i < tam && !copia.isEmpty()) {
				String hex = "";
				Info in = copia.dequeue();
				String wr = escribirParte(in.getID());
				if (t) wr = escribirParte2(in.getID());
				writer.write("          GrafoC " + wr);
				i++;
				writer.write("     Grafo = new google.maps.Polyline({\r\n" + "          path: GrafoC,\r\n"
						+ "          geodesic: true,\r\n" + "          strokeColor: '#");
				a = a + b;
				hex = Integer.toHexString(a);
				writer.write(hex + "',\r\n" + "          strokeOpacity: 1.0,\r\n" + "          strokeWeight: 2\r\n"
						+ "        });\r\n" + "        Grafo.setMap(map);\r\n");
			}
			writer.write("    }\r\n" + "  </script>\r\n"
					+ "  <script async defer src=\"https://maps.googleapis.com/maps/api/js?key=&callback=initMap\">\r\n"
					+ "  </script>\r\n" + "</body>\r\n" + "\r\n" + "</html>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String escribirParte(int V) {
		Iterator<Integer> it = datos.getCC(V).iterator();
		Info info = datos.getInfoVertex(V);
		String wr = "= [{lat: " + info.getLat() + ",lng: " + info.getLng() + "},\r\n";
		while (it.hasNext()) {
			Info inf = datos.getInfoVertex((it.next()));
			wr = wr + "{lat: " + inf.getLat() + ",lng: " + inf.getLng() + "},\r\n";
		}
		wr = wr + "];\r\n";
		return wr;
	}

	private String escribirParte2(int V) {
		Iterator<Integer> it = metodo.getCC(V).iterator();
		Info info = metodo.getInfoVertex(V);
		String wr = "= [{lat: " + info.getLat() + ",lng: " + info.getLng() + "},\r\n";
		while (it.hasNext()) {
			Info inf = metodo.getInfoVertex((it.next()));
			wr = wr + "{lat: " + inf.getLat() + ",lng: " + inf.getLng() + "},\r\n";
		}
		wr = wr + "];\r\n";
		return wr;
	}

	public Info infoVertex(int V) {
		return datos.getInfoVertex(V);
	}

	public LinearProbingHashST<Integer, Integer> keyToInteger() {
		return datos.keyToInteger();
	}
}
