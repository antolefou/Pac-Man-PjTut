package pacman.model.deplacement;

import javafx.scene.image.Image;
import pacman.model.Map;
import pacman.model.deplacement.Deplacement;

import java.util.Objects;

public class FantomeCampeur extends Fantome {


    public FantomeCampeur() {
        super(261, 281);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost4.gif"))));
        this.initialisation();
    }


    public boolean peutAvancerHorizontalement(int i) {
        double fantomeX = this.getPosX();
        double fantomeY = this.getPosY();
        if (fantomeY % 20 == 1) {
            int x = ((((int) fantomeX / 20) + i) + 25) % 25;
            int y = ((int) fantomeY / 20);
            if ((fantomeX % 20 != 1) || (map.grid[x][y] != Map.ValeurCase.MUR )) {
                return true;
            }
        }
        return false;
    }
    public boolean peutAvancerVerticalement(int i) {
        int fantomeX = this.getPosX();
        int fantomeY = this.getPosY();
        if (fantomeX % 20 == 1 && fantomeX > 1 && fantomeX < 500) {
            if ((fantomeY % 20 != 1) || (map.grid[(int)fantomeY/20][((int)fantomeX/20)+i] != Map.ValeurCase.MUR)) {
                return true;
            }
        }
        return false;
    }

    public void updateDeplacement(int pacmanX, int pacmanY) {
        if (peutAvancerHorizontalement(-1))
            avanceGauche();
    }
}
