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
//            System.out.println("liste après IA " + listeCoordoneDeplacementFant);
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
