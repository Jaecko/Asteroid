void HighScore(){
  bg = color(#000000);
  background(bg);
  String[] file;
  String[] contenu = new String[2];
  String texte = "";
  String[] lines = new String[5];
  file = loadStrings("data/highScore.txt");
  
  
  
  textSize(64);
  fill(#ffffff);
  textAlign(CENTER);
  text("SCORE", 400, 100);
  
  if(file != null){
    for(int i = 0; i < file.length; i++){
      if(file[i].equals("---") == false){
        contenu = split(file[i], "\t");
        texte = contenu[0]+" : "+contenu[1];
      }
      else{
        texte = file[i];
      }
        
        textSize(32);
        fill(#ffffff);
        textAlign(CENTER);
        text(""+texte, 400, 225+(i*50));
    }
    
    textSize(24);
    fill(#ffffff);
    textAlign(CENTER);
    text("ENTER", 400, 550);
  }
  else{
    for(int i = 0; i < 5; i++){
      lines[i] = "---";
    }
    saveStrings("data/highScore.txt", lines);
    HighScore();
    //state=stateMenu;
  }
}
