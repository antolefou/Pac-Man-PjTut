package pacman.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public int score = 0;
    public int nbVie = 3;
    public boolean enVie = true;

    public Pacman() {

        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanLeft.gif"));
        affichePacman();
    }
    public void affichePacman() {
        imageView = new ImageView(this.imagePacmanGauche);
        imageView.setX(241);
        imageView.setY(321);
        imageView.setFitWidth(Map.TAILLE_CASE-2);
        imageView.setFitHeight(Map.TAILLE_CASE-2);
        this.getChildren().add(imageView);
    }

    public void avanceDroite() {
        this.imageView.setImage(this.imagePacmanDroite);
        if (this.imageView.getX() == 501)
            this.imageView.setX(1);
        this.imageView.setX(this.imageView.getX() + this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());

    }
    public void avanceBas() {
        this.imageView.setImage(this.imagePacmanBas);
        this.imageView.setY(this.imageView.getY() + this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    public void avanceGauche() {
        this.imageView.setImage(this.imagePacmanGauche);
//        System.out.println("pacman pos x " +this.imageView.getX());
        if (this.imageView.getX() == 1)
            this.imageView.setX(501);
        this.imageView.setX(this.imageView.getX() - this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
    }
    public void avanceHaut() {
        this.imageView.setImage(this.imagePacmanHaut);
        this.imageView.setY(this.imageView.getY() - this.velocity);
//        System.out.println("X:  " + this.imageView.getX() + "    Y:  " + this.imageView.getY());
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

    public void initPosition() {
        imageView.setX(241);
        imageView.setY(321);
    }
}
