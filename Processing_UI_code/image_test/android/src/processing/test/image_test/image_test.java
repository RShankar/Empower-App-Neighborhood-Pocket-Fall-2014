package processing.test.image_test;

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

public class image_test extends PApplet {

 PImage img;
 
 public void setup(){
  
   img = loadImage("map_icon1.png");
 }
 
 public void draw(){
   background(0);
   image(img,0,0,90,60);
 }

  public int sketchWidth() { return 240; }
  public int sketchHeight() { return 340; }
}
