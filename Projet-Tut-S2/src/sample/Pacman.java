package sample;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pacman extends Group {
    public Map map;
    public Image imagePacmanHaut;
    public Image imagePacmanDroite;
    public Image imagePacmanBas;
    public Image imagePacmanGauche;
    public ImageView imageView;
    private int velocity = 2;
    public int velocityThread = 30;

    /**
     * définit les images de pac man en fonction de sa direction et l'affiche
     */
    public Pacman() {
        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanLeft.gif"));
        affichePacman();
    }

    /**
     * affiche un pac man orienté vers la gauche
     */
    public void affichePacman() {
        imageView = new ImageView(this.imagePacmanGauche);
        imageView.setX(101);
        imageView.setY(101);
        imageView.setFitWidth(Map.TAILLE_CASE-2);
        imageView.setFitHeight(Map.TAILLE_CASE-2);
        this.getChildren().add(imageView);
    }

    public void avanceDroite() {
        this.imageView.setImage(this.imagePacmanDroite);
        this.imageView.setX(this.imageView.getX() + this.velocity);
        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    public void avanceBas() {
        this.imageView.setImage(this.imagePacmanBas);
        this.imageView.setY(this.imageView.getY() + this.velocity);
        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    public void avanceGauche() {
        this.imageView.setImage(this.imagePacmanGauche);
        this.imageView.setX(this.imageView.getX() - this.velocity);
        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    public void avanceHaut() {
        this.imageView.setImage(this.imagePacmanHaut);
        this.imageView.setY(this.imageView.getY() - this.velocity);
        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public double getPacmanX() {
        return this.imageView.getX();
    }
    public double getPacmanY() {
        return this.imageView.getY();
    }
}
