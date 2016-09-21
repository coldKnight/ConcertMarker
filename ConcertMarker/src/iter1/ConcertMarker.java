package iter1;

import java.util.List;
import java.math.*;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;


public class ConcertMarker extends CommonMarker {
	public static int TRI_SIZE = 5;
	
	public ConcertMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(148, 0, 211);
		pg.ellipse(x, y, 5, 5);
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		
		pg.pushStyle();
		
		pg.stroke(200, 0, 0, 200);
		String name = "Venue: " + getName() + "; City: " + getCity() + ", " + getState();
		String Meta = "Date: " + getDate() + "; EventID: " + getEventID() + "; Band: " + getBandURL();
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-39, pg.textWidth(name)+5, 20);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x+3, y-35);
		
		pg.fill(255, 255, 255);
		pg.rect(x, y-15, pg.textWidth(Meta)+5, 20);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(Meta, x+3, y-11);
		
		pg.strokeWeight(15);
		//pg.stroke(200, 0, 0, 200);
		pg.noFill();
		float s = 5;
		float startPoint = (float) (Math.PI*0.95);
		float stopPoint = (float) (Math.PI*0.05);
		pg.arc(x, y, s, s, -startPoint, -stopPoint);
		pg.arc(x, y, s, s, stopPoint,startPoint);
		
		pg.popStyle();
	}
	
	private String getDate() {	
		return (String) getProperty("date");
	}
	
	private String getEventID() {
		return (String) getProperty("eventID");
	}

	private String getBandURL() {
		return (String) getProperty("band");
	}

	private String getName() {
		return (String) getProperty("venue_name");
	}

	private String getState() {
		return (String) getProperty("state");
	}

	public String getCity() {
		return (String) getProperty("city");	
		
	}
}
