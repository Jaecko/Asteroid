void Menu(){
  background(bg);
  
  flag_nom = false;
  
  textSize(72);
  fill(#ffffff);
  textAlign(CENTER);
  text("Asteroid", 400, 200);
  
  textSize(24);
  fill(#ffffff);
  textAlign(LEFT);
  text("1 -> Jouer", 320, 300);
  
  textSize(24);
  fill(#ffffff);
  textAlign(LEFT);
  text("2 -> Scores", 320, 350);
  
  textSize(24);
  fill(#ffffff);
  textAlign(LEFT);
  text("3 -> Commandes", 320, 400);
  
  textSize(24);
  fill(#ffffff);
  textAlign(LEFT);
  text("4 -> Quitter", 320, 450);
  
  textSize(10);
  fill(#ffffff);
  textAlign(RIGHT);
  text("Version "+version, 785, 20);
   
}
