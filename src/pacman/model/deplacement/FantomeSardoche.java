package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FantomeSardoche extends Fantome {

    public FantomeSardoche() {
        super(241, 261);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost1.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia() {
        Random rand = new Random();
        int random = rand.nextInt(10);

        int x = getPosX() / 20;
        int y = getPosY() / 20;
        String[][] grille = map.getGrilleGraph();

        if (random != 0) {

            (map.getG()).removeEdge(this.coordoneePasse, this.coordoneeActuel);

            List<String> dijkstra = DijkstraShortestPath.findPathBetween(map.g, grille[x][y], pacman.getPosX() / 20 + "/" + pacman.getPosY() / 20).getVertexList();

            if (dijkstra.size() > 1)
                listeCoordoneDeplacementFant.add(dijkstra.get(1));
            else
                listeCoordoneDeplacementFant.add(dijkstra.get(0));

            if (!coordoneeActuel.equals(coordoneePasse))
                (map.getG()).addEdge(this.coordoneePasse, this.coordoneeActuel);
        }

        else {

            iaFantomeAppeure();
        }
    }
}
