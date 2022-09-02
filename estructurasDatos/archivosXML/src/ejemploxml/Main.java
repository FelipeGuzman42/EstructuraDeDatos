package ejemploxml;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

//Basado en:
//https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html

public class Main extends DefaultHandler {

	private Hashtable tags;

	static public void main(String[] args) throws Exception{
		String filename = "./data/map.osm";
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		    
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new Main());
		xmlReader.parse(filename);
	}

	
	public void startDocument() throws SAXException {
		tags = new Hashtable();
	}

	public boolean printedFirstNode = false;
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

		String key = localName;
		Object value = tags.get(key);

		if (value == null) {
			tags.put(key, new Integer(1));
		} else {
			int count = ((Integer) value).intValue();
			count++;
			tags.put(key, new Integer(count));
		}
		
		if(key.equals("node") && !printedFirstNode) {
			System.out.println("First node");
			for(int n = 0; n < atts.getLength(); n++) {
				System.out.println(atts.getQName(n)+ ": " + atts.getValue(n));
			}
			System.out.println();
			printedFirstNode = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
            throws SAXException {
		System.out.println(localName);
	}

	public void endDocument() throws SAXException {
		Enumeration e = tags.keys();
		
		for(Object o: tags.keySet()) {
			String tag = (String) o;
			int count = ((Integer) tags.get(tag)).intValue();
			System.out.println("Local Name \"" + tag + "\" occurs " + count + " times");
		}
	}
}
