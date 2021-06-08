package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pacman.controller.ControllerJouer;
import pacman.model.Map;
import pacman.model.Utilisateur;

import java.io.IOException;

public class UpdateRender extends Thread{
    private FantomeGroup fantomeGroup;
    private final Utilisateur UTILISATEUR;
    private final Label LABEL_SCORE;
    private final Map MAP;
    public final Pacman PACMAN;
    private ControllerJouer controllerJouer;

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
        PACMAN.competenceADeverouillee = this.UTILISATEUR.niveauCompetenceTirer>=0;
        PACMAN.competenceBDeverouillee = this.UTILISATEUR.niveauCompetenceFreeze >=0;
        PACMAN.competenceCDeverouillee = this.UTILISATEUR.niveauCompetenceTeleporteur>=0;
    }

    public void jouer() {
//        Thred update
        this.update = new Thread(() -> {
            while (PACMAN.enVie) {
                update();
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
//            start = System.nanoTime();
//            stop = System.nanoTime();
//            System.out.println(stop - start);
    }

    private void update() {
//        System.out.println("Pacman x " + pacman.getPosX() + "    y " + pacman.getPosY());
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
        if (MAP.aGagne()) { // réinitialise la map si tout est mangé
            PACMAN.initPosition(); // il faut rajouter init power
            PACMAN.deplacementActuel = Deplacement.deplacements.AUCUN;
            PACMAN.compteurFantomeMange = 0;
            PACMAN.numNiveau ++;
            fantomeGroup.reinitialisePosition();
            Platform.runLater(() -> {
                MAP.creeMapAleatoire(PACMAN.numNiveau);
            });
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
                    PACMAN.compteurFantomeMange++;
                } else if (fantome.etat != Fantome.ValeurEtat.MORT){
                    PACMAN.compteurFantomeMange = 0;
                    PACMAN.initPosition();
                    PACMAN.nbVie--;
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
}
