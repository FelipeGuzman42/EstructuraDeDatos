package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.opencsv.CSVReader;

import model.data_structures.HashTableSeparateChaining;
import model.logic.TravelTime;

public class TestHashTableSeparateChaining {
	
	private HashTableSeparateChaining<String, String> sepCh = new HashTableSeparateChaining(35);

	public void setUp1() {
		CSVReader reader = null;
		String[] header = new String[1];
		TravelTime carga;
		try {
			reader = new CSVReader(new FileReader("./docs/DatosPrueba.csv"));
			header = reader.readNext();
			for (String[] nextLine : reader) {
				carga = new TravelTime(nextLine, 1);
				sepCh.put(carga.darLlave(), carga.darValor());
			}

		} catch (Exception e) {
				fail("Fallo la lectura del archivo csv "+e);
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
	
	@Test
	public void testDarTamano() {
		setUp1();
		int tam = sepCh.darTamano();
		assertEquals("El tamano de la cola no es correcto", 35, tam);
	}
	
	@Test
	public void testInsertar1() {
		setUp1();
		String[] nuevo = {"4", "58", "10", "1474.35", "483.6", "1414.18", "1.32"};
		TravelTime nuevoDato = new TravelTime(nuevo, 1);
		sepCh.put(nuevoDato.darLlave(), nuevoDato.darValor());
		assertEquals("No se agrego el dato correctamente", 36, sepCh.darTamano());
	}
	
	@Test
	public void testInsertar2() {
		setUp1();
		boolean siEs = false;
		String[] nuevo = {"4", "58", "10", "1474.35", "483.6", "1414.18", "1.32"};
		TravelTime nuevoDato = new TravelTime(nuevo, 1);
		sepCh.put(nuevoDato.darLlave(), nuevoDato.darValor());
		String key = 1+"-"+4+"-"+58;
		String valor = sepCh.get(key);
		if (nuevoDato.darValor().equals(valor) && nuevoDato.darLlave().equals(key)) {
			siEs = true;
		}
		assertEquals("El dato no se agrego correctamente", true, siEs);
	}
	
	@Test
	public void testEliminar1() {
		setUp1();
		String key = 1+"-"+1141+"-"+416;
		sepCh.delete(key);
		assertEquals("No se elimino el dato correctamente", 34, sepCh.darTamano());
	}
	
	@Test
	public void testEliminar2() {
		setUp1();
		String key = 1+"-"+1141+"-"+416;
		String valor = sepCh.delete(key);
		boolean siEs = false;
		if (valor.equals(1+","+1141+","+416+","+3+","+2094.83)) {
			siEs = true;
		}
		assertEquals("No se elimino el dato correctamente", true, siEs);
	}

	@Test
	public void testIsEmpty() {
		setUp1();
		boolean empty = sepCh.isEmpty();
		assertEquals("La cola no esta vacia", false, empty);
	}
	
	@Test
	public void testGet() {
		setUp1();
		String key = 1+"-"+903+"-"+900;
		String value = 1+","+903+","+900+","+2+","+341.25;
		String valorEsp = sepCh.get(key);
		boolean siEs = false;
		if (valorEsp.equals(value)) {
			siEs = true;
		}
		assertEquals("No se encontró el dato correctamente", true, siEs);
	}
	
	@Test
	public void testRehash() {
		setUp1();
		double dado = (1.0 * sepCh.darTamano()/64);
		assertEquals("La carga no se disminuye", 0.546875,dado,0.001);
	}
}
