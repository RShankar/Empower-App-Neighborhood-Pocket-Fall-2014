PImage splashImage;
 float rectX4, rectY4; //position of the fourth button
        
         //the size of the rectangle for the button
  float rectWidth, rectHeight;
  
  String continueLabel;
  
  PFont  labelFont;
void setup(){
  orientation(PORTRAIT);
  size(displayWidth, displayHeight);
  
 
  //setting the dimensions for the rectangle
    rectWidth = (float) (displayWidth/1.5);
    rectHeight = displayHeight/10;
    
    background(#3EB2F7);
    
  //setting the background to the picture
  //if usinfg processing, the image is found in the data folder
  splashImage = loadImage("neighbor_hood_pocket_splash1.png");
  splashImage.resize(displayWidth,displayHeight);
  noStroke();
  rectX4 = displayWidth/3 - rectHeight;
  rectY4 = displayHeight/1.4;
  labelFont = createFont("SansSerif.bold", 80, true);
  continueLabel = "START";
}

void draw(){
  //setting the image
  image(splashImage,0,0);
  
   //placing the labels on the buttons
  fill(#0870FF);
  
  //button location
  rect(rectX4,rectY4,rectWidth, rectHeight, 20);
  
  fill(#FFFFFF);
  textFont(labelFont);
  text(continueLabel, (float)(rectWidth/1.8),(float)(rectY4 + (rectHeight/2)));


}

void mousePressed(){
  
  //checking for the coordinates of the buttons and giving the buttons funtionality

  if(mouseX >= rectX4 && mouseX <= rectX4 + rectWidth && mouseY >= rectY4 && mouseY <= rectY4 + rectHeight){
    println("Button 4");
  }
  
}


