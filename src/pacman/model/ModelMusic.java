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

        mediaPlayerHashMap.put("death", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_death.wav")));
        mediaPlayerHashMap.put("eatfruit", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_eatfruit.wav")));
        mediaPlayerHashMap.put("eatghost", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_eatghost.wav")));
        mediaPlayerHashMap.put("extrapac", new MediaPlayer(new Media("file://" + System.getProperty("user.dir") + "/src/pacman/ressources/music/pacman_extrapac.wav")));

//        for (String i : mediaPlayerHashMap.keySet()) {
//            System.out.println(mediaPlayerHashMap.get(i));
//        }
    }


    public void music(String key, boolean loop) {
        try {
            if (loop) {
                mediaPlayerHashMap.get(key).setCycleCount(MediaPlayer.INDEFINITE);
            }
            //mediaPlayer.setRate(0.5);
            //mediaPlayer.setBalance(50);
            System.out.println(mediaPlayerHashMap.get(key).getVolume());
            mediaPlayerHashMap.get(key).play();


        } catch (Exception e) {
            System.out.println("Erreur de Fichier");
        }


    }

    public void stopMusic(String key) {
        mediaPlayerHashMap.get(key).stop();
    }

    public void stopAllMusic() {
        for (String i : mediaPlayerHashMap.keySet()) {
            if (mediaPlayerHashMap.get(i).getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerHashMap.get(i).stop();
            }
        }
    }

    public void stopAllMusicWhithout(String key) {
        for (String i : mediaPlayerHashMap.keySet()) {
            if (mediaPlayerHashMap.get(i).getStatus() == MediaPlayer.Status.PLAYING && mediaPlayerHashMap.get(key) != mediaPlayerHashMap.get(i)) {
                mediaPlayerHashMap.get(i).stop();
            }
        }

    }

    public void pauseMusic(String key) {
        if (mediaPlayerHashMap.get(key).getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerHashMap.get(key).pause();
        }
    }

    public boolean isPlaying(String key) {
        if (mediaPlayerHashMap.get(key).getStatus() == MediaPlayer.Status.PLAYING) {
            return true;
        } else return false;
    }

    public void setVolume(String key, double value) {
        mediaPlayerHashMap.get(key).setVolume(value);
    }

    public void music(String key, boolean loop, double value) {
        try {
            mediaPlayerHashMap.get(key).setVolume(value);
            if (loop) {
                mediaPlayerHashMap.get(key).setCycleCount(MediaPlayer.INDEFINITE);
            }

            //mediaPlayer.setRate(0.5);
            //mediaPlayer.setBalance(50);
            //mediaPlayerHashMap.get(key).play();
            //mediaPlayerHashMap.get(key).pause();
            //mediaPlayerHashMap.get(key).setVolume(value);
            mediaPlayerHashMap.get(key).play();




        } catch (Exception e) {
            System.out.println("Erreur de Fichier");
        }


    }
}

