package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.image.Image;
import pacman.controller.ControllerJouer;
import pacman.model.Map;

import java.util.Objects;

public class Pacman extends Deplacement {

    private ControllerJouer controllerJouer;
    public boolean enVie = true;
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
    public boolean peutManger;
    public long debutTempsPeutManger;

    public int compteurFantomeMange;

    private final Image imagePacman = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanUp.gif")));
    private final Image imagePacmanEtourdi = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanEtourdi.gif")));
    private final Image imagePacmanGele = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanGele.gif")));

    /**
     * Créé un nouveau pacman et initialise ses variables
     */
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
        this.tempsDebutFreeze = 0;

        this.peutManger = true;

        this.projectileLance = false;
        this.compteurFantomeMange = 0;

        Platform.runLater(() -> {
            this.setImageView(imagePacman);
            this.getChildren().add(getImageView());
        });
    }

    /**
     * Récupère les données de l'utilisateur pour initialiser les variables des compétences de pacman
     */
    public void initialiseCompetences() {
        if (competenceTirerDeverouillee) {
            this.tempsDeRechargeCompetenceTirer = Double.parseDouble(this.controllerJouer.utilisateur.tabCompetence[this.controllerJouer.utilisateur.niveauCompetenceTirer][3]);
            this.pertePointsTirer = Integer.parseInt(this.controllerJouer.utilisateur.tabCompetence[this.controllerJouer.utilisateur.niveauCompetenceTirer][2]);
        }
        if (competenceFreezeDeverouillee) {
            this.tempsDeRechargeCompetenceFreeze = Double.parseDouble(this.controllerJouer.utilisateur.tabCompetence[this.controllerJouer.utilisateur.niveauCompetenceFreeze][7]);
            this.pertePointsFreeze = Integer.parseInt(this.controllerJouer.utilisateur.tabCompetence[this.controllerJouer.utilisateur.niveauCompetenceFreeze][6]);
        }
        if (competenceTeleporteurDeverouillee) {
            this.tempsDeRechargeCompetenceTeleporteur = Double.parseDouble(this.controllerJouer.utilisateur.tabCompetence[this.controllerJouer.utilisateur.niveauCompetenceTeleporteur][11]);
            this.pertePointsTeleporte = Integer.parseInt(this.controllerJouer.utilisateur.tabCompetence[this.controllerJouer.utilisateur.niveauCompetenceTeleporteur][10]);
        }
    }

    /**
     * Initialise la position de pacman
     */
    @Override
    public void initPosition() {
        super.initPosition();
        this.deplacementFutur = deplacements.AUCUN;
        this.deplacementActuel = deplacements.AUCUN;
    }

    /**
     * Détecte si un mur ou une barrière se trouve dans la direction verticale demandée
     * @param i La direction : -1 pour le haut et 1 pour le bas
     * @return true si il n'y a pas de mur ou  de barrière sur la case devant pacman dans la direction demandée
     */
    boolean peutAvancerVerticalement(int i) { //haut -> -1 bas -> 1
        double pacmanX = this.getPosX();
        double pacmanY = this.getPosY();
        if (pacmanX % 20 == 1) {
            return (pacmanY % 20 != 1) || ((this.map.grid[((int) pacmanX / 20) % 25][(((int) pacmanY / 20) + i) % 30] != Map.ValeurCase.MUR) && (map.grid[((int) pacmanX / 20)][((int) pacmanY / 20) + i] != Map.ValeurCase.INTERDIT));
        }
        return false;
    }

    /**
     * Détecte si un mur ou une barrière se trouve dans la direction horizontale demandée
     * @param i La direction : -1 pour la gauche et 1 pour la droite
     * @return true si il n'y a pas de mur ou  de barrière sur la case devant pacman dans la direction demandée
     */
    boolean peutAvancerHorizontalement(int i) {
        double pacmanX = this.getPosX();
        double pacmanY = this.getPosY();
        if (pacmanY % 20 == 1) {
            return (pacmanX % 20 != 1) || (this.map.grid[((((int) pacmanX / 20) + i) + 25) % 25][((int) pacmanY / 20)] != Map.ValeurCase.MUR);
        }
        return false;
    }

    /**
     * Actualise la position de l'image et l'oriente selon la direction de pacman
     */
    @Override
    public void affichage() {
        super.affichage();
        switch (this.deplacementActuel) {
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

    /**
     * Actualise les coordonnées de pacman selon où il se déplace
     */
    public void updateDeplacement() {
        boolean peutAvancer = false;
        if (this.deplacementFutur != Deplacement.deplacements.AUCUN) {
            switch (this.deplacementFutur) {
                case HAUT:
                    if (this.peutAvancerVerticalement(-1) && !this.touchesInversees) {
                        this.avanceHaut();
                        peutAvancer = true;
                    }else if (this.peutAvancerVerticalement(1) && this.touchesInversees) {
                        this.avanceBas();
                        peutAvancer = true;
                    }
                    break;
                case DROITE:
                    if (this.peutAvancerHorizontalement(1) && !this.touchesInversees) {
                        this.avanceDroite();
                        peutAvancer = true;
                    }else if (this.peutAvancerHorizontalement(-1) && this.touchesInversees) {
                        this.avanceGauche();
                        peutAvancer = true;
                    }
                    break;
                case BAS:
                    if (this.peutAvancerVerticalement(1) && !this.touchesInversees) {
                        this.avanceBas();
                        peutAvancer = true;
                    }else if (this.peutAvancerVerticalement(-1) && this.touchesInversees) {
                        this.avanceHaut();
                        peutAvancer = true;
                    }
                    break;
                case GAUCHE:
                    if (this.peutAvancerHorizontalement(-1) && !this.touchesInversees) {
                        this.avanceGauche();
                        peutAvancer = true;
                    } else if (this.peutAvancerHorizontalement(1) && this.touchesInversees) {
                        this.avanceDroite();
                        peutAvancer = true;
                    }
                    break;
            }
        }
        if (peutAvancer) {
            this.deplacementActuel = this.deplacementFutur;
            this.deplacementFutur = deplacements.AUCUN;
        } else {
            switch (this.deplacementActuel) {
                case HAUT:
                    if (this.peutAvancerVerticalement(-1) && !this.touchesInversees)
                        this.avanceHaut();
                    else if (this.peutAvancerVerticalement(1) && this.touchesInversees)
                        this.avanceBas();
                    break;
                case DROITE:
                    if (this.peutAvancerHorizontalement(1) && !this.touchesInversees)
                        this.avanceDroite();
                    else if (this.peutAvancerHorizontalement(-1) && this.touchesInversees)
                        this.avanceGauche();
                    break;
                case BAS:
                    if (this.peutAvancerVerticalement(1) && !this.touchesInversees)
                        this.avanceBas();
                    else if (this.peutAvancerVerticalement(-1) && this.touchesInversees)
                        this.avanceHaut();
                    break;
                case GAUCHE:
                    if (this.peutAvancerHorizontalement(-1) && !this.touchesInversees)
                        this.avanceGauche();
                    else if (this.peutAvancerHorizontalement(1) && this.touchesInversees)
                        this.avanceDroite();
                    break;
            }
        }
    }

    /**
     * Détecte ce qui se trouve sur la case de pacman et l'enlève de la map
     * en ajoutant la valeur correspondante au score si pacman peut manger
     */
    public void updateMapPacman() {
        // ---------------------------- interractions de pacman ------------------------
        int pacmanX = this.getPosX();
        int pacmanY = this.getPosY();
        int comparaisonX = 1;
        int addX = 0;
        int addY = 0;
        int comparaisonY = 1;
        switch (this.deplacementActuel) {
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
        if (pacmanX % 20 == comparaisonX && pacmanY % 20 == comparaisonY && peutManger) {
            int x = (((pacmanX / 20) + 25) % 25 + addX)%25;
            int y = pacmanY / 20 + addY;
            if (this.map.grid[x][y] == Map.ValeurCase.GOMME) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 10;
            } else if (this.map.grid[x][y] == Map.ValeurCase.SUPERGOMME) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 50;
                this.initSuperPacGomme();
            } else if (this.map.grid[x][y] == Map.ValeurCase.BOOST) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 50;
                this.initPowerBoost();
            }else if (this.map.grid[x][y] == Map.ValeurCase.CERISE) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 100;
            }else if (this.map.grid[x][y] == Map.ValeurCase.FRAISE) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 300;
            }else if (this.map.grid[x][y] == Map.ValeurCase.ORANGE) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 500;
            }else if (this.map.grid[x][y] == Map.ValeurCase.POMME) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 700;
            }else if (this.map.grid[x][y] == Map.ValeurCase.MELON) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 1000;
            }else if (this.map.grid[x][y] == Map.ValeurCase.VAISSEAU) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 2000;
            }else if (this.map.grid[x][y] == Map.ValeurCase.CLOCHE) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 3000;
            }else if (this.map.grid[x][y] == Map.ValeurCase.CLEF) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
                this.score += 5000;
            }else if (this.map.grid[x][y] == null) {
                this.map.grid[x][y] = Map.ValeurCase.VIDE;
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
            this.velocityMultiplicator = this.velocityMultiplicatorInitial * 2;
        else this.velocityMultiplicator = this.velocityMultiplicatorInitial;
        this.debutPowerBoost = System.currentTimeMillis();
        this.powerBoost = true;
    }
    /**
     * Initialise pacman avec les attributs de l'objet bonus: super pac-gomme
     */
    public void initSuperPacGomme() {
        this.debutSuperPacGomme = System.currentTimeMillis();
        this.powerSuperPacGomme = true;
        this.controllerJouer.fantomeGroup.setVulnerable();
    }

    /**
     * Si un téléporteur est posé, modifie les coordonnees de pacman. Sinon pose un téléporteur
     */
    public void competenceTeleportation() {
        if (!this.teleporteurPose && this.score >= this.pertePointsTeleporte && this.competenceTeleporteurPrete) {
            this.teleporteurPose = true;
            this.teleporteur = new Teleporteur(this, map);
            this.teleporteur.poseTeleporteur(getPosX(), getPosY());
            this.pertePoints(pertePointsTeleporte);
        }
        else if (this.teleporteurPose){
            this.debutTempsDeRechargeCompetenceTeleporteur = System.currentTimeMillis();
            this.competenceTeleporteurPrete = false;
            this.teleporteur.teleporte();
            this.touchesInversees();
        }
    }

    /**
     * Active la compétence freeze et lance son temps de recharge
     */
    public void competenceFreeze() {
        this.debutTempsDeRechargeCompetenceFreeze = System.currentTimeMillis();
        this.competenceFreezePrete = false;
        this.freeze = true;
        this.controllerJouer.fantomeGroup.freezeFantomes();
        this.tempsDebutFreeze = System.currentTimeMillis();
        this.ralentissement();
        this.pertePoints(this.pertePointsFreeze);
    }

    /**
     * Créé et affiche le projectile et lance le temps de recharge de la compétence
     */
    public void competenceProjectile() {
        this.debutTempsDeRechargeCompetenceTirer = System.currentTimeMillis();
        this.competenceTirerPrete = false;
        this.peutManger = false;
        this.debutTempsPeutManger = System.currentTimeMillis();
        Image imageProjectile= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/projectile.gif")));
        this.projectileLance = true;
        this.projectile = new Deplacement(getPosX(), getPosY());
        this.projectile.setImage(imageProjectile);
        this.projectile.initialisation();
        this.projectile.velocityMultiplicator = 5;

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
        this.pertePoints(this.pertePointsTirer);
        Platform.runLater(() -> this.getChildren().add(this.projectile.getImageView()));
    }

    /**
     * Détecte si le projectile de pacman est sur la même case qu'un fantôme
     * @return vrai si le projectile est sur la même case qu'un fantôme, sinon return faux
     */
    public boolean projectileSurFantome() {
        for (Fantome fantome : this.controllerJouer.fantomeGroup.fantomes) {
            if (!fantome.mort && (fantome.getCoordFantome().equals(this.projectile.getPosX() / 20 + "/" + this.projectile.getPosY() / 20) || (((fantome.getPosX() - this.projectile.getPosX()) < 18) && ((fantome.getPosX() - this.projectile.getPosX()) >= 0) && ((fantome.getPosY() - this.projectile.getPosY()) < 18) && ((fantome.getPosY() - this.projectile.getPosY()) >= 0)))){
                fantome.faisDemiTour();
                return true;
            }

        }
        return false;
    }

    /**
     * Actualise les coordonnées du projectile et détecte les collisions
     */
    public void updateProjectile() {

        switch ((int) this.projectileRotate) {
            case -90 :
                if (this.map.grid[this.projectile.getPosX()/20][(this.projectile.getPosY()/20)] == Map.ValeurCase.MUR || this.projectileSurFantome()) {
                    this.projectileLance = false;
                }
                else this.projectile.avanceHaut();
                break;
            case 0 :
                if (this.map.grid[((this.projectile.getPosX()/20)+1)%25][this.projectile.getPosY()/20] == Map.ValeurCase.MUR || (this.projectile.getPosX()/20) +1  >= 24 || this.projectileSurFantome()) {
                    this.projectileLance = false;
                }
                else this.projectile.avanceDroite();
                break;
            case 90 :
                if (this.map.grid[this.projectile.getPosX()/20][(this.projectile.getPosY()/20)+1] == Map.ValeurCase.MUR || this.projectileSurFantome()) {
                    this.projectileLance = false;
                }
                else this.projectile.avanceBas();
                break;
            case 180 :
                if (this.map.grid[(this.projectile.getPosX()/20)%25][this.projectile.getPosY()/20] == Map.ValeurCase.MUR || (this.projectile.getPosX()/20) -1  <= -1 || this.projectileSurFantome()) {
                    this.projectileLance = false;
                }
                else this.projectile.avanceGauche();
                break;
            default:
                this.projectileLance = false;
        }
    }

    /**
     * Actualise l'affichage du projectile
     */
    public void renderProjectile() {
        if(this.projectile.getImageView() != null) {
            this.projectile.affichage();
            this.projectile.getImageView().setRotate(this.projectileRotate);
        }
    }

    // ---------------------  CONTREPARTIES DES POUVOIRS   ------------------------------------------

    /**
     * Les touches de pacman sont inversées pendant un certain temps
     */
    public void touchesInversees() {
        this.touchesInversees = true;
        this.tempsDebutTouchesInversees = System.currentTimeMillis();
        Platform.runLater(() -> setImageView(this.imagePacmanEtourdi));
    }

    /**
     * Pacman se déplace 2x plus lentement et son image est modifiée
     */
    public void ralentissement() {
        if (powerBoost) this.velocityMultiplicator = this.velocityMultiplicatorInitial;
        else this.velocityMultiplicator = this.velocityMultiplicatorInitial/2;
        this.ralentissement = true;
        this.tempsDebutRalentissement = System.currentTimeMillis();
        Platform.runLater(() -> setImageView(this.imagePacmanGele));
    }

    /**
     * Enlève des points au score de pacman
     * @param pointsPerdus le nombre de points que pacman perd
     */
    public void pertePoints(int pointsPerdus) {
        this.score -= pointsPerdus;
    }

    /**
     * Stoppe tous les objets bonus qui ont atteint le temps d'effet
     */
    public void stopPower() {
        if (this.powerBoost) {
            long tempsPowerBoost = System.currentTimeMillis();
            if (tempsPowerBoost-this.debutPowerBoost > 1000 * 5) {  // durée 5 sec
                if(this.ralentissement) this.velocityMultiplicator = this.velocityMultiplicatorInitial/2;
                else this.velocityMultiplicator = this.velocityMultiplicatorInitial;
                this.powerBoost = false;
            }
        }
        if(this.freeze) {
            long tempsFreeze = System.currentTimeMillis();
            if (tempsFreeze - this.tempsDebutFreeze > 5000) {
                this.controllerJouer.fantomeGroup.unfreezeFantomes();
            }
        }
        else if (this.powerSuperPacGomme) {
            long tempsPacGomme = System.currentTimeMillis();
            if (tempsPacGomme-this.debutSuperPacGomme > 1000 * 10) {  // durée 10 sec
                this.powerSuperPacGomme = false;
                this.controllerJouer.fantomeGroup.stopVulnerable();
            }
            else this.controllerJouer.fantomeGroup.setClignotant(tempsPacGomme - this.debutSuperPacGomme > 1000 * 7.5);
        }
        if(this.touchesInversees) {
            long tempsTouchesInversees = System.currentTimeMillis();
            if (tempsTouchesInversees-this.tempsDebutTouchesInversees > 1000 * 15) {
                Platform.runLater(() -> {
                    if(!this.freeze) this.setImageView(this.imagePacman);
                });
                this.touchesInversees = false;
            }
        }
        if(this.ralentissement) {
            long tempsRalentissement = System.currentTimeMillis();
            if (tempsRalentissement-this.tempsDebutRalentissement > 1000 * 8) {
                this.ralentissement = false;
                if(this.powerBoost) this.velocityMultiplicator = this.velocityMultiplicatorInitial * 2;
                else this.velocityMultiplicator = this.velocityMultiplicatorInitial;
                Platform.runLater(() -> {
                    if(!this.touchesInversees) this.setImageView(this.imagePacman);
                });
            }
        }
        if (!this.peutManger) {
            long tempsNePeutPlusManger = System.currentTimeMillis();
            if (tempsNePeutPlusManger-this.debutTempsPeutManger > 1000 * 2) {
                this.peutManger = true;
            }
        }
    }

    /**
     * Met une compétence prête si son temps de recharge est terminé
     */
    public void tempsDeRecharge() {
        long tempsDeRecharge = System.currentTimeMillis();
        if (!this.competenceTeleporteurPrete) {
            if (tempsDeRecharge- this.debutTempsDeRechargeCompetenceTeleporteur > 1000 * this.tempsDeRechargeCompetenceTeleporteur) this.competenceTeleporteurPrete = true;
        }if (!this.competenceFreezePrete) {
            if (tempsDeRecharge- this.debutTempsDeRechargeCompetenceFreeze > 1000 * this.tempsDeRechargeCompetenceFreeze) this.competenceFreezePrete = true;
        }if (!this.competenceTirerPrete) {
            if (tempsDeRecharge- this.debutTempsDeRechargeCompetenceTirer > 1000 * this.tempsDeRechargeCompetenceTirer) this.competenceTirerPrete = true;
        }
    }

    /**
     * Réinitialise le temps de recharge des compétences déverouillées et les met prêtes
     */
    public void reinitialiseTempsDeRecharge() {
        if (this.competenceTirerDeverouillee) this.competenceTirerPrete = true;
        if (this.competenceFreezeDeverouillee) this.competenceFreezePrete = true;
        if (this.competenceTeleporteurDeverouillee) this.competenceTeleporteurPrete = true;

        this.debutTempsDeRechargeCompetenceTirer = 0;
        this.debutTempsDeRechargeCompetenceFreeze = 0;
        this.debutTempsDeRechargeCompetenceTeleporteur = 0;
    }

    /**
     * Réinitialise tous les pouvoirs activés
     */
    public void reinitialisePowers() {
        if (this.powerBoost) {
            this.powerBoost = false;
        }
        if (this.teleporteurPose) this.teleporteur.supprimeTeleporteur();

        if (this.projectileLance) {
            this.projectileLance = false;
            this.projectile.setImageView(null);
        }
        this.controllerJouer.fantomeGroup.unfreezeFantomes();

        if(this.touchesInversees) {
            this.touchesInversees = false;
        }
        if(this.ralentissement) this.ralentissement = false;
        this.velocityMultiplicator = this.velocityMultiplicatorInitial;

        this.peutManger = true;

        Platform.runLater(() -> this.setImageView(this.imagePacman));
    }

    /**
     * Défini le controllerJouer de pacman
     * @param controllerJouer le controllerJouer
     */
    public void setControllerJouer(ControllerJouer controllerJouer) {
        this.controllerJouer = controllerJouer;
    }
}