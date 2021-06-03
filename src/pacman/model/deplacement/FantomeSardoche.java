package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.sql.SQLOutput;
import java.util.List;
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

        int x = getPosX() / 20;
        int y = getPosY() / 20;
        String[][] grille = map.getGrilleGraph();

        if (random != 0) {

            Graph<String, DefaultEdge> graphCopie = new SimpleGraph<>(DefaultEdge.class);
            Graphs.addAllVertices(graphCopie, map.getG().vertexSet());
            Graphs.addAllEdges(graphCopie, map.getG(), map.getG().edgeSet());
            if (!coordoneeActuel.equals(coordoneePasse) && graphCopie.containsEdge(this.coordoneePasse, this.coordoneeActuel)) {
                graphCopie.removeEdge(this.coordoneePasse, this.coordoneeActuel);
            }

            List<String> dijkstra = DijkstraShortestPath.findPathBetween(graphCopie, grille[x][y], pacman.getPosX() / 20 + "/" + pacman.getPosY() / 20).getVertexList();
            if(dijkstra.size()>1) {
                dijkstra.remove(0);
                listeCoordoneDeplacementFant.add(dijkstra.get(0));
            } else System.out.println("Erreur dans ia (FantomeSardoche)");
        }
        else {
            iaFantomeAppeure();
        }
    }
}
