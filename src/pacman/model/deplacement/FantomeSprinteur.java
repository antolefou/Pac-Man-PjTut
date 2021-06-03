package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.List;
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
        System.out.println(System.currentTimeMillis() - temps);
        if (temps == 0) temps = System.currentTimeMillis();
        if (System.currentTimeMillis() - temps > 2000) {
            this.velocityMultiplicator = 1;
        }
        if (System.currentTimeMillis() - temps > 4000) {
            temps = System.currentTimeMillis();
            this.velocityMultiplicator = velocityMultiplicatorInitial;
        }
        Random rand = new Random();
        int random = rand.nextInt(5);

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
        } else {

            iaFantomeAppeure();
        }
    }


}
