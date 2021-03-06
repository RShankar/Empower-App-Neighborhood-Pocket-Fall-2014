package processing.test.np_splash_screen;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NP_splash_screen extends PApplet {

PImage backgroundImage;
 float rectX4, rectY4; //position of the fourth button
        
         //the size of the rectangle for the button
  float rectWidth, rectHeight;
  
  String continueLabel;
  
  PFont  labelFont;
public void setup(){
  
 
  //setting the dimensions for the rectangle
    rectWidth = (float) (displayWidth/1.5f);
    rectHeight = displayHeight/10;
  
   
  
  //setting the size to that of the device's display
 
  
  //setting the background to the picture
  //if usinfg processing, the image is found in the data folder
  backgroundImage = loadImage("neighbor_hood_pocket_splash1.png");
  
  rectX4 = displayWidth/3 - rectHeight;
  rectY4 = displayHeight/4 + (displayHeight/4);
  labelFont = createFont("SansSerif.bold", 80, true);
  continueLabel = "START";
}

public void draw(){
  //setting the background
  background(backgroundImage);
  
  
   //placing the labels on the buttons
  fill(0xff0870FF);
  
  //button location
  rect(rectX4,rectY4,rectWidth, rectHeight, 20);
  
  fill(0xffFFFFFF);
  textFont(labelFont);
  text(continueLabel, (float)(rectWidth/1.8f),(float)(rectY4 + (rectHeight/2)));


}

public void mousePressed(){
  
  //checking for the coordinates of the buttons and giving the buttons funtionality

  if(mouseX >= rectX4 && mouseX <= rectX4 + rectWidth && mouseY >= rectY4 && mouseY <= rectY4 + rectHeight){
    println("Button 4");
  }
  
}



  public int sketchWidth() { return 1020; }
  public int sketchHeight() { return 1528; }
}
