package model.logic;

public class MallaVial {
	private Integer nodo;
	private double longitud;
	private double latitud;
	private Double[] coordenadas = new Double[2];

	public MallaVial(String nodo, String longitud, String latitud) {
		this.nodo = Integer.parseInt(nodo);
		this.longitud = Double.parseDouble(longitud);
		this.latitud = Double.parseDouble(latitud);
		this.coordenadas[0] = this.longitud;
		this.coordenadas[1] = this.latitud;
	}

	public int getNodo() {
		return nodo;
	}

	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}
	
	public Double[] getCoordenadas() {
		return coordenadas;
	}
	
	public String toString() {
		return "mallaVial [nodo=" + nodo + ", longitud=" + longitud + ", latitud=" + latitud + "]";
	}

}
