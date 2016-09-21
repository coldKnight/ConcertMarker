package iter1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PImage;

public class ConcertMap extends PApplet{
	
	UnfoldingMap map;
	private List<Marker> concertList;
	
	private ConcertMarker lastClicked;
	
	public void setup() {
		// setting up PAppler
		size(1500,700, OPENGL);

		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 1450, 650);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from concert data
		List<PointFeature> features = ParseFeed.parseConcerts(this, "events_small.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		concertList = new ArrayList<Marker>();
		HashMap<Integer, Location> concerts = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			
			ConcertMarker m = new ConcertMarker(feature);
	
			//m.setRadius(5);
			concertList.add(m);
			
			// put concert in hashmap with concerts unique id for key
			concerts.put(Integer.parseInt(feature.getId()), feature.getLocation());
			//System.out.println(feature.getProperties());
		
		}
		
		map.addMarkers(concertList);
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	/*=================================================================================*/
	
	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked.setSelected(false);
			lastClicked = null;
			
		}
		else if (lastClicked == null) 
		{	
			//lastClicked.setSelected(true);
			checkConcertsForClick();
		}
	}
	
	private void checkConcertsForClick()
	{
		if (lastClicked != null) return;
		
		// Loop over the concert markers to see if one of them is selected
		for (Marker marker : concertList) {
			
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (ConcertMarker)marker;
				marker.setSelected(true);
				// Hide all the other concerts
				for (Marker mhide : concertList) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}				
				}
				
				String name = (String)lastClicked.getProperty("venue_name");
				String city = (String)lastClicked.getProperty("city");
				String state = (String)lastClicked.getProperty("state");
				String location = name.concat(city).concat(state);
				System.out.println(lastClicked.getProperties());
			}
		}
	}
	
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : concertList) {
			marker.setHidden(false);
		}
	}

}
