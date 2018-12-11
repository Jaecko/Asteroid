void Game() {
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
      temps_pop_ammo = int(random(15, 30));
      last_pop_ammo = seconde;
    }

    if (seconde == (last_pop_vie + temps_pop_vie)) {
      popbonus.add(new popBonus(bonusHearth,random(v.rayon, width-v.rayon), -50, recharge_vies));
      nb_bonus++;
      temps_pop_vie = int(random(20, 50));
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
    bg = color(#ff0000);
    flag_vies = true;
  } else {
    bg = color(#000000);
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
  fill(#ffffff);
  textAlign(LEFT);
  text("X"+nb_ammos, 45, 60);

  textSize(21);
  fill(#ffffff);
  text("Score : "+score, 20, 580);

  textSize(12);
  fill(#ffffff);
  textAlign(RIGHT);
  text("Temps : "+seconde+" sec", 785, 580);
}
