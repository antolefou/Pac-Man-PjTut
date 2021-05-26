package pacman.model.deplacement;

import javafx.scene.Group;
import pacman.model.Map;

import java.util.ArrayList;

public class FantomeGroup extends Group {
    public Fantome[] fantomes;

    public FantomeGroup() {
        fantomes = new Fantome[4];

        fantomes[0] = new FantomeCampeur();
        fantomes[1] = new FantomeBrindille();
        fantomes[2] = new FantomeFanBoy();
        fantomes[3] = new FantomeSardoche();
        addFantomeToScene();
    }

    public void addFantomeToScene() {
        for (Fantome fantome : this.fantomes) {
            this.getChildren().add(fantome.getImageView());
        }
    }

    public boolean sontSurPacman() {
        for (Fantome fantome : this.fantomes) {
            if (fantome.estSurPacman()) return true;
        }
        return false;
    }

    public void reinitialisePosition() {
        for (Fantome fantome : this.fantomes) {
            fantome.initPosition();
            fantome.listeCoordoneDeplacementFant = new ArrayList<>();
        }
    }
}
