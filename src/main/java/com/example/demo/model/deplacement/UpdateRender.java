package com.example.demo.model.deplacement;

import javafx.application.Platform;
import javafx.scene.control.Label;
import com.example.demo.controller.ControllerJouer;
import com.example.demo.model.Map;
import com.example.demo.model.Utilisateur;

import java.io.IOException;

public class UpdateRender extends Thread{
    private final FantomeGroup fantomeGroup;
    private final Utilisateur UTILISATEUR;
    private final Label LABEL_SCORE;
    private final Map MAP;
    public final Pacman PACMAN;
    private final ControllerJouer controllerJouer;

    public Thread update;
    public Thread render;

    /**
     * Mise à jour des variables et affichage
     * @param controllerJouer ControllerJouer
     * @param utilisateur Utilisateur
     * @param labelScore Label score
     * @param map Map
     * @param pacman Pacman
     * @param fantomeGroup Group de fantomes
     */
    public UpdateRender(ControllerJouer controllerJouer, Utilisateur utilisateur, Label labelScore, Map map, Pacman pacman, FantomeGroup fantomeGroup) {
        this.controllerJouer = controllerJouer;
        this.UTILISATEUR = utilisateur;
        this.LABEL_SCORE = labelScore;
        this.MAP = map;
        this.PACMAN = pacman;
        this.fantomeGroup = fantomeGroup;
        this.initialize();
    }

    public void initialize() {
        this.PACMAN.setControllerJouer(controllerJouer);
        this.PACMAN.setMap(MAP);
        this.addPacmanToFantome();
        this.fantomeGroup.initNumFantome();
        // competence
        this.PACMAN.competenceTeleporteurDeverouillee = this.UTILISATEUR.niveauCompetenceTeleporteur>=0;
        this.PACMAN.competenceFreezeDeverouillee = this.UTILISATEUR.niveauCompetenceFreeze >=0;
        this.PACMAN.competenceTirerDeverouillee = this.UTILISATEUR.niveauCompetenceTirer>=0;
        this.PACMAN.initialiseCompetences();
    }

    public void jouer() {
//        Thred update
        this.update = new Thread(() -> {
            while (this.PACMAN.enVie) {
                try {
                    this.update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(this.UTILISATEUR.getTHREAD_UPDATE());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.update.setDaemon(true);
        this.update.start();
//        Thread render
        this.UTILISATEUR.updateFps(UTILISATEUR.getFps());
        this.render = new Thread(() -> {
            while (this.PACMAN.enVie){
                this.render();
                try {
                    sleep(this.UTILISATEUR.getThreadRender());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.render.setDaemon(true);
        this.render.start();
    }

    private void update() throws IOException {
        for (int i = 0; i< this.PACMAN.velocityMultiplicator; i++) {  // gère le multiplicateur de pacman
            this.PACMAN.updateDeplacement();
            this.PACMAN.updateMapPacman();
            fantomeSurPacman();
        }
        if(this.PACMAN.projectileLance) {
            for (int i = 0; i< this.PACMAN.projectile.velocityMultiplicator; i++) {
                this.PACMAN.updateProjectile();
            }
        }
        if (!this.PACMAN.freeze) {
            for (Fantome fantome : this.fantomeGroup.fantomes) {
                for (int i = 0; i < fantome.velocityMultiplicator; i++) {
                    fantome.updateDeplacement();
                    fantomeSurPacman();
//                System.out.println(fantome.listeCoordoneDeplacementFant);
                }
            }
        }
    }

    private void render() {
        Platform.runLater(() -> {
            //affichage pacman
            this.PACMAN.affichage();
            //affichage projectile
            if (this.PACMAN.projectileLance) {
                this.PACMAN.renderProjectile();
            }
            if (!this.PACMAN.projectileLance && this.PACMAN.projectile != null) this.PACMAN.projectile.setImageView(null);
            //affichage fantomes
            for (Fantome fantome : this.fantomeGroup.fantomes) {
                fantome.affichage();
            }
            // mise à jour de la map
            this.MAP.miseAJourMap();
            //affichage score
            this.updateScore();
            try {this.controllerJouer.viePac();} catch (IOException e) {e.printStackTrace();}
            if (this.MAP.aGagne()){
                this.UTILISATEUR.pointJoueur=this.PACMAN.score;
                this.UTILISATEUR.ecritureUtilisateur();
                try {this.controllerJouer.switchToSceneAmelioration();} catch (IOException e) {e.printStackTrace();}
            }
            //affichage des compétences
            this.renderCompetences();
        });
    }

    // ------------------- UPDATE SCORE -------------------------
    public void updateScore() {
        this.LABEL_SCORE.setText(String.valueOf(this.PACMAN.score));
    }

    /**
     * si le fantome est apeuré et sur pacman on le passe en comportement MORT et on augmente les points selon le nombre de fantôme mangé
     * sinon si le fantôme n'est ni appeuré ni mort, Pacman perd une vie, on reinitialise la postion des fantômes et de Pacman et on joue la musique de mort.
     */
    public void fantomeSurPacman() {
        for (Fantome fantome : this.fantomeGroup.fantomes) {
            if (fantome.estSurPacman()) {
                if (fantome.etat == Fantome.ValeurEtat.APPEURE && this.PACMAN.peutManger) {
                    fantome.etat = Fantome.ValeurEtat.MORT;
                    fantome.velocityMultiplicator = 3;
                    if(this.PACMAN.compteurFantomeMange < 4) {
                        this.PACMAN.score += 100 * Math.pow(2.0, this.PACMAN.compteurFantomeMange);
                        this.PACMAN.compteurFantomeMange++;
                    }else{
                        this.PACMAN.score += 1000;
                        this.PACMAN.compteurFantomeMange = 0;
                    }
                } else if (fantome.etat != Fantome.ValeurEtat.MORT && fantome.etat!= Fantome.ValeurEtat.APPEURE){
                    this.PACMAN.compteurFantomeMange = 0;
                    this.PACMAN.initPosition();
                    this.PACMAN.nbVie--;
                    this.PACMAN.reinitialiseTempsDeRecharge();
                    this.PACMAN.reinitialisePowers();
                    this.controllerJouer.playMusic("death", false);
                    this.fantomeGroup.reinitialisePosition();
                }
            }
        }
    }

    /**
     * donne la classe de pacman à tout les fantômes.
     */
    public void addPacmanToFantome() {
        for (Fantome fantome : this.fantomeGroup.fantomes) {
            fantome.setMap(MAP);
            fantome.pacman = PACMAN;
        }
    }

    /**
     * Permet d'afficher le cooldown des compétences si il en a et grise l'image dans le fond
     * sinon affiche l'image avec une opacité max.
     */
    public void renderCompetences() {
        //tirer
        if (System.currentTimeMillis()-this.PACMAN.debutTempsDeRechargeCompetenceTirer < 1000 * this.PACMAN.tempsDeRechargeCompetenceTirer) {
            int cooldown = (int) ((1000 * this.PACMAN.tempsDeRechargeCompetenceTirer - (System.currentTimeMillis()-this.PACMAN.debutTempsDeRechargeCompetenceTirer))/1000);
            this.controllerJouer.imageCompetenceTirer.setOpacity(0.5);
            this.controllerJouer.cooldownCompetenceTirer.setText(String.valueOf(cooldown));
        } else {
            this.controllerJouer.cooldownCompetenceTirer.setText("");
            this.controllerJouer.imageCompetenceTirer.setOpacity(1);
        }
        //freeze
        if (System.currentTimeMillis()-this.PACMAN.debutTempsDeRechargeCompetenceFreeze < 1000 * this.PACMAN.tempsDeRechargeCompetenceFreeze) {
            int cooldown = (int) ((1000 * this.PACMAN.tempsDeRechargeCompetenceFreeze - (System.currentTimeMillis()-this.PACMAN.debutTempsDeRechargeCompetenceFreeze))/1000);
            this.controllerJouer.imageCompetenceFreeze.setOpacity(0.5);
            this.controllerJouer.cooldownCompetenceFreeze.setText(String.valueOf(cooldown));
        } else {
            this.controllerJouer.cooldownCompetenceFreeze.setText("");
            this.controllerJouer.imageCompetenceFreeze.setOpacity(1);
        }
        //teleporteur
        if (System.currentTimeMillis()-this.PACMAN.debutTempsDeRechargeCompetenceTeleporteur < 1000 * this.PACMAN.tempsDeRechargeCompetenceTeleporteur) {
            int cooldown = (int) ((1000 * this.PACMAN.tempsDeRechargeCompetenceTeleporteur - (System.currentTimeMillis()-this.PACMAN.debutTempsDeRechargeCompetenceTeleporteur))/1000);
            this.controllerJouer.imageCompetenceTeleporteur.setOpacity(0.5);
            this.controllerJouer.cooldownCompetenceTeleporteur.setText(String.valueOf(cooldown));
        } else {
            this.controllerJouer.cooldownCompetenceTeleporteur.setText("");
            this.controllerJouer.imageCompetenceTeleporteur.setOpacity(1);
        }
    }
}