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
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost4.gif"))));
        this.initialisation();
    }

    public void ia(){

        if (vueSurPacman()) {
            String coordFantome = Math.round(getPosX() * 0.0499) + "/" + Math.round(getPosY() * 0.0499);
            String coordPacman = Math.round(pacman.getPosX() * 0.0499) + "/" + Math.round(pacman.getPosY()*0.0499);


//                    List dijkstra = DijkstraShortestPath.findPathBetween(g, grilleGraph[pixToCoord(posActuelleFantomeX())][pixToCoord(posActuelleFantomeY()] , grilleGraph[12][15]);
//                    while(dijkstra.get(0) != null) {
//                        String split = dijkstra.get(0).split("/");
//                        String x = coordToPixX(split[0]);
//                        String y = coordToPixY(split[1]);
//                        fantome.seDeplacerVers(x, y);
//                        dijkstra.remove(0);
//                    }
        }

        //IA mode campeur
        else if (getPosX() > 247 || getPosY() > 241) {
            int x = getPosX()/20;
            int y = getPosY()/20;
            String[][] grille = map.getGrilleGraph();
            Graph<String, DefaultEdge> graphFantome = map.getG();
            graphFantome.removeVertex(this.coordoneePasse);
            List<String> dijkstra = DijkstraShortestPath.findPathBetween(graphFantome, grille[x][y], grille[1][1]).getVertexList();
            dijkstra.remove(0);
            this.listeCoordoneDeplacementFant = dijkstra;
        } else {
            iaFantomeAppeure();
        }
    }

    public boolean vueSurPacman() {
        for (int i=0; i < 23; i++) {
            String posPacman = Math.round(pacman.getPosX() * 0.0499) + "/" + Math.round(pacman.getPosY() * 0.0499);
            switch (deplacementActuel) {
                case HAUT:
                    if (map.grid[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)-i] == Map.ValeurCase.MUR){
                        return false;
                    }
                    else if (map.grilleGraph[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)-i].equals(posPacman)) {
                        return true;
                    }
                    break;
                case GAUCHE:
                    if (map.grid[(int) ((Math.round(getPosX()) * 0.0499)-i+25)%25][(int) (Math.round(getPosY()) * 0.0499)] == Map.ValeurCase.MUR) {
                        return false;
                    }
                    else if (map.grilleGraph[((int) (Math.round(getPosX()) * 0.0499)-i+25)%25][(int) (Math.round(getPosY()) * 0.0499)].equals(posPacman)) {
                        return true;
                    }
                    break;
                case DROITE:
                    if (map.grid[(int) ((Math.round(getPosX()) * 0.0499)+i+25)%25][(int) (Math.round(getPosY()) * 0.0499)] == Map.ValeurCase.MUR || (Math.round(getPosX() * 0.0499)+i)%25 == 0.0) {
                        return false;
                    }
                    else if (map.grilleGraph[((int) (Math.round(getPosX()) * 0.0499)+i+25)%25][(int) (Math.round(getPosY()) * 0.0499)].equals(posPacman)) {
                        return true;
                    }
                    break;
                case BAS:
                    if (map.grid[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)+i] == Map.ValeurCase.MUR){
                        return false;
                    }
                    else if (map.grilleGraph[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)+i].equals(posPacman)) {
                        return true;
                    }
                    break;
                default:
                    return false;
            }
        }
        return false;

    }

}
