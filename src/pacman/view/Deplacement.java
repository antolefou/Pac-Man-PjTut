package pacman.view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pacman.controller.Controller;
import pacman.controller.ControllerJouer;


public class Deplacement extends Thread {
    private boolean enGame;
    Map map;
    Pacman pacman;
    //Fantome fantome1;
    //Fantome fantome2;
    ControllerJouer controller;
    Thread scoreThread;
    Label scoreLabel;
    public enum Deplacements { AUCUN, HAUT, DROITE, BAS, GAUCHE}
    public Deplacements deplacementActuel;
    public Deplacements deplacementFutur;


    public Deplacement(Map map, Pacman pacman, ControllerJouer controller) {
        this.map = map;
        this.pacman = pacman;
        this.controller = controller;
        enGame = true;
        deplacementActuel = Deplacements.DROITE;
        deplacementFutur = Deplacements.AUCUN;
    }

    public void run() {
        boolean peutAvancer;
        while (pacman.enVie) {
            updateScore();
            if (map.aGagne()) {
                map.recommence();
                pacman.initPosition();
            }
            peutAvancer = false;
            try {
                if (deplacementFutur != Deplacements.AUCUN) {
                    switch (deplacementFutur) {
                        case HAUT:
                            if (peutAvancerVerticalement(-1)) {
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
                            if (peutAvancerVerticalement(1)) {
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
                            if (peutAvancerVerticalement(-1))
                                pacman.avanceHaut();
                            break;
                        case DROITE:
                            if (peutAvancerHorizontalement(1))
                                pacman.avanceDroite();
                            break;
                        case BAS:
                            if (peutAvancerVerticalement(1))
                                pacman.avanceBas();
                            break;
                        case GAUCHE:
                            if (peutAvancerHorizontalement(-1))
                                pacman.avanceGauche();
                            break;
                    }
                }
                double pacmanX = pacman.getPacmanX();
                double pacmanY = pacman.getPacmanY();


                // ---------------------------- interractions de pacman ------------------------
                if (pacmanX % 20 == 1 && pacmanY%20 == 1) {
                    if (map.grid[(((int)pacmanX/20)+25)%25][(int)pacmanY/20] == Map.ValeurCase.GOMME) {
                        map.grid[(((int)pacmanX/20)+25)%25][(int)pacmanY/20] = Map.ValeurCase.VIDE;
                        map.caseMap[(((int)pacmanX/20)+25)%25][(int)pacmanY/20].setImage(map.imageFond);
                        pacman.score += 10;
                    } else if (map.grid[(((int)pacmanX/20)+25)%25][(int)pacmanY/20] == Map.ValeurCase.SUPERGOMME) {
                        map.grid[(((int)pacmanX/20)+25)%25][(int)pacmanY/20] = Map.ValeurCase.VIDE;
                        map.caseMap[(((int)pacmanX/20)+25)%25][(int)pacmanY/20].setImage(map.imageFond);
                        pacman.score += 100;
                        pacman.initSuperPacGomme();
                        // il faudra réduire la vitesse des fantomes et les mettre mangeables
                    } else if (map.grid[(((int)pacmanX/20)+25)%25][(int)pacmanY/20] == Map.ValeurCase.BOOST) {
                        map.grid[(((int)pacmanX/20)+25)%25][(int)pacmanY/20] = Map.ValeurCase.VIDE;
                        map.caseMap[(((int)pacmanX/20)+25)%25][(int)pacmanY/20].setImage(map.imageFond);
                        pacman.score += 200;
                        pacman.initPowerBoost();
                    }
                }
                pacman.stopPower();
                sleep(pacman.sleepThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean peutAvancerVerticalement(int i) { //haut -> -1 bas -> 1
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanX % 20 == 1) {
            if ((pacmanY % 20 != 1) || ((map.grid[((int)pacmanX/20)%25][(((int)pacmanY/20)+i)%30] != Map.ValeurCase.MUR) && (map.grid[((int)pacmanX/20)][((int)pacmanY/20)+i] != Map.ValeurCase.INTERDIT))) {
                return true;
            }
        }
        return false;
    }
    private boolean peutAvancerHorizontalement(int i) {
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanY % 20 == 1) {
            if (((pacmanX % 20 != 1) || (map.grid[((((int)pacmanX/20)+i)+24)%24][((int)pacmanY/20)] != Map.ValeurCase.MUR))) {
                return true;
            }
        }
        return false;
    }

    public void updateScore(){
        scoreThread = new Thread(()->{
            Platform.runLater(()->{
                controller.affichageScore.setText(String.valueOf(pacman.score));
            });
        });
        scoreThread.start();
    }

}
