package pacman.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeSardoche extends Fantome {

    public FantomeSardoche() {
        super(241, 261);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost1.gif"))));
        this.initialisation();
    }

    public void ia() {

        iaFantomeAppeure();

    }
}
