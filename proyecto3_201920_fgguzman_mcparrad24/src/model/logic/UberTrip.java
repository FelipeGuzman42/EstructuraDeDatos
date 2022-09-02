package model.logic;

public class UberTrip implements Comparable<UberTrip>{

	private int idOrigen;
	private int idDestino;
	private int dia;
	private double tiempo;
	private String[] viajeUber;
	private String llave;
	private String valor;
	private int trimestre;
	
	public UberTrip(String[] viajeUber, int trim) {
		this.viajeUber = viajeUber;
		int i = 0;
		this.idOrigen = Integer.parseInt(this.viajeUber[i++]);
		this.idDestino = Integer.parseInt(this.viajeUber[i++]);
		this.dia = Integer.parseInt(this.viajeUber[i++]);
		this.tiempo = Double.valueOf(this.viajeUber[i++]);
		this.trimestre = trim;
		this.llave = darLlave();
		this.valor = darValor();
	}

	public int compareTo(UberTrip viaje) {
		int menor = 0;
		if(this.tiempo < viaje.tiempo) {
			menor = -1;
		}
		return menor;
	}
	
	public int darIdOrigen() {
		return idOrigen;
	}
	
	public int darIdDestino() {
		return idDestino;
	}
	
	public int darDia() {
		return dia;
	}
	
	public double darTiempoPromedio() {
		return tiempo;
	}
	
	public String darLlave() {
		llave = this.trimestre+"-"+this.idOrigen+"-"+this.idDestino;
		return llave;
	}
	
	public String darValor() {
		valor = this.trimestre+","+this.idOrigen+","+this.idDestino+","+this.dia+","+this.tiempo;
		return valor;
	}
	
	public String toString() {
		return "["+this.idOrigen+","+this.idDestino+","+this.dia+","+this.tiempo+","+this.idDestino+"]";
	}
}
