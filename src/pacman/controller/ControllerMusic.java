package pacman.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class ControllerMusic extends Controller {


    public ControllerMusic() {

    }


    public void play() {
        MediaPlayer mediaPlayer;
        String s = "pacman_intermission.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
