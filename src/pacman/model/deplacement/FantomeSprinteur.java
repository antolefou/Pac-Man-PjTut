package pacman.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class FantomeSprinteur extends Fantome {
    private long temps = 0;

    public FantomeSprinteur() {
        super(241, 281);
        this.velocityMultiplicatorInitial = 3;
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost2.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia(){
        if (System.currentTimeMillis() - temps > 2000) {
            this.velocityMultiplicator = 1;
        }
        if (System.currentTimeMillis() - temps > 4000) {
            temps = System.currentTimeMillis();
            this.velocityMultiplicator = velocityMultiplicatorInitial;
        }
        Random rand = new Random();
        int random = rand.nextInt(3);
        if (random != 0) {
            listeCoordoneDeplacementFant = dijkstra(false, false, getCoordFantome(), getCoordPacman());
            if(this.listeCoordoneDeplacementFant.isEmpty()) System.out.println("ia Sprinteur renvoie liste vide");

        } else {
            iaRandom();
        }
    }


}
