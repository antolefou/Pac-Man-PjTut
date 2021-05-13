package pacman.model;

import javafx.scene.Group;
import pacman.model.Fantome;

public class FantomeGroup extends Group {

    public Fantome[] fantomes;

    public FantomeGroup(){
        fantomes = new Fantome[4];
        for (int i=0; i<4; i++)
            fantomes[i] = new Fantome(i+1);
        afficheFantome();
    }

    private void afficheFantome() {
        for (int i=0; i<4; i++)
            this.getChildren().add(fantomes[i].imageView);
    }
}
