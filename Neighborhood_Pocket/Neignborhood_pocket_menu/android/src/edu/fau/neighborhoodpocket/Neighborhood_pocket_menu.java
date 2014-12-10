package edu.fau.neighborhoodpocket;

import java.util.ArrayList;
import java.util.List;

import ketai.sensors.KetaiLocation;
import ketai.sensors.Location;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * @author Group 1
 * The Neighborhood Pocekt Menu class is a class that was
 * written in Processing. This class displays the menu that
 * the user can use to manuever through the Neighborhood Pocket
 * application. The initial connection to the Parse database is also performed in this class.
 */
public class Neighborhood_pocket_menu extends PApplet {

	//variable for images
	PImage img, img2, img3, img4;

	//location variable
	KetaiLocation location;

	//variables to hold your coordinates
	double longitude, latitude;

	float rectX1, rectY1, //position of the first button 
	      rectX2, rectY2, //position of the second button
	      rectX3, rectY3, //position of the third button
	      rectX4, rectY4; //position of the fourth button
	      

	//the size of the rectangle for the button
	float rectWidth, rectHeight;

	//these are labels for the buttons
	String labelReport, labelMap, label911, labelEmergency;

	//setting the font for labels
	PFont font;
	PFont labelFont;
	   
	int buttonColor; //color of the buttons

	//setup method that gets parameters for the screen ready
	public void setup(){
		
		//connecting to Parse
        Parse.initialize(this, "09NgkqEX1lWJkzXJjMnLGfRC5jiB65Xcp0TtW1t5", "LdGlAnWAP9ZEYLJcKCdQmyjS72bv2I9vurw3bEQI");
        
        //querying parse for data
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SuspiciousActivity");
        query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null){
					MapTester.listItems =  (ArrayList<ParseObject>) arg0;
					//getting the info from a parse object
					
			        for(int i = 0; i < MapTester.listItems.size(); ++i){
			        	LatLng coordinates = new LatLng(MapTester.listItems.get(i).getDouble("latitude"), MapTester.listItems.get(i).getDouble("longitude"));
			        	SuspiciousActivity act = new SuspiciousActivity(getApplicationContext(), coordinates);
			        	act.setDescription(MapTester.listItems.get(i).getString("description"));
			        	act.setDate(MapTester.listItems.get(i).getDate("date"));
			        	//getting the BitMap
			        	ParseFile file = MapTester.listItems.get(i).getParseFile("image");
			        	try {
							byte[] byteArray = file.getData();
							Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
							act.setImage(bm);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	//placing items from database into Hashmap
			        	MapTester.activityMap.put(i, act);
			        }
				}
				else{
					Toast.makeText(getApplicationContext(), "Object download failed", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	  
	  size(displayWidth, displayHeight);
	  
	  //locks the screen orientation in portrait
	  orientation(PORTRAIT);
	  
	  //loading the images which are stored in the "data" file found in this sketches directory
	  //if you are working from Eclipse, then the images are stored in the "assests" folder
	  img = loadImage("map_icon1.png"); //map icon
	  img2 = loadImage("report_icon.png"); //report icon
	  img3 = loadImage("911_icon.jpg"); //911 icon
	  img4 = loadImage("emergency_call_icon.jpg");//emergency call icon
	 
	  noStroke();
	  
	  //setting the location 
	  location = new KetaiLocation(this);
	  
	  font = createFont("SansSerif", displayWidth/10, true);
	  labelFont = createFont("SansSerif.bold", 54, true);
	  
	  //setting the dimensions for the rectangle
	  rectWidth = (float) (displayWidth/1.5);
	  rectHeight = displayHeight/10;
	  
	  //setting the location for the buttons on the screen beginning with the left most button
	  rectX1 = displayWidth/3 - rectHeight;
	  rectY1 = displayHeight/4;
	  
	  rectX2 = displayWidth/3 - rectHeight;
	  rectY2 = displayHeight/4 + displayHeight/8;
	 
	  rectX3 = displayWidth/3 - rectHeight;
	  rectY3 = displayHeight/4 + (displayHeight/8*2);
	  
	  rectX4 = displayWidth/3 - rectHeight;
	  rectY4 = displayHeight/4 + (displayHeight/8 * 3);
	  
	  //checking the width and height of the device screen
	  println("Width: " + displayWidth + "\nHeight: " + displayHeight);
	  
	  //background color
	  background(0xff0870FF);
	  
	  
	  labelReport = "Report";
	  labelMap = "Map";
	  label911 = "911"; 
	  labelEmergency = "Call Mother";
	}

	public void draw(){
	  fill(0xffCBD2DB);
	  textFont(font);
	  textAlign(CENTER);
	  text("Neighborhood Pocket", displayWidth/2, displayHeight/6);
	 
	  //setting up the buttons on the screen
	  //a rectangle object is used here to set the buttons up
	  rect(rectX1,rectY1,rectWidth, rectHeight, 20);  
	  rect(rectX2,rectY2,rectWidth, rectHeight, 20);
	  rect(rectX3,rectY3,rectWidth, rectHeight, 20);
	  rect(rectX4,rectY4,rectWidth, rectHeight, 20);
	  
	  //placing the labels on the buttons
	  fill(0xff030000);
	  textFont(labelFont);
	  
	  //placing the images on the main menu
	  image(img2, (float)(rectX1 + (rectWidth/2.75)), rectY1 ,(float)(displayWidth/5.4),(float)(displayHeight/10)); //report icon image 
	 
	  image(img,(float)(rectX2 + (rectWidth/2.75)),rectY2, (float)(displayWidth/5.4), (float)(displayHeight/7.7)); //,200,250);
	
	  image(img3, (float)(rectX3 + (rectWidth/2.75)),(float) (rectY3 + (rectHeight/9)), (float)(displayWidth/5.4), (float)(displayHeight/12));
	  
	  //text(labelEmergency,(float)(rectX4 + (rectWidth/1.5)),(float)(rectY4 + (rectHeight/2)));
	  
	  image(img4,(float)(rectX4 +(rectWidth/2.75)), (float)(rectY4 + (rectHeight/9)),(float)(displayWidth/5.4), (float)(displayHeight/12));

	  if(location.getProvider() == "none"){
	    println("No location available5");
	  }
	}

//this is the listener method that is used in processing to detect a user touching the screen
public void mousePressed(){
  
  //checking for the coordinates of the buttons and giving the buttons funtionality
  //if the user clicks the Report Button, this will launch the Report Map Activity	
  if(mouseX >= rectX1 && mouseX <= rectX1 + rectWidth && mouseY >= rectY1 && mouseY <= rectY1 + rectHeight){
	  //using intents to launch the ReportMap Activiy
	  //this android code
	  //intents can pass data; in this case, the latitude and longitude data is passed
	  Intent intent = new Intent(this, ReportMap.class);
      intent.putExtra("latitude", latitude);
      intent.putExtra ("longitude", longitude);
      startActivity(intent);
    
  }
  //if the user clicks the Map button, this code will run
  if(mouseX >= rectX2 && mouseX <= rectX2 + rectWidth && mouseY >= rectY2 && mouseY <= rectY2 + rectHeight){
	//using intents to launch the MapTester Activiy
    //this android code
	//intents can pass data; in this case, the latitude and longitude data is passed  
    Intent intent = new Intent(this, MapTester.class);
    intent.putExtra("latitude", latitude);
    intent.putExtra ("longitude", longitude);
    startActivity(intent);
    
  }
  //if the user clicks the 911 button, this code will launch
  if(mouseX >= rectX3 && mouseX <= rectX3 + rectWidth && mouseY >= rectY3 && mouseY <= rectY3 + rectHeight){
    //android code to have the device call 911
    Intent intent = new Intent(this, PoliceButton.class);
    startActivity(intent);
  }
  //if the user clicks the EmergencyCall button, this code will launch
  if(mouseX >= rectX4 && mouseX <= rectX4 + rectWidth && mouseY >= rectY4 && mouseY <= rectY4 + rectHeight){
    //Android code to have phone dial the number
    Intent intent = new Intent(this, EmergencyContactButton.class);
    startActivity(intent);
  }
  
}

/**
 * This method will get the device's latitude and longitude coordinates
 * @param _loc
 */
public void onLocationEvent(Location _loc){
	
  latitude = _loc.getLatitude();
  longitude = _loc.getLongitude();
  
}

public int sketchWidth() { 
	  
	  return displayWidth; 
	  }
  
  public int sketchHeight() { 
	  return displayHeight;
	  }
}
