package com.example.demo.model.deplacement;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.demo.model.Map;

public class Deplacement extends Group {
    public Map map;
    private Image image;
    private ImageView imageView;
    public final int INIT_POS_X;
    public final int INIT_POS_Y;
    private int posX;
    private int posY;

    public final int VELOCITY = 1;
    public int velocityMultiplicator;
    public int velocityMultiplicatorInitial = 2;

    public enum deplacements {AUCUN, HAUT, DROITE, BAS, GAUCHE}

    public deplacements deplacementActuel = deplacements.AUCUN;


    /**
     * Créé un déplacement
     * @param init_pos_x position horizontale initiale
     * @param init_pos_y position verticale initiale
     */
    public Deplacement(int init_pos_x, int init_pos_y) {
        this.INIT_POS_X = init_pos_x;
        this.INIT_POS_Y = init_pos_y;
    }

    /**
     * Initialise l'image du déplacement
     */
    protected void initialisation() {
        this.setPosX(INIT_POS_X);
        this.setPosY(INIT_POS_Y);
        // ajoute les gifs au imageView puis l'ajoute à la scène
        this.imageView = new ImageView();
        this.imageView.setImage(this.image);
        this.imageView.setX(this.INIT_POS_X);
        this.imageView.setY(this.INIT_POS_Y);
        this.imageView.setFitWidth(Map.TAILLE_CASE-2);
        this.imageView.setFitHeight(Map.TAILLE_CASE-2);
        this.imageView.toFront();
    }

    /**
     * déplace la position vers le haut
     */
    public void avanceHaut() {
        this.posY -= this.VELOCITY;
    }

    /**
     * déplace la position vers la droite
     */
    public void avanceDroite() {
        if (posX == 499) posX = -1;
        this.posX += this.VELOCITY;
    }

    /**
     * déplace la position vers le bas
     */
    public void avanceBas() {
        this.posY += this.VELOCITY;
    }

    /**
     * déplace la position vers la gauche
     */
    public void avanceGauche() {
        if (posX == 1) posX = 501;
        this.posX -= this.VELOCITY;
    }

    /**
     * Actualise la position de l'image
     */
    public void affichage() {
        this.imageView.setX(this.getPosX());
        this.imageView.setY(this.getPosY());
    }

    /**
     * Initialise la position
     */
    public void initPosition() {
        this.posX = this.INIT_POS_X;
        this.posY = this.INIT_POS_Y;
    }


    ////////////////////////////
    /*    GETTER ET SETTER    */
    ////////////////////////////
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(Image image) {
        this.imageView.setImage(image);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
