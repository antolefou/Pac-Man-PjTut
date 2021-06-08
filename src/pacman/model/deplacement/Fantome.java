package pacman.model.deplacement;

import java.lang.invoke.SwitchPoint;
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



public class Fantome extends Deplacement {
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
    public Image imageBlueGhostGele;
    public Image imageMort;
    public Image imageYeuxGeles;
    public Image imageFantomeGele;

    public final int VelocityMultiplicatorAppeure = 1;

    public boolean estVulnerable;
    public int compteur;


    public Fantome(int init_pos_x, int init_pos_y) {
        super(init_pos_x, init_pos_y);
        this.listeCoordoneDeplacementFant = new ArrayList<>();
        this.coordoneeActuel = init_pos_x / 20 + "/" + init_pos_y / 20;
        this.coordoneePasse = init_pos_x / 20 + "/" + init_pos_y / 20;
        this.imageBlueGhostClignote = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghostClignote.gif")));
        this.imageYeuxGeles = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/yeuxGeles.gif")));
        this.imageBlueGhostGele = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghostGele.gif")));

        this.etat = ValeurEtat.SPAWN;
        this.estVulnerable = false;
        this.mort = false;
        this.clignote = false;
        this.compteur = 0;

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
                } else if (immobile && System.currentTimeMillis() - debutSpawn > 1000L * tempsSpawn) {
                    debutSpawn = 0L;
                    etat = ValeurEtat.NORMAL;
                    listeCoordoneDeplacementFant = dijkstra(true, true, this.coordoneeActuel, "12/12");
                    immobile = false;
                }
                break;
            case NORMAL:
                if (this.estVulnerable) {
                    if(listeCoordoneDeplacementFant.size()>1) {
                        String tmp = listeCoordoneDeplacementFant.get(0);
                        listeCoordoneDeplacementFant.clear();
                        listeCoordoneDeplacementFant.add(tmp);
                    }
                    estVulnerable = false;
                }
                updateDeplacements();
                break;
            case APPEURE:
                if(!estVulnerable) {
                    if(listeCoordoneDeplacementFant.size()>1) {
                        String tmp = listeCoordoneDeplacementFant.get(0);
                        listeCoordoneDeplacementFant.clear();
                        listeCoordoneDeplacementFant.add(tmp);
                    }
                    this.estVulnerable = true;
                }
                updateDeplacements();
                break;
            case MORT:
                this.estVulnerable = false;
                if (!this.mort) {
                    listeCoordoneDeplacementFant.clear();
                    mort = true;
                    String coordSpawn = INIT_POS_X / 20 + "/" + INIT_POS_Y / 20;
                    List<String> dijkstra = dijkstra(true, true, getCoordFantome(), coordSpawn);
                    listeCoordoneDeplacementFant.addAll(dijkstra);
                } else if (getPosX() == INIT_POS_X && getPosY() == INIT_POS_Y) {
                    etat = ValeurEtat.SPAWN;
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
     *
     * @param isDemiTourAutorise si vrai on ne supprime pas la coordonnée derrière lui si faux on la supprime
     * @param cheminEntier       si vrai on retourne le tableau de coordonnee en entier si faux on ne renvoie que la première coordonee
     * @param coordoneeDepart    départ dijkstra
     * @param coordoneeArrive    arrivé dijkstra
     * @return List<String> de coordonnee
     */
    public List<String> dijkstra(boolean isDemiTourAutorise, boolean cheminEntier, String coordoneeDepart, String coordoneeArrive) {
        Graph<String, DefaultEdge> graphCopie = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addAllVertices(graphCopie, map.getG().vertexSet());
        Graphs.addAllEdges(graphCopie, map.getG(), map.getG().edgeSet());
        if (!coordoneeActuel.equals(coordoneePasse) && graphCopie.containsEdge(this.coordoneePasse, this.coordoneeActuel) && !isDemiTourAutorise) {
            graphCopie.removeEdge(this.coordoneePasse, this.coordoneeActuel);
        }
        List<String> dijkstra = DijkstraShortestPath.findPathBetween(graphCopie, coordoneeDepart, coordoneeArrive).getVertexList();
        if(getPosX()%20 == 1 && getPosY()%20 == 1) dijkstra.remove(0);
        if (!cheminEntier) {
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
            updateCoordonnees();
            this.listeCoordoneDeplacementFant.remove(0);
        } else if (positionXFinDeplacement != this.getPosX() || positionYFinDeplacement != this.getPosY()) {
            switch (this.deplacementActuel) {
                case HAUT:
                    if (peutAvancerVerticalement(map, -1)) this.avanceHaut();
                    break;
                case DROITE:
                    if (peutAvancerHorizontalement(map, 1)) this.avanceDroite();
                    break;
                case BAS:
                    if (peutAvancerVerticalement(map, 1)) this.avanceBas();
                    break;
                case GAUCHE:
                    if (peutAvancerHorizontalement(map, -1)) this.avanceGauche();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean doitRechargerNextPos() {
        return (this.positionXFinDeplacement == this.getPosX() && this.positionYFinDeplacement == this.getPosY());
    }

    public boolean peutAvancerHorizontalement(Map map, int i) {
        if (getPosY() % 20 == 1) {
            if (getPosX() % 20 != 1) {
                return true;
            } else return (map.grid[((getPosX() / 20) + i + 25) % 25][getPosY() / 20] != Map.ValeurCase.MUR);
        }
        return false;
    }

    public boolean peutAvancerVerticalement(Map map, int i) {
        if (getPosX() % 20 == 1 && getPosX() > 1 && getPosX() < 500) {
            // Renvoie vrai si le fantome est mort.
            // Renvoie vrai si il ne peut pas avoir atteint un mur.
            // Renvoie vrai si la prochaine case n'est pas un mur et que la prochaine case n'est pas interdite.
            // Pour être plus exact sur l'interdit : on vérifie si il est sur une case non interdite et que sa prochaine case est interdite dans ce cas on return false.
            if(getPosY() % 20 != 1) return true;
            if((map.grid[getPosX()/20][(getPosY()/20)+i] == Map.ValeurCase.MUR)) return false;
            else return !(!this.mort && (map.grid[getPosX() / 20][(getPosY() / 20)] != Map.ValeurCase.INTERDIT && map.grid[getPosX() / 20][(getPosY() / 20) + i] == Map.ValeurCase.INTERDIT));
        }
        return false;
    }

    public boolean estSurPacman() {
        if (getCoordPacman().equals(getCoordFantome())) return true;
        else return (((pacman.getPosX() - getPosX()) < 18) && ((pacman.getPosX() - getPosX()) >= 0) && ((pacman.getPosY() -  getPosY()) < 18) && ((pacman.getPosY() -  getPosY()) >= 0));
    }

    @Override
    public void initPosition() {
        super.initPosition();
        deplacementActuel = deplacements.AUCUN;
        this.coordoneeActuel= this.INIT_POS_X/20 + "/" + this.INIT_POS_Y/20;
        this.coordoneePasse = this.INIT_POS_X/20 + "/" + this.INIT_POS_Y/20;
        etat = ValeurEtat.SPAWN;
        debutSpawn = 0L;
    }

    public void iaRandom() {
        List<String> choixPossible = Graphs.neighborListOf(map.getG(), getCoordFantome());
        choixPossible.remove(coordoneePasse);
        choixPossible.remove("12/13");
        Random rand = new Random();
        listeCoordoneDeplacementFant = dijkstra(false, true, getCoordFantome(), choixPossible.get(rand.nextInt(choixPossible.size())));
    }

    public void getNextFinalPos(){
        String coord = this.listeCoordoneDeplacementFant.get(0);
        String[] coorXY = coord.split("/");

        int x = Integer.parseInt(coorXY[0]);
        int y = Integer.parseInt(coorXY[1]);

        this.positionXFinDeplacement = x * Map.TAILLE_CASE + 1;
        this.positionYFinDeplacement = y * Map.TAILLE_CASE + 1;
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
    public void updateCoordonnees(){
        String tmp = this.coordoneeActuel;
        this.coordoneeActuel = this.getCoordFantome();
        this.coordoneePasse = tmp;
    }

    public boolean estAuSpawn() {
        int x = this.getPosX()/20;
        int y = this.getPosY()/20 ;
        return x>9 && x<15 && y>12 && y<16;
    }

    public void affichage() {
        super.affichage();
        switch (this.etat) {
            case APPEURE:
                if (pacman.freeze) this.setImageView(this.imageBlueGhostGele);
                else if(this.clignote) this.setImageView(this.imageBlueGhostClignote);
                else this.setImageView(this.imageBlueGhost);
                break;
            case MORT:
                if (pacman.freeze) this.setImageView(this.imageYeuxGeles);
                else this.setImageView(this.imageMort);
                break;
            default:
                if (pacman.freeze) this.setImageView(this.imageFantomeGele);
                else this.setImageView(this.getImage());
                break;
        }
    }

    public void faisDemiTour(){
        listeCoordoneDeplacementFant.clear();
        List<String> choixPossible = Graphs.neighborListOf(map.getG(), getCoordFantome());
        switch (this.deplacementActuel){
            case BAS:
                if(choixPossible.contains((getPosX()/20) + "/" + ((getPosY()/20)-1))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(), (getPosX()/20) + "/" + ((getPosY()/20)-1));
                else if (choixPossible.contains( ((getPosX()/20)-1) + "/" + (getPosY()/20))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(),  ((getPosX()/20)-1) + "/" + (getPosY()/20));
                else listeCoordoneDeplacementFant = dijkstra(true, false, getCoordFantome(), ((getPosX()/20)+1) + "/" + (getPosY()/20));
                break;
            case HAUT:
                if(choixPossible.contains((getPosX()/20) + "/" + ((getPosY()/20)+1))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(),  (getPosX()/20) + "/" + ((getPosY()/20)+1));
                else if (choixPossible.contains( ((getPosX()/20)-1) + "/" + (getPosY()/20))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(),   ((getPosX()/20)-1) + "/" + (getPosY()/20));
                else listeCoordoneDeplacementFant = dijkstra(true, false, getCoordFantome(), ((getPosX()/20)+1) + "/" + (getPosY()/20));
                break;
            case DROITE:
                if(choixPossible.contains(((getPosX()/20)-1) + "/" + (getPosY()/20))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(), ((getPosX()/20)-1) + "/" + (getPosY()/20));
                else if (choixPossible.contains( (getPosX()/20) + "/" + ((getPosY()/20)-1))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(),  (getPosX()/20) + "/" + ((getPosY()/20)-1));
                else listeCoordoneDeplacementFant = dijkstra(true, false, getCoordFantome(),  (getPosX()/20) + "/" + ((getPosY()/20)+1));
                break;
            case GAUCHE:
                if(choixPossible.contains(((getPosX()/20)+1) + "/" + (getPosY()/20))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(), ((getPosX()/20)+1) + "/" + (getPosY()/20));
                else if (choixPossible.contains((getPosX()/20) + "/" + ((getPosY()/20)-1))) listeCoordoneDeplacementFant = dijkstra(true, true, getCoordFantome(),  (getPosX()/20) + "/" + ((getPosY()/20)-1));
                else listeCoordoneDeplacementFant = dijkstra(true, false, getCoordFantome(),  (getPosX()/20) + "/" + ((getPosY()/20)+1));
                break;
            default:
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
