package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import pacman.controller.Controller;
import pacman.controller.ControllerJouer;
import pacman.model.Map;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;

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
        initialize();
    }

    public void initialize() {
        this.PACMAN.setControllerJouer(controllerJouer);
        this.PACMAN.setMap(MAP);
        addPacmanToFantome();
        fantomeGroup.initNumFantome();
        // competence
        PACMAN.competenceTeleporteurDeverouillee = this.UTILISATEUR.niveauCompetenceTeleporteur>=0;
        PACMAN.competenceFreezeDeverouillee = this.UTILISATEUR.niveauCompetenceFreeze >=0;
        PACMAN.competenceTirerDeverouillee = this.UTILISATEUR.niveauCompetenceTirer>=0;
        this.PACMAN.initialiseCompetences();
    }

    public void jouer() {
//        Thred update
        this.update = new Thread(() -> {
            while (PACMAN.enVie) {
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(UTILISATEUR.getTHREAD_UPDATE());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        update.setDaemon(true);
        update.start();
//        Thread render
        UTILISATEUR.updateFps(UTILISATEUR.getFps());
        this.render = new Thread(() -> {
            while (PACMAN.enVie){
                render();
                try {
                    sleep(UTILISATEUR.getThreadRender());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        render.setDaemon(true);
        render.start();
    }

    private void update() throws IOException {
        for (int i = 0; i< PACMAN.velocityMultiplicator; i++) {  // gère le multiplicateur de pacman
            PACMAN.updateDeplacement();
            PACMAN.updateMapPacman();
            fantomeSurPacman();
        }
        if(PACMAN.projectileLance) {
            for (int i = 0; i< PACMAN.projectile.velocityMultiplicator; i++) {
                PACMAN.updateProjectile();
            }
        }
        if (!PACMAN.freeze) {
            for (Fantome fantome : fantomeGroup.fantomes) {
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
            PACMAN.affichage();
            //affichage projectile
            if (PACMAN.projectileLance) {
                PACMAN.renderProjectile();
            }
            if (!PACMAN.projectileLance && PACMAN.projectile != null) PACMAN.projectile.setImageView(null);
            //affichage fantomes
            for (Fantome fantome : fantomeGroup.fantomes) {
                fantome.affichage();
            }
            // mise à jour de la map
            MAP.miseAJourMap();
            //affichage score
            this.updateScore();
            try {controllerJouer.viePac();} catch (IOException e) {e.printStackTrace();}
            if (MAP.aGagne()){
                UTILISATEUR.pointJoueur=PACMAN.score;
                UTILISATEUR.ecritureUtilisateur();
                try {controllerJouer.switchTosceneAmelioration();} catch (IOException e) {e.printStackTrace();}
            }
            //affichage des compétences
            renderCompetences();
        });
    }

    // ------------------- UPDATE SCORE -------------------------
    public void updateScore() {
        this.LABEL_SCORE.setText(String.valueOf(PACMAN.score));
    }

    public void fantomeSurPacman() {
        for (Fantome fantome : fantomeGroup.fantomes) {
            if (fantome.estSurPacman()) {
                if (fantome.etat == Fantome.ValeurEtat.APPEURE) {
                    fantome.mort = false;
                    fantome.etat = Fantome.ValeurEtat.MORT;
                    fantome.velocityMultiplicator = 3;
                    PACMAN.score += 200 * Math.pow(2.0, PACMAN.compteurFantomeMange);
                    if(PACMAN.compteurFantomeMange < 4) PACMAN.compteurFantomeMange++;
                } else if (fantome.etat != Fantome.ValeurEtat.MORT){
                    PACMAN.compteurFantomeMange = 0;
                    PACMAN.initPosition();
                    PACMAN.nbVie--;
                    PACMAN.reinitialiseTempsDeRecharge();
                    PACMAN.reinitialisePowers();
                    this.controllerJouer.playMusic("death", false);
                    fantomeGroup.reinitialisePosition();
                }
            }
        }
    }

    public void addPacmanToFantome() {
        for (Fantome fantome : fantomeGroup.fantomes) {
            fantome.setMap(MAP);
            fantome.pacman = PACMAN;
        }
    }

    public void renderCompetences() {
        //tirer
        if (System.currentTimeMillis()-PACMAN.debutTempsDeRechargeCompetenceTirer < 1000 * PACMAN.tempsDeRechargeCompetenceTirer) {
            int cooldown = (int) ((1000 * PACMAN.tempsDeRechargeCompetenceTirer - (System.currentTimeMillis()-PACMAN.debutTempsDeRechargeCompetenceTirer))/1000);
            controllerJouer.imageCompetenceTirer.setOpacity(0.5);
            controllerJouer.cooldownCompetenceTirer.setText(String.valueOf(cooldown));
        } else {
            controllerJouer.cooldownCompetenceTirer.setText("");
            controllerJouer.imageCompetenceTirer.setOpacity(1);
        }
        //freeze
        if (System.currentTimeMillis()-PACMAN.debutTempsDeRechargeCompetenceFreeze < 1000 * PACMAN.tempsDeRechargeCompetenceFreeze) {
            int cooldown = (int) ((1000 * PACMAN.tempsDeRechargeCompetenceFreeze - (System.currentTimeMillis()-PACMAN.debutTempsDeRechargeCompetenceFreeze))/1000);
            controllerJouer.imageCompetenceFreeze.setOpacity(0.5);
            controllerJouer.cooldownCompetenceFreeze.setText(String.valueOf(cooldown));
        } else {
            controllerJouer.cooldownCompetenceFreeze.setText("");
            controllerJouer.imageCompetenceFreeze.setOpacity(1);
        }
        //teleporteur
        if (System.currentTimeMillis()-PACMAN.debutTempsDeRechargeCompetenceTeleporteur < 1000 * PACMAN.tempsDeRechargeCompetenceTeleporteur) {
            int cooldown = (int) ((1000 * PACMAN.tempsDeRechargeCompetenceTeleporteur - (System.currentTimeMillis()-PACMAN.debutTempsDeRechargeCompetenceTeleporteur))/1000);
            controllerJouer.imageCompetenceTeleporteur.setOpacity(0.5);
            controllerJouer.cooldownCompetenceTeleporteur.setText(String.valueOf(cooldown));
        } else {
            controllerJouer.cooldownCompetenceTeleporteur.setText("");
            controllerJouer.imageCompetenceTeleporteur.setOpacity(1);
        }
    }
}
