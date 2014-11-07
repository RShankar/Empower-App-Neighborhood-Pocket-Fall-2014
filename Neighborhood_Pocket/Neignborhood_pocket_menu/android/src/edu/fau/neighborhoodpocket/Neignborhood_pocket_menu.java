package edu.fau.neighborhoodpocket;

import ketai.sensors.KetaiLocation;
import ketai.sensors.Location;
import processing.core.PApplet;
import processing.core.PFont;
import android.content.Intent;

public class Neignborhood_pocket_menu extends PApplet {



//location variable
KetaiLocation location;

//variables to hold your coordinates
double longitude, latitude;

float rectX1, rectY1, //position of the first button 
      rectX2, rectY2, //position of the second button
      rectX3, rectY3, //position of the third button
      rectX4, rectY4; //position of the fourth button
      

//the size of the rectangle for the button
int rectWidth, rectHeight;

//these are labels for the buttons
String labelReport, labelMap, label911, labelEmergency;

//setting the font for labels
PFont font;
PFont labelFont;
   
int buttonColor; //color of the buttons

//setup method that gets parameters for the screen ready
public void setup(){
  orientation(PORTRAIT);
 
  noStroke();
  location = new KetaiLocation(this);
  
  font = createFont("SansSerif", 100, true);
  labelFont = createFont("SansSerif", 48, true);
  
  //setting the dimensions for the rectangle
  rectWidth = 400;
  rectHeight = 200;
  
  //setting the location for the buttons on the screen beginning with the left most button
  rectX1 = displayWidth/2 - rectHeight;
  rectY1 = displayHeight/4;
  
  rectX2 = displayWidth/2 - rectHeight;
  rectY2 = displayHeight/4 + 300;
  
  rectX3 = displayWidth/2 - rectHeight;
  rectY3 = displayHeight/4 + (300*2);
  
  rectX4 = displayWidth/2 - rectHeight;
  rectY4 = displayHeight/4 + (300 * 3);
  
  //checking the width and height of the device screen
  println("Width: " + displayWidth + "\nHeight: " + displayHeight);
  background(0xff3EB2F7);
  
  labelReport = "Report";
  labelMap = "Map";
  label911 = "911"; 
  labelEmergency = "Emergency Call";
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
  text(labelReport, rectX1 + (rectWidth/2), rectY1 + (rectHeight/2));
  text(labelMap, rectX2 + (rectWidth/2), rectY2 + (rectHeight/2));
  text(label911, rectX3 + (rectWidth/2), rectY3 + (rectHeight/2));
  text(labelEmergency, rectX4 + (rectWidth/2), rectY4 + (rectHeight/2));

  if(location.getProvider() == "none"){
    println("No location available5");
  }
}

public void mousePressed(){
  
  //checking for the coordinates of the buttons and giving the buttons funtionality
  if(mouseX >= rectX1 && mouseX <= rectX1 + rectWidth && mouseY >= rectY1 && mouseY <= rectY1 + rectHeight){
    println("Button 1");
    
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
