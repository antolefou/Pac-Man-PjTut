package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.image.Image;
import pacman.controller.ControllerJouer;
import pacman.model.Map;

import java.util.Objects;

import static java.lang.Integer.parseInt;

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

    public boolean competenceTeleporteurDeverouillee;
    public boolean competenceFreezeDeverouillee;
    public boolean competenceTirerDeverouillee;

    public boolean competenceTeleporteurPrete;
    public boolean competenceFreezePrete;
    public boolean competenceTirerPrete;

    public double tempsDeRechargeCompetenceTeleporteur;
    public double tempsDeRechargeCompetenceFreeze;
    public double tempsDeRechargeCompetenceTirer;
    public double debutTempsDeRechargeCompetenceTeleporteur;
    public double debutTempsDeRechargeCompetenceFreeze;
    public double debutTempsDeRechargeCompetenceTirer;

    public Teleporteur teleporteur;
    public boolean teleporteurPose;

    public Deplacement projectile;
    public boolean projectileLance;

    public boolean freeze;
    public double tempsDebutFreeze;
    private double projectileRotate;

    public boolean touchesInversees;
    private long tempsDebutTouchesInversees;
    private long tempsDebutRalentissement;
    private boolean ralentissement;
    public int pertePointsTirer;
    public int pertePointsFreeze;
    public int pertePointsTeleporte;

    public int compteurFantomeMange;

    private final Image imagePacman = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanUp.gif")));
    private final Image imagePacmanEtourdi = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanEtourdi.gif")));
    private final Image imagePacmanGele = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanGele.gif")));

    public Pacman() {
        super(241, 321);
        this.velocityMultiplicator = velocityMultiplicatorInitial;

        this.initialisation();

        this.competenceTeleporteurDeverouillee = false;
        this.competenceFreezeDeverouillee = false;
        this.competenceTirerDeverouillee = false;
        this.competenceTeleporteurPrete = true;
        this.competenceFreezePrete = true;
        this.competenceTirerPrete = true;

        this.teleporteurPose = false;
        this.freeze = false;
        tempsDebutFreeze = 0;

        this.projectileLance = false;
        this.compteurFantomeMange = 0;

        Platform.runLater(() -> {
            this.setImageView(imagePacman);
            this.getChildren().add(getImageView());
        });
    }

    public void initialiseCompetences() {
        if (competenceTirerDeverouillee) {
            this.tempsDeRechargeCompetenceTirer = Double.parseDouble(controllerJouer.utilisateur.tabCompetence[controllerJouer.utilisateur.niveauCompetenceTirer][3]);
            this.pertePointsTirer = Integer.parseInt(controllerJouer.utilisateur.tabCompetence[controllerJouer.utilisateur.niveauCompetenceTirer][2]);;
        }
        if (competenceFreezeDeverouillee) {
            this.tempsDeRechargeCompetenceFreeze = Double.parseDouble(controllerJouer.utilisateur.tabCompetence[controllerJouer.utilisateur.niveauCompetenceFreeze][7]);
            this.pertePointsFreeze = Integer.parseInt(controllerJouer.utilisateur.tabCompetence[controllerJouer.utilisateur.niveauCompetenceFreeze][6]);;
        }
        if (competenceTeleporteurDeverouillee) {
            this.tempsDeRechargeCompetenceTeleporteur = Double.parseDouble(controllerJouer.utilisateur.tabCompetence[controllerJouer.utilisateur.niveauCompetenceTeleporteur][11]);
            this.pertePointsTeleporte = Integer.parseInt(controllerJouer.utilisateur.tabCompetence[controllerJouer.utilisateur.niveauCompetenceTeleporteur][10]);;
        }
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
            return (pacmanY % 20 != 1) || ((map.grid[((int) pacmanX / 20) % 25][(((int) pacmanY / 20) + i) % 30] != Map.ValeurCase.MUR) && (map.grid[((int) pacmanX / 20)][((int) pacmanY / 20) + i] != Map.ValeurCase.INTERDIT));
        }
        return false;
    }

    boolean peutAvancerHorizontalement(int i) {
        double pacmanX = this.getPosX();
        double pacmanY = this.getPosY();
        if (pacmanY % 20 == 1) {
            return (pacmanX % 20 != 1) || (map.grid[((((int) pacmanX / 20) + i) + 25) % 25][((int) pacmanY / 20)] != Map.ValeurCase.MUR);
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
                    if (peutAvancerVerticalement(-1) && !touchesInversees) {
                        avanceHaut();
                        peutAvancer = true;
                    }else if (peutAvancerVerticalement(1) && touchesInversees) {
                        avanceBas();
                        peutAvancer = true;
                    }
                    break;
                case DROITE:
                    if (peutAvancerHorizontalement(1) && !touchesInversees) {
                        avanceDroite();
                        peutAvancer = true;
                    }else if (peutAvancerHorizontalement(-1) && touchesInversees) {
                        avanceGauche();
                        peutAvancer = true;
                    }
                    break;
                case BAS:
                    if (peutAvancerVerticalement(1) && !touchesInversees) {
                        avanceBas();
                        peutAvancer = true;
                    }else if (peutAvancerVerticalement(-1) && touchesInversees) {
                        avanceHaut();
                        peutAvancer = true;
                    }
                    break;
                case GAUCHE:
                    if (peutAvancerHorizontalement(-1) && !touchesInversees) {
                        avanceGauche();
                        peutAvancer = true;
                    } else if (peutAvancerHorizontalement(1) && touchesInversees) {
                        avanceDroite();
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
                    if (peutAvancerVerticalement(-1) && !touchesInversees)
                        avanceHaut();
                    else if (peutAvancerVerticalement(1) && touchesInversees)
                        avanceBas();
                    break;
                case DROITE:
                    if (peutAvancerHorizontalement(1) && !touchesInversees)
                        avanceDroite();
                    else if (peutAvancerHorizontalement(-1) && touchesInversees)
                        avanceGauche();
                    break;
                case BAS:
                    if (peutAvancerVerticalement(1) && !touchesInversees)
                        avanceBas();
                    else if (peutAvancerVerticalement(-1) && touchesInversees)
                        avanceHaut();
                    break;
                case GAUCHE:
                    if (peutAvancerHorizontalement(-1) && !touchesInversees)
                        avanceGauche();
                    else if (peutAvancerHorizontalement(1) && touchesInversees)
                        avanceDroite();
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
            }
        }
        this.stopPower();
        this.tempsDeRecharge();
    }

    // ---------------------  POUVOIRS DE PACMAN   ------------------------------------------
    /**
     * Initialise pacman avec les attributs de l'objet bonus: boost
     */
    public void initPowerBoost() {
        if (!ralentissement)
            this.velocityMultiplicator = velocityMultiplicatorInitial * 2;
        else this.velocityMultiplicator = velocityMultiplicatorInitial;
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
        if (!teleporteurPose && score >= pertePointsTeleporte && competenceTeleporteurPrete) {
            teleporteurPose = true;
            this.teleporteur = new Teleporteur(this, map);
            teleporteur.poseTeleporteur(getPosX(), getPosY());
            pertePoints(pertePointsTeleporte);
        }
        else if (teleporteurPose){
            debutTempsDeRechargeCompetenceTeleporteur = System.currentTimeMillis();
            competenceTeleporteurPrete = false;
            teleporteur.teleporte();
            touchesInversees();
        }
    }

    public void competenceFreeze() {
        debutTempsDeRechargeCompetenceFreeze = System.currentTimeMillis();
        competenceFreezePrete = false;
        this.freeze = true;
        controllerJouer.fantomeGroup.freezeFantomes();
        tempsDebutFreeze = System.currentTimeMillis();
        ralentissement();
        pertePoints(pertePointsFreeze);
    }

    public void competenceProjectile() {
        debutTempsDeRechargeCompetenceTirer = System.currentTimeMillis();
        competenceTirerPrete = false;
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
        pertePoints(pertePointsTirer);
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

    // ---------------------  CONTREPARTIES DES POUVOIRS   ------------------------------------------

    public void touchesInversees() {
        this.touchesInversees = true;
        tempsDebutTouchesInversees = System.currentTimeMillis();
        Platform.runLater(() -> setImageView(imagePacmanEtourdi));
    }

    public void ralentissement() {
        if (powerBoost) this.velocityMultiplicator = velocityMultiplicatorInitial;
        else this.velocityMultiplicator = velocityMultiplicatorInitial/2;
        ralentissement = true;
        tempsDebutRalentissement = System.currentTimeMillis();
        Platform.runLater(() -> setImageView(imagePacmanGele));
    }

    public void pertePoints(int pointsPerdus) {
        this.score -= pointsPerdus;
    }

    /**
     * Stoppe tous les objets bonus qui ont atteint le temps d'effet
     */
    public void stopPower() {
        if (powerBoost) {
            long tempsPowerBoost = System.currentTimeMillis();
            if (tempsPowerBoost-debutPowerBoost > 1000 * 5) {  // durée 5 sec
                if(ralentissement) velocityMultiplicator = velocityMultiplicatorInitial/2;
                else velocityMultiplicator = velocityMultiplicatorInitial;
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
        if(touchesInversees) {
            long tempsTouchesInversees = System.currentTimeMillis();
            if (tempsTouchesInversees-tempsDebutTouchesInversees > 1000 * 15) {
                Platform.runLater(() -> {
                    if(!freeze) this.setImageView(imagePacman);
                });
                touchesInversees = false;
            }
        }
        if(ralentissement) {
            long tempsRalentissement = System.currentTimeMillis();
            if (tempsRalentissement-tempsDebutRalentissement > 1000 * 8) {
                ralentissement = false;
                if(powerBoost) velocityMultiplicator = velocityMultiplicatorInitial * 2;
                else velocityMultiplicator = velocityMultiplicatorInitial;
                Platform.runLater(() -> {
                    if(!touchesInversees) this.setImageView(imagePacman);
                });
            }
        }
    }

    /**
     * Met une compétence prête si son temps de recharge est terminé
     */
    public void tempsDeRecharge() {
        long tempsDeRecharge = System.currentTimeMillis();
        if (!competenceTeleporteurPrete) {
            if (tempsDeRecharge- debutTempsDeRechargeCompetenceTeleporteur > 1000 * tempsDeRechargeCompetenceTeleporteur) competenceTeleporteurPrete = true;
        }if (!competenceFreezePrete) {
            if (tempsDeRecharge- debutTempsDeRechargeCompetenceFreeze > 1000 * tempsDeRechargeCompetenceFreeze) competenceFreezePrete = true;
        }if (!competenceTirerPrete) {
            if (tempsDeRecharge- debutTempsDeRechargeCompetenceTirer > 1000 * tempsDeRechargeCompetenceTirer) competenceTirerPrete = true;
        }
    }

    /**
     * Réinitialise le temps de recharge des sorts déverouillés
     */
    public void reinitialiseTempsDeRecharge() {
        if (competenceTirerDeverouillee) competenceTirerPrete = true;
        if (competenceFreezeDeverouillee) competenceFreezePrete = true;
        if (competenceTeleporteurDeverouillee) competenceTeleporteurPrete = true;

        debutTempsDeRechargeCompetenceTirer = 0;
        debutTempsDeRechargeCompetenceFreeze = 0;
        debutTempsDeRechargeCompetenceTeleporteur = 0;
    }

    /**
     * Réinitialise tous les pouvoirs activés
     */
    public void reinitialisePowers() {
        if (powerBoost) {
            powerBoost = false;
        }
        if (teleporteurPose) teleporteur.supprimeTeleporteur();

        if (projectileLance) {
            projectileLance = false;
            projectile.setImageView(null);
        }
        controllerJouer.fantomeGroup.unfreezeFantomes();

        if(touchesInversees) {
            touchesInversees = false;
        }
        if(ralentissement) ralentissement = false;
        velocityMultiplicator = velocityMultiplicatorInitial;

        Platform.runLater(() -> this.setImageView(imagePacman));
    }

    public void setControllerJouer(ControllerJouer controllerJouer) {
        this.controllerJouer = controllerJouer;
    }
}
