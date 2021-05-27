package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pacman.model.ScoreModel;
import java.io.IOException;

public class ControllerScore extends Controller {

    public Label Labscore1;
    public Label Labscore2;
    public Label Labscore3;
    public Label Labscore4;
    public Label Labscore5;
    public Label Nom1;
    public Label Nom2;
    public Label Nom3;
    public Label Nom4;
    public Label Nom5;

    public ScoreModel scoreModel;

    public ControllerScore() {
        super();
    }

    @FXML
    public void initialize() throws IOException {
        scoreModel = new ScoreModel();

        scoreModel.lectureTxt();
        scoreModel.triTab();
        scoreModel.reecritureTxt();

        //Affichage des 5 meilleurs scores
        Labscore1.setText(String.valueOf(scoreModel.tab[0][1]));
        Labscore2.setText(String.valueOf(scoreModel.tab[1][1]));
        Labscore3.setText(String.valueOf(scoreModel.tab[2][1]));
        Labscore4.setText(String.valueOf(scoreModel.tab[3][1]));
        Labscore5.setText(String.valueOf(scoreModel.tab[4][1]));

        Nom1.setText(String.valueOf(scoreModel.tab[0][0]));
        Nom2.setText(String.valueOf(scoreModel.tab[1][0]));
        Nom3.setText(String.valueOf(scoreModel.tab[2][0]));
        Nom4.setText(String.valueOf(scoreModel.tab[3][0]));
        Nom5.setText(String.valueOf(scoreModel.tab[4][0]));
    }

    @Override
    public void switchToScene(ActionEvent event) throws IOException {
        super.switchToScene(event);
        scoreModel = null;
    }
}




