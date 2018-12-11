void GameOver(){
   bg = color(#000000);
   background(bg);
   textSize(72);
   fill(#ffffff);
   textAlign(CENTER);
   text("Vous avez perdu !", 400, 200);
   
   textSize(32);
   fill(#ffffff);
   textAlign(CENTER);
   text("Votre score : "+score, 400, 350);
   
   flag_nom = true;
   if(flag_nom == true){
     textSize(32);
     fill(#ffffff);
     textAlign(CENTER);
     text("Votre nom : "+monNom, 400, 400);
   }
  
   textSize(40);
   fill(#ffffff);
   textAlign(CENTER);
   text("ENTER", 400, 480);
   
}
