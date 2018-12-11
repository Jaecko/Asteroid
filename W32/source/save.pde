void SaveHS(){
  
  // Load file
  String[] file;
  String[] tab_nom = new String[5];
  int[] tab_score = new int[5];
  String[] contenu = new String[2];
  boolean flag_tab = true;
  
  String[] lines = new String[5];
  file = loadStrings("data/highScore.txt");
  
  if(file != null && file.length > 0){
    for(int i = 0; i < file.length; i++){
      if(file[i].equals("---") == false){
        contenu = split(file[i], "\t");
        tab_nom[i] = contenu[0];
        tab_score[i] = int(contenu[1]);
      }
      else{
        tab_nom[i] = null;
        tab_score[i] = 0;
      }
    }
    
    // Tab Order
    for(int k = 0; k < tab_score.length; k++){
      if(score > int(tab_score[k]) && flag_tab == true){
        flag_tab = false;
        for(int m = tab_score.length - 1; m >= k+1; m--){
          String nom_interm = tab_nom[m-1];
          int score_interm = tab_score[m-1];
          
          tab_score[m]=score_interm;
          tab_nom[m]=nom_interm;
          
        }
        tab_nom[k] = monNom;
        tab_score[k] = score;
      }
    }
    
    for (int i = 0; i < 5; i++) {
      if(tab_nom[i] != null && tab_score[i] != 0){
        lines[i] = tab_nom[i]+'\t'+ tab_score[i];
      }
      else{
        lines[i] = "---";
      }
    }
    saveStrings("data/highScore.txt", lines);
    
  }
  else{
    for (int i = 0; i < 5; i++) {
      if(i == 0){
        lines[i] = monNom+'\t'+score;
      }
      else{
        lines[i] = "---";
      }
    }
    saveStrings("data/highScore.txt", lines);
  }
  
  state = stateMenu;
}
