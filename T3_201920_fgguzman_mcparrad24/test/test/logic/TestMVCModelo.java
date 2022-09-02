package test.logic;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.opencsv.CSVReader;

import model.data_structures.Queue;
import model.logic.MVCModelo;
import model.logic.UBERTrips;

public class TestMVCModelo {

	private MVCModelo modelo = new MVCModelo();
	private Queue<UBERTrips> datos1 = new Queue<>();
	private Queue<UBERTrips> datos2 = new Queue<>();
	private Queue<UBERTrips> datos3 = new Queue<>();
	private Comparable<UBERTrips>[] arregloD1 = null;
	private Comparable<UBERTrips>[] arregloD2 = null;
	private Comparable<UBERTrips>[] arregloD3 = null;

	public void setUp1() {
		CSVReader reader = null;
		String[] header = new String[1];
		UBERTrips carga;
		try {
			reader = new CSVReader(new FileReader("./data/Ascendentes.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new UBERTrips(nextLine);
				datos1.enqueue(carga);
			}

		} catch (Exception e) {
			fail("Fallo la lectura del archivo csv");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					fail("No se pudo cerrar el lector");
				}	
			}
		}
	}

	public void setUp2() {
		CSVReader reader = null;
		String[] header = new String[1];
		UBERTrips carga;
		try {
			reader = new CSVReader(new FileReader("./data/Descendentes.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new UBERTrips(nextLine);
				datos2.enqueue(carga);
			}

		} catch (Exception e) {
			fail("Fallo la lectura del archivo csv");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					fail("No se pudo cerrar el lector");
				}	
			}
		}
	}

	public void setUp3() {
		CSVReader reader = null;
		String[] header = new String[1];
		UBERTrips carga;
		try {
			reader = new CSVReader(new FileReader("./data/Desordenados.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new UBERTrips(nextLine);
				datos3.enqueue(carga);
			}

		} catch (Exception e) {
			fail("Fallo la lectura del archivo csv");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					fail("No se pudo cerrar el lector");
				}	
			}
		}
	}

	public void datos1Arreglo(){
		setUp1();
		int tam = datos1.darTamano();
		Comparable<UBERTrips>[] arreglo = new Comparable[tam];
		for (int i = 0; i <= tam-1; i++) {
			UBERTrips actual = datos1.dequeue();
			arreglo[i] = actual;
		}
		arregloD1 = arreglo;
	}

	public void datos2Arreglo(){
		setUp2();
		int tam = datos2.darTamano();
		Comparable<UBERTrips>[] arreglo = new Comparable[tam];
		for (int i = 0; i <= tam-1; i++) {
			arreglo[i] = datos2.dequeue();
		}
		arregloD2 = arreglo;
	}

	public void datos3Arreglo(){
		setUp3();
		int tam = datos3.darTamano();
		Comparable<UBERTrips>[] arreglo = new Comparable[tam];
		for (int i = 0; i <= tam-1; i++) {
			arreglo[i] = datos3.dequeue();
		}
		arregloD3 = arreglo;
	}

	@Test
	public void testTotalViajesTrimestre() {
		setUp2();
		assertEquals("El número de datos no es correcto", 50, datos2.darTamano());
	}

	@Test
	public void testPrimerElemento() {
		setUp2();
		String[] datosPrimero = new String [4];
		datosPrimero[0] = datos2.consultarPrimerElemento().darIdOrigen();
		datosPrimero[1] = datos2.consultarPrimerElemento().darIdDestino();
		datosPrimero[2] = datos2.consultarPrimerElemento().darHoraPromedio();
		String tPromedio = String.valueOf(datos2.consultarPrimerElemento().darTiempoPromedio());
		datosPrimero[3] = tPromedio;
		String[] datosPrueba = {"8", "15", "19", "4219.33"};
		boolean es = false;
		if ((datosPrimero[0].equals(datosPrueba[0])) && (datosPrimero[1].equals(datosPrueba[1])) && (datosPrimero[2].equals(datosPrueba[2])) && (datosPrimero[3].equals(datosPrueba[3]))){
			es = true;
		}
		assertEquals("El primer elemento no es el correcto", true, es);
	}

	@Test
	public void testUltimoElemento() {
		setUp3();
		String[] datosUltimo = new String [4];
		datosUltimo[0] = datos3.consultarUltimoElemento().darIdOrigen();
		datosUltimo[1] = datos3.consultarUltimoElemento().darIdDestino();
		datosUltimo[2] = datos3.consultarUltimoElemento().darHoraPromedio();
		String tPromedio = String.valueOf(datos3.consultarUltimoElemento().darTiempoPromedio());
		datosUltimo[3] = tPromedio;
		String[] datosPrueba = {"6", "39", "21", "693.04"};
		boolean es = false;
		if ((datosUltimo[0].equals(datosPrueba[0])) && (datosUltimo[1].equals(datosPrueba[1])) && (datosUltimo[2].equals(datosPrueba[2])) && (datosUltimo[3].equals(datosPrueba[3]))){
			es = true;
		}
		assertEquals("El primer elemento no es el correcto", true, es);
	}

	@Test
	public void testCluster() {
		setUp2();
		int hora = 6;
		ArrayList<UBERTrips> viajes = new ArrayList<UBERTrips>();
		int tamano = datos2.darTamano();
		for (int i = 0; i <= tamano-1; i++) {
			UBERTrips actual = datos2.dequeue();
			if (actual.darHoraPromedio().equals(Integer.toString(hora))) {
				viajes.add(actual);
			}
		}
		Comparable<UBERTrips>[] viajesFinal = new Comparable[viajes.size()];
		for (int i = 0; i <= viajes.size()-1; i++) {
			viajesFinal[i] = viajes.get(i);
		}
		assertEquals("El número de viajes con la hora dada no es correcto", 5, viajesFinal.length);
	}

	@Test
	public void testOrdenarViajesShellSortAscendente() {
		setUp1();
		datos1Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesShellSort(arregloD1);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesShellSortDescendente() {
		setUp1();
		setUp2();
		datos1Arreglo();
		datos2Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesShellSort(arregloD2);
		for (int i = 0; i <= ordenado.length-1; i++) {
			
		}
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			UBERTrips actual = (UBERTrips) arregloD1[i];
			if (ordenado[i].equals(actual)){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesShellSortDesordenado() {
		setUp1();
		setUp3();
		datos1Arreglo();
		datos3Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesShellSort(arregloD3);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesMergeSortAscendente() {
		setUp1();
		datos1Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesMergeSort(arregloD1);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesMergeSortDescendente() {
		setUp1();
		setUp2();
		datos1Arreglo();
		datos2Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesMergeSort(arregloD2);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesMergeSortDesordenado() {
		setUp1();
		setUp3();
		datos1Arreglo();
		datos3Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesMergeSort(arregloD3);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesQuickSortAscendente() {
		setUp1();
		datos1Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesQuickSort(arregloD1);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesQuickSortDescendente() {
		setUp1();
		setUp2();
		datos1Arreglo();
		datos2Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesQuickSort(arregloD2);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}

	@Test
	public void testOrdenarViajesQuickSortDesordenado() {
		setUp1();
		setUp3();
		datos1Arreglo();
		datos3Arreglo();
		Comparable<UBERTrips>[] ordenado = modelo.ordenarViajesQuickSort(arregloD3);
		boolean estaBien = false;
		int correctos = 0;
		for (int i = 0; i <= ordenado.length-1; i++) {
			if (ordenado[i].equals(arregloD1[i])){
				correctos++;
			}
		}
		if (correctos == ordenado.length) {
			estaBien = true;
		}
		assertEquals("El arreglo no se ordeno correctamente", true, estaBien);
	}
}
