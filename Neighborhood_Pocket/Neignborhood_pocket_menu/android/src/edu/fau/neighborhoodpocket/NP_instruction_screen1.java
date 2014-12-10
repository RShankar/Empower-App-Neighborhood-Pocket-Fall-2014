package edu.fau.neighborhoodpocket;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import android.content.Intent;

/**
 * @author Group 1
 * This class contains the image that will display instructions
 * for using Neighborhood Pocket. 
 *
 */
public class NP_instruction_screen1 extends PApplet {

PImage instructImage;
 float rectX4, rectY4; //position of the fourth button
        
         //the size of the rectangle for the button
  float rectWidth, rectHeight;
  PFont  labelFont;
  
public void setup(){
  orientation(PORTRAIT);
 
    background(0xff3EB2F7);
    
  //setting the background to the picture
  //if usinfg processing, the image is found in the data folder
  instructImage = loadImage("latestScaledDown.jpg");
  instructImage.resize(displayWidth,displayHeight);
  noStroke();
}

public void draw(){
  //setting the image
  image(instructImage,0,0);
}

public void mousePressed(){
  //tap anywhere to continue
  if(mouseX <= displayWidth &&  mouseY <= displayHeight){
	  Intent intent = new Intent(this, Neighborhood_pocket_menu.class);
	  startActivity(intent);
  }
  
}



  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
}
