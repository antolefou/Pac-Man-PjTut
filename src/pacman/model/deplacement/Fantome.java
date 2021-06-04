package pacman.model.deplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javafx.scene.image.Image;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
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
    public boolean clignote;
    //image
    public Image imageBlueGhost;
    public Image imageBlueGhostClignote;
    public Image imageMort;

    public final int VelocityMultiplicatorAppeure = 1;

    public boolean estVulnerable;


    public Fantome(int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y);
        this.listeCoordoneDeplacementFant = new ArrayList<>();
        this.coordoneeActuel = init_pos_x/20 + "/" + init_pos_y/20;
        this.coordoneePasse = init_pos_x/20 + "/" + init_pos_y/20;
        imageBlueGhostClignote = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghostClignote.gif")));

        this.etat = ValeurEtat.SPAWN;
        this.estVulnerable = false;
        this.mort = false;
        this.clignote = false;

    }

    public void updateDeplacement() {
        switch (etat) {
            case SPAWN:
                this.estVulnerable = false;
                if (debutSpawn == 0L && pacman.deplacementActuel != deplacements.AUCUN) {
                    if (mort) {
                        debutSpawn = System.currentTimeMillis();
                        tempsSpawn = 0.25;
                        immobile = true;
                        mort = false;
                        listeCoordoneDeplacementFant.clear();
                        this.positionXFinDeplacement = getPosX();
                        this.positionYFinDeplacement = getPosY();
                    } else {
                        debutSpawn = System.currentTimeMillis();
                        tempsSpawn = numFantome;
                        immobile = true;
                    }
                } else if(immobile && System.currentTimeMillis()-debutSpawn > 1000L * tempsSpawn) {
                    debutSpawn = 0L;
                    etat = ValeurEtat.NORMAL;
                    listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(), "12/12");
                    immobile = false;
                }
                break;
            case NORMAL:
                if(this.estVulnerable){
                    this.estVulnerable = false;
                    listeCoordoneDeplacementFant.clear();
                }
                updateDeplacements();
                break;
            case APPEURE:
                listeCoordoneDeplacementFant.clear();
                this.estVulnerable = true;
                updateDeplacements();
                break;
            case MORT:
                this.estVulnerable = false;
                if (!this.mort) {
                    mort = true;
                    String coordSpawn = INIT_POS_X / 20 + "/" + INIT_POS_Y / 20;
                    listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(), coordSpawn);
                } else if (getPosX() == INIT_POS_X && getPosY() == INIT_POS_Y) {
                    etat = ValeurEtat.SPAWN;
                    this.setImageView(this.getImage());
                    velocityMultiplicator = velocityMultiplicatorInitial;
                }
                updateDeplacements();
                break;
        }
    }

    public void ia() {

    }

    /**
     * Renvoie le chemin de coordonnée le plus rapide pour aller de départ à arrivé avec quelques précisions selon les boolean.
     * @param isDemiTourAutorise si vrai on ne supprime pas la coordonnée derrière lui si faux on la supprime
     * @param cheminEntier si vrai on retourne le tableau de coordonnee en entier si faux on ne renvoie que la première coordonee
     * @param coordoneeDepart départ dijkstra
     * @param coordoneeArrive arrivé dijkstra
     * @return List<String> de coordonnee
     */
    public List<String> dijkstra(boolean isDemiTourAutorise, boolean cheminEntier, String coordoneeDepart, String coordoneeArrive){
        Graph<String, DefaultEdge> graphCopie = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addAllVertices(graphCopie, map.getG().vertexSet());
        Graphs.addAllEdges(graphCopie, map.getG(), map.getG().edgeSet());
        if (!coordoneeActuel.equals(coordoneePasse) && graphCopie.containsEdge(this.coordoneePasse, this.coordoneeActuel) && !isDemiTourAutorise) {
            graphCopie.removeEdge(this.coordoneePasse, this.coordoneeActuel);
        }
        List<String> dijkstra = DijkstraShortestPath.findPathBetween(graphCopie, coordoneeDepart, coordoneeArrive).getVertexList();
        if(!this.mort) dijkstra.remove(0);
        if(!cheminEntier) {
            String tmp = dijkstra.get(0);
            dijkstra.clear();
            dijkstra.add(tmp);
        }
        return dijkstra;
    }

    private void updateDeplacements() {
        if (this.listeCoordoneDeplacementFant.isEmpty()) {
            if (this.estVulnerable) this.iaRandom();
            else this.ia();
        }
        getNextFinalPos();
        avancePos();
    }

    public void avancePos() {
        if (doitRechargerNextPos()) {
            String tmp = this.coordoneeActuel;
            this.coordoneeActuel = this.listeCoordoneDeplacementFant.get(0);
            this.coordoneePasse = tmp;
            this.listeCoordoneDeplacementFant.remove(0);
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
        return (this.positionXFinDeplacement == this.getPosX() && this.positionYFinDeplacement == this.getPosY());
    }

    public boolean peutAvancerHorizontalement(Map map, int i) {
        if (getPosY() % 20 == 1) {
            return (this.mort || (getPosX() % 20 != 1) || (map.grid[((getPosX()/20)+i+25)%25][getPosY()/20] != Map.ValeurCase.MUR));
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
        return (getCoordPacman().equals(getCoordFantome()));
    }

    @Override
    public void initPosition() {
        super.initPosition();
        deplacementActuel = deplacements.AUCUN;
        etat = ValeurEtat.SPAWN;
        debutSpawn = 0L;
    }

    public void iaRandom() {
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
        if(this.getPosX()>20 && this.getPosX()<480){
            if (positionXFinDeplacement - this.getPosX() < 0) this.deplacementActuel = deplacements.GAUCHE;
            else if (positionXFinDeplacement - this.getPosX() > 0) this.deplacementActuel = deplacements.DROITE;
            else if (positionYFinDeplacement - this.getPosY() < 0) this.deplacementActuel = deplacements.HAUT;
            else if (positionYFinDeplacement - this.getPosY() > 0) this.deplacementActuel = deplacements.BAS;
        }
    }

    public int tradCoorToPx(int coordone){
        return (int) (coordone*Map.TAILLE_CASE + 1);
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
                if(this.clignote) this.setImageView(this.imageBlueGhostClignote);
                else this.setImageView(this.imageBlueGhost);
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
