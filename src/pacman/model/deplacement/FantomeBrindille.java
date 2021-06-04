package pacman.model.deplacement;

import javafx.scene.image.Image;

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
        if (random != 0){
            if (this.getPosY() <= pacman.getPosY() && this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
                this.ajouteAvanceDirection("HAUT");
            } else if (this.getPosY() >= pacman.getPosY() && this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
                this.ajouteAvanceDirection("BAS");
            } else if (this.getPosX() <= pacman.getPosX() && this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
                this.ajouteAvanceDirection("GAUCHE");
            } else if (this.getPosX() >= pacman.getPosX() && this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
                this.ajouteAvanceDirection("DROITE");
            } else {
                iaRandom();
            }
        } else listeCoordoneDeplacementFant = dijkstra(false, true, getCoordFantome(), getCoordPacman());
    }
}
