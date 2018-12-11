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

public class Asteroide_V1_07 extends PApplet {

// Liste des touches : 
// https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes

// Liste des methodes PVector :
// https://processing.org/reference/PVector.html

// Version du jeu 
String version = "1.07";

// Variables globales

Intervalometre intervalometre;
Intervalometre_tire intervalometre_tire;

ArrayList<Asteroid> asteroids;
ArrayList<Laser> lasers;
ArrayList<Vie> vies;
ArrayList<popBonus> popbonus;

Vaisseaux v;
int bg ;
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

int nb_ast=1;
int temps_pop_ast = 10;
float vitesse_min = 1.9f;
float vitesse_max = 6.5f;


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
public void setup () {
  
  
  
  intervalometre = new Intervalometre(1000);
  intervalometre_tire = new Intervalometre_tire(200);
  
  asteroids = new ArrayList<Asteroid>();
  lasers = new ArrayList<Laser>();
  vies = new ArrayList<Vie>();
  popbonus = new ArrayList<popBonus>();
  
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
public void draw () {  
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

public void keyPressed() {
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
}
 
public void keyReleased() {
  v.setMove(keyCode, false);
}
public void Game() {
  smooth();
  background(bg);

  if (flag_start == true) {    
    vitesse_ast = new PVector(0, random(vitesse_min, vitesse_max));
    asteroids.add(new Asteroid(random(v.rayon, width-v.rayon), 1000, vitesse_ast));
    vitesse_laser = new PVector(0, 15);
    lasers.add(new Laser(-50, -50, vitesse_laser));
    popbonus.add(new popBonus(bonusHearth,650, 650, recharge_vies));
    flag_start=false;
  }


  if (intervalometre.verifierIntervalle()) {
    seconde = seconde + 1;
    if (seconde % temps_pop_ast == 0) {
      vitesse_ast = new PVector(0, random(vitesse_min, vitesse_max));
      asteroids.add(new Asteroid(random(v.rayon, width-v.rayon), 1000, vitesse_ast));
      nb_ast++;
      score = score + 1;
    }

    if (seconde == (last_pop_ammo + temps_pop_ammo)) {
      popbonus.add(new popBonus(bonusAmmo,random(v.rayon, width-v.rayon), -50, recharge_ammos));
      nb_bonus++;
      temps_pop_ammo = PApplet.parseInt(random(15, 30));
      last_pop_ammo = seconde;
    }

    if (seconde == (last_pop_vie + temps_pop_vie)) {
      popbonus.add(new popBonus(bonusHearth,random(v.rayon, width-v.rayon), -50, recharge_vies));
      nb_bonus++;
      temps_pop_vie = PApplet.parseInt(random(20, 50));
      last_pop_vie = seconde;
    }
  }

  for (i=0; i<nb_lasers; i++) {
    Laser Laser = lasers.get(i);

    if (Laser.location.y < -10) {
      lasers.remove(i);
      nb_lasers--;
    }

    Laser.afficher();
    Laser.deplacer();
  }


  for (i=0; i<nb_ast; i++) {
    vitesse_ast = new PVector(0, random(vitesse_min, vitesse_max));
    asteroids.add(new Asteroid(random(v.rayon, width-v.rayon), -150, vitesse_ast));

    Asteroid Asteroid = asteroids.get(i);

    Asteroid.deplacer();
    Asteroid.afficher();


    if (Asteroid.location.y > 650) {
      asteroids.remove(i);
    }

    float ast_col  = sqrt((v.location.x-Asteroid.location.x)*(v.location.x-Asteroid.location.x)+(v.location.y-Asteroid.location.y)*(v.location.y-Asteroid.location.y));
    float v_ast_ray = v.rayon+Asteroid.rayon;

    if ( ast_col < v_ast_ray) {
      flag_toucher = true;
      asteroids.remove(i);
    }
    
    
    
    for (j=0; j<nb_lasers; j++) {
      Laser Laser = lasers.get(j);

      float ast_y = Asteroid.location.y;
      float ast_x = Asteroid.location.x;
      float laser_x = Laser.location.x;
      float laser_y = Laser.location.y;
      float laser_col = sqrt((laser_x-ast_x)*(laser_x-ast_x)+(laser_y-ast_y)*(laser_y-ast_y));

      if (laser_col < Asteroid.rayon) {
        asteroids.remove(i);
        lasers.remove(j);
        nb_lasers--;
        score = score + 1;
      }
    }
    
    
  }


  if (flag_toucher == true) {
    bg = color(0xffff0000);
    flag_vies = true;
  } else {
    bg = color(0xff000000);
  }

  flag_toucher = false;

  if (flag_vies == true) {
    if (v.vies > 0) {
      v.vies = v.vies - 1;
    }
    flag_vies = false;
  }
  
  
  
  
  
  for (i=0; i<nb_bonus; i++) {
    popBonus popBonus = popbonus.get(i);

    if (popBonus.location.y > 650) {
      popbonus.remove(i);
      nb_bonus--;
    }

    popBonus.afficher();
    popBonus.deplacer();

    float popbonus_col  = sqrt((v.location.x-popBonus.location.x)*(v.location.x-popBonus.location.x)+(v.location.y-popBonus.location.y)*(v.location.y-popBonus.location.y));
    float v_popbonus_ray = v.rayon+popBonus.rayon;

    if ( popbonus_col < v_popbonus_ray) {
      popbonus.remove(i);
      
      if(popBonus.bonus == bonusHearth){
        if (v.vies < nb_max_vies) {
          v.vies += popBonus.recharge;
        } else {
          score = score + 10;
        }
      }
      
      if(popBonus.bonus == bonusAmmo){
        nb_ammos += popBonus.recharge;
      }
      
      nb_bonus--;
    }
  }
  

  v.deplacer();
  v.fire();
  v.afficher();    

  if (v.vies > 0) {
    for (i=0; i<v.vies; i++) {
      Vie Vie = vies.get(i);
      Vie.afficher();
    }
  } else {
    state = stateGameOver;
  }

  imageMode(CENTER);
  image(ammo, 30, 55);

  textSize(12);
  fill(0xffffffff);
  textAlign(LEFT);
  text("X"+nb_ammos, 45, 60);

  textSize(21);
  fill(0xffffffff);
  text("Score : "+score, 20, 580);

  textSize(12);
  fill(0xffffffff);
  textAlign(RIGHT);
  text("Temps : "+seconde+" sec", 785, 580);
}
public void GameOver(){
   bg = color(0xff000000);
   background(bg);
   textSize(72);
   fill(0xffffffff);
   textAlign(CENTER);
   text("Vous avez perdu !", 400, 200);
   
   textSize(32);
   fill(0xffffffff);
   textAlign(CENTER);
   text("Votre score : "+score, 400, 350);
   
   flag_nom = true;
   if(flag_nom == true){
     textSize(32);
     fill(0xffffffff);
     textAlign(CENTER);
     text("Votre nom : "+monNom, 400, 400);
   }
  
   textSize(40);
   fill(0xffffffff);
   textAlign(CENTER);
   text("ENTER", 400, 480);
   
}

// ====== Aide (Game)  ================================================================

public void Help(){
  bg = color(0xff000000);
  background(bg);
  
  textSize(64);
  fill(0xffffffff);
  textAlign(CENTER);
  text("AIDE", 400, 100);
  
  textSize(32);
  fill(0xffffffff);
  textAlign(CENTER);
  text("Déplacement :", 400, 300);
  text("Flèches directionnelles ou ZQSD", 400, 350);
  text("P : Pause", 400, 400);
    
  textSize(24);
  fill(0xffffffff);
  textAlign(CENTER);
  text("ENTER", 400, 550);
}

// ====== Commandes(Menu) ================================================================

public void Commands(){
  bg = color(0xff000000);
  background(bg);
  
  textSize(64);
  fill(0xffffffff);
  textAlign(CENTER);
  text("COMMANDES", 400, 100);
  
  textSize(32);
  fill(0xffffffff);
  textAlign(CENTER);
  text("Déplacement :", 400, 250);
  text("Flèches directionnelles ou ZQSD", 400, 300);
  text("P : Pause", 400, 350);
  text("F1 : Aide", 400, 400);
    
  textSize(24);
  fill(0xffffffff);
  textAlign(CENTER);
  text("ENTER", 400, 550);
}
public void HighScore(){
  bg = color(0xff000000);
  background(bg);
  String[] file;
  String[] contenu = new String[2];
  String texte = "";
  String[] lines = new String[5];
  file = loadStrings("data/highScore.txt");
  
  
  
  textSize(64);
  fill(0xffffffff);
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
        fill(0xffffffff);
        textAlign(CENTER);
        text(""+texte, 400, 225+(i*50));
    }
    
    textSize(24);
    fill(0xffffffff);
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
public void Menu(){
  background(bg);
  
  flag_nom = false;
  
  textSize(72);
  fill(0xffffffff);
  textAlign(CENTER);
  text("Asteroid", 400, 200);
  
  textSize(24);
  fill(0xffffffff);
  textAlign(LEFT);
  text("1 -> Jouer", 320, 300);
  
  textSize(24);
  fill(0xffffffff);
  textAlign(LEFT);
  text("2 -> Scores", 320, 350);
  
  textSize(24);
  fill(0xffffffff);
  textAlign(LEFT);
  text("3 -> Commandes", 320, 400);
  
  textSize(24);
  fill(0xffffffff);
  textAlign(LEFT);
  text("4 -> Quitter", 320, 450);
  
  textSize(10);
  fill(0xffffffff);
  textAlign(RIGHT);
  text("Version "+version, 785, 20);
   
}

// ====== Asteroides ================================================================

class Asteroid {
  PVector location;
  float rayon;
  PVector vitesse;
  
  Asteroid(float temp_x, float temp_y, PVector temp_vitesse) {
    location = new PVector(temp_x, temp_y);
    rayon=23;
    vitesse= temp_vitesse;
  }

  public void afficher() {
    imageMode(CENTER);
    image(asteroid, location.x, location.y);
    smooth();
  }

  public void deplacer() {
    location.add(vitesse);
  }
} 

// ====== Laser ================================================================

class Laser {
  PVector location;
  int hauteur;
  float largueur;
  PVector vitesse;

  Laser(float temp_x, float temp_y, PVector temp_vitesse) {
    location = new PVector(temp_x, temp_y);
    hauteur=15;
    largueur=2;
    vitesse= temp_vitesse;
  }

  public void afficher() {
    fill(0xff00ff00);
    rectMode(CENTER);
    rect(location.x,location.y,largueur,hauteur);
    smooth();
  }

  public void deplacer() {
    location.sub(vitesse);
  }  
} 

// ====== Vies ================================================================

class Vie {
  int x ;
  int y ;
  int margin;

  Vie(int temp_x, int temp_y, int temp_margin) {
    x=temp_x;
    y=temp_y;
    margin=temp_margin;
  }

  public void afficher() {
    imageMode(CENTER);
    image(hearth, (x+margin), y);
    smooth();
  }
} 

// ====== Apparition des bonus ================================================================

class popBonus {
  PVector location;
  float rayon;
  PVector vitesse;
  int recharge;
  PImage bonus;

  popBonus(PImage temp_image, float temp_x, float temp_y, int temp_recharge) {
    bonus = temp_image;
    location = new PVector(temp_x, temp_y);
    
    vitesse= new PVector(0, 2);
    recharge = temp_recharge;
    
    
    rayon=23;
  }

  public void afficher() {
    imageMode(CENTER);
    image(bonus, location.x, location.y);
    smooth();
  }
  
  public void deplacer() {
    location.add(vitesse);
  }
  
} 


// ====== Vaisseau ================================================================

class Vaisseaux {
  PVector location;
  int rayon;
  int vitesse;
  PVector vitesse_loc;
  int vies;
  int ammos;
  boolean isLeft, isRight, isUp, isDown, isFire;
  int x;
  int y;

  Vaisseaux(float temp_x, float temp_y) {
    location = new PVector(temp_x, temp_y);
    rayon=26;
    vitesse = 6;
  }

  public void afficher() {
    imageMode(CENTER);
    image(spaceship, location.x, location.y);
    smooth();
  }

  public void deplacer() {
    if(location.x <= rayon+6){
      isLeft = false;
    }
    if(location.x >= width-(rayon+6)){
      isRight = false;
    }
    if(location.y <= rayon+6){
      isUp= false;
    }
    if(location.y >= height-(rayon+6)){
      isDown = false;
    }
    vitesse_loc= new PVector(vitesse*(PApplet.parseInt(isRight) - PApplet.parseInt(isLeft)),vitesse*(PApplet.parseInt(isDown)  - PApplet.parseInt(isUp)));
    location.add(vitesse_loc);
  }
  
  public void fire(){
    if (isFire == true && nb_ammos > 0) {
      if (intervalometre_tire.verifierIntervalle()) {
        vitesse_laser = new PVector(0, 15);
        lasers.add(new Laser(location.x, location.y-rayon,vitesse_laser));
        nb_lasers++;
        nb_ammos--;
      }
    }
  }
 
  public boolean setMove(int k, boolean b) {
    switch (k) {
    case 'Z':
    case UP:
      return isUp = b;
 
    case 'S':
    case DOWN:
      return isDown = b;
 
    case 'Q':
    case LEFT:
      return isLeft = b;
 
    case 'D':
    case RIGHT:
      return isRight = b;    
      
    case ' ':
      return isFire = b;
 
    default:
      return b;
    }
  }
} 
public void Pause(){
   textSize(72);
   fill(0xffffffff);
   textAlign(CENTER);
   text("Pause", 400, 250);
   
   textSize(36);
   fill(0xffffffff);
   textAlign(CENTER);
   text("P : Continuer", 400, 300);
   
   textSize(36);
   fill(0xffffffff);
   textAlign(CENTER);
   text("M : Menu", 400, 350);
}
public void Reset(){  
  
  // ====== Variables ================================================================
  
  intervalometre = new Intervalometre(1000);
  intervalometre_tire = new Intervalometre_tire(200);
  
  asteroids = new ArrayList<Asteroid>();
  lasers = new ArrayList<Laser>();
  vies = new ArrayList<Vie>();
  popbonus = new ArrayList<popBonus>();
 
  i = 0;
  j = 0;
  temp_i = 0;
  
  flag_vies = false;
  flag_toucher = false;
  flag_start = true;
  flag_nom = false;
  
  score = 0;
  
  seconde = 0;
  
  nb_ast=1;
  temps_pop_ast = 10;
  vitesse_min = 1.9f;
  vitesse_max = 6.5f;
  
  nb_bonus = 1;
  
  last_pop_ammo = 0;
  temps_pop_ammo = 30;
  nb_ammos = 50;
  recharge_ammos = 30;
  
  nb_max_vies=3;
  last_pop_vie = 0;
  temps_pop_vie = 60;
  
  nb_lasers=1;
  
  monNom = "VOTRE NOM";
  
  // ====== Initialisation ================================================================
  
  v = new Vaisseaux(400, 300);
  v.vies = nb_max_vies;
  v.ammos = 0;
  
  for(i=0;i<v.vies;i++){
    vies.add(new Vie((i*25),30,(i+1*25)));
  }
  
  state = stateGame;
  
}
public void SaveHS(){
  
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
        tab_score[i] = PApplet.parseInt(contenu[1]);
      }
      else{
        tab_nom[i] = null;
        tab_score[i] = 0;
      }
    }
    
    // Tab Order
    for(int k = 0; k < tab_score.length; k++){
      if(score > PApplet.parseInt(tab_score[k]) && flag_tab == true){
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
// ====== Intervalles ================================================================

class Intervalometre {
  int intervalle;
  int dernier_tic;

  Intervalometre(int intervalle_initial) {
    intervalle = intervalle_initial;
    dernier_tic = millis();
  }

  public boolean verifierIntervalle() {
    if (millis() > dernier_tic + intervalle) {
      dernier_tic = millis();
      return true;
    } else {
      return false;
    }
  }
}

class Intervalometre_tire {
  int intervalle;
  int dernier_tic;

  Intervalometre_tire(int intervalle_initial) {
    intervalle = intervalle_initial;
    dernier_tic = millis();
  }

  public boolean verifierIntervalle() {
    if (millis() > dernier_tic + intervalle) {
      dernier_tic = millis();
      return true;
    } else {
      return false;
    }
  }
}
  public void settings() {  size(800, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Asteroide_V1_07" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
