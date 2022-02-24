package com.example.demo.model.deplacement;

import javafx.scene.image.Image;

import java.util.Objects;

public class FantomeStalker extends Fantome {

    /**
     * Créé et initialise un fantôme stalker
     */
    public FantomeStalker() {
        super(241, 261);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(        "ghost1.gif"))));
        this.imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("blueghost.gif")));
        this.imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream(     "yeux.gif")));
        this.initialisation();
    }

    /**
     * Fantôme qui suit pacman 10 fois sur 14 sinon fais des déplacements aléatoires.
     */
    public void ia() {
        if (this.compteur < 10) {
            this.listeCoordoneDeplacementFant = dijkstra(false, false, this.coordoneeActuel, this.getCoordPacman());
            this.compteur++;
        } else if (compteur <= 13){
            this.iaRandom();
            this.compteur++;
        }else{
            this.compteur = 0;
            this.iaRandom();
        }
    }
}