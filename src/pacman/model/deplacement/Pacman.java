package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.image.Image;
import pacman.controller.ControllerJouer;
import pacman.model.Map;

import java.util.Objects;

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

    public boolean competenceADeverouillee;
    public boolean competenceBDeverouillee;
    public boolean competenceCDeverouillee;

    public boolean competenceAPrete;
    public boolean competenceBPrete;
    public boolean competenceCPrete;

    public Teleporteur teleporteur;
    public boolean teleporteurPose;

    public Deplacement projectile;
    public boolean projectileLance;

    public boolean freeze;
    public double tempsDebutFreeze;
    private double projectileRotate;

    public Pacman() {
        super(241, 321);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanUp.gif")));

        this.competenceADeverouillee = false;
        this.competenceBDeverouillee = false;
        this.competenceCDeverouillee = false;
        this.competenceAPrete = true;
        this.competenceBPrete = true;
        this.competenceCPrete = true;

        this.teleporteurPose = false;
        this.freeze = false;
        tempsDebutFreeze = 0;

        this.projectileLance = false;

        this.initialisation();
        Platform.runLater(() -> this.getChildren().add(getImageView()));
    }

    @Override
    public void initPosition() {
        super.initPosition();
        deplacementFutur = deplacements.AUCUN;
        deplacementActuel = deplacements.AUCUN;
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
            int x = (((pacmanX / 20) + 25) % 25 + addX)%25;
            int y = pacmanY / 20 + addY;
            if (map.grid[x][y] == Map.ValeurCase.GOMME) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 10;
            } else if (map.grid[x][y] == Map.ValeurCase.SUPERGOMME) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 50;
                this.initSuperPacGomme();
            } else if (map.grid[x][y] == Map.ValeurCase.BOOST) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 50;
                this.initPowerBoost();
            }else if (map.grid[x][y] == Map.ValeurCase.VIDE) {
                // ne fait rien...
            }else if (map.grid[x][y] == Map.ValeurCase.CERISE) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 100;
            }else if (map.grid[x][y] == Map.ValeurCase.FRAISE) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 300;
            }else if (map.grid[x][y] == Map.ValeurCase.ORANGE) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 500;
            }else if (map.grid[x][y] == Map.ValeurCase.POMME) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 700;
            }else if (map.grid[x][y] == Map.ValeurCase.MELON) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 1000;
            }else if (map.grid[x][y] == Map.ValeurCase.VAISSEAU) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 2000;
            }else if (map.grid[x][y] == Map.ValeurCase.CLOCHE) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 3000;
            }else if (map.grid[x][y] == Map.ValeurCase.CLEF) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
                score += 5000;
            }else if (map.grid[x][y] == null) {
                map.grid[x][y] = Map.ValeurCase.VIDE;
            }else if (map.grid[x][y] == Map.ValeurCase.TELEPORTEUR) {
                //
            } else {
                System.out.println("Execption dans l'interraction de pacman");
            }
        }
        this.stopPower();
    }

    // ---------------------  POUVOIRS DE PACMAN   ------------------------------------------
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
        controllerJouer.fantomeGroup.setVulnerable();
    }

    public void competenceTeleportation() {
        if (!teleporteurPose) {
            teleporteurPose = true;
            this.teleporteur = new Teleporteur(this, map);
            teleporteur.poseTeleporteur(getPosX(), getPosY());
        }
        else {
            teleporteur.teleporte();
        }
    }

    public void competenceFreeze() {
        this.freeze = true;
        controllerJouer.fantomeGroup.freezeFantomes();
        tempsDebutFreeze = System.currentTimeMillis();
    }

    public void competenceProjectile() {
        Image imageProjectile= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/projectile.gif")));

        projectileLance = true;
        projectile = new Deplacement(getPosX(), getPosY());
        projectile.setImage(imageProjectile);
        projectile.initialisation();
        projectile.velocityMultiplicator = 5;

        switch (this.deplacementActuel) {
            case DROITE:
                this.projectileRotate = 0;
                break;
            case BAS:
                this.projectileRotate = 90;
                break;
            case GAUCHE:
                this.projectileRotate = 180;
                break;
            default:
                this.projectileRotate = -90;
                break;
        }

        Platform.runLater(() -> this.getChildren().add(projectile.getImageView()));
    }

    public boolean projectileSurFantome() {
        for (Fantome fantome : controllerJouer.fantomeGroup.fantomes) {
            if (!fantome.mort && (fantome.getCoordFantome().equals(projectile.getPosX() / 20 + "/" + projectile.getPosY() / 20) || (((fantome.getPosX() - projectile.getPosX()) < 18) && ((fantome.getPosX() - projectile.getPosX()) >= 0) && ((fantome.getPosY() - projectile.getPosY()) < 18) && ((fantome.getPosY() - projectile.getPosY()) >= 0)))){
                fantome.faisDemiTour();
                return true;
            }

        }
        return false;
    }

    public void updateProjectile() {

        switch ((int) projectileRotate) {
            case -90 :
                if (map.grid[projectile.getPosX()/20][(projectile.getPosY()/20)] == Map.ValeurCase.MUR || projectileSurFantome()) {
                    projectileLance = false;
                }
                else projectile.avanceHaut();
                break;
            case 0 :
                if (map.grid[((projectile.getPosX()/20)+1)%25][projectile.getPosY()/20] == Map.ValeurCase.MUR || (projectile.getPosX()/20) +1  >= 24 || projectileSurFantome()) {
                    projectileLance = false;
                }
                else projectile.avanceDroite();
                break;
            case 90 :
                if (map.grid[projectile.getPosX()/20][(projectile.getPosY()/20)+1] == Map.ValeurCase.MUR || projectileSurFantome()) {
                    projectileLance = false;
                }
                else projectile.avanceBas();
                break;
            case 180 :
                if (map.grid[(projectile.getPosX()/20)%25][projectile.getPosY()/20] == Map.ValeurCase.MUR || (projectile.getPosX()/20) -1  <= -1 || projectileSurFantome()) {
                    projectileLance = false;
                }
                else projectile.avanceGauche();
                break;
            default:
                projectileLance = false;
        }
    }

    public void renderProjectile() {
        if(this.projectile.getImageView() != null) {
            projectile.affichage();
            this.projectile.getImageView().setRotate(this.projectileRotate);
        }
    }

    /**
     * Stoppe tous les objets bonus qui ont atteint le temps d'effet
     */
    public void stopPower() {
        if (powerBoost) {
            long tempsPowerBoost = System.currentTimeMillis();
            if (tempsPowerBoost-debutPowerBoost > 1000 * 5) {  // durée 5 sec
                velocityMultiplicator = velocityMultiplicatorInitial;
                powerBoost = false;
            }
        }
        if(freeze) {
            long tempsFreeze = System.currentTimeMillis();
            if (tempsFreeze - tempsDebutFreeze > 5000) {
                controllerJouer.fantomeGroup.unfreezeFantomes();
            }
        }
        else if (powerSuperPacGomme) {
            long tempsPacGomme = System.currentTimeMillis();
            if (tempsPacGomme-debutSuperPacGomme > 1000 * 10) {  // durée 10 sec
                powerSuperPacGomme = false;
                controllerJouer.fantomeGroup.stopVulnerable();
            }
            else controllerJouer.fantomeGroup.setClignotant(tempsPacGomme - debutSuperPacGomme > 1000 * 7.5);
        }
    }

    public void reinitialisePowers() {
        if (powerBoost) {
            velocityMultiplicator = velocityMultiplicatorInitial;
            powerBoost = false;
        }
        if (teleporteurPose) {
            teleporteur.supprimeTeleporteur();
        }
        if (projectileLance) {
            projectileLance = false;
            projectile.setImageView(null);
        }
        controllerJouer.fantomeGroup.unfreezeFantomes();
    }

    public void setControllerJouer(ControllerJouer controllerJouer) {
        this.controllerJouer = controllerJouer;
    }
}
