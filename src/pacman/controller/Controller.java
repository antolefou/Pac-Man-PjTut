package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pacman.model.Model;
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
    public void playMusic(String key, boolean loop) {
        modelMusic.music(key).stop();
        try {
            if (loop) {
                modelMusic.music(key).setCycleCount(MediaPlayer.INDEFINITE);
            }
            //mediaPlayer.setRate(0.5);
            //mediaPlayer.setBalance(50);
            modelMusic.music(key).play();


        } catch (Exception e) {
            System.out.println("Erreur de Fichier");
        }


    }
    public void stopAllMusic() {
        for (String i : modelMusic.getMediaPlayerHashMap().keySet()) {
            if (modelMusic.getMediaPlayerHashMap().get(i).getStatus() == MediaPlayer.Status.PLAYING) {
                modelMusic.getMediaPlayerHashMap().get(i).stop();
            }
        }
    }

}