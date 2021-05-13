package pacman.controller;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pacman.model.FantomeGroup;
import pacman.model.Map;
import pacman.model.Pacman;
import pacman.model.ScoreModel;

import java.io.IOException;

public class ControllerJouer extends Controller  implements EventHandler<KeyEvent> {

    @FXML public Pacman pacman;
    @FXML public Map map;
    public ControllerDeplacement deplacement;
    @FXML public Label meilleurscore;
    @FXML public Label affichageScore;
    @FXML public FantomeGroup fantomeGroup;

    public ControllerJouer() {
        modelMusic.music("chomp", true);
    }

    @FXML
    public void initialize() throws IOException {
        affichageScore.setText(String.valueOf(pacman.score));
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.lectureTxt();
        scoreModel.triTab();
        scoreModel.reecritureTxt();
        int MS = scoreModel.getMeilleurScore();
        meilleurscore.setText(String.valueOf(MS));
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        affichageScore.setText(String.valueOf(pacman.score));
        KeyCode code = keyEvent.getCode();
        if (pacman != null && map != null) {
            if (deplacement == null) {
//                System.out.println("deplacement null");
                deplacement = new ControllerDeplacement(map, pacman, fantomeGroup, this);
                deplacement.start();
            }
            if (code == KeyCode.UP) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.HAUT;
            } else if (code == KeyCode.RIGHT) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.DROITE;
            } else if (code == KeyCode.DOWN) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.BAS;
            } else if (code == KeyCode.LEFT) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.GAUCHE;
            }
        }
    }
}
