package model.logic;

import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {

	private MaxColaCP<TravelTime> datosCola1 = new MaxColaCP<>();
	private MaxHeapCP<TravelTime> datosHeap1 = new MaxHeapCP<>(0);
	private MaxColaCP<TravelTime> datosCola2 = new MaxColaCP<>();
	private MaxHeapCP<TravelTime> datosHeap2 = new MaxHeapCP<>(0);

	/**
	 * Lector de los archivos de excel
	 */
	public void loadTravelTimes() {
		CSVReader reader = null;
		String[] header = new String[1];
		TravelTime carga;
		try {
			//./docs/Muestra_de_datos.csv
			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-1-All-HourlyAggregate.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new TravelTime(nextLine);
				datosHeap1.insertar(carga);
				datosCola1.insertar(carga);
			}
			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-2-All-HourlyAggregate.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new TravelTime(nextLine);
				datosHeap2.insertar(carga);
				datosCola2.insertar(carga);
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
	
	public TravelTime[] generarMuestra(int N) {
		int n = 0, j = 0;
		TravelTime[] retorno = new TravelTime[N];
		TravelTime muestra = null;
		while (n < N) {
			MaxHeapCP<TravelTime> copia = datosHeap1;
			int rnd = new Random().nextInt(datosHeap1.darTamano()) + 1;
			muestra = copia.getIndex(rnd);
			retorno[j++] = muestra;
			n++;
		}
		return retorno;
	}
	
	public MaxColaCP<TravelTime> tiempoPromViajesCola(String N, String horaI, String horaF){
		MaxColaCP<TravelTime> retorno = new MaxColaCP<>();
		MaxColaCP<TravelTime> copia = datosCola1;
		int tam = datosCola1.darTamano();
		int num = 0;
		TravelTime actual = copia.eliminarMax();
		for (int i = 0; i < tam; i++) {
			if ((Integer.parseInt(actual.darHoraPromedio()) >= Integer.parseInt(horaI)) && (Integer.parseInt(actual.darHoraPromedio()) <= Integer.parseInt(horaF)) && (num <= Integer.parseInt(N))){
				retorno.insertar(actual);
				num++;
			}
			actual = copia.eliminarMax();
		}
		return retorno;
	}
	
	public MaxHeapCP<TravelTime> tiempoPromViajesHeap(String N, String horaI, String horaF){
		MaxHeapCP<TravelTime> retorno = new MaxHeapCP<>(Integer.parseInt(N));
		MaxHeapCP<TravelTime> copia = datosHeap1;
		int tam = copia.darTamano();
		int num = 0;
		for (int i = 0; i < tam; i++) {
			TravelTime actual = copia.eliminarMax();
			if ((Integer.parseInt(actual.darHoraPromedio()) >= Integer.parseInt(horaI)) && (Integer.parseInt(actual.darHoraPromedio()) <= Integer.parseInt(horaF)) && (num <= Integer.parseInt(N))){
				retorno.insertar(actual);
				num++;
			}
		}
		return retorno;
	}
}