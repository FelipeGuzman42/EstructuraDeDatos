package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import com.opencsv.CSVReader;

import model.data_structures.HashTableLinearProbing;
import model.data_structures.MaxColaCP;
import model.data_structures.RedBlackBST;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	private UberTrip viajesH1, viajesM1, viajesW1, viajesH2, viajesM2, viajesW2;
	private RedBlackBST<String, String> datosH1 = new RedBlackBST<>(), datosM1= new RedBlackBST<>(), datosW1= new RedBlackBST<>(), datosH2= new RedBlackBST<>(), datosM2= new RedBlackBST<>(), datosW2 = new RedBlackBST<>();
	private ZonaJSON zonas;
	private MaxColaCP<String> datosJSON = new MaxColaCP<>();
	private MallaVial mallas;
	private HashTableLinearProbing<Integer, Double[]> datosMallas = new HashTableLinearProbing<>(0);
	private static int Nmax = 20;

	// CARGA DE INFORMACION
	/**
	 * Lector de los archivos de excel
	 * 
	 * @param numero de trimestre deseado
	 */
	public void CSVLector() {
		CSVReader reader = null;
		String archivoH1 = "./data/bogota-cadastral-2018-5-All-HourlyAggregate.csv";
		String archivoM1 = "./data/bogota-cadastral-2018-5-All-MonthlyAggregate.csv";
		String archivoW1 = "./data/bogota-cadastral-2018-5-WeeklyAggregate.csv";
		String archivoH2 = "./data/bogota-cadastral-2018-5-All-HourlyAggregate.csv";
		String archivoM2 = "./data/bogota-cadastral-2018-5-All-MonthlyAggregate.csv";
		String archivoW2 = "./data/bogota-cadastral-2018-5-WeeklyAggregate.csv";
		try {
			reader = new CSVReader(new FileReader(archivoH1));
			reader.readNext();
			for (String[] nextLine : reader) {
				viajesH1 = new UberTrip(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], 1);
				datosH1.insertar(viajesH1.darLlave(), viajesH1.darValor());
			}
			reader = new CSVReader(new FileReader(archivoM1));
			reader.readNext();
			for (String[] nextLine : reader) {
				viajesM1 = new UberTrip(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], 1);
				datosM1.insertar(viajesM1.darLlave(), viajesM1.darValor());
			}
			reader = new CSVReader(new FileReader(archivoW1));
			reader.readNext();
			for (String[] nextLine : reader) {
				viajesW1 = new UberTrip(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], 1);
				datosW1.insertar(viajesW1.darLlave(), viajesW1.darValor());
			}
			reader = new CSVReader(new FileReader(archivoH2));
			reader.readNext();
			for (String[] nextLine : reader) {
				viajesH2 = new UberTrip(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], 2);
				datosH2.insertar(viajesH2.darLlave(), viajesH2.darValor());
			}
			reader = new CSVReader(new FileReader(archivoM2));
			reader.readNext();
			for (String[] nextLine : reader) {
				viajesM2 = new UberTrip(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], 2);
				datosM2.insertar(viajesM2.darLlave(), viajesM2.darValor());
			}
			reader = new CSVReader(new FileReader(archivoW2));
			reader.readNext();
			for (String[] nextLine : reader) {
				viajesW2 = new UberTrip(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], 2);
				datosW2.insertar(viajesW2.darLlave(), viajesW2.darValor());
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

	public void JSONLector() {
		Gson gson = new Gson();
		String path = "./data/bogota_cadastral.json";
		JsonReader reader;
		int i = 0, pts;
		String nor = "";
		try {
			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonElement e = elem.getAsJsonObject().get("features").getAsJsonArray().get(0).getAsJsonObject()
					.get("properties");
			while (i < elem.getAsJsonObject().get("features").getAsJsonArray().size()) {
				e = elem.getAsJsonObject().get("features").getAsJsonArray().get(i).getAsJsonObject().get("properties");
				pts = elem.getAsJsonObject().get("features").getAsJsonArray().get(i).getAsJsonObject().get("geometry")
						.getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsJsonArray().get(0)
						.getAsJsonArray().size();
				nor = elem.getAsJsonObject().get("features").getAsJsonArray().get(i).getAsJsonObject().get("geometry")
						.getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsJsonArray().get(0)
						.getAsJsonArray().get(0).getAsJsonArray().get(0)
						+ ","
						+ elem.getAsJsonObject().get("features").getAsJsonArray().get(i).getAsJsonObject()
								.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0)
								.getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonArray().get(1);
				zonas = gson.fromJson(e, ZonaJSON.class);
				zonas.setPtosGeo(pts);
				zonas.setPtNorte(nor);
				datosJSON.insertar(zonas.getValor());
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void TXTLector() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("./data/Nodes_of_red_vial-wgs84_shp.txt");
			br = new BufferedReader(fr);
			String st;
			String[] a = new String[3];
			while ((st = br.readLine()) != null) {
				a = st.split(",");
				mallas = new MallaVial(a[0], a[1], a[2]);
				datosMallas.put(mallas.getNodo(), mallas.getCoordenadas());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Total de viajes CSV, zonas JSON y nodos TXT
	 * 
	 * @return El total (tamano)
	 */
	public int totalHora1() {
		return datosH1.darTamano();
	}

	public int totalSemana1() {
		return datosW1.darTamano();
	}

	public int totalMes1() {
		return datosM1.darTamano();
	}

	public int totalHora2() {
		return datosH2.darTamano();
	}

	public int totalSemana2() {
		return datosW2.darTamano();
	}

	public int totalMes2() {
		return datosM2.darTamano();
	}

	public int totalZonas() {
		return datosJSON.darTamano();
	}

	public int totalNodos() {
		return datosMallas.darTamano();
	}

	// PARTE A
	/**
	 * 1A-Obtener las N letras mas frecuentes por las que comienza el nombre de una
	 * zona. N es un dato de entrada.El resultado debe aparecer de mayor a menor.
	 * Para cada letra se debe imprimir la letra y el nombre de las zonas que
	 * comienzan por esa letra.
	 * 
	 * @param N numero de letras mas frecuentes.
	 * @return arreglo ordenado de las N letras mas frecuentes
	 */
	public Queue<String> letrasMasFrecuentes(int N) {
		RedBlackBST<Integer, String> rta = new RedBlackBST<>();
		Queue<String> retu = new Queue<>();
		char[] letras = new char[30];
		int[] num = new int[30];
		String[] nom = new String[30];
		String[] json = new String[7];
		MaxColaCP<String> copia = datosJSON;
		for (int i = 0; i < datosJSON.darTamano(); i++) {
			json = copia.eliminarMax().split(",");
			for (int j = 0; j < letras.length; j++) {
				if (Character.compare(letras[j],json[1].charAt(0)) == 0) {
					num[j] = num[j]++;
					nom[j] = nom[j] + " , " + json[1];
				} else {
					for(int k = 0; k<letras.length;k++) {
						if(letras[k] == '\0') {
						letras[k] = json[1].charAt(0);
						num[k] = 0;
						nom[k] = json[1];
						}
					}
				}
			}
		}
		for (int i = 0; i < letras.length; i++) {
			rta.insertar(num[i], nom[i]);
		}
		Iterator<Integer> it = rta.keys();
		int norte;
		while (N != 0 && it.hasNext()) {
			norte = it.next();
			retu.enqueue(rta.get(norte));
			N--;
		}
		return retu;
	}

	/**
	 * 2A-Buscar los nodos que delimitan las zonas por Localizaci�n Geogr�fica
	 * (latitud, longitud). Dadas una latitud y una longitud, se deben mostrar todos
	 * los nodos en la frontera de las zonas que tengan la misma latitud y longitud
	 * truncando a las primeras 3 cifras decimales.
	 * 
	 * @param lat latitud dada, lon longitud dada
	 * @return arreglo de los nodos que pertenecen
	 */
	public Queue<String> buscarLatitudLongitud(double lat, double lon) {
		Queue<String> rta = new Queue<String>();
		MaxColaCP<String> copia = datosJSON;
		String[] json = new String[7];
		double pruLa = 0, pruLo = 0;
		String rtn = "";
		double pruebaLa, pruebaLo;
		for (int i = 0; i < datosJSON.darTamano(); i++) {
			json = copia.eliminarMax().split(",");
			pruebaLa = Double.parseDouble(json[5]);
			pruebaLo = Double.parseDouble(json[6]);
			pruLa = Math.floor(pruebaLa * 1000) / 1000;
			pruLo = Math.floor(pruebaLo * 1000) / 1000;
			if (pruLa == lat && pruLo == lon) {
				rtn = "Nodo: " + json[1] + ", latitud: " + pruLa + ", longitud: " + pruLo;
				rta.enqueue(rtn);
			}
		}
		return rta;
	}

	/**
	 * 3A - Buscar los tiempos promedio de viaje que est�n en un rango y que son del
	 * primer trimestre del 2018. Dado un rango de tiempos promedio de viaje en
	 * segundos [limite_bajo,limite_alto], retornar los viajes cuyo tiempo promedio
	 * mensual est� en ese rango. Se debe mostrar �nicamente N viajes ordenados
	 * por zona de origen y zona de destino. Por cada viaje se debe mostrar su zona
	 * de origen, zona de destino, mes y tiempo promedio mensual del viaje.
	 * 
	 * @param El minimo y maximo para buscar
	 * @return arreglo de los viajes
	 */
	public Queue<String> tiempoPromRangoMes(int min, int max) {
		Queue<String> rta = new Queue<String>();
		Iterator it = datosM1.keys();
		String llaveActual = (String) it.next();
		String valorActual = "";
		while (it.hasNext()) {
			valorActual = datosM1.get(llaveActual);
			String[] params = valorActual.split(",");
			int tiempo = Integer.parseInt(params[4]);
			if (tiempo >= min && tiempo <= max) {
				rta.enqueue(llaveActual);
			}
			llaveActual = (String) it.next();
		}
		return rta;
	}

	// PARTE B
	/**
	 * 1B Buscar los N zonas que estan mas al norte. Una zona A esta mas al Norte
	 * que una zona B si algun punto de la frontera de A esta mas al norte que todos
	 * los puntos de la frontera de B.
	 * 
	 * @param N viajes mas al norte
	 * @return arreglo zonas ordenadas desde las que estan mas al norte.
	 */
	public Queue<String> viajesAlNorte(int N) {
		Queue<String> rta = new Queue<String>();
		MaxColaCP<String> copia = datosJSON;
		RedBlackBST<Double, String[]> prueba = new RedBlackBST<>();
		double norte = 0;
		String[] json = new String[7];
		for (int i = 0; i < copia.darTamano(); i++) {
			json = copia.eliminarMax().split(",");
			norte = Double.parseDouble(json[6]);
			prueba.insertar(norte, json);
		}
		Iterator<Double> it = prueba.keys();
		while (N != 0 && it.hasNext()) {
			norte = it.next();
			json = prueba.get(norte);
			rta.enqueue("Zona: " + json[1] + " zona norte: " + json[6]);
			N--;
		}
		return rta;
	}

	/**
	 * 2B Buscar nodos de la malla vial por Localizaci�n Geogr�fica (latitud,
	 * longitud). Dado una latitud y una longitud, se deben mostrar todos los nodos
	 * que tengan esas mismas latitud y longitud truncando a 2 cifras decimales.
	 * 
	 * @param lat latitud dada, lon longitud dada
	 * @return arreglo de los nodos que pertenecen
	 */
	public Queue<String> mismaLatitudLongitud(double lat, double lon) {
		Queue<String> rta = new Queue<String>();
		Iterator it = datosMallas.keys().iterator();
		int nodo = (int) it.next();
		Double[] prueba = new Double[2];
		double pruLa = 0, pruLo = 0;
		String rtn = "";
		while (it.hasNext()) {
			prueba = datosMallas.get(nodo);
			pruLa = Math.floor(prueba[1] * 100) / 100;
			pruLo = Math.floor(prueba[0] * 100) / 100;
			if (pruLa == lat && pruLo == lon) {
				rtn = "Nodo: " + nodo + ", latitud: " + pruLa + ", longitud: " + pruLo;
				rta.enqueue(rtn);
			}
		}
		return rta;
	}

	/**
	 * 3B Buscar los tiempos de espera que tienen una desviaci�n est�ndar en un
	 * rango dado y que son del primer trimestre del 2018. Dado un rango de
	 * desviaciones est�ndares [limite_bajo,limite_alto] retornar los viajes cuya
	 * desviaci�n est�ndar mensual este en ese rango. Se debe mostrar
	 * �nicamente N viajes ordenados por zona de origen y zona de destino. De cada
	 * viaje se debe mostrar la zona de origen, zona de destino, mes y la
	 * desviaci�n est�ndar del viaje.
	 * 
	 * @param El minimo y maximo para buscar
	 * @return arreglo de los viajes
	 */
	public Queue<String> desviacionEstandarRangoMes(int min, int max) {
		Queue<String> rta = new Queue<String>();
		Iterator it = datosM1.keys();
		String llaveActual = (String) it.next();
		String valorActual = "";
		while (it.hasNext()) {
			valorActual = datosM1.get(llaveActual);
			String[] params = valorActual.split(",");
			int desvEst = Integer.parseInt(params[5]);
			if (desvEst >= min && desvEst <= max) {
				rta.enqueue(llaveActual);
			}
			llaveActual = (String) it.next();
		}
		return rta;
	}

	// PARTE C
	/**
	 * 1C Retornar todos los tiempos de viaje promedio que salen de una zona dada y
	 * a una hora dada.Dados el Id de una zona de salida y una hora que son
	 * ingresados por el usuario, retornar los tiempos de viaje promedio con esas
	 * caracter�sticas. Se debe mostrar la zona de origen, zona de destino, hora y
	 * tiempo promedio de cada viaje
	 * 
	 * @param inicio de zona de salida y h hora
	 * @return arreglo de los viajes
	 */
	public Queue<String> viajesFranja(String inicio, String h) {
		Queue<String> rta = new Queue<String>();
		Iterator it = datosH1.keys();
		String llaveActual = (String) it.next();
		String valorActual = "";
		while (it.hasNext()) {
			valorActual = datosH1.get(llaveActual);
			String[] params = valorActual.split(",");
			String zonaO = params[1];
			String hora = params[3];
			if (zonaO.equals(inicio) && hora.equals(h)) {
				rta.enqueue(llaveActual);
			}
			llaveActual = (String) it.next();
		}
		return rta;
	}

	/**
	 * 2C Retornar todos los tiempos de viaje que llegan de una zona dada y en un
	 * rango de horas. mostrar todos los tiempos de viaje promedio que cumplan esos
	 * criterios. Se debe mostrar la zona de origen, zona de destino, hora y tiempo
	 * promedio de cada viaje.
	 * 
	 * @param destino zona de llegada, y rango de horas
	 * @return Arreglo con los viajes
	 */
	public Queue<String> tiempoPromViajesHora(String destino, int horainicial, int horafinal) {
		Queue<String> rta = new Queue<String>();
		Iterator it = datosH1.keys();
		String llaveActual = (String) it.next();
		String valorActual = "";
		while (it.hasNext()) {
			valorActual = datosH1.get(llaveActual);
			String[] params = valorActual.split(",");
			String zonaD = params[2];
			int hora = Integer.parseInt(params[3]);
			if (zonaD.equals(destino) && hora >= horainicial && hora <= horafinal) {
				rta.enqueue(llaveActual);
			}
			llaveActual = (String) it.next();
		}
		return rta;
	}

	/**
	 * 3C Obtener las N zonas priorizadas por la mayor cantidad de nodos que definen
	 * su frontera. El valor N es un dato de entrada.Por cada zona se debe mostrar
	 * el nombre de la zona y el n�mero de nodos que definen su frontera.
	 * 
	 * @param N numero de datos
	 * @return Arreglo con los viajes
	 */
	public Queue<String> zonasFronterizas(int N) {
		Queue<String> rta = new Queue<String>();
		MaxColaCP<String> copia = datosJSON;
		RedBlackBST<Integer, String[]> prueba = new RedBlackBST<>();
		int norte = 0;
		String[] json = new String[7];
		for (int i = 0; i < copia.darTamano(); i++) {
			json = copia.eliminarMax().split(",");
			norte = Integer.parseInt(json[4]);
			prueba.insertar(norte, json);
		}
		Iterator<Integer> it = prueba.keys();
		while (N != 0 && it.hasNext()) {
			norte = it.next();
			json = prueba.get(norte);
			rta.enqueue("Zona: " + json[1] + " puntos: " + json[4]);
			N--;
		}
		return rta;
	}

	/**
	 * 4C Grafica ASCII -Porcentaje de datos faltantes para el primer semestre 2018.
	 * Crear una grafica que muestre por cada zona de origen que porcentaje de datos
	 * faltan (un dato faltante indica que no hubo ningun viaje desde la zona de
	 * origen a la zona destino a una hora dada en un trimestre dado)
	 * 
	 * @param 
	 * @return Arreglo con los viajes
	 */
	public HashTableLinearProbing<String, String> graficaASCII() {
		HashTableLinearProbing<String, Integer> dat = new HashTableLinearProbing<>(1);
		HashTableLinearProbing<String, String> rta = new HashTableLinearProbing<>(1);
		String[] params = null;
		Iterator<String> it1 = datosH1.keys();
		Iterator<String> it2 = datosH2.keys();
		String llaveActual = (String) it1.next();
		String valorActual = "", zonaI = "";
		while (it1.hasNext()) {
			valorActual = datosH1.get(llaveActual);
			params = valorActual.split(",");
			zonaI = params[1];
			if (!dat.contains(zonaI)) {
				dat.put(zonaI, 0);
			} else {
				dat.cambiarDato(zonaI, dat.get(zonaI)+1);
			}
			llaveActual = (String) it1.next();
		}
		llaveActual = (String) it2.next();
		while (it2.hasNext()) {
			valorActual = datosH2.get(llaveActual);
			params = valorActual.split(",");
			zonaI = params[1];
			if (!dat.contains(zonaI)) {
				dat.put(zonaI, 0);
			} else {
				dat.cambiarDato(zonaI, dat.get(zonaI)+1);
			}
			llaveActual = (String) it2.next();
		}
		Iterator<String> it3 = dat.keys().iterator();
		llaveActual = (String) it3.next();
		int j = 0;
		String aster = "";
		while (it3.hasNext()) {
			j = dat.get(llaveActual);
			for(int i = 0;i<dat.darTamano()-j;i++) {
				String a = "*";
				aster = aster + a;
			}
			rta.put(llaveActual, aster);
			llaveActual = (String) it3.next();
		}
		return rta;
	}
}
