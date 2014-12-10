package edu.fau.neighborhoodpocket;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import android.content.Intent;

/**
 * @author Group1
 * This class draws the Splash Screen for the Neighborhood Pocket
 * application. This code written using Processing.
 */
public class NP_splash_screen1 extends PApplet {

	//variable for the splashscreen image
	PImage splashImage;
	float rectX4, rectY4; //position of the start button
        
     //the size of the rectangle for the button
	float rectWidth, rectHeight;
  
	//variable for the font
	PFont  labelFont;
	
	//label for button
	String continueLabel;
	

public void setup(){
	
	//setting the orientation of the screen to portrait 
	orientation(PORTRAIT);
 
  
 
  //setting the dimensions for the rectangle that will make up the button
    rectWidth = (float) (displayWidth/1.5f);
    rectHeight = displayHeight/10;
    
    //setting the background, used color selector to get the correct hex number
    background(0xff3EB2F7);
    
  //setting the background to the picture
  //if usinfg processing, the image is found in the data folder
  //in Eclipse, the image is found in the Assests folder  
  splashImage = loadImage("latestSplash.jpg");
  splashImage.resize(displayWidth,displayHeight);
  noStroke();
  rectX4 = displayWidth/3 - rectHeight;
  rectY4 = displayHeight/1.4f;
  labelFont = createFont("SansSerif.bold", 80, true);
  continueLabel = "START";
}

public void draw(){
  //setting the image
  image(splashImage,0,0);
  
   //placing the labels on the buttons
  fill(0xff0870FF);
  
  //button location
  rect(rectX4,rectY4,rectWidth, rectHeight, 20);
  //color of the button
  fill(0xffFFFFFF);
  textFont(labelFont);
  //placing the text on the screen
  text(continueLabel, (float)(rectWidth/1.8f),(float)(rectY4 + (rectHeight/2)));


}

public void mousePressed(){
  
  //checking for the coordinates of the buttons and giving the buttons funtionality
  //using an intent to call the next Android screen	
  if(mouseX >= rectX4 && mouseX <= rectX4 + rectWidth && mouseY >= rectY4 && mouseY <= rectY4 + rectHeight){
	  Intent intent = new Intent(this, NP_instruction_screen1.class);
	  startActivity(intent);
  }
  
}



  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
}
