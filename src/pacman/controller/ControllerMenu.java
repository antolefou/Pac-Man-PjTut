package pacman.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pacman.model.Map;
import pacman.model.deplacement.Pacman;

import java.util.Objects;

public class ControllerMenu extends Controller{
    @FXML public Pacman pacman;
    @FXML public Map map;
    @FXML ImageView fleche1;
    @FXML ImageView fleche2;
    @FXML ImageView fleche3;
    @FXML ImageView fleche4;
    @FXML ImageView fleche5;


    public ControllerMenu() {
        super();
        if (!modelMusic.isPlaying("menu")) {
            this.stopAllMusic();
            this.playMusic("menu", true);
        }
    }

    public void affiche_fleche1() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        fleche1.setImage(image);
    }
    public void enlever_fleche1() {
        fleche1.setImage(null);
    }

    public void affiche_fleche2() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        fleche2.setImage(image);
    }
    public void enlever_fleche2() {
        fleche2.setImage(null);
    }

    public void affiche_fleche3() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        fleche3.setImage(image);
    }
    public void enlever_fleche3() {
        fleche3.setImage(null);
    }

    public void affiche_fleche4() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        fleche4.setImage(image);
    }
    public void enlever_fleche4() {
        fleche4.setImage(null);
    }

    public void affiche_fleche5() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        fleche5.setImage(image);
    }
    public void enlever_fleche5() {
        fleche5.setImage(null);
    }

    public void quitter() {
        Platform.exit();
        System.exit(0);
    }
}
