void Reset(){  
  
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
  vitesse_min = 1.9;
  vitesse_max = 6.5;
  
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
