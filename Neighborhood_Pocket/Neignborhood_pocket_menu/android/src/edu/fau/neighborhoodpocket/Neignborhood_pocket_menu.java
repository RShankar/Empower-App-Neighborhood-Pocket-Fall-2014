package edu.fau.neighborhoodpocket;

import ketai.sensors.KetaiLocation;
import ketai.sensors.Location;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import android.content.Intent;

public class Neignborhood_pocket_menu extends PApplet {

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
	  
	  size(displayWidth, displayHeight); 
	  orientation(PORTRAIT);
	  
	  //loading the images which are stored in the "data" file found in this sketches directory
	  img = loadImage("map_icon1.png"); //map icon
	  img2 = loadImage("report_icon.png"); //report icon
	  img3 = loadImage("911_icon.jpg"); //911 icon
	  img4 = loadImage("emergency_call_icon.jpg");//emergency call icon
	 
	  noStroke();
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
	  background(0xff3EB2F7);
	  
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
	  
	 
	  image(img2, (float)(rectX1 + (rectWidth/2.75)), rectY1 ,(float)(displayWidth/5.4),(float)(displayHeight/10)); //report icon image 
	 
	  image(img,(float)(rectX2 + (rectWidth/2.75)),rectY2, (float)(displayWidth/5.4), (float)(displayHeight/7.7)); //,200,250);
	
	  image(img3, (float)(rectX3 + (rectWidth/2.75)),(float) (rectY3 + (rectHeight/9)), (float)(displayWidth/5.4), (float)(displayHeight/12));
	  text(labelEmergency,(float)(rectX4 + (rectWidth/1.5)),(float)(rectY4 + (rectHeight/2)));
	  image(img4,(float)(rectX4 +(rectWidth/5.5)), (float)(rectY4 + (rectHeight/9)),(float)(displayWidth/5.4), (float)(displayHeight/12));

	  if(location.getProvider() == "none"){
	    println("No location available5");
	  }
	}

public void mousePressed(){
  
  //checking for the coordinates of the buttons and giving the buttons funtionality
  if(mouseX >= rectX1 && mouseX <= rectX1 + rectWidth && mouseY >= rectY1 && mouseY <= rectY1 + rectHeight){
	  Intent intent = new Intent(this, ReportMap.class);
      intent.putExtra("latitude", latitude);
      intent.putExtra ("longitude", longitude);
      startActivity(intent);
    
  }
  
  if(mouseX >= rectX2 && mouseX <= rectX2 + rectWidth && mouseY >= rectY2 && mouseY <= rectY2 + rectHeight){
    println("Button 2");
    println("Lat: " + latitude + "\nLong: " + longitude);
    Intent intent = new Intent(this, MapTester.class);
    intent.putExtra("latitude", latitude);
    intent.putExtra ("longitude", longitude);
    startActivity(intent);
    
  }
  
  if(mouseX >= rectX3 && mouseX <= rectX3 + rectWidth && mouseY >= rectY3 && mouseY <= rectY3 + rectHeight){
    println("Button 3");
    Intent intent = new Intent(this, PoliceButton.class);
    startActivity(intent);
  }
  
  if(mouseX >= rectX4 && mouseX <= rectX4 + rectWidth && mouseY >= rectY4 && mouseY <= rectY4 + rectHeight){
    println("Button 4");
    Intent intent = new Intent(this, EmergencyContactButton.class);
    startActivity(intent);
  }
  
}


public void onLocationEvent(Location _loc){
  latitude = _loc.getLatitude();
  longitude = _loc.getLongitude();
  
}



 

  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
}
