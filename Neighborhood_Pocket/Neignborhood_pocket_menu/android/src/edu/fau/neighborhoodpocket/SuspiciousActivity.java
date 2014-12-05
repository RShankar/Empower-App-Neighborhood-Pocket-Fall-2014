package edu.fau.neighborhoodpocket;

import java.util.Calendar;

import processing.test.neignborhood_pocket_menu.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;


public class SuspiciousActivity {
	private String description;
	private Bitmap image;
	private LatLng coordinates;
	private Calendar date;
	private String title;
	
	public SuspiciousActivity(Context context, LatLng coordinates) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		date = Calendar.getInstance();
		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.report_icon, options);
		description = "Description:\nThis activity was added without a description on " + date.getTime();
		this.coordinates = coordinates;
		title = "Suspicious Activity Reported on " + date.getTime();
	}
	
	public String getDescription(){
		return description;
	}
	
	public Bitmap getBitmap(){
		return image;
	}
	
	public void setDescription(String s){
		description = "Description: " + s;
	}
	
	public void setImage(Bitmap i){
		image = i;
	}
	
	public LatLng getCoordinates(){
		return coordinates;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean containsCoordinates(LatLng l){
		if(l.equals(coordinates))
			return true;
		else
			return false;
	}
	
	public void setPosition(LatLng l){
		coordinates = l;
	}

}
