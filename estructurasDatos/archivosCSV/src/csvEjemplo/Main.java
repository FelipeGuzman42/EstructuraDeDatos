package csvEjemplo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

/**
 * Ejemplo basado en el ejemplo de:
 * http://opencsv.sourceforge.net/
 * 
 * Descargar los .jar de open csv y apache-commons-lang de:
 * https://mvnrepository.com/artifact/com.opencsv/opencsv/4.0
 * https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.8.1
 * y agregar al classpath
 */

public class Main {
	
	public static void main(String[] args) {

		CSVReader reader = null;
		try {
			
			reader = new CSVReader(new FileReader("./data/data.csv"));
			for(String[] nextLine : reader) {
		       System.out.println("col1: " + nextLine[0] + ", col2: "+ nextLine[1]);
		    }
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	    
	     
	}

}
