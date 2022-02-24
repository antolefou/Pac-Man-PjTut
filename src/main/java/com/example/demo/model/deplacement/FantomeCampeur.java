package com.example.demo.model.deplacement;

import javafx.scene.image.Image;
import com.example.demo.model.Map;

import java.util.Objects;

public class FantomeCampeur extends Fantome {

    public FantomeCampeur() {
        super(261, 281);
        this.velocityMultiplicator = velocityMultiplicatorInitial;
        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(        "ghost4.gif"))));
        this.imageBlueGhost = new Image(Objects.requireNonNull(getClass().getResourceAsStream("blueghost.gif")));
        this.imageMort = new Image(Objects.requireNonNull(getClass().getResourceAsStream(     "yeux.gif")));
        this.initialisation();
        this.deplacementActuel = deplacements.GAUCHE;
    }

    /**
     * Permet de savoir si il voit pacman
     * @return
     */
    public boolean vueSurPacman() {
        for (int i=0; i < 23; i++) {
            String posPacman = Math.round(this.pacman.getPosX() * 0.0499) + "/" + Math.round(this.pacman.getPosY() * 0.0499);
            switch (this.deplacementActuel) {
                case HAUT:
                    if (this.map.grid[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)-i] == Map.ValeurCase.MUR){
                        return false;
                    }
                    else if (this.map.grilleGraph[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)-i].equals(posPacman)) {
                        return true;
                    }
                    break;
                case GAUCHE:
                    if (this.map.grid[(int) ((Math.round(getPosX()) * 0.0499)-i+25)%25][(int) (Math.round(getPosY()) * 0.0499)] == Map.ValeurCase.MUR) {
                        return false;
                    }
                    else if (this.map.grilleGraph[((int) (Math.round(getPosX()) * 0.0499)-i+25)%25][(int) (Math.round(getPosY()) * 0.0499)].equals(posPacman)) {
                        return true;
                    }
                    break;
                case DROITE:
                    if (this.map.grid[(int) ((Math.round(getPosX()) * 0.0499)+i+25)%25][(int) (Math.round(getPosY()) * 0.0499)] == Map.ValeurCase.MUR || (Math.round(getPosX() * 0.0499)+i)%25 == 0.0) {
                        return false;
                    }
                    else if (this.map.grilleGraph[((int) (Math.round(getPosX()) * 0.0499)+i+25)%25][(int) (Math.round(getPosY()) * 0.0499)].equals(posPacman)) {
                        return true;
                    }
                    break;
                case BAS:
                    if (this.map.grid[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)+i] == Map.ValeurCase.MUR){
                        return false;
                    }
                    else if (this.map.grilleGraph[(int) (Math.round(getPosX()) * 0.0499)][(int) (Math.round(getPosY()) * 0.0499)+i].equals(posPacman)) {
                        return true;
                    }
                    break;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * ia qui par ordre de préfèrence va  :
     * - allez vers pacman si il le voit
     * - allez dans le coin en haut à gauche si il n'est pas dans sa zone (partie gauche de la map)
     * - se déplacer aléatoirement
     * coordonée par cordonnée.
     */
    public void ia(){
        if (this.vueSurPacman()) this.listeCoordoneDeplacementFant = dijkstra(false, false, this.getCoordFantome(), this.getCoordPacman());
        else if (this.getPosX() > 247) { //IA mode campeur
            this.listeCoordoneDeplacementFant = dijkstra(false, false, this.getCoordFantome(), this.coinGaucheHaut());
            if(this.listeCoordoneDeplacementFant.isEmpty()) System.out.println("ia Campeur renvoie liste vide");
        } else {
            iaRandom();
        }
    }

    /**
     * Cherche le coin en haut à gauche le plus haut de la map
     * @return
     */
    private String coinGaucheHaut() {
        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (map.getGrilleGraph()[x][y].equals(x + "/" + y)) return x+"/"+y;
            }
        }
        return "";
    }
}