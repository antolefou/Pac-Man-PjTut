package pacman.model.deplacement;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pacman.model.Map;

public class Deplacement extends Group {
    public Map map;
    private Image image;
    private ImageView imageView;
    public final int INIT_POS_X;
    public final int INIT_POS_Y;
    private int posX;
    private int posY;

    public final int VELOCITY = 1;
    public int velocityMultiplicator = 2;

    public enum deplacements {AUCUN, HAUT, DROITE, BAS, GAUCHE}

    public deplacements deplacementActuel = deplacements.AUCUN;



    public Deplacement(int init_pos_x, int init_pos_y) {
        this.INIT_POS_X = init_pos_x;
        this.INIT_POS_Y = init_pos_y;
    }

    protected void initialisation() {
        this.setPosX(INIT_POS_X);
        this.setPosY(INIT_POS_Y);
        // ajoute les gifs au imageView puis l'ajoute à la scène
        this.imageView = new ImageView();
        this.imageView.setImage(this.image);
        this.imageView.setX(INIT_POS_X);
        this.imageView.setY(INIT_POS_Y);
        this.imageView.setFitWidth(Map.TAILLE_CASE-2);
        this.imageView.setFitHeight(Map.TAILLE_CASE-2);
        this.imageView.toFront();
//        this.getChildren().add(imageView);
//        System.out.println(this.getChildren());
    }


    public void avanceHaut() {
        this.posY -= this.VELOCITY;
    }

    public void avanceDroite() {
        if (posX == 501) posX = 1;
        this.posX += this.VELOCITY;;
    }

    public void avanceBas() {
        this.posY += this.VELOCITY;
    }

    public void avanceGauche() {
        if (posX == 1) posX = 501;
        this.posX -= this.VELOCITY;
    }

    public void affichage() {
        this.imageView.setX(this.getPosX());
        this.imageView.setY(this.getPosY());
    }

    public void initPosition() {
        posX = INIT_POS_X;
        posY = INIT_POS_Y;
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
