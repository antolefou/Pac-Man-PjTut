package pacman.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class ModelMusic {
    //private HashMap<String, Media> media;
    HashMap<String, MediaPlayer> mediaPlayerHashMap;
    // int cpt;

    public ModelMusic() {

        mediaPlayerHashMap = new HashMap<String, MediaPlayer>();
        mediaPlayerHashMap.put("theme", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/ace.mp3")));
        mediaPlayerHashMap.put("menu", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/dancin.mp3")));
        mediaPlayerHashMap.put("chomp", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_chomp.wav")));
        mediaPlayerHashMap.put("gameOver", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/gameOver.mp3")));
        mediaPlayerHashMap.put("death", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_death.wav")));
        mediaPlayerHashMap.put("eatfruit", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_eatfruit.wav")));
        mediaPlayerHashMap.put("eatghost", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_eatghost.wav")));
        mediaPlayerHashMap.put("extrapac", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_extrapac.wav")));
    }


    public MediaPlayer music(String key) {
        return mediaPlayerHashMap.get(key);
    }


    public boolean isPlaying(String key) {
        if (mediaPlayerHashMap.get(key).getStatus() == MediaPlayer.Status.PLAYING) {
            return true;
        } else return false;
    }

    public HashMap<String, MediaPlayer> getMediaPlayerHashMap() {
        return mediaPlayerHashMap;
    }
}

