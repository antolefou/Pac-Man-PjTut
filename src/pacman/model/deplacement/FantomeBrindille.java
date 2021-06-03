package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FantomeBrindille extends Fantome {

    private int compteur = 0;
    private int random = 0;
    private final Random rand = new Random();

    public FantomeBrindille() {
        super(221, 281);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost3.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia(){

        iaFantomeAppeure();
    }

}
