package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FantomeBrindille extends Fantome {

    private int compteur = 0;
    private int random = 0;
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
        random = rand.nextInt(7);
        if (random != 5){
            if (this.getPosY() <= pacman.getPosY() && this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
                this.ajouteAvanceDirection("HAUT");
            } else if (this.getPosY() >= pacman.getPosY() && this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
                this.ajouteAvanceDirection("BAS");
            } else if (this.getPosX() <= pacman.getPosX() && this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
                this.ajouteAvanceDirection("GAUCHE");
            } else if (this.getPosX() >= pacman.getPosX() && this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
                this.ajouteAvanceDirection("DROITE");
            } else {
                if (this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
                    this.ajouteAvanceDirection("HAUT");
                } else if (this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
                    this.ajouteAvanceDirection("BAS");
                } else if (this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
                    this.ajouteAvanceDirection("GAUCHE");
                } else if (this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
                    ajouteAvanceDirection("DROITE");
                } else {
                    System.out.println("erreur");
                }
            }
        } else {
            Graph<String, DefaultEdge> graphCopie = new SimpleGraph<>(DefaultEdge.class);
            Graphs.addAllVertices(graphCopie, map.getG().vertexSet());
            Graphs.addAllEdges(graphCopie, map.getG(), map.getG().edgeSet());
            if (!coordoneeActuel.equals(coordoneePasse) && graphCopie.containsEdge(this.coordoneePasse, this.coordoneeActuel)) {
                graphCopie.removeEdge(this.coordoneePasse, this.coordoneeActuel);
                String coordFantome = getCoordFantome();
                String coordPacman = getCoordPacman();
                List<String> dijkstra = DijkstraShortestPath.findPathBetween(graphCopie, coordFantome, coordPacman).getVertexList();
                if(dijkstra.size()>1) {
                    dijkstra.remove(0);
                    listeCoordoneDeplacementFant = dijkstra;
                }else iaFantomeAppeure();
            } else {
                iaFantomeAppeure();
            }
        }
    }

}
