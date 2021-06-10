package pacman.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class ModelMusic {
    //private HashMap<String, Media> media;
    HashMap<String, MediaPlayer> mediaPlayerHashMap;
    // int cpt;

    public ModelMusic() {
        //              A mettre si vous êtes sur windows
        // String system = "/" + System.getProperty("user.dir").replaceAll("\\\\", "/");
        //              A mettre si vous êtes sur Linux
        String system = System.getProperty("user.dir");
        this.mediaPlayerHashMap = new HashMap<>();
        this.mediaPlayerHashMap.put("theme", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/ace.mp3")));
        this.mediaPlayerHashMap.put("menu", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/dancin.mp3")));
        this.mediaPlayerHashMap.put("chomp", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/pacman_chomp.wav")));
        this.mediaPlayerHashMap.put("gameOver", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/gameOver.mp3")));
        this.mediaPlayerHashMap.put("death", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/pacman_death.wav")));
        this.mediaPlayerHashMap.put("eatfruit", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/pacman_eatfruit.wav")));
        this.mediaPlayerHashMap.put("eatghost", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/pacman_eatghost.wav")));
        this.mediaPlayerHashMap.put("extrapac", new MediaPlayer(new Media("file://" + system + "/src/pacman/ressources/music/pacman_extrapac.wav")));
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

