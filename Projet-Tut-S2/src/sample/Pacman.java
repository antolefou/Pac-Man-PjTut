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
    private Image imagePacmanHaut;
    private Image imagePacmanDroite;
    private Image imagePacmanBas;
    private Image imagePacmanGauche;
    private ImageView imageView;

    public Pacman() {

        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanLeft.gif"));
        AffichePacman();
    }
    public void AffichePacman() {

        imageView = new ImageView(this.imagePacmanGauche);
        imageView.setX(100);
        imageView.setY(100);
        imageView.setFitWidth(Map.TAILLE_CASE);
        imageView.setFitHeight(Map.TAILLE_CASE);
        this.getChildren().add(imageView);
    }

    public void AvanceDroite() {
        this.imageView.setImage(this.imagePacmanDroite);
        this.imageView.setX(this.imageView.getX()+5);
    }

    public void AvanceBas() {
        this.imageView.setImage(this.imagePacmanBas);
        this.imageView.setY(this.imageView.getY()+5);
    }
    public void AvanceGauche() {
        this.imageView.setImage(this.imagePacmanGauche);
        this.imageView.setX(this.imageView.getX()-5);
    }
    public void AvanceHaut() {
        this.imageView.setImage(this.imagePacmanHaut);
        this.imageView.setY(this.imageView.getY()-5);
    }
}
