package pacman.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.List;
import java.util.Objects;

public class FantomeSardoche extends Fantome {

    public FantomeSardoche() {
        super(241, 261);
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost1.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia(){
        if (vueSurPacman()) {
            String coordFantome = (int)(getPosX()/20) + "/" + (int)(getPosY()/20);
            String coordPacman = (int)(pacman.getPosX()/20) + "/" + (int)(pacman.getPosY()/20);
            listeCoordoneDeplacementFant = DijkstraShortestPath.findPathBetween(map.g, coordFantome, coordPacman).getVertexList();
        } else if (getPosX() > 247 || getPosY() > 241) { //IA mode campeur
            int x = getPosX() / 20;
            int y = getPosY() / 20;
            String[][] grille = map.getGrilleGraph();
            (map.getG()).removeEdge(this.coordoneePasse, this.coordoneeActuel);
            List<String> dijkstra = DijkstraShortestPath.findPathBetween(map.g, grille[x][y],coinGaucheHaut()).getVertexList();
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
