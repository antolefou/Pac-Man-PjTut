package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pacman.model.ModelMusic;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;



public class Controller {
    public Utilisateur utilisateur;
    public static Stage primaryStage;
    static ModelMusic modelMusic;

    public Controller() {
        this.utilisateur = new Utilisateur();
        if(Controller.modelMusic == null) {
            Controller.modelMusic = new ModelMusic();
        }
    }


    @FXML
    public void switchToScene(ActionEvent event) throws IOException {
//        System.out.println(((Node) event.getSource()).getId());
        Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/"+ ((Node) event.getSource()).getId() +".fxml")));
        if (primaryStage  == null) primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(scoreView));
        primaryStage.sizeToScene();
        primaryStage.show();
        scoreView.requestFocus();
    }

}