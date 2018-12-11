
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

  void afficher() {
    imageMode(CENTER);
    image(asteroid, location.x, location.y);
    smooth();
  }

  void deplacer() {
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

  void afficher() {
    fill(#00ff00);
    rectMode(CENTER);
    rect(location.x,location.y,largueur,hauteur);
    smooth();
  }

  void deplacer() {
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

  void afficher() {
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

  void afficher() {
    imageMode(CENTER);
    image(bonus, location.x, location.y);
    smooth();
  }
  
  void deplacer() {
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

  void afficher() {
    imageMode(CENTER);
    image(spaceship, location.x, location.y);
    smooth();
  }

  void deplacer() {
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
    vitesse_loc= new PVector(vitesse*(int(isRight) - int(isLeft)),vitesse*(int(isDown)  - int(isUp)));
    location.add(vitesse_loc);
  }
  
  void fire(){
    if (isFire == true && nb_ammos > 0) {
      if (intervalometre_tire.verifierIntervalle()) {
        vitesse_laser = new PVector(0, 15);
        lasers.add(new Laser(location.x, location.y-rayon,vitesse_laser));
        nb_lasers++;
        nb_ammos--;
      }
    }
  }
 
  boolean setMove(int k, boolean b) {
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
