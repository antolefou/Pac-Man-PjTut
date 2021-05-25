package pacman.model.deplacement;

import javafx.scene.Group;
import pacman.model.Map;

public class FantomeGroup extends Group {
    public Fantome[] fantomes;

    public FantomeGroup() {
        fantomes = new Fantome[4];

        fantomes[0] = new FantomeCampeur();
        this.getChildren().add(fantomes[0].getImageView());

        fantomes[1] = new FantomeBrindille();
        this.getChildren().add(fantomes[1].getImageView());

        fantomes[2] = new FantomeFanBoy();
        this.getChildren().add(fantomes[2].getImageView());

        fantomes[3] = new FantomeSardoche();
        this.getChildren().add(fantomes[3].getImageView());
    }

    public boolean sontSurPacman() {
        for (Fantome fantome : this.fantomes) {
            if (fantome.estSurPacman()) return true;
        }
        return false;
    }
}
