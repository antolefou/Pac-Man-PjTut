package pacman.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

import pacman.model.Map;
import pacman.model.Utilisateur;
import pacman.model.deplacement.Pacman;

public class ControllerMenu extends Controller{
//    pacman et map
    @FXML public Pacman pacman;
    @FXML public Map map;
//    fleches vertes du menu
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
        this.initUtilisateur();
    }

    /**
     * initialise l'utilisateur
     */
    private void initUtilisateur() {
        this.utilisateur = new Utilisateur();
        this.utilisateur.reinitialiseCompetencesUtilisateur();
        this.utilisateur.ecritureUtilisateur();
    }

    /**
     * Enlève toutes les flèches
     */
    @FXML
    public void enlever_fleches() {
        this.fleche1.setImage(null);
        this.fleche2.setImage(null);
        this.fleche3.setImage(null);
        this.fleche4.setImage(null);
        this.fleche5.setImage(null);
    }

    /**
     * Affiche la flèche du premier élèment du menu
     */
    @FXML
    public void affiche_fleche1() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        this.fleche1.setImage(image);
    }

    /**
     * Affiche la flèche du deuxième élèment du menu
     */
    @FXML
    public void affiche_fleche2() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        this.fleche2.setImage(image);
    }

    /**
     * Affiche la flèche du troisième élèment du menu
     */
    @FXML
    public void affiche_fleche3() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        this.fleche3.setImage(image);
    }

    /**
     * Affiche la flèche du quatrième élèment du menu
     */
    @FXML
    public void affiche_fleche4() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        this.fleche4.setImage(image);
    }

    /**
     * Affiche la flèche du cinquième élèment du menu
     */
    @FXML
    public void affiche_fleche5() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Menu/pacman_fleche.png")));
        this.fleche5.setImage(image);
    }

    /**
     * Quitte l'application.
     */
    @FXML
    public void quitter() {
        Platform.exit();
        System.exit(0);
    }
}