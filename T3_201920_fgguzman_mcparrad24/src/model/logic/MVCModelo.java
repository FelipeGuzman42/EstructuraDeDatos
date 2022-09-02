package model.logic;

import model.data_structures.Queue;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {

	private Queue<UBERTrips> datosQ = new Queue<>();

	/**
	 * Lector de los archivos de excel
	 */
	public void CVSLector() {
		CSVReader reader = null;
		String[] header = new String[1];
		UBERTrips carga;
		try {
			//reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-2-All-HourlyAggregate.csv"));
			reader = new CSVReader(new FileReader("./data/Descendentes.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new UBERTrips(nextLine);
				datosQ.enqueue(carga);
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

	public int totalViajesTrimestre() {
		return datosQ.darTamano();
	}

	public String[] primerElemento() {
		String[] datosPrimero = new String[4];
		datosPrimero[0] = datosQ.consultarPrimerElemento().darIdOrigen();
		datosPrimero[1] = datosQ.consultarPrimerElemento().darIdDestino();
		datosPrimero[2] = datosQ.consultarPrimerElemento().darHoraPromedio();
		String tPromedio = String.valueOf(datosQ.consultarPrimerElemento().darTiempoPromedio());
		datosPrimero[3] = tPromedio;
		return datosPrimero;
	}

	public String[] ultimoElemento() {
		String[] datosUltimo = new String[4];
		datosUltimo[0] = datosQ.consultarUltimoElemento().darIdOrigen();
		datosUltimo[1] = datosQ.consultarUltimoElemento().darIdDestino();
		datosUltimo[2] = datosQ.consultarUltimoElemento().darHoraPromedio();
		String tPromedio = String.valueOf(datosQ.consultarUltimoElemento().darTiempoPromedio());
		datosUltimo[3] = tPromedio;
		return datosUltimo;
	}

	public Comparable<UBERTrips>[] cluster(int hora) {
		ArrayList<UBERTrips> viajes = new ArrayList<UBERTrips>();
		int tamano = datosQ.darTamano();
		for (int i = 0; i <= tamano - 1; i++) {
			UBERTrips actual = datosQ.dequeue();
			if (actual.darHoraPromedio().equals(Integer.toString(hora))) {
				viajes.add(actual);
			}
		}
		Comparable<UBERTrips>[] viajesFinal = new Comparable[viajes.size()];
		for (int i = 0; i <= viajes.size() - 1; i++) {
			viajesFinal[i] = viajes.get(i);
		}
		return viajesFinal;
	}

	public Comparable<UBERTrips>[] ordenarViajesShellSort(Comparable<UBERTrips>[] clus) {
		UBERTrips temporal = new UBERTrips();
		int N = clus.length;
		for (int h = N / 2; h > 0; h /= 2) {
			for (int i = h; i < N; i += 1) {
				temporal = (UBERTrips) clus[i];
				int j;
				for (j = i; j >= h && clus[j - h].compareTo(temporal) == 1; j -= h)
					clus[j] = clus[j - h];
				clus[j] = temporal;
			}
		}
		return clus;
	}

	public Comparable<UBERTrips>[] ordenarViajesMergeSort(Comparable<UBERTrips>[] clus) {
		if(clus.length < 2) {
			return clus;
		}
		int m = (clus.length) / 2;
		UBERTrips[] izq = new UBERTrips[m];
		UBERTrips[] der = new UBERTrips[clus.length - m];
		for(int i = 0; i<m ;i++) {
			izq[i] = (UBERTrips) clus[i];
		}
		for(int i = m; i<clus.length ;i++) {
			der[i - m] = (UBERTrips) clus[i];
		}
		merge(clus, izq, m, der);
		return clus;
	}

	public static void merge(Comparable<UBERTrips>[] clus, UBERTrips[] izq, int m, UBERTrips[] der) {
		int i = 0, j = 0, k = 0;
		UBERTrips[] aux = new UBERTrips[clus.length];
		while (i < m && j < clus.length - m) {
	        if (izq[i].compareTo(der[j]) == 1) {
	            aux[k++] = izq[i++];
	        }
	        else {
	            aux[k++] = der[j++];
	        }
	    }
	    while (i < m) {
	        aux[k++] = izq[i++];
	    }
	    while (j < clus.length - m) {
	        aux[k++] = der[j++];
	    }
	}

	public Comparable<UBERTrips>[] ordenarViajesQuickSort(Comparable<UBERTrips>[] clus) {
		int izq = 0;
		int der = clus.length - 1;
		int pivote = quicksort(clus, izq, der);
		if(izq < pivote-1 && pivote+1 > der) {
			quicksort(clus, izq, pivote-1);
			quicksort(clus, pivote+1, der);
		}	
		return clus;
	}

	public int quicksort(Comparable<UBERTrips>[] clus, int izq, int der) {
		UBERTrips pivote = (UBERTrips) clus[izq];
		int i = izq-1;
		UBERTrips aux = new UBERTrips();
		for (int j = izq; j < der; j++) {
			if (clus[j].compareTo(pivote) == -1) {
				i++;
				aux = (UBERTrips) clus[i];
				clus[i] = clus[j];
				clus[j] = aux;
			}
		}
		aux =(UBERTrips) clus[i+1];
		clus[i+1] = clus[der];
		clus[der] = aux;
		return i+1;
	}
}