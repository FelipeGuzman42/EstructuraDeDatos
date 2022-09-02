package model.logic;

import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;

public class PruebaColaPrioridad {
	private MaxColaCP<TravelTime> datosCola = new MaxColaCP<>();
	private MaxHeapCP<TravelTime> datosHeap = new MaxHeapCP<>(0);
	private TravelTime[] pruebas;

	public PruebaColaPrioridad(TravelTime[] datos) {
		pruebas = datos;
	}

	public void agregarCola() {
		for(int i = 0; i<pruebas.length; i++) {
			datosCola.insertar(pruebas[i]);
		}
	}
	
	public void agregarHeap() {
		for(int i = 0; i<pruebas.length; i++) {
			datosHeap.insertar(pruebas[i]);
		}
	}

	public void sacarCola() {
		for(int i = 0; i<pruebas.length; i++) {
			datosCola.eliminarMax();
		}
	}
	
	public void sacarHeap() {
		for(int i = 0; i<pruebas.length; i++) {
			datosHeap.eliminarMax();
		}
	}
}
