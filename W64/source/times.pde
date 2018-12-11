// ====== Intervalles ================================================================

class Intervalometre {
  int intervalle;
  int dernier_tic;

  Intervalometre(int intervalle_initial) {
    intervalle = intervalle_initial;
    dernier_tic = millis();
  }

  boolean verifierIntervalle() {
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

  boolean verifierIntervalle() {
    if (millis() > dernier_tic + intervalle) {
      dernier_tic = millis();
      return true;
    } else {
      return false;
    }
  }
}
