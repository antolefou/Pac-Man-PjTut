package pacman.model.deplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import javafx.scene.image.Image;
import pacman.model.Map;



public class Fantome extends Deplacement{
    public deplacements deplacementActuel = deplacements.AUCUN;
    public int positionXFinDeplacement;
    public int positionYFinDeplacement;
    public List<String> listeCoordoneDeplacementFant;
    public Pacman pacman;
    public String coordoneePasse = null;
    public String coordoneeActuel = null;


    public Fantome(int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y);
        this.listeCoordoneDeplacementFant = new ArrayList<>();
        this.coordoneeActuel = init_pos_x/20 + "/" + init_pos_y/20;
        this.coordoneePasse = init_pos_x/20 + "/" + init_pos_y/20;
    }

    public void getNextFinalPos(){
        String coord = this.listeCoordoneDeplacementFant.get(0);
        String[] coorXY = coord.split("/");

        int x = Integer.parseInt(coorXY[0]);
        int y = Integer.parseInt(coorXY[1]);

        this.positionXFinDeplacement = tradCoorToPx(x);
        this.positionYFinDeplacement = tradCoorToPx(y);
        this.setOrientation();
    }

    public void setOrientation(){
        System.out.println(this.getPosX());
        if(this.getPosX()>20 && this.getPosX()<480){
            if (positionXFinDeplacement - this.getPosX() < 0) this.deplacementActuel = deplacements.GAUCHE;
            else if (positionXFinDeplacement - this.getPosX() > 0) this.deplacementActuel = deplacements.DROITE;
            else if (positionYFinDeplacement - this.getPosY() < 0) this.deplacementActuel = deplacements.HAUT;
            else if (positionYFinDeplacement - this.getPosY() > 0) this.deplacementActuel = deplacements.BAS;
            else System.out.println("erreur déplcaemnt setOrientation");
        }
    }

    public int tradCoorToPx(int coordone){
        return (int) (coordone* Map.TAILLE_CASE + 1);
    }

    public void updateDeplacement() {
//        System.out.println(listeCoordoneDeplacementFant);
        if (this.listeCoordoneDeplacementFant.isEmpty()) {
//            System.out.println(listeCoordoneDeplacementFant);
            this.ia();
//            System.out.println("liste pleine"  + listeCoordoneDeplacementFant);
            getNextFinalPos();
        }
//        System.out.println("x : " + positionXFinDeplacement + "y :" + positionYFinDeplacement);
        if (doitRechargerNextPos()) {
            String tmp = this.coordoneeActuel;
            this.coordoneeActuel = this.listeCoordoneDeplacementFant.get(0);
            if (!tmp.equals(this.coordoneeActuel))
                this.coordoneePasse = tmp;

            this.listeCoordoneDeplacementFant.remove(0);
            if(this.listeCoordoneDeplacementFant.isEmpty()){
                this.ia();
            }
            getNextFinalPos();
            System.out.println("liste pleine"  + listeCoordoneDeplacementFant);
        } else if (positionXFinDeplacement != this.getPosX() || positionYFinDeplacement != this.getPosY()) {
            switch (this.deplacementActuel) {
                case HAUT:
                    if (peutAvancerVerticalement(map,-1)) this.avanceHaut();
                    break;
                case DROITE:
                    if (peutAvancerHorizontalement(map,1)) this.avanceDroite();
                    break;
                case BAS:
                    if (peutAvancerVerticalement(map,1)) this.avanceBas();
                    break;
                case GAUCHE:
                    if (peutAvancerHorizontalement(map,-1)) this.avanceGauche();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean doitRechargerNextPos(){
        return  (this.positionXFinDeplacement == this.getPosX() && this.positionYFinDeplacement == this.getPosY());
    }

    public void ia(){}


    public boolean peutAvancerHorizontalement(Map map, int i) {
        if (getPosY() % 20 == 1) {
            if ((getPosX() % 20 != 1) || (map.grid[((getPosX()/20)+i+25)%25][getPosY()/20] != Map.ValeurCase.MUR)) {
                return true;
            }
        }
        return false;
    }
    public boolean peutAvancerVerticalement(Map map, int i) {
        if (getPosX() % 20 == 1 && getPosX() > 1 && getPosX() < 500) {
            if ((getPosY() % 20 != 1) || (map.grid[getPosX()/20][(getPosY()/20)+i] != Map.ValeurCase.MUR)) {
                if (map.grid[getPosX()/20][(getPosY()/20)] != Map.ValeurCase.INTERDIT && (map.grid[getPosX()/20][(getPosY()/20)+i] == Map.ValeurCase.INTERDIT))
                    return false;
                return true;
            }
        }
        return false;
    }

    public void ancienDeplacementFantome() {
        switch (deplacementActuel) {
            case BAS:
                if (peutAvancerVerticalement(map,1))
                    ajouteAvanceDirection("BAS");
                break;
            case DROITE:
                if (peutAvancerHorizontalement(map,1))
                    ajouteAvanceDirection("DROITE");
                break;
            case HAUT:
                if (peutAvancerVerticalement(map,-1))
                    ajouteAvanceDirection("HAUT");
                break;
            case GAUCHE:
                if (peutAvancerHorizontalement(map,-1))
                    ajouteAvanceDirection("GAUCHE");
                break;
        }
    }

    public boolean estSurPacman() {
        double ratioX = (this.getPosX()*1.0) / (pacman.getPosX()*1.0);
        double ratioY = (this.getPosY()*1.0) / (pacman.getPosY()*1.0);
        if (((ratioX>0.95 && ratioX<1.05) || (ratioX<-0.95 && ratioX>-1.05)) && ((ratioY>0.95 && ratioY<1.05) || (ratioY<-0.95 && ratioY>-1.05))) {
            return true;
        }
        return false;
    }

    @Override
    public void initPosition() {
        super.initPosition();
        deplacementActuel = deplacements.AUCUN;
    }


    public void iaFantomeAppeure() {
        Random rand = new Random();
        int random;
        boolean aAvance = false;

        while (!aAvance) {
            random = rand.nextInt(4);
            switch (random) {
                case 0:
                    if (this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
                        ajouteAvanceDirection("HAUT");
                        aAvance = true;
                    }
                    break;

                case 1:
                    if (this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
                        ajouteAvanceDirection("GAUCHE");
                        aAvance = true;
                    }
                    break;

                case 2:
                    if (this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
                        ajouteAvanceDirection("BAS");
                        aAvance = true;
                    }
                    break;

                case 3:
                    if (this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
                        ajouteAvanceDirection("DROITE");
                        aAvance = true;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void ajouteAvanceDirection(String direction) {
        int x = getPosX()/20;
        int y = getPosY()/20;
        switch (direction){
            case "HAUT":
                y--;
                this.listeCoordoneDeplacementFant.add(x + "/" + y);
                break;
            case "BAS":
                y++;
                this.listeCoordoneDeplacementFant.add(x + "/" + y);
                break;
            case "GAUCHE":
                x = (x-1 +25)%25;
                this.listeCoordoneDeplacementFant.add(x + "/" + y);
                break;
            case "DROITE":
                x = (x+1 +25)%25;
                this.listeCoordoneDeplacementFant.add(x + "/" + y);
                break;
        }
    }
}
