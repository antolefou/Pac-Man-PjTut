package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.image.Image;
import pacman.controller.ControllerJouer;
import pacman.model.Map;
import pacman.model.deplacement.Deplacement;

import java.io.IOException;

public class Pacman extends Deplacement {
    private ControllerJouer controllerJouer;
    public boolean enVie = true;
    public int numNiveau = 1;
    // Score de pacman
    public int score = 0;
    // Nombre de vie de pacman
    public int nbVie = 5;
    // ------------ OBJET BONUS -------------
    // objet bonus: boost
    private boolean powerBoost;
    private long debutPowerBoost;
    // objet bonus: super pac-gomme
    private boolean powerSuperPacGomme;
    private long debutSuperPacGomme;

    public deplacements deplacementFutur = deplacements.AUCUN;


    public Pacman() {
        super(241, 321);
        this.setImage(new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanUp.gif")));
        this.initialisation();
        this.getChildren().add(getImageView());
    }


    boolean peutAvancerVerticalement(int i) { //haut -> -1 bas -> 1
        double pacmanX = this.getPosX();
        double pacmanY = this.getPosY();
        if (pacmanX % 20 == 1) {
            if ((pacmanY % 20 != 1) || ((map.grid[((int) pacmanX / 20) % 25][(((int) pacmanY / 20) + i) % 30] != Map.ValeurCase.MUR) && (map.grid[((int) pacmanX / 20)][((int) pacmanY / 20) + i] != Map.ValeurCase.INTERDIT))) {
                return true;
            }
        }
        return false;
    }

    boolean peutAvancerHorizontalement(int i) {
        double pacmanX = this.getPosX();
        double pacmanY = this.getPosY();
        if (pacmanY % 20 == 1) {
//            System.out.println("x " + ((((int)pacmanX/20)+i)+24)%24);
//            System.out.println("y " + ((int)pacmanY/20));
            if (((pacmanX % 20 != 1) || (map.grid[((((int) pacmanX / 20) + i) + 25) % 25][((int) pacmanY / 20)] != Map.ValeurCase.MUR))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void affichage() {
        super.affichage();
        switch (deplacementActuel) {
            case HAUT:
                this.getImageView().setRotate(0);
                break;
            case DROITE:
                this.getImageView().setRotate(90);
                break;
            case BAS:
                this.getImageView().setRotate(180);
                break;
            case GAUCHE:
                this.getImageView().setRotate(-90);
                break;
        }
    }

    public void updateDeplacement() {
        boolean peutAvancer = false;
        if (deplacementFutur != Deplacement.deplacements.AUCUN) {
            switch (deplacementFutur) {
                case HAUT:
                    if (peutAvancerVerticalement(-1)) {
                        avanceHaut();
                        peutAvancer = true;
                    }
                    break;
                case DROITE:
                    if (peutAvancerHorizontalement(1)) {
                        avanceDroite();
                        peutAvancer = true;
                    }
                    break;
                case BAS:
                    if (peutAvancerVerticalement(1)) {
                        avanceBas();
                        peutAvancer = true;
                    }
                    break;
                case GAUCHE:
                    if (peutAvancerHorizontalement(-1)) {
                        avanceGauche();
                        peutAvancer = true;
                    }
                    break;
            }
        }
        if (peutAvancer) {
            deplacementActuel = deplacementFutur;
            deplacementFutur = deplacements.AUCUN;
        } else {
            switch (deplacementActuel) {
                case HAUT:
                    if (peutAvancerVerticalement(-1))
                        avanceHaut();
                    break;
                case DROITE:
                    if (peutAvancerHorizontalement(1))
                        avanceDroite();
                    break;
                case BAS:
                    if (peutAvancerVerticalement(1))
                        avanceBas();
                    break;
                case GAUCHE:
                    if (peutAvancerHorizontalement(-1))
                        avanceGauche();
                    break;
            }
        }
    }

    public void updateMapPacman() {
        // ---------------------------- interractions de pacman ------------------------
        int pacmanX = this.getPosX();
        int pacmanY = this.getPosY();
        int comparaisonX = 1;
        int addX = 0;
        int addY = 0;
        int comparaisonY = 1;
        switch (deplacementActuel) {
            case HAUT:
                comparaisonY = 7;
                break;
            case DROITE:
                comparaisonX = 15;
                addX = 1;
                break;
            case BAS:
                comparaisonY = 15;
                addY = 1;
                break;
            case GAUCHE:
                comparaisonX = 7;
                break;
        }
        if (pacmanX % 20 == comparaisonX && pacmanY % 20 == comparaisonY) {
            int x = ((((int) pacmanX / 20) + 25) % 25 + addX)%25;
            int y = (int) pacmanY / 20 + addY;
            if (map.grid[x][y] == Map.ValeurCase.GOMME) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                map.caseMap[x][y].setImage(null);
                score += 10;
            } else if (map.grid[x][y] == Map.ValeurCase.SUPERGOMME) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                map.caseMap[x][y].setImage(null);

                /*
                Platform.runLater(()->{
                    try {
                        controllerJouer.viePac();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                 */

                score += 50;
                this.initSuperPacGomme();
            } else if (map.grid[x][y] == Map.ValeurCase.BOOST) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                map.caseMap[x][y].setImage(null);
                score += 100;
                this.initPowerBoost();
            }else if (map.grid[x][y] == Map.ValeurCase.VIDE) {
                // ne fait rien...
            } else {
                System.out.println("Execption dans l'interraction de pacman");
            }
        }
        this.stopPower();
    }

    // ---------------------  POUVOIR DE PACMAN   ------------------------------------------
    /**
     * Initialise pacman avec les attributs de l'objet bonus: boost
     */
    public void initPowerBoost() {
        this.velocityMultiplicator = 4;
        debutPowerBoost = System.currentTimeMillis();
        powerBoost = true;
    }
    /**
     * Initialise pacman avec les attributs de l'objet bonus: super pac-gomme
     */
    public void initSuperPacGomme() {
        debutSuperPacGomme = System.currentTimeMillis();
        powerSuperPacGomme = true;
    }

    /**
     * Stoppe tous les objets bonus qui ont atteinds le temps d'effet
     */
    public void stopPower() {
        if (powerBoost) {
            if (System.currentTimeMillis()-debutPowerBoost > 1000 * 5) {  // durée 5 sec
                velocityMultiplicator = 2;
                powerBoost = false;
            }
        } else if (powerSuperPacGomme) {
            if (System.currentTimeMillis()-debutSuperPacGomme > 1000 * 10) {  // durée 10 sec
                powerSuperPacGomme = false;
            }
        }
    }

    public void setControllerJouer(ControllerJouer controllerJouer) {
        this.controllerJouer = controllerJouer;
    }
}
