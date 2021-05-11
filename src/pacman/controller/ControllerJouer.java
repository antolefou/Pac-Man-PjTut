package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pacman.model.ScoreModel;
import pacman.view.Deplacement;

import java.awt.*;
import java.io.IOException;

public class ControllerJouer extends Controller  implements EventHandler<KeyEvent> {

    @FXML public pacman.view.Pacman pacman;
    @FXML public pacman.view.Map map;
    public Deplacement deplacement;
    @FXML public Label meilleurscore;
    @FXML public Label affichageScore = new Label();

    public ControllerJouer() {
        clip = playSound("src/pacman/ressources/music/pacman_chomp.wav", true);
    }

    @FXML
    public void initialize() throws IOException {
        affichageScore.setText(String.valueOf(pacman.score));
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.lectureFichierTxt();
        scoreModel.triScores();
        scoreModel.reecritureFichierTxt();
        int MS = scoreModel.getMeilleurScore();
        meilleurscore.setText(String.valueOf(MS));
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        affichageScore.setText(String.valueOf(pacman.score));
        KeyCode code = keyEvent.getCode();
        if (pacman != null && map != null) {
            if (deplacement == null) {
                System.out.println("deplacement null");
                deplacement = new Deplacement(map, pacman, this);
                map.mapGenerator.afficheFinal();
                deplacement.start();
            }
            if (code == KeyCode.UP) {
                deplacement.deplacementFutur = Deplacement.Deplacements.HAUT;
            } else if (code == KeyCode.RIGHT) {
                deplacement.deplacementFutur = Deplacement.Deplacements.DROITE;
            } else if (code == KeyCode.DOWN) {
                deplacement.deplacementFutur = Deplacement.Deplacements.BAS;
            } else if (code == KeyCode.LEFT) {
                deplacement.deplacementFutur = Deplacement.Deplacements.GAUCHE;
            }
        }
    }
}
