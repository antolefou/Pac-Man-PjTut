package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.util.Objects;

import com.example.demo.model.ScoreModel;

public class ControllerGameOver extends Controller{
    @FXML public TextField txtpseudo;
    public ScoreModel scoreModel = new ScoreModel();

    public ControllerGameOver() {
        super();
        this.stopAllMusic();
        this.playMusic("gameOver", true);
    }

    /**
     * Gère les actions sur le clavier du joueur, et change la scène en fonction de la touche appuier
     * @param event évènement de la touche
     * @throws IOException renvoie une exception en cas d'erreur
     */
    @FXML
    public void handle(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("choixCompetence.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
            ecritureScore();
        } else if (event.getCode() == KeyCode.ALT) {
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
            ecritureScore();
        }
    }

    /**
     * Écrit le score en fonction du pseudo dans le fichier score.txt
     * @throws IOException Retourne une exception si on a pas les droits d'écriture ou si le fichier est absent
     */
    private void ecritureScore() throws IOException {
        scoreModel.lectureTxt();
        utilisateur.setPseudoUtilisateur(txtpseudo.getText());
        for(int i =0; i<scoreModel.tab.length; i++){
            if (scoreModel.tab[i][0].equals("pseudoUt") ){
                scoreModel.tab[i][0] = utilisateur.getPseudoUtilisateur();
                scoreModel.reecritureTxt();
            }
        }
    }
}
