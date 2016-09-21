package parsing;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.io.*;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.data.XML;
//import org.apache.commons.csv.*;

public class ParseFeed {
	/*
	 * This method is to parse a GeoRSS feed corresponding to earthquakes around
	 * the globe.
	 * 
	 * @param p - PApplet being used
	 * @param fileName - file name or URL for data source
	 */	
	public static List<PointFeature> parseConcerts(PApplet p, String fileName) {
		List<PointFeature> features = new ArrayList<PointFeature>();
		//File csvData = new File(fileName);
		
		String[] rows = p.loadStrings(fileName);
		
		//CSVParser parser = CSVParser.parse(csvData);
		
		System.out.println(fileName);
		for (String row : rows) {
			
			String[] columns = row.split("\\|");
			
			// get location and create feature
			//System.out.println(row);
			float lat = Float.parseFloat(columns[4]);
			float lon = Float.parseFloat(columns[5]);
			
			String url = columns[7];
			String[] url_contents = url.split("=");
			String artist = url_contents[1];
			
			Location loc = new Location(lat, lon);
			PointFeature point = new PointFeature(loc);
			
			// set ID to concerts unique identifier
			point.setId(columns[0]);
			
			// get other fields
			point.addProperty("date", columns[1]);
			point.addProperty("venue_name", columns[6]);
			point.putProperty("city", columns[2]);
			point.putProperty("state", columns[3]);
			point.addProperty("eventID", columns[0]);
			point.addProperty("band", artist);
			
			features.add(point);
		}

		return features;
		
	}
}