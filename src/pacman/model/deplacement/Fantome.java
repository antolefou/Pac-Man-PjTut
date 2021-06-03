package pacman.model.deplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import pacman.model.Map;



public class Fantome extends Deplacement{
    public int numFantome;
    public deplacements deplacementActuel = deplacements.AUCUN;
    public int positionXFinDeplacement;
    public int positionYFinDeplacement;
    public List<String> listeCoordoneDeplacementFant;
    public Pacman pacman;
    public String coordoneePasse = null;
    public String coordoneeActuel = null;
    // spawn -> attend un temps aléatoire, puis sort du spawn
    public enum ValeurEtat {NORMAL, SPAWN, APPEURE, MORT}
    public ValeurEtat etat;
    // spawn
    private long debutSpawn = 0L;
    private double tempsSpawn = 0;
    private boolean immobile;
    public boolean mort;
    //image
    public Image imageBlueGhost;
    public Image imageMort;

    public final int VelocityMultiplicatorAppeure = 1;

    public boolean estVulnerable;


    public Fantome(int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y);
        this.listeCoordoneDeplacementFant = new ArrayList<>();
        this.coordoneeActuel = init_pos_x/20 + "/" + init_pos_y/20;
        this.coordoneePasse = init_pos_x/20 + "/" + init_pos_y/20;

        this.etat = ValeurEtat.SPAWN;
        this.estVulnerable = false;
        this.mort = false;

    }

    public void updateDeplacement() {
        switch (etat) {
            case SPAWN:
                this.estVulnerable = false;
                if (debutSpawn == 0L) {
                    if (mort) {
                        debutSpawn = System.currentTimeMillis();
                        tempsSpawn = 0.25;
                        immobile = true;
                        mort = false;
                        listeCoordoneDeplacementFant = null;
                        this.positionXFinDeplacement = 0;
                        this.positionYFinDeplacement = 0;
                    } else {
                        debutSpawn = System.currentTimeMillis();
                        tempsSpawn = numFantome;
                        immobile = true;
                    }
                } else if(immobile && System.currentTimeMillis()-debutSpawn > 1000L * tempsSpawn) {
                    String coordFantome = getCoordFantome();
                    listeCoordoneDeplacementFant = DijkstraShortestPath.findPathBetween(map.g, coordFantome, "12/12").getVertexList();
                    immobile = false;
                    getNextFinalPos();
                } else if (!immobile && !listeCoordoneDeplacementFant.isEmpty()) {
                     avancePos();
                } else if (this.getPosX() == 241 && this.getPosY() == 241){
                    etat = ValeurEtat.NORMAL;
                    debutSpawn = 0L;
                }
                break;
            case NORMAL:
                this.estVulnerable = false;
                updateDeplacements();
                break;
            case APPEURE:
                if (listeCoordoneDeplacementFant.size()>1) {
                    for(int i = 0; i < listeCoordoneDeplacementFant.size()-1; i++){
                        this.listeCoordoneDeplacementFant.remove(i+1);
                    }
                }
                this.estVulnerable = true;
                updateDeplacements();
                break;
            case MORT:
                this.estVulnerable = false;
                if (!this.mort) {
                    String coordFantome = getCoordFantome();
                    String coordSpawn = INIT_POS_X / 20 + "/" + INIT_POS_Y / 20;
                    listeCoordoneDeplacementFant = DijkstraShortestPath.findPathBetween(map.g, coordFantome, coordSpawn).getVertexList();
                    getNextFinalPos();
                    mort = true;
                } else if (getPosX() == INIT_POS_X && getPosY() == INIT_POS_Y) {
                    etat = ValeurEtat.SPAWN;
                    this.setImageView(this.getImage());
                    velocityMultiplicator = velocityMultiplicatorInitial;
                } else {
                    getNextFinalPos();
                    avancePos();
                }
                break;
        }
    }

    public void ia() {

    }

    private void updateDeplacements() {
        if (this.listeCoordoneDeplacementFant.isEmpty()) {
            if (this.estVulnerable) this.iaFantomeAppeure();
            else this.ia();
            getNextFinalPos();
        }
        avancePos();
    }

    public void avancePos() {
        if (doitRechargerNextPos() &&  (!listeCoordoneDeplacementFant.isEmpty())) {
            String tmp = this.coordoneeActuel;
            this.coordoneeActuel = this.listeCoordoneDeplacementFant.get(0);
            if (!tmp.equals(this.coordoneeActuel))
                this.coordoneePasse = tmp;
            this.listeCoordoneDeplacementFant.remove(0);
            if (!listeCoordoneDeplacementFant.isEmpty())
                getNextFinalPos();
        }
        else if (positionXFinDeplacement != this.getPosX() || positionYFinDeplacement != this.getPosY()) {
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

    public boolean peutAvancerHorizontalement(Map map, int i) {
        if (getPosY() % 20 == 1) {
            return ((getPosX() % 20 != 1) || (map.grid[((getPosX()/20)+i+25)%25][getPosY()/20] != Map.ValeurCase.MUR));
        }
        return false;
    }
    public boolean peutAvancerVerticalement(Map map, int i) {
        if (getPosX() % 20 == 1 && getPosX() > 1 && getPosX() < 500) {
            // Renvoie vrai si le fantome est mort.
            // Renvoie vrai si il ne peut pas avoir atteint un mur.
            // Renvoie vrai si la prochaine case n'est pas un mur et que la prochaine case n'est pas interdite.
            // Pour être plus exact sur l'interdit : on vérifie si il est sur une case non interdite et que sa prochaine case est interdite dans ce cas on return false.
            return (this.mort || (getPosY() % 20 != 1) || (map.grid[getPosX()/20][(getPosY()/20)+i] != Map.ValeurCase.MUR) && !((map.grid[getPosX() / 20][(getPosY() / 20)] != Map.ValeurCase.INTERDIT) && (map.grid[getPosX() / 20][(getPosY() / 20) + i] == Map.ValeurCase.INTERDIT)));
        }
        return false;
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
        etat = ValeurEtat.SPAWN;
        debutSpawn = 0L;
    }

    public void iaFantomeAppeure() {
        ArrayList<String> listePossible = new ArrayList<>();
        if (this.peutAvancerVerticalement(map, -1) && deplacementActuel != deplacements.BAS) {
            listePossible.add("HAUT");
        }
        if (this.peutAvancerHorizontalement(map, -1) && deplacementActuel != deplacements.DROITE) {
            listePossible.add("GAUCHE");
        }
        if (this.peutAvancerVerticalement(map, 1) && deplacementActuel != deplacements.HAUT) {
            listePossible.add("BAS");
        }
        if (this.peutAvancerHorizontalement(map, 1) && deplacementActuel != deplacements.GAUCHE) {
            listePossible.add("DROITE");
        }
        Random rand = new Random();
        ajouteAvanceDirection(listePossible.get(rand.nextInt(listePossible.size())));
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

    public void getNextFinalPos(){
        if (!listeCoordoneDeplacementFant.isEmpty()) {
            String coord = this.listeCoordoneDeplacementFant.get(0);
            String[] coorXY = coord.split("/");

            int x = Integer.parseInt(coorXY[0]);
            int y = Integer.parseInt(coorXY[1]);

            this.positionXFinDeplacement = tradCoorToPx(x);
            this.positionYFinDeplacement = tradCoorToPx(y);
            this.setOrientation();
        }
    }

    public void setOrientation(){
        if(this.getPosX()>20 && this.getPosX()<480){
            if (positionXFinDeplacement - this.getPosX() < 0) this.deplacementActuel = deplacements.GAUCHE;
            else if (positionXFinDeplacement - this.getPosX() > 0) this.deplacementActuel = deplacements.DROITE;
            else if (positionYFinDeplacement - this.getPosY() < 0) this.deplacementActuel = deplacements.HAUT;
            else if (positionYFinDeplacement - this.getPosY() > 0) this.deplacementActuel = deplacements.BAS;
        }
    }

    public int tradCoorToPx(int coordone){
        return (int) (coordone* Map.TAILLE_CASE + 1);
    }

    public boolean estAuSpawn() {
        int x = this.getPosX()/20;
        int y = this.getPosY()/20 ;
        if (x>9 && x<15 && y>12 && y<16) return true;
        return false;
    }

    public void affichage() {
        super.affichage();
        switch (this.etat) {
            case SPAWN:
                this.setImageView(this.getImage());
                break;
            case NORMAL:
                this.setImageView(this.getImage());
                break;
            case APPEURE:
                this.setImageView(this.imageBlueGhost);
                break;
            case MORT:
                this.setImageView(this.imageMort);
                break;
            default:
                this.setImageView(this.getImage());
                break;
        }
    }

    public String getCoordPacman(){
        return (pacman.getPosX() / 20) + "/" + (pacman.getPosY() / 20);
    }

    public String getCoordFantome(){
        return (getPosX() / 20) + "/" + (getPosY() / 20);
    }
}
