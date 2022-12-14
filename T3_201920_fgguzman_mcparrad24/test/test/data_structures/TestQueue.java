package test.data_structures;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

import model.data_structures.Queue;
import model.logic.UBERTrips;

import org.junit.Test;

public class TestQueue <T extends Comparable<T>>{

	/**
	 * Cola que contiene los datos del archivo de prueba.
	 */
	Queue<UBERTrips> datosH = new Queue<>();
	
	/**
	 * Inicializa la cola con los datos que lee el lector.
	 */
	public void setUp1() {
		CSVReader reader = null;
		String[] header = new String[1];
		UBERTrips carga;
		try {
			reader = new CSVReader(new FileReader("./data/datos_de_prueba.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new UBERTrips(nextLine);
				datosH.enqueue(carga);
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
	
	/**
	 * Verifica el método enqueue con el tamaño actual de la lista.
	 */
	@Test
	public void testEnqueue() {
		setUp1();
		String[] dato = {"5","7","3","1440.57","198.16","1029.94","1.36"};
		UBERTrips nuevoDato = new UBERTrips(dato);
		datosH.enqueue(nuevoDato);
		assertEquals("El numero de datos no es correcto", 11, datosH.darTamano());
	}
	
	/**
	 * Verifica el método enqueue confirmando que el elemento que se agrego se convierta en el último elemento de la lista.
	 */
	@Test
	public void testEnqueue2() {
		setUp1();
		UBERTrips ultimo = null;
		String[] dato = {"5","7","3","1440.57","198.16","1029.94","1.36"};
		UBERTrips nuevoDato = new UBERTrips(dato);
		datosH.enqueue(nuevoDato);
		int tamano = datosH.darTamano();
		for (int i = 0; i <= (tamano-1); i++) {
			UBERTrips actual = datosH.dequeue();
			if (actual != null) {
				ultimo = actual;
			}
		}
		System.out.println(ultimo.darIdOrigen());
		boolean siEs = false;
		if ((ultimo.darIdOrigen().equals(Integer.toString(5)))&&(ultimo.darIdDestino().equals(Integer.toString(7)))&&(ultimo.darHoraPromedio().equals(Integer.toString(3)))) {
			siEs = true;
		}
		assertEquals("El último dato no es el correcto", true, siEs);
	}
	
	/**
	 * Verifica el metodo dequeue con el tamano actual de la lista.
	 */
	@Test
	public void testDequeue() {
		setUp1();
		datosH.dequeue();
		assertEquals("El número de datos no es correcto", 9, datosH.darTamano());
	}
	
	/**
	 * Verifica el metodo dequeue confirmando que el elemento eliminado haya sido aquel que se encontraba en la cabeza de la lista.
	 */
	@Test
	public void testDequeue2() {
		setUp1();
		UBERTrips eliminado = datosH.dequeue();
		boolean siEs = false;
		if ((eliminado.darIdOrigen().equals(Integer.toString(1))) && (eliminado.darIdDestino().equals(Integer.toString(4))) && (eliminado.darHoraPromedio().equals(Integer.toString(20)))) {
			siEs = true;
		}
		assertEquals("El dato eliminado no es el correcto", true, siEs);
	}
	
	/**
	 * Verifica el metodo darTamaño.
	 */
	@Test
	public void testDarTamano() {
		setUp1();
		assertEquals("El tamano de la lista es incorrecta", 10, datosH.darTamano());
	}
	
	/**
	 * Verifica el método isEmpty.
	 */
	@Test
	public void testIsEmpty() {
		setUp1();
		assertEquals("El arreglo no esta vacio", false, datosH.isEmpty());
	}
	
	/**
	 * Verifica el metodo consultarPrimerElemento confirmando que el elemento retornado sea el que se encuentra en la cabeza de la lista.
	 */
	@Test
	public void testConsultarPrimerElemento() {
		setUp1();
		boolean siEs = false;
		if ((datosH.consultarPrimerElemento().darIdOrigen().equals(Integer.toString(1))) && (datosH.consultarPrimerElemento().darIdDestino().equals(Integer.toString(4))) && (datosH.consultarPrimerElemento().darHoraPromedio().equals(Integer.toString(20)))) {
			siEs = true;
		}
		assertEquals("El primer elemento es incorrecto", true, siEs);
	}
}
