package pacman.model.deplacement;

import javafx.scene.Group;
import pacman.model.Map;

public class FantomeGroup extends Group {
    public Fantome[] fantomes;

    public FantomeGroup() {
        fantomes = new Fantome[1];

        fantomes[0] = new FantomeCampeur();
        this.getChildren().add(fantomes[0].getImageView());
    }
}
