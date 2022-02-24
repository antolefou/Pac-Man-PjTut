package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

import com.example.demo.model.ScoreModel;

public class ControllerScore extends Controller {

//     Label du score et du nom du joueur
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
//    score model
    public ScoreModel scoreModel;

    public ControllerScore() {
        super();
    }

    /**
     * tri le tableau et affiche les 5 meilleurs scores
     * @throws IOException retourne une exception si le fichier score.txt n'est pas trouvé
     */
    @FXML
    public void initialize() throws IOException {
        this.scoreModel = new ScoreModel();

        this.scoreModel.lectureTxt();
        this.scoreModel.triTab();
        this.scoreModel.reecritureTxt();

        //Affichage des 5 meilleurs scores
        this.Labscore1.setText(String.valueOf(this.scoreModel.tab[0][1]));
        this.Labscore2.setText(String.valueOf(this.scoreModel.tab[1][1]));
        this.Labscore3.setText(String.valueOf(this.scoreModel.tab[2][1]));
        this.Labscore4.setText(String.valueOf(this.scoreModel.tab[3][1]));
        this.Labscore5.setText(String.valueOf(this.scoreModel.tab[4][1]));

        this.Nom1.setText(String.valueOf(scoreModel.tab[0][0]));
        this.Nom2.setText(String.valueOf(scoreModel.tab[1][0]));
        this.Nom3.setText(String.valueOf(scoreModel.tab[2][0]));
        this.Nom4.setText(String.valueOf(scoreModel.tab[3][0]));
        this.Nom5.setText(String.valueOf(scoreModel.tab[4][0]));
    }

    /**
     * Change la scène suivant l'évènement du bouton
     * @param event évènement du bouton
     * @throws IOException retourne une exception si ne trouve pas la scène
     */
    @Override
    public void switchToScene(ActionEvent event) throws IOException {
        super.switchToScene(event);
        this.scoreModel = null;
    }
}