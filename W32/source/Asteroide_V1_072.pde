// Liste des touches : 
// https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes

// Liste des methodes PVector :
// https://processing.org/reference/PVector.html

// A regarder :
// https://www.openprocessing.org/sketch/106239/


// Version du jeu 
String version = "1.072";

// Variables globales

Intervalometre intervalometre;
Intervalometre_tire intervalometre_tire;

ArrayList<Asteroid> asteroids;
ArrayList<Laser> lasers;
ArrayList<Vie> vies;
ArrayList<popBonus> popbonus;
ArrayList<Etoile> etoiles;


Vaisseaux v;
color bg ;
PImage spaceship;
PImage asteroid;
PImage hearth;
PImage ammo;
PImage bonusHearth;
PImage bonusAmmo;

int i = 0;
int j = 0;
int temp_i = 0;
boolean flag_vies = false;
boolean flag_toucher = false;
boolean flag_start = true;
boolean flag_nom = false;

int score = 0;

int seconde = 0;

int nb_Etoiles=30;
PVector vitesse_etoile;

int nb_ast=1;
int temps_pop_ast = 10;
float vitesse_min = 1.9;
float vitesse_max = 6.5;


int nb_bonus=1;

int last_pop_ammo = 0;
int temps_pop_ammo = 30;
int nb_ammos = 50;
int recharge_ammos = 30;

int nb_max_vies=3;
int recharge_vies=1;
int last_pop_vie = 0;
int temps_pop_vie = 60;

int nb_lasers=1;

PVector vitesse_ast;
PVector vitesse_laser;
PVector vitesse_bonus;

String monNom = "VOTRE NOM";

final int stateMenu = 0;
final int stateGame = 1;
final int stateHightScore = 2;
final int stateGameOver = 3;
final int stateReset = 4;
final int statePause = 5;
final int stateSaveHS = 6;
final int stateHighScore = 7;
final int stateCommands = 8;
final int stateHelp = 9;

int state = stateMenu;

// ====== Setup ================================================================
void setup () {
  size(800, 600);
  frameRate(60);
  loop();
  smooth();
  
  intervalometre = new Intervalometre(1000);
  intervalometre_tire = new Intervalometre_tire(200);
  
  asteroids = new ArrayList<Asteroid>();
  lasers = new ArrayList<Laser>();
  vies = new ArrayList<Vie>();
  popbonus = new ArrayList<popBonus>();
  etoiles = new ArrayList<Etoile>();
  
  String path = "data/img/";

  spaceship=loadImage(path+"SpaceShip.png");
  asteroid=loadImage(path+"asteroid.png");
  hearth=loadImage(path+"hearth.png");
  ammo=loadImage(path+"ammo.png");
  bonusHearth=loadImage(path+"bonusHearth.png");
  bonusAmmo=loadImage(path+"bonusAmmo.png");
  
  v = new Vaisseaux(400, 300);
  v.vies = nb_max_vies;
  v.ammos = 0;
  
  for(i=0;i<v.vies;i++){
    vies.add(new Vie((i*25),30,(i+1*25)));
  }
}

// ====== Draw ================================================================
void draw () {  
  // states: 
  switch(state) {
    case stateGame:
      Game();
    break;
      
    case stateMenu:
      Menu();
    break; 
      
    case stateHighScore:
      HighScore();
    break;  
    
    case stateGameOver:
       GameOver();
    break;  
    
    case stateReset:
       Reset();
    break;  
    
    case statePause:
       Pause();
    break;  
    
    case stateSaveHS:
       SaveHS();
    break;  
    
    case stateCommands:
       Commands();
    break; 
    
    case stateHelp:
       Help();
    break; 
      
    default:
      // error 
    break;
  } // switch

}

// ====== Keys ================================================================

void keyPressed() {
  v.setMove(keyCode, true);
  
  switch(state) {
    case stateGame:
      //     
      if (keyCode == 116) { // F5
        state = stateReset;
      }
      if (keyCode == 80) { // p
        state = statePause;
      }
      
      if (keyCode == 112) { // F1
        state = stateHelp;
      }
      break;
      
    case stateMenu:
      // read key in menu
      switch (key) {
        case '1' :
          state = stateReset;
        break;
          
        case '2' :
          state = stateHighScore;
        break;
        
        case '3' :
          state = stateCommands;
        break;
          
        case '4' :
          exit();
        break;
          
        default :
          //println ("unknown input");
        break;
      } // inner switch  
      break; 
      
      case stateGameOver:
        switch (keyCode) {
          case 13 : // enter 
            state = stateMenu;
          break;
          
          default :
            //println ("unknown input");
          break;
        } // inner switch  
       break;
       
       case stateHighScore:
        switch (key) {
          case ENTER : // enter
            state = stateMenu;
          break;
          
          default :
            //println ("unknown input");
          break;
        } // inner switch  
       break;
       
       case stateCommands:
        switch (key) {
          case ENTER : // enter
            state = stateMenu;
          break;
          
          default :
            //println ("unknown input");
          break;
        } // inner switch  
       break;
       
       
       case stateHelp:
        switch (key) {
          case ENTER : // enter
            state = stateGame;
          break;
          
          default :
            //println ("unknown input");
          break;
        } // inner switch  
       break;
       
       case statePause:
        switch (keyCode) {
          case 80 : // p 
            state = stateGame;
          break;
          
          case 116 : // f5
            state = stateReset;
          break;
          
          case 77 : // m
            state = stateMenu;
          break;
          
          default :
            //println ("unknown input");
          break;
        } // inner switch  
       break;
      
    default:
      // error 
    break;
  } // switch
  //
  
  if(flag_nom == true){
    if (keyCode == BACKSPACE) {
      if (monNom.length() > 0) {
        monNom = monNom.substring(0, monNom.length()-1);
      }
    }
    else if (keyCode != SHIFT && keyCode != CONTROL && keyCode != ALT) {
      if(monNom == "VOTRE NOM"){
        monNom = "";
        monNom = monNom + key;
      }
      else{
        monNom = monNom + key;
      }
    }
    if (keyCode == ENTER) {
      monNom = monNom.substring(0, monNom.length()-1);
      state = stateSaveHS;
    }
  }
  
  if (keyCode == 123) {
      screenShot();
    }
}
 
void keyReleased() {
  v.setMove(keyCode, false);
}
