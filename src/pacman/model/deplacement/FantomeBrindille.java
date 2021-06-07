package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.Graphs;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FantomeBrindille extends Fantome {

    private final Random rand = new Random();

    public FantomeBrindille() {
        super(221, 281);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost3.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia(){
        if(this.compteur<10) {
            List<String> choixPossible = Graphs.neighborListOf(map.getG(), getCoordFantome());
            choixPossible.remove(coordoneePasse);
            choixPossible.remove("12/13");
            if (choixPossible.size() > 1) {
                choixPossible.remove(dijkstra(false, false, getCoordFantome(), getCoordPacman()).get(0));
            }
            listeCoordoneDeplacementFant = dijkstra(false, true, getCoordFantome(), choixPossible.get(rand.nextInt(choixPossible.size())));
            this.compteur++;
        }
        else{
            listeCoordoneDeplacementFant = dijkstra(false, true, this.coordoneeActuel, getCoordPacman());
            this.compteur = 0;
        }
    }
}
