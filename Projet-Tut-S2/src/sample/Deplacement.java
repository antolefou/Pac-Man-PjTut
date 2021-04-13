package sample;

public class Deplacement extends Thread {
    private boolean enGame;
    Map map;
    Pacman pacman;
    public enum Deplacements { AUCUN, HAUT, DROITE, BAS, GAUCHE}
    public Deplacements deplacementActuel;
    public Deplacements deplacementFutur;


    public Deplacement(Map map, Pacman pacman) {
        this.map = map;
        this.pacman = pacman;
        enGame = true;
        deplacementActuel = Deplacements.DROITE;
        deplacementFutur = Deplacements.AUCUN;
    }

    public void run() {
        boolean peutAvancer;
        while (enGame) {
            peutAvancer = false;
            try {
                if (deplacementFutur != Deplacements.AUCUN) {
                    switch (deplacementFutur) {
                        case HAUT:
                            if (peutAvancerVerticale(-1)) {
                                pacman.avanceHaut();
                                peutAvancer = true;
                            }
                            break;
                        case DROITE:
                            if (peutAvancerHorizontalement(1)) {
                                pacman.avanceDroite();
                                peutAvancer = true;
                            }
                            break;
                        case BAS:
                            if (peutAvancerVerticale(1)) {
                                pacman.avanceBas();
                                peutAvancer = true;
                            }
                            break;
                        case GAUCHE:
                            if (peutAvancerHorizontalement(-1)) {
                                pacman.avanceGauche();
                                peutAvancer = true;
                            }
                            break;
                    }
                }
                if (peutAvancer) {
                    deplacementActuel = deplacementFutur;
                    deplacementFutur = Deplacements.AUCUN;
                } else {
                    switch (deplacementActuel) {
                        case HAUT:
                            if (peutAvancerVerticale(-1))
                                pacman.avanceHaut();
                            break;
                        case DROITE:
                            if (peutAvancerHorizontalement(1))
                                pacman.avanceDroite();
                            break;
                        case BAS:
                            if (peutAvancerVerticale(1))
                                pacman.avanceBas();
                            break;
                        case GAUCHE:
                            if (peutAvancerHorizontalement(-1))
                                pacman.avanceGauche();
                            break;
                    }
                }
                sleep(pacman.velocityThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean peutAvancerVerticale(int i) {
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanX % 20 == 1) {
            if ((pacmanY % 20 != 1) || (map.grid[((int)pacmanY/20)+i][(int)pacmanX/20] != Map.ValeurCase.MUR)) {
                return true;
            }
        }
        return false;
    }
    private boolean peutAvancerHorizontalement(int i) {
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanY % 20 == 1) {
            if ((pacmanX % 20 != 1) || (map.grid[(int)pacmanY/20][((int)pacmanX/20)+i] != Map.ValeurCase.MUR)) {
                return true;
            }
        }
        return false;
    }
}
