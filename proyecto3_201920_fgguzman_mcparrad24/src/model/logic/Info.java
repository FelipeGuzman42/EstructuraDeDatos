package model.logic;

public class Info implements Comparable<Info>{
	
	private int id;
	private double lng;
	private double lat;
	private int MOVEMENT_ID;
	private double velocidad;
	
	public Info(String ID, String lng, String lat, String mOVEMENT_ID) {
		this.id = Integer.parseInt(ID);
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
		this.MOVEMENT_ID = Integer.parseInt(mOVEMENT_ID);
	}
	
	public int getID() {
		return id;
	}
	
	public double getLng() {
		return lng;
	}
	public double getLat() {
		return lat;
	}
	public int getMOVEMENT_ID() {
		return MOVEMENT_ID;
	}

	public String toString() {
		return "Vertice [longitud=" + lng + ", latitud=" + lat + ", MOVEMENT_ID=" + MOVEMENT_ID + "]";
	}
	
	public void setVelocidad(double vel) {
		this.velocidad = vel;
	}
	
	public double getVelocidad() {
		return velocidad;
	}

	@Override
	public int compareTo(Info o) {
		int menor = 0;
		if (this.velocidad < o.velocidad) {
			menor = -1;
		} 
		else if (this.velocidad > o.velocidad) {
			menor = 1;
		}
		
		return menor;
	}
}
