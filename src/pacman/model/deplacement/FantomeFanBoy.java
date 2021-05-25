package pacman.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeFanBoy extends Fantome {

    public FantomeFanBoy() {
        super(241, 281);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost2.gif"))));
        this.initialisation();
    }

    public void ia() {

        iaFantomeAppeure();

    }
}
