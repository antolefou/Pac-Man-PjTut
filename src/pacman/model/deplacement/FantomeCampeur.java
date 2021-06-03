package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import pacman.model.Map;
import pacman.model.deplacement.Deplacement;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FantomeCampeur extends Fantome {

    private final Random rand = new Random();

    public FantomeCampeur() {
        super(261, 281);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost4.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
        this.deplacementActuel = deplacements.GAUCHE;
    }

    public void ia(){
        if (vueSurPacman() && !estVulnerable) {
            String coordFantome = getCoordFantome();
            String coordPacman = getCoordPacman();
            if (!coordFantome.equals(coordPacman)) {
                listeCoordoneDeplacementFant.add(DijkstraShortestPath.findPathBetween(map.g, coordFantome, coordPacman).getVertexList().get(1));
            } else {
                iaFantomeAppeure();
            }
        } else if (getPosX() > 247) { //IA mode campeur
            int x = getPosX() / 20;
            int y = getPosY() / 20;
            String[][] grille = map.getGrilleGraph();
            if (!coordoneeActuel.equals(coordoneePasse)) (map.getG()).removeEdge(this.coordoneePasse, this.coordoneeActuel);
            List<String> dijkstra = DijkstraShortestPath.findPathBetween(map.g, grille[x][y], coinGaucheHaut()).getVertexList();
            if (!coordoneeActuel.equals(coordoneePasse)) (map.getG()).addEdge(this.coordoneePasse, this.coordoneeActuel);
            dijkstra.remove(0);
//            System.out.println("calcule diskjtra");
            this.listeCoordoneDeplacementFant = dijkstra;
        } else {
            iaFantomeAppeure();
        }
    }

    private String coinGaucheHaut() {
        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (map.getGrilleGraph()[x][y].equals(x + "/" + y)) return x+"/"+y;
            }
        }
        return "";
    }



}
