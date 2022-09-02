package model.logic;

public class ZonaJSON {
	private int MOVEMENT_ID;
	private String scanombre;
	private double shape_leng;
	private String shape_area;
	private int ptosGeo;
	private String ptNorte;
	private String valor = MOVEMENT_ID + "," + scanombre + "," + shape_leng + "," + shape_area + "," + ptosGeo + "," + ptNorte;

	public ZonaJSON(String id, String nombre, String perimetro, String area) {
		this.MOVEMENT_ID = Integer.parseInt(id);
		this.scanombre = nombre;
		this.shape_leng = Double.parseDouble(perimetro);
		this.shape_area = area;
		this.ptosGeo = getPtosGeo();
		this.ptNorte = getPtNorte();
		this.valor = getValor();
	}
	
	public int compareTo(ZonaJSON viaje) {
		int menor = 1;
		if(this.ptosGeo < viaje.ptosGeo) {
			menor = -1;
		}
		return menor;
	}

	public int getId() {
		return MOVEMENT_ID;
	}

	public String getNombre() {
		return scanombre;
	}

	public double getPerimetro() {
		return shape_leng;
	}

	public String getArea() {
		return shape_area;
	}

	public int getPtosGeo() {
		return ptosGeo;
	}

	public void setPtosGeo(int ptosGeo) {
		this.ptosGeo = ptosGeo;
		cambiarValor();	
	}

	public String getPtNorte() {
		return ptNorte;
	}

	public void setPtNorte(String ptNorte) {
		this.ptNorte = ptNorte;
		cambiarValor();
	}

	public String getValor() {
		return valor;
	}
	
	public void cambiarValor() {
		valor = MOVEMENT_ID + "," + scanombre + "," + shape_leng + "," + shape_area + "," + ptosGeo + "," + ptNorte;
	}

	public String toString() {
		return "ZonaJSON [MOVEMENT_ID=" + MOVEMENT_ID + ", scanombre=" + scanombre + ", shape_leng=" + shape_leng
				+ ", shape_area=" + shape_area + ", ptosGeo=" + ptosGeo + ", ptNorte=" + ptNorte + ", valor=" + valor
				+ "]";
	}

}
