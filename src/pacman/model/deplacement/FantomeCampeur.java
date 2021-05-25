package pacman.model.deplacement;

import javafx.scene.image.Image;
import pacman.model.Map;
import pacman.model.deplacement.Deplacement;

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
            if (peutAvancerVerticalement(map,-1) && this.deplacementActuel != deplacements.BAS) {
                avanceHaut();
                deplacementActuel = deplacements.HAUT;
            } else if (peutAvancerHorizontalement(map,-1) && deplacementActuel != deplacements.DROITE) {
                avanceGauche();
                deplacementActuel = deplacements.GAUCHE;
            } else if (peutAvancerHorizontalement(map,1) && deplacementActuel != deplacements.GAUCHE) {
                avanceDroite();
                deplacementActuel = deplacements.DROITE;
            } else if (peutAvancerVerticalement(map,1) && deplacementActuel != deplacements.HAUT) {
                avanceBas();
                deplacementActuel = deplacements.BAS;
            }
        } else {

            int random = rand.nextInt(4);

            switch (random) {
                case 0:
                    if (peutAvancerVerticalement(map,-1)) {
                        if (deplacementActuel != deplacements.BAS) {
                            avanceHaut();
                            deplacementActuel = deplacements.HAUT;
                        }
                    } else {
                        ancienDeplacementFantome();
                    }
                    break;

                case 1:
                    if (peutAvancerHorizontalement(map, -1)) {
                        if (deplacementActuel != deplacements.DROITE) {
                            avanceGauche();
                            deplacementActuel = deplacements.GAUCHE;
                        }
                    } else {
                        ancienDeplacementFantome();
                        break;
                    }
                    break;

                case 2:
                    if (peutAvancerVerticalement(map,1)) {
                        if (deplacementActuel != deplacements.HAUT) {
                            avanceBas();
                            deplacementActuel = deplacements.BAS;
                        }
                    } else {
                        ancienDeplacementFantome();
                    }
                    break;

                case 3:
                    if (peutAvancerHorizontalement(map,1)) {
                        if (deplacementActuel != deplacements.GAUCHE) {
                            avanceDroite();
                            deplacementActuel = deplacements.DROITE;
                        }
                    } else {
                        ancienDeplacementFantome();
                    }
                    break;
                default:
                    break;
            }
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
