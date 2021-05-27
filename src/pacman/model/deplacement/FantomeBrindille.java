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

    /*public void ia(){
        if (vueSurPacman()) {
            String coordFantome = (getPosX()/20) + "/" + (getPosY()/20);
            String coordPacman = (pacman.getPosX()/20) + "/" + (pacman.getPosY()/20);
            System.out.println("vue pacman : " + coordFantome + coordPacman);
            if (!coordFantome.equals(coordPacman)) {
                listeCoordoneDeplacementFant = DijkstraShortestPath.findPathBetween(map.g, coordFantome, coordPacman).getVertexList();
            }else {
                iaFantomeAppeure();
            }
        } else if (getPosX() > 247 || getPosY() > 241) { //IA mode campeur
            int x = getPosX() / 20;
            int y = getPosY() / 20;
            String[][] grille = map.getGrilleGraph();
            if (!coordoneeActuel.equals(coordoneePasse)) (map.getG()).removeEdge(this.coordoneePasse, this.coordoneeActuel);
            List<String> dijkstra = DijkstraShortestPath.findPathBetween(map.g, grille[x][y],coinGaucheHaut()).getVertexList();
            if (!coordoneeActuel.equals(coordoneePasse)) (map.getG()).addEdge(this.coordoneePasse, this.coordoneeActuel);
            dijkstra.remove(0);
//            System.out.println("calcule diskjtra");
            this.listeCoordoneDeplacementFant = dijkstra;
        } else {
            iaFantomeAppeure();
        }
    }*/

    private String coinGaucheHaut() {
        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (map.getGrilleGraph()[x][y].equals(x + "/" + y)) return x+"/"+y;
            }
        }
        return "";
    }
}
