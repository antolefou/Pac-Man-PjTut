package pacman.model.deplacement;

import java.util.ArrayList;

import javafx.scene.image.Image;
import pacman.model.Map;



public class Fantome extends Deplacement{
    public deplacements deplacementActuel = deplacements.AUCUN;
    public int positionXFinDeplacement;
    public int positionYFinDeplacement;
    public ArrayList<String> listeCoordoneDeplacementFant = new ArrayList<>();

    public Fantome(int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y);
    }

    public void getNextFinalPos(){
        String[] coorXY = this.listeCoordoneDeplacementFant.get(0).split("/");
        this.listeCoordoneDeplacementFant.remove(0);

        int x = Integer.parseInt(coorXY[0]);
        int y = Integer.parseInt(coorXY[1]);

        this.positionXFinDeplacement = tradCoorToPx(x);
        this.positionYFinDeplacement = tradCoorToPx(y);
        setOrientation();
    }

    public void setOrientation(){
        if (positionXFinDeplacement-this.getPosX() < 0) this.deplacementActuel = deplacements.DROITE;
        else if (positionXFinDeplacement-this.getPosX() > 0) this.deplacementActuel = deplacements.GAUCHE;
        else if (positionYFinDeplacement-this.getPosY() < 0) this.deplacementActuel = deplacements.BAS;
        else this.deplacementActuel = deplacements.HAUT;
    }

    public int tradCoorToPx(int coordone){
        return (int) (coordone* Map.TAILLE_CASE + 1);
    }

    public void updateDeplacement() {

        if (this.listeCoordoneDeplacementFant.size() > 0) this.ia();

        if (doitRechargerNextPos()) getNextFinalPos();

        else if (positionXFinDeplacement != this.getPosX() && positionYFinDeplacement != this.getPosY()) {
            switch (this.deplacementActuel) {
                case HAUT:
                    avanceHaut();
                    break;
                case DROITE:
                    avanceDroite();
                    break;
                case BAS:
                    avanceBas();
                    break;
                case GAUCHE:
                    avanceGauche();
                    break;
            }
        }
    }

    public boolean doitRechargerNextPos(){
        return  (this.positionXFinDeplacement == this.getPosX() && this.positionYFinDeplacement == this.getPosY());
    }

    public void ia(){}

}
