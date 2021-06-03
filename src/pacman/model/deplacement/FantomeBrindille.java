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
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost3.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    public void ia(){
        random = rand.nextInt(6);
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
            String coordFantome = getCoordFantome();
            String coordPacman = getCoordPacman();
            if (!coordFantome.equals(coordPacman)) {
                if (!coordoneeActuel.equals(coordoneePasse)) (map.getG()).removeEdge(this.coordoneePasse, this.coordoneeActuel);
                List<String> dijkstra = DijkstraShortestPath.findPathBetween(map.g, coordFantome, coordPacman).getVertexList();
                if (!coordoneeActuel.equals(coordoneePasse)) (map.getG()).addEdge(this.coordoneePasse, this.coordoneeActuel);
                dijkstra.remove(0);
                listeCoordoneDeplacementFant = dijkstra;
            } else {
                iaFantomeAppeure();
            }
        }
    }

}
