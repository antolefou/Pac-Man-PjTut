package pacman.model.deplacement;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pacman.controller.ControllerJouer;
import pacman.model.Map;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateRender extends Thread{
    private FantomeGroup fantomeGroup = new FantomeGroup();
    private final Utilisateur UTILISATEUR;
    private final Label LABEL_SCORE;
    private final Map MAP;
    public final Pacman PACMAN;
    private ControllerJouer controllerJouer;
    /*
    private final FantomeCampeur FantomeCAMPEUR;*/
    public Thread update;
    public Thread render;

    public UpdateRender(ControllerJouer controllerJouer, Utilisateur utilisateur, Label labelScore, Map map, Pacman pacman, FantomeGroup fantomeGroup) {
        this.controllerJouer = controllerJouer;
        this.UTILISATEUR = utilisateur;
        this.LABEL_SCORE = labelScore;
        this.MAP = map;
        this.PACMAN = pacman;
        this.fantomeGroup = fantomeGroup;
        /*
        this.FantomeCAMPEUR = fantomeCampeur;*/
        initialize();
    }
    public void initialize() {
        this.PACMAN.setControllerJouer(controllerJouer);
        this.PACMAN.setMap(MAP);
        this.fantomeGroup.fantomes[0].setMap(MAP);
        this.fantomeGroup.fantomes[0].pacman = PACMAN;

//        this.fantomeGroup.fantomes[1].setMap(MAP);
//        this.fantomeGroup.fantomes[1].pacman = PACMAN;
//
//        this.fantomeGroup.fantomes[2].setMap(MAP);
//        this.fantomeGroup.fantomes[2].pacman = PACMAN;
//
//        this.fantomeGroup.fantomes[3].setMap(MAP);
//        this.fantomeGroup.fantomes[3].pacman = PACMAN;

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
        for (Fantome fantome : fantomeGroup.fantomes) {
            for (int i = 0; i< fantome.velocityMultiplicator; i++) {
                fantome.updateDeplacement();
//                  System.out.println(fantome.listeCoordoneDeplacementFant);
            }
        }
        if (MAP.aGagne()) { // réinitialise la map si tout est mangé
            PACMAN.initPosition(); // il faut rajouter init power
            PACMAN.numNiveau ++;
            MAP.recommence(PACMAN.numNiveau);
        }
    }

    private void render() {
        Platform.runLater(() -> {
            //affichage pacman
            PACMAN.affichage();
            //affichage fantomes
            for (Fantome fantome : fantomeGroup.fantomes) {
                fantome.affichage();
            }
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
        if (fantomeGroup.sontSurPacman()) {
            PACMAN.initPosition();
            PACMAN.nbVie --;
            this.controllerJouer.playMusic("death", false);
            for (Fantome fantome : fantomeGroup.fantomes) {
                fantome.initPosition();
                fantome.listeCoordoneDeplacementFant = new ArrayList<>();
            }
        }
    }
}
