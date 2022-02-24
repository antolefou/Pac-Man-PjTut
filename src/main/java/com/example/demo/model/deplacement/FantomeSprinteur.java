package com.example.demo.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeSprinteur extends Fantome {
    private long temps = 0;

    public FantomeSprinteur() {
        super(241, 281);
        this.velocityMultiplicatorInitial = 3;
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(        "ghost2.gif"))));
        this.imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("blueghost.gif")));
        this.imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream(     "yeux.gif")));
        this.initialisation();
    }

    /**
     * ia qui coordonnee par coordonnee va allez rapidement pendant 2s puis lentement pendant 2s vers pacman 6 fois sur 7 sinon fais un déplacement aléatoire
     */
    public void ia(){
        if (System.currentTimeMillis() - temps > 2000 && System.currentTimeMillis() - temps <= 4000 ) {
            this.velocityMultiplicator = 1;
        } else if (System.currentTimeMillis() - temps > 4000) {
            this.temps = System.currentTimeMillis();
            this.velocityMultiplicator = velocityMultiplicatorInitial;
        }
        if (compteur < 6) {
            this.listeCoordoneDeplacementFant = dijkstra(false, false, this.coordoneeActuel, getCoordPacman());
            this.compteur++;
        } else {
            this.compteur = 0;
            iaRandom();
        }
    }
}
