package com.example.demo.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class ModelMusic {
    //private HashMap<String, Media> media;
    HashMap<String, MediaPlayer> mediaPlayerHashMap;
    // int cpt;

    public ModelMusic() {
        //              A mettre si vous êtes sur windows
        // String system = "/" + System.getProperty("user.dir").replaceAll("\\\\", "/");
        //              A mettre si vous êtes sur Linux
//        String system = System.getProperty("user.dir");
        this.mediaPlayerHashMap = new HashMap<>();
        try {
            this.mediaPlayerHashMap.put("theme", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("ace.mp3")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("menu", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("dancin.mp3")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("chomp", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("pacman_chomp.wav")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("gameOver", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("gameOver.mp3")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("death", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("pacman_death.wav")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("eatfruit", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("pacman_eatfruit.wav")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("eatghost", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("pacman_eatghost.wav")).toURI().toString()   )));
            this.mediaPlayerHashMap.put("extrapac", new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("pacman_extrapac.wav")).toURI().toString()   )));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public MediaPlayer music(String key) {
        return mediaPlayerHashMap.get(key);
    }

    /**
     * Permet de savoir si un media est en train d'être jouer
     * @param key nom de la music
     * @return true si il joue sinon false
     */
    public boolean isPlaying(String key) {
        return this.mediaPlayerHashMap.get(key).getStatus() == MediaPlayer.Status.PLAYING;
    }

    public HashMap<String, MediaPlayer> getMediaPlayerHashMap() {
        return this.mediaPlayerHashMap;
    }
}

