package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;


public class Controller {
    public Utilisateur utilisateur;

    public Controller() {
        this.utilisateur = new Utilisateur();
    }


    @FXML
    public void switchToScene(ActionEvent event) throws IOException {
//        System.out.println(((Node) event.getSource()).getId());
        Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/"+ ((Node) event.getSource()).getId() +".fxml")));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(scoreView));
        primaryStage.sizeToScene();
        primaryStage.show();
        scoreView.requestFocus();
    }
}