
// ====== Aide (Game)  ================================================================

void Help(){
  bg = color(#000000);
  background(bg);
  
  textSize(64);
  fill(#ffffff);
  textAlign(CENTER);
  text("AIDE", 400, 100);
  
  textSize(32);
  fill(#ffffff);
  textAlign(CENTER);
  text("Déplacement :", 400, 300);
  text("Flèches directionnelles ou ZQSD", 400, 350);
  text("P : Pause", 400, 400);
    
  textSize(24);
  fill(#ffffff);
  textAlign(CENTER);
  text("ENTER", 400, 550);
}

// ====== Commandes(Menu) ================================================================

void Commands(){
  bg = color(#000000);
  background(bg);
  
  textSize(64);
  fill(#ffffff);
  textAlign(CENTER);
  text("COMMANDES", 400, 100);
  
  textSize(32);
  fill(#ffffff);
  textAlign(CENTER);
  text("Déplacement :", 400, 250);
  text("Flèches directionnelles ou ZQSD", 400, 300);
  text("P : Pause", 400, 350);
  text("F1 : Aide", 400, 400);
    
  textSize(24);
  fill(#ffffff);
  textAlign(CENTER);
  text("ENTER", 400, 550);
}
