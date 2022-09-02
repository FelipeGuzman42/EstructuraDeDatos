package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import com.opencsv.CSVReader;

import model.data_structures.Bag;
import model.data_structures.Grafo;
import model.data_structures.LinearProbingHashST;
import model.logic.Info;
import model.logic.Queue;

public class TestGrafo {

	private Grafo<Integer, Info> Grafo = new Grafo(10);

	public void setUp1() {
		Info info1 = new Info("0", "-74.08921298299998", "4.582989396000016", "275");
		int id1 = 0;
		Info info2 = new Info("1", "-74.09175497299998", "4.5795689170000164", "684");
		int id2 = 1;
		Info info3 = new Info("2", "-74.08656902000001", "4.60729538999999", "35");
		int id3 = 2;
		Info info4 = new Info("3", "-74.07312877999998", "4.648768679999988", "1035");
		int id4 = 3;
		Info info5 = new Info("4", "-74.05100413999997", "4.724598160000029", "96");
		int id5 = 4;
		Info info6 = new Info("5", "-74.20408764000003", "4.610146419999976", "1");
		int id6 = 5;
		Info info7 = new Info("6", "-74.16112719", "4.615544890000023", "1099");
		int id7 = 6;
		Info info8 = new Info("7", "-74.06925505999997", "4.674469439999996", "833");
		int id8 = 7;
		Info info9 = new Info("8", "-74.08833413000002", "4.619344660000023", "104");
		int id9 = 8;
		Info info10 = new Info("9", "-74.16485762000002", "4.4879909200000165", "37");
		int id10 = 9;
		
		Grafo.addVertex(id1, info1);
		Grafo.addVertex(id2, info2);
		Grafo.addVertex(id3, info3);
		Grafo.addVertex(id4, info4);
		Grafo.addVertex(id5, info5);
		Grafo.addVertex(id6, info6);
		Grafo.addVertex(id7, info7);
		Grafo.addVertex(id8, info8);
		Grafo.addVertex(id9, info9);
		Grafo.addVertex(id10, info10);
		
		Grafo.addEdge(id1, id2, 10, 9, 8);
		Grafo.addEdge(id2, id4, 5, 4, 3);
		Grafo.addEdge(id5, id8, 8, 7, 6);
		Grafo.addEdge(id8, id10, 13, 12, 11);
		Grafo.addEdge(id1, id5, 6, 5, 4);
	}
	
	public void setUp2() {
		Info info1 = new Info("0", "-74.08921298299998", "4.582989396000016", "275");
		int id1 = 0;
		Info info2 = new Info("1", "-74.09175497299998", "4.5795689170000164", "684");
		int id2 = 1;
		Info info3 = new Info("2", "-74.08656902000001", "4.60729538999999", "35");
		int id3 = 2;
		Info info4 = new Info("3", "-74.07312877999998", "4.648768679999988", "1035");
		int id4 = 3;
		Info info5 = new Info("4", "-74.05100413999997", "4.724598160000029", "96");
		int id5 = 4;
		Info info6 = new Info("5", "-74.20408764000003", "4.610146419999976", "1");
		int id6 = 5;
		Info info7 = new Info("6", "-74.16112719", "4.615544890000023", "1099");
		int id7 = 6;
		Info info8 = new Info("7", "-74.06925505999997", "4.674469439999996", "833");
		int id8 = 7;
		Info info9 = new Info("8", "-74.08833413000002", "4.619344660000023", "104");
		int id9 = 8;
		
		Grafo.addVertex(id1, info1);
		Grafo.addVertex(id2, info2);
		Grafo.addVertex(id3, info3);
		Grafo.addVertex(id4, info4);
		Grafo.addVertex(id5, info5);
		Grafo.addVertex(id6, info6);
		Grafo.addVertex(id7, info7);
		Grafo.addVertex(id8, info8);
		Grafo.addVertex(id9, info9);
		
		Grafo.addEdge(id1, id2, 10, 9, 8);
		Grafo.addEdge(id2, id4, 5, 4, 3);
		Grafo.addEdge(id5, id8, 8, 7, 6);
		Grafo.addEdge(id8, id9, 13, 12, 11);
		Grafo.addEdge(id1, id5, 6, 5, 4);
	}

	@Test
	public void testV() {
		setUp1();
		int tam = Grafo.V();
		assertEquals("El tamano del grafo no es correcto", 10, tam);
	}
	
	@Test
	public void testE() {
		setUp1();
		int tam = Grafo.E();
		assertEquals("El tamano del grafo no es correcto", 5, tam);
	}

	@Test
	public void testAddVertex() {
		setUp1();
		Info info11 = new Info("11", "-74.16485762000002", "4.4879909200000165", "37");
		int id11 = 11;
		Grafo.addVertex(id11, info11);
		assertEquals("El vertice no se agrego correctamente", 11, Grafo.V());
		
	}
	
	@Test
	public void testAddArc1() {
		setUp1();
		Grafo.addEdge(9, 3, 11, 10, 9);
		assertEquals("No se agrego el arco correctamente", 6, Grafo.E());
	}
	
	@Test
	public void testGetInfoVertex() {
		setUp1();
		boolean siEs = false;
		Info info = new Info("0", "-74.08921298299998", "4.582989396000016", "275");
		Info infoComparar = (Info) Grafo.getInfoVertex(0);
		if (info.getID() == infoComparar.getID() && info.getLng() == infoComparar.getLng() && info.getLat() == infoComparar.getLat() && info.getMOVEMENT_ID() == infoComparar.getMOVEMENT_ID()) {
			siEs = true;
		}
		assertEquals("La información del vertice es incorrecta", true, siEs);
	}
	
	@Test
	public void testSetInfoVertex() {
		setUp1();
		Info info = new Info("0", "-74.12957995", "4.425596960000006", "42");
		Grafo.setInfoVertex(0, info);
		Info infoAct = (Info) Grafo.getInfoVertex(0);
		assertEquals("La información del vertice no se actualizo correctamente", info, infoAct);
	}
	
	@Test
	public void testGetCostArc() {
		setUp1();
		boolean siEs = false;
		double costoReal = Grafo.getCostArcHarversine(4, 7);
		if (costoReal == 8) {
			siEs = true;
		}
		assertEquals("El costo del arco es incorrecto", true, siEs);
	}
	
	@Test
	public void testSetCostArc() {
		setUp1();
		boolean siEs = false;
		Grafo.setCostArc(4, 7, 10, 9, 8);
		double costo = Grafo.getCostArcTiempo(4, 7);
		if (costo == 9) {
			siEs = true;
		}
		assertEquals("El costo del arco no se actualizo correctamente", true, siEs);
	}
	
	@Test
	public void testAdj() {
		setUp1();
		boolean igual = true;
		Bag<Integer> res = (Bag<Integer>) Grafo.adj(0);
		for (Integer adj : res) {
			if (adj != 1 && adj != 4) {
				igual = false;
			}
		}
		assertEquals("Los ID's de los vertices adyacentes no son los correctos", true, igual);
	}
	
	@Test
	public void testUncheck() {
		setUp1();
		boolean siEs = true;
		Grafo.dfs(1);
		Grafo.uncheck();
		boolean [] marked = Grafo.marcados();
		int tam = marked.length;
		for (int i = 0; i < tam; i++) {
			if (marked[i]) {
				siEs = false;
			}
			
		}
		assertEquals("No se desmarcaron los vertices correctamente", true, siEs);
	}
	
	@Test
	public void testDfs() {
		setUp1();
		boolean siEs = true;
		Grafo.dfs(1);
		boolean [] marked = Grafo.marcados();
		int tam = marked.length;
		for (int i = 0; i < tam; i++) {
			if (i == 0 || i == 1 || i == 3 || i == 4 || i == 7 || i == 9) {
				if (!marked[i]) {
					siEs = false;
				}
			}
			
		}
		assertEquals("DFS no funciona correctamente", true, siEs);
	}
	
	@Test
	public void testCcn() {
		setUp1();
		int numReal = 5;
		assertEquals("El numero de componentes conexos es incorrecto", numReal, Grafo.cc());
	}
	
	@Test
	public void testGetCC() {
		setUp1();
		boolean siEs = true;
		Iterable<Integer> res = Grafo.getCC(0);
		for (Integer cc: res) {
			if (cc != 0 && cc != 1 && cc != 3 && cc != 4 && cc != 7 && cc != 9) {
				siEs = false;
			}
		}
		assertEquals("Los elementos del componente conexo son incorrectos", true, siEs);
	}
}

