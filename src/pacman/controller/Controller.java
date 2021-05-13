package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pacman.model.ModelMusic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;


public class Controller {
ModelMusic modelMusic;


    public Controller() {
        modelMusic = new ModelMusic();
        modelMusic.music("theme", true);
    }


    @FXML
    public void switchToScene(ActionEvent event) throws IOException {
        modelMusic.stopAllMusic();
        System.out.println(((Node) event.getSource()).getId());
        Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/"+ ((Node) event.getSource()).getId() +".fxml")));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(scoreView));
        primaryStage.sizeToScene();
        primaryStage.show();
        scoreView.requestFocus();
    }



}