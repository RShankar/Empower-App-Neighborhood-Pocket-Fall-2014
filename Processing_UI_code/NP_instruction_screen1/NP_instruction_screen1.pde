PImage instructImage;
 float rectX4, rectY4; //position of the fourth button
        
         //the size of the rectangle for the button
  float rectWidth, rectHeight;
  PFont  labelFont;
  
void setup(){
  orientation(PORTRAIT);
  size(displayWidth, displayHeight);
    background(#3EB2F7);
    
  //setting the background to the picture
  //if usinfg processing, the image is found in the data folder
  instructImage = loadImage("latestScaledDown.jpg");
  instructImage.resize(displayWidth,displayHeight);
  noStroke();
}

void draw(){
  //setting the image
  image(instructImage,0,0);
}

void mousePressed(){
  //tap anywhere to continue
  if(mouseX <= displayWidth &&  mouseY <= displayHeight){
    println("Button 4");
  }
  
}


