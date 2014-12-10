package processing.test.np_instruction_screen1;

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
    println("Button 4");
  }
  
}



  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
}
