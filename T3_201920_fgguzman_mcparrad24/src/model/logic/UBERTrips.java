package model.logic;

public class UBERTrips implements Comparable<UBERTrips> {

	private String idOrigen;
	private String idDestino;
	private String hora;
	private double tiempo;
	private double desviacionEstandar;
	private String tiempoGeometrico;
	private String desviacionEstandarGeometrica;
	private String[] viajeUber;
	
	public UBERTrips(String[] viajeUber) {
		this.viajeUber = viajeUber;
		int i = 0;
		this.idOrigen = this.viajeUber[i++];
		this.idDestino = this.viajeUber[i++];
		this.hora = this.viajeUber[i++];
		this.tiempo = Double.valueOf(this.viajeUber[i++]);
		this.desviacionEstandar = Double.valueOf(this.viajeUber[i++]);
		this.tiempoGeometrico = this.viajeUber[i++];
		this.desviacionEstandarGeometrica = this.viajeUber[i++];
	}

	public UBERTrips() {
	}

	public int compareTo(UBERTrips viaje) {
		int menor = 0;
		if(this.tiempo < viaje.tiempo) {
			menor = -1;
		} else {
			if(this.tiempo == viaje.tiempo) {
				if(this.desviacionEstandar < viaje.desviacionEstandar) {
					menor = -1;
				}
			} else {
				menor = 1;
			}
		}
		return menor;
	}
	
	public String darIdOrigen() {
		return idOrigen;
	}
	
	public String darIdDestino() {
		return idDestino;
	}
	
	public String darHoraPromedio() {
		return hora;
	}
	
	public double darTiempoPromedio() {
		return tiempo;
	}
	
	public String toString() {
		return "["+this.idOrigen+","+this.idDestino+","+this.hora+","+this.tiempo+","+this.desviacionEstandar+","+this.tiempoGeometrico+","+this.desviacionEstandarGeometrica+","+this.idDestino+"]";
	}
	
}
