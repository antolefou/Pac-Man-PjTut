package pacman.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class FantomeSardoche extends Fantome {

    public FantomeSardoche() {
        super(241, 261);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost1.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia() {
        Random rand = new Random();
        int random = rand.nextInt(10);
        if (random != 0) {
            listeCoordoneDeplacementFant = dijkstra(false, false, this.coordoneeActuel, getCoordPacman());
            if(this.listeCoordoneDeplacementFant.isEmpty()) System.out.println("ia Sardoche renvoie liste vide");
        } else {
            iaRandom();
        }
    }
}
