package ordenamientosBasicos;

import java.util.Comparator;

public class Dinero implements Comparable<Dinero>{
	public int dolar;
	public int centavo;
	
	public Dinero(int d, int c) {
		this.dolar = d;
		this.centavo = c;
	}
	@Override
	public int compareTo(Dinero o) {
		if(this.dolar > o.dolar) {return 1;}
		if(this.dolar < o.dolar) {return -1;}
		if(this.centavo > o.centavo) {return 1;}
		if(this.centavo < o.centavo) {return -1;}
		return 0;
	}
	
	
	public static class ComparadorDolar implements Comparator<Dinero>{
		@Override
		public int compare(Dinero o1, Dinero o2) {
			if(o1.dolar > o2.dolar) {return 1;}
			if(o1.dolar < o2.dolar) {return -1;}
			return 0;
		}
		
	}
	
	public static class ComparadorCentavos implements Comparator<Dinero>{
		@Override
		public int compare(Dinero o1, Dinero o2) {
			if(o1.centavo > o2.centavo) {return 1;}
			if(o1.centavo < o2.centavo) {return -1;}
			return 0;
		}
		
	}


	@Override
	public String toString() {
		return "Dinero [dolar=" + dolar + ", centavo=" + centavo + "]";
	}
	
	
	
}
