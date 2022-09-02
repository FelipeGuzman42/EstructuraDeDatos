package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import model.data_structures.*;

import com.opencsv.CSVReader;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {

	private HashTableSeparateChaining<String, String> sepCh1 = new HashTableSeparateChaining(31);
	private HashTableLinearProbing<String, String> linPr1 = new HashTableLinearProbing(31);
	private HashTableSeparateChaining<String, String> sepCh2 = new HashTableSeparateChaining(31);
	private HashTableLinearProbing<String, String> linPr2 = new HashTableLinearProbing(31);
	private HashTableSeparateChaining<String, String> sepCh3 = new HashTableSeparateChaining(31);
	private HashTableLinearProbing<String, String> linPr3 = new HashTableLinearProbing(31);
	private TravelTime[] viajes;
	private String primero1;
	private String ultimo1;
	private String primero2;
	private String ultimo2;
	private String primero3;
	private String ultimo3;

	/**
	 * Lector de los archivos de excel
	 */
	public void CVSLector() {
		CSVReader reader = null;
		String archivo1 = "./data/bogota-cadastral-2018-1-WeeklyAggregate.csv";
		String archivo2 = "./data/bogota-cadastral-2018-2-WeeklyAggregate.csv";
		String archivo3 = "./data/bogota-cadastral-2018-3-WeeklyAggregate.csv";
		String[] header = new String[1];
		TravelTime carga = null;
		int i;
		try {
			reader = new CSVReader(new FileReader(archivo1));
			header = reader.readNext();
			i = 1;
			for (String[] nextLine : reader) {
				carga = new TravelTime(nextLine, 1);
				linPr1.put(carga.darLlave(), carga.darValor());
				sepCh1.put(carga.darLlave(), carga.darValor());
				if (i == 1) {
					primero1 = carga.darLlave();
					i++;
				}
				ultimo1 = carga.darLlave();
			}
			reader = new CSVReader(new FileReader(archivo2));
			header = reader.readNext();
			i = 1;
			for (String[] nextLine : reader) {
				carga = new TravelTime(nextLine, 2);
				linPr2.put(carga.darLlave(), carga.darValor());
				sepCh2.put(carga.darLlave(), carga.darValor());
				if (i == 1) {
					primero2 = carga.darLlave();
					i++;
				}
				ultimo2 = carga.darLlave();
			}
			reader = new CSVReader(new FileReader(archivo3));
			header = reader.readNext();
			i = 1;
			for (String[] nextLine : reader) {
				carga = new TravelTime(nextLine, 3);
				linPr3.put(carga.darLlave(), carga.darValor());
				sepCh3.put(carga.darLlave(), carga.darValor());
				if (i == 1) {
					primero3 = carga.darLlave();
					i++;
				}
				ultimo3 = carga.darLlave();
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

	//LINEAR PROBING
	public int darTamanoL(int trim) {
		int tam = 0;
		if (trim == 1) {
			tam = linPr1.darTamano();
		}
		else if (trim == 2) {
			tam = linPr2.darTamano();
		}
		else if (trim == 3) {
			tam = linPr3.darTamano();
		}
		return tam;
	}
	public double darFactorCargaL(int trim) {
		double car = 0;
		if (trim == 1) {
			car = linPr1.cargaF();
		}
		else if (trim == 2) {
			car = linPr2.cargaF();
		}
		else if (trim == 3) {
			car = linPr3.cargaF();
		}
		return car;
	}
	public int darRehashesL(int trim) {
		int tam = 0;
		if (trim == 1) {
			tam = linPr1.darRehashes();
		}
		else if (trim == 2) {
			tam = linPr2.darRehashes();
		}
		else if (trim == 3) {
			tam = linPr3.darRehashes();
		}
		return tam;
	}
	public int darTamanoMaxL(int trim) {
		int tam = 0;
		if (trim == 1) {
			tam = linPr1.darTamanoMax();
		}
		else if (trim == 2) {
			tam = linPr2.darTamanoMax();
		}
		else if (trim == 3) {
			tam = linPr3.darTamanoMax();
		}
		return tam;
	}

	//SEPARATE CHAINING
	public int darTamanoS(int trim) {
		int tam = 0;
		if (trim == 1) {
			tam = sepCh1.darTamano();
		}
		else if (trim == 2) {
			tam = sepCh2.darTamano();
		}
		else if (trim == 3) {
			tam = sepCh3.darTamano();
		}
		return tam;
	}
	public double darFactorCargaS(int trim) {
		double car = 0;
		if (trim == 1) {
			car = sepCh1.cargaF();
		}
		else if (trim == 2) {
			car = sepCh2.cargaF();
		}
		else if (trim == 3) {
			car = sepCh3.cargaF();
		}
		return car;
	}
	public int darRehashesS(int trim) {
		int tam = 0;
		if (trim == 1) {
			tam = sepCh1.darRehashes();
		}
		else if (trim == 2) {
			tam = sepCh2.darRehashes();
		}
		else if (trim == 3) {
			tam = sepCh3.darRehashes();
		}
		return tam;
	}
	public int darTamanoMaxS(int trim) {
		int tam = 0;
		if (trim == 1) {
			tam = sepCh1.darTamanoMax();
		}
		else if (trim == 2) {
			tam = sepCh2.darTamanoMax();
		}
		else if (trim == 3) {
			tam = sepCh3.darTamanoMax();
		}
		return tam;
	}

	//OTROS
	public String darUltimo(int trim) {
		String ult = "";
		if (trim == 1) {
			ult = ultimo1;
		}
		else if (trim == 2) {
			ult = ultimo2;
		}
		else if (trim == 3) {
			ult = ultimo3;
		}
		return ult;
	}

	public String darPrimero(int trim) {
		String prim = "";
		if (trim == 1) {
			prim = primero1;
		}
		else if (trim == 2) {
			prim = primero2;
		}
		else if (trim == 3) {
			prim = primero3;
		}
		return prim;
	}

	public TravelTime[] viajesLinProb(int trim, String zonaOrigen, String zonaDestino) {
		viajes = null;
		Iterator it = null;
		if (trim == 1) {
			it = linPr1.keys().iterator();
		}
		else if (trim == 2) {
			it = linPr2.keys().iterator();
		}
		else if (trim == 3) {
			it = linPr3.keys().iterator();
		}
		String llave = "";
		String valor = "";
		int j = 0;
		while (it.hasNext()) {
			llave = (String) it.next();
			if (trim == 1) {
				valor = linPr1.get(llave);
			}
			else if (trim == 2) {
				valor = linPr2.get(llave);
			}
			else if (trim == 3) {
				valor = linPr3.get(llave);
			}
			String[] valores = valor.split(",");
			if (Integer.parseInt(valores[0]) == trim && valores[1].equals(zonaOrigen) && valores[2].equals(zonaDestino)) {
				valores[0] = valores[1];
				valores[1] = valores[2];
				valores[2] = valores[3];
				valores[3] = valores[4];
				valores[4] = null;
				TravelTime viaje = new TravelTime(valores, Integer.parseInt(valores[0]));
				viajes[j] = viaje;
				j++;
			}
		}
		if (viajes==null) {
			return null;
		}
		else if (viajes[1] == null) {
			return viajes;
		}
		else {
			TravelTime[] ordenados = ordenarViajesQuickSort(viajes);
			return ordenados;
		}
	}

	public TravelTime[] viajesSepCh(int trim, String zonaOrigen, String zonaDestino) {
		viajes = new TravelTime[sepCh1.darTamano()];
		Iterator it = null;
		if (trim == 1) {
			it = sepCh1.keys().iterator();
		}
		else if (trim == 2) {
			it = sepCh2.keys().iterator();
		}
		else if (trim == 3) {
			it = sepCh3.keys().iterator();
		}
		String llave = "";
		String valor = "";
		int j = 0;
		while (it.hasNext()) {
			llave = (String) it.next();
			if (trim == 1) {
				valor = sepCh1.get(llave);
			}
			else if (trim == 2) {
				valor = sepCh2.get(llave);
			}
			else if (trim == 3) {
				valor = sepCh3.get(llave);
			}
			String[] valores = valor.split(",");
			if (Integer.parseInt(valores[0]) == trim && valores[1].equals(zonaOrigen) && valores[2].equals(zonaDestino)) {
				int t = Integer.parseInt(valores[0]);
				valores[0] = valores[1];
				valores[1] = valores[2];
				valores[2] = valores[3];
				valores[3] = valores[4];
				valores[4] = null;
				TravelTime viaje = new TravelTime(valores, t);
				viajes[j] = viaje;
				j++;
			}
		}
		if (viajes==null) {
			return null;
		}
		else if (viajes[1] == null) {
			return viajes;
		}
		else {
			TravelTime[] ordenados = ordenarViajesQuickSort(viajes);
			return ordenados;
		}
	}

	public TravelTime[] ordenarViajesQuickSort(TravelTime[] clus) {
		int izq = 0;
		int der = clus.length - 1;
		int pivote = quicksort(clus, izq, der);
		if(izq < pivote-1 && pivote+1 > der) {
			quicksort(clus, izq, pivote-1);
			quicksort(clus, pivote+1, der);
		}	
		return clus;
	}

	public int quicksort(TravelTime[] clus, int izq, int der) {
		String[] au = {"0", "0", "0", "0", "0"};
		TravelTime pivote = (TravelTime) clus[izq];
		int i = izq;
		TravelTime aux = new TravelTime(au, 1);
		for (int j = izq; j < der; j++) {
			if (clus[j].compareTo(pivote) == -1) {
				i++;
				aux = clus[i];
				clus[i] = clus[j];
				clus[j] = aux;
			}
		}
		aux = clus[i+1];
		clus[i+1] = clus[der];
		clus[der] = aux;
		return i+1;
	}
}