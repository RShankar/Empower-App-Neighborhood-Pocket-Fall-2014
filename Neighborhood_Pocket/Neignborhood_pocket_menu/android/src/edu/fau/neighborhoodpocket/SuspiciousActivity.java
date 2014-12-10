package edu.fau.neighborhoodpocket;

import java.util.Calendar;
import java.util.Date;

import processing.test.neignborhood_pocket_menu.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;


/**
 * @author group1
 * This class is used to model a SuspiciousActivity. A SuspiciousActivity object is created whenever
 * a user reports a suspicious activity. This class encapsulates different attributes that make up a
 * Suspicious Activity.
 *
 */
public class SuspiciousActivity {
	
	private String description;
	private Bitmap image;
	private LatLng coordinates;
	private Calendar date;
	private Date serverDate = null;
	private String title;
	
	/**
	 * @param context
	 * @param coordinates
	 * Constructor for the SuspiciousActivity object.
	 */
	public SuspiciousActivity(Context context, LatLng coordinates) {
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		date = Calendar.getInstance();
		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.report_icon, options);
		description = "Description:\nThis activity was added without a description on " + date.getTime();
		this.coordinates = coordinates;
		title = "Suspicious Activity Reported on " + date.getTime();
	}
	
	/**
	 * @return the SuspiciousActivity description
	 */
	public String getDescription(){
		
		return description;
	}
	
	/**
	 * @return the image in the form of a Bitmap object assoicated with SuspiciousActivity
	 */
	public Bitmap getBitmap(){
		
		return image;
	}
	
	/**
	 * @param s
	 * This method will set the description of the SuspicousActivity to the parameter that is passed
	 * in.
	 */
	public void setDescription(String s){
		
		description = s;
	}
	
	/**
	 * @param i]
	 * This method will set the image associated with the SuspiciousActivity to the parameter that is
	 * passed in.
	 */
	public void setImage(Bitmap i){
		
		image = i;
	}
	
	/**
	 * @return the coordinates as a LatLng object of the Suspicious Activity                                                                                                                                       
	 */
	public LatLng getCoordinates(){
		
		return coordinates;
	}
	
	/**
	 * @return the title associated with the SuspiciousAcitivity
	 */
	public String getTitle(){
		
		if(serverDate != null)
			title = "Suspicious Activity Reported on " + serverDate;
		return title;
	}
	
	/**
	 * @param l
	 * @return true or false depending if the SuspiciousActivity object contains the coordinates that
	 * are passed in.
	 */
	public boolean containsCoordinates(LatLng l){
		
		if(l.equals(coordinates))
			return true;
		else
			return false;
	}
	
	/**
	 * @param l
	 * This method sets the LatLng attribute of the SuspiciousActivity object.
	 */
	public void setPosition(LatLng l){
		
		coordinates = l;
	}
	
	/**
	 * @return the timestamp associated with the SuspiciousActivity object
	 */
	public Date getDate(){
		
		return date.getTime();
	}
	
	/**
	 * @param d
	 * This method will set the timestamp for the SuspiciousActivity.
	 */
	public void setDate(Date d){
		serverDate = d;
		date.setTime(d);
	}
	
	public Calendar getCalendar(){
		return date;
	}

}
