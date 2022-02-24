package com.example.demo.model.deplacement;

import javafx.scene.image.Image;
import org.jgrapht.Graphs;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FantomeBrindille extends Fantome {

    private final Random rand = new Random();

    public FantomeBrindille() {
        super(221, 281);
        this.velocityMultiplicator = this.velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(        "ghost3.gif"))));
        this.imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("blueghost.gif")));
        this.imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream(     "yeux.gif")));
        this.initialisation();
    }

    /**
     * ia fuyarde 1 fois sur 11 sinon génère une liste de coordonnée du chemin entier pour allez vers Pac-man à un instant T.
     */
    public void ia(){
        if(this.compteur<10) {
            List<String> choixPossible = Graphs.neighborListOf(map.getG(), getCoordFantome());
            choixPossible.remove(coordoneePasse);
            choixPossible.remove("12/13");
            if (choixPossible.size() > 1) {
                choixPossible.remove(dijkstra(false, false, getCoordFantome(), getCoordPacman()).get(0));
            }
            this.listeCoordoneDeplacementFant = dijkstra(false, true, this.getCoordFantome(), choixPossible.get(rand.nextInt(choixPossible.size())));
            this.compteur++;
        }
        else{
            this.listeCoordoneDeplacementFant = dijkstra(false, true, this.coordoneeActuel, this.getCoordPacman());
            this.compteur = 0;
        }
    }
}