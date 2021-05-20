package pacman.model.deplacement;

import javafx.scene.image.Image;

public class Fantome extends Deplacement{
    public deplacements deplacementPasse = deplacements.AUCUN;
//    public Image =
    public Fantome(int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y);
    }
}
