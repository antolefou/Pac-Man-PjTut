package pacman.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeStalker extends Fantome {

    /**
     * Créé et initialise un fantôme stalker
     */
    public FantomeStalker() {
        super(241, 261);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost1.gif"))));
        imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif")));
        imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeux.gif")));
        this.initialisation();
    }

    /**
     * Fantôme qui suit pacman 10 fois sur 14 sinon fais des déplacements aléatoires.
     */
    public void ia() {
        if (compteur < 10) {
            listeCoordoneDeplacementFant = dijkstra(false, false, this.coordoneeActuel, getCoordPacman());
            compteur++;
        } else if (compteur <= 13){
            iaRandom();
            compteur++;
        }else{
            compteur = 0;
            iaRandom();
        }
    }
}
