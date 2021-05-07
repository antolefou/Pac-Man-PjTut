package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;


public class Controller {

    public Clip clip;

    public Controller() {

    }


    @FXML
    public void switchToScene(ActionEvent event) throws IOException {
        stopSound();
        System.out.println(((Node) event.getSource()).getId());
        Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/"+ ((Node) event.getSource()).getId() +".fxml")));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(scoreView));
        primaryStage.sizeToScene();
        primaryStage.show();
        scoreView.requestFocus();
    }


    public void stopSound() {
        if (clip != null)
            clip.stop();
    }

    public Clip playSound(String path, boolean loop) {
        File musicFile = new File(path);
        try {
            if(!musicFile.exists())
                throw new FileNotFoundException();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            return clip;
        } catch (Exception e){
            return null;
        }
    }
}