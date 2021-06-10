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



public class Fantome extends Deplacement {
    public int numFantome;
    public deplacements deplacementActuel = deplacements.AUCUN;
    public int positionXFinDeplacement;
    public int positionYFinDeplacement;
    public List<String> listeCoordoneDeplacementFant;
    public Pacman pacman;
    public String coordoneePasse;
    public String coordoneeActuel;

    // spawn -> attend un temps aléatoire, puis sort du spawn
    public enum ValeurEtat {NORMAL, SPAWN, APPEURE, MORT}

    public ValeurEtat etat;
    // spawn
    private long debutSpawn = 0L;
    private double multiplicateurTempsSpawn;
    private final long tempsSpawn;
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
        this.tempsSpawn = 2500L;
    }

    /**
     * Selon les états du fantômes qui peuvent être SPAWN, MORT, APPEURE, NORMAL change l'IA et le comportement de ce que doivent faire les fantômes lors de leurs déplacements.
     */
    public void updateDeplacement() {
        switch (etat) {
            case SPAWN:
                this.estVulnerable = false;
                if (this.debutSpawn == 0L && this.pacman.deplacementActuel != deplacements.AUCUN) {
                    if (this.mort) {
                        this.debutSpawn = System.currentTimeMillis();
                        this.multiplicateurTempsSpawn = 1.0/2;
                        this.immobile = true;
                        this.mort = false;
                        this.listeCoordoneDeplacementFant.clear();
                        this.positionXFinDeplacement = getPosX();
                        this.positionYFinDeplacement = getPosY();
                    } else {
                        this.debutSpawn = System.currentTimeMillis();
                        this.multiplicateurTempsSpawn = this.numFantome;
                        this.immobile = true;
                    }
                } else if (this.immobile && (System.currentTimeMillis() - this.debutSpawn > (this.tempsSpawn * this.multiplicateurTempsSpawn))) {
                    this.debutSpawn = 0L;
                    this.etat = ValeurEtat.NORMAL;
                    this.listeCoordoneDeplacementFant = dijkstra(true, true, this.coordoneeActuel, "12/12");
                    this.immobile = false;
                }
                break;
            case NORMAL:
                if (this.estVulnerable) {
                    if(this.listeCoordoneDeplacementFant.size()>1) {
                        String tmp = this.listeCoordoneDeplacementFant.get(0);
                        this.listeCoordoneDeplacementFant.clear();
                        this.listeCoordoneDeplacementFant.add(tmp);
                    }
                    this.estVulnerable = false;
                }
                deplaceFantome();
                break;
            case APPEURE:
                if(!this.estVulnerable) {
                    if(this.listeCoordoneDeplacementFant.size()>1) {
                        String tmp = this.listeCoordoneDeplacementFant.get(0);
                        this.listeCoordoneDeplacementFant.clear();
                        this.listeCoordoneDeplacementFant.add(tmp);
                    }
                    this.estVulnerable = true;
                }
                this.deplaceFantome();
                break;
            case MORT:
                this.estVulnerable = false;
                if (!this.mort) {
                    this.listeCoordoneDeplacementFant.clear();
                    this.mort = true;
                    String coordSpawn = INIT_POS_X / 20 + "/" + INIT_POS_Y / 20;
                    List<String> dijkstra = dijkstra(true, true, this.getCoordFantome(), coordSpawn);
                    this.listeCoordoneDeplacementFant.addAll(dijkstra);
                } else if (getPosX() == this.INIT_POS_X && getPosY() == this.INIT_POS_Y) {
                    this.etat = ValeurEtat.SPAWN;
                    this.velocityMultiplicator = this.velocityMultiplicatorInitial;
                }
                this.deplaceFantome();
                break;
        }
    }

    /**
     * Cette methode est redéfini dans chaque fantomes
     */
    public void ia() {}

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
        Graphs.addAllVertices(graphCopie, this.map.getG().vertexSet());
        Graphs.addAllEdges(graphCopie, this.map.getG(), this.map.getG().edgeSet());
        if (!this.coordoneeActuel.equals(this.coordoneePasse) && graphCopie.containsEdge(this.coordoneePasse, this.coordoneeActuel) && !isDemiTourAutorise) {
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

    /**
     * Fais déplacer les fantômes selon leur première coordonnée dans leur liste de déplacement.
     * Si la liste est est vide appelle l'IA associé à son état pour recharger sa liste.
     */
    private void deplaceFantome() {
        if (this.listeCoordoneDeplacementFant.isEmpty()) {
            if (this.estVulnerable) this.iaRandom();
            else this.ia();
        }
        this.getNextFinalPos();
        this.avancePos();
    }

    /**
     * Permet de déplacer le fantôme selon la direction où il va.
     */
    public void avancePos() {
        if (doitRechargerNextPos()) {
            this.updateCoordonnees();
            this.listeCoordoneDeplacementFant.remove(0);
        } else if (this.positionXFinDeplacement != this.getPosX() || this.positionYFinDeplacement != this.getPosY()) {
            switch (this.deplacementActuel) {
                case HAUT:
                    if (this.peutAvancerVerticalement(-1)) this.avanceHaut();
                    break;
                case DROITE:
                    if (this.peutAvancerHorizontalement(1)) this.avanceDroite();
                    break;
                case BAS:
                    if (this.peutAvancerVerticalement(1)) this.avanceBas();
                    break;
                case GAUCHE:
                    if (this.peutAvancerHorizontalement(-1)) this.avanceGauche();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *  Retourne true si le fantome doit recharger la prochaine position
     * @return true si il a atteint la coordonnée où il doit aller au pixel près sinon c'est false.
     */
    public boolean doitRechargerNextPos() {
        return (this.positionXFinDeplacement == this.getPosX() && this.positionYFinDeplacement == this.getPosY());
    }

    /**
     * Renvoie vrai si il ne peut pas avoir atteint un mur.
     * Renvoie vrai si la prochaine case n'est pas un mur.
     * Sinon renvoie faux.
     * @param i -1 pour la gauche et 1 pour la droite
     * @return retourne true s'il peut avancer horizontalement
     */
    public boolean peutAvancerHorizontalement(int i) {
        if (this.getPosY() % 20 == 1) {
            if (this.getPosX() % 20 != 1) {
                return true;
            } else return (this.map.grid[((this.getPosX() / 20) + i + 25) % 25][this.getPosY() / 20] != Map.ValeurCase.MUR);
        }
        return false;
    }

    /**
     * Renvoie vrai si le fantome est mort.
     * Renvoie vrai si il ne peut pas avoir atteint un mur.
     * Renvoie vrai si la prochaine case n'est pas un mur et que la prochaine case n'est pas interdite.
     * Pour être plus exact sur l'interdit : on vérifie si il est sur une case non interdite et que sa prochaine case est interdite dans ce cas on return false.
     * @param i -1 pour le haut et 1 pour le bas
     * @return retourne true s'il peut avancer verticalement
     */
    public boolean peutAvancerVerticalement(int i) {
        if (this.getPosX() % 20 == 1 && this.getPosX() > 1 && this.getPosX() < 500) {
            if(this.getPosY() % 20 != 1) return true;
            if((this.map.grid[this.getPosX()/20][(this.getPosY()/20)+i] == Map.ValeurCase.MUR)) return false;
            else return !(!this.mort && (this.map.grid[this.getPosX() / 20][(this.getPosY() / 20)] != Map.ValeurCase.INTERDIT && map.grid[this.getPosX() / 20][(this.getPosY() / 20) + i] == Map.ValeurCase.INTERDIT));
        }
        return false;
    }

    /**
     * Renvoie vrai si un fantôme est sur pac-man sinon faux
     * @return retourne true si le fantome est sur pacman
     */
    public boolean estSurPacman() {
        if (this.getCoordPacman().equals(this.getCoordFantome())) return true;
        else return (((this.pacman.getPosX() - this.getPosX()) < 18) && ((this.pacman.getPosX() - this.getPosX()) >= 0) && ((this.pacman.getPosY() -  this.getPosY()) < 18) && ((this.pacman.getPosY() -  this.getPosY()) >= 0));
    }

    /**
     * initialise les fantômes en le mettant dans ces conditions de début de jeu.
     */
    @Override
    public void initPosition() {
        super.initPosition();
        this.deplacementActuel = deplacements.AUCUN;
        this.coordoneeActuel= this.INIT_POS_X/20 + "/" + this.INIT_POS_Y/20;
        this.coordoneePasse = this.INIT_POS_X/20 + "/" + this.INIT_POS_Y/20;
        this.etat = ValeurEtat.SPAWN;
        this.debutSpawn = 0L;
        this.immobile = false;
    }

    /**
     * ia random en enlevant la coordonée passée du fantôme.
     */
    public void iaRandom() {
        List<String> choixPossible = Graphs.neighborListOf(this.map.getG(), this.getCoordFantome());
        choixPossible.remove(coordoneePasse);
        choixPossible.remove("12/13");
        Random rand = new Random();
        this.listeCoordoneDeplacementFant = dijkstra(false, true, this.getCoordFantome(), choixPossible.get(rand.nextInt(choixPossible.size())));
    }

    /**
     * Regarde la première coordonnée de la liste du fantôme et la traduit en pixel de fin de déplacement.
     */
    public void getNextFinalPos(){
        String coord = this.listeCoordoneDeplacementFant.get(0);
        String[] coorXY = coord.split("/");

        int x = Integer.parseInt(coorXY[0]);
        int y = Integer.parseInt(coorXY[1]);

        this.positionXFinDeplacement = x * Map.TAILLE_CASE + 1;
        this.positionYFinDeplacement = y * Map.TAILLE_CASE + 1;
        this.setOrientation();
    }

    /**
     * La prochaine coordonnée est adjacente à la sienne donc suivant la différence en pixel oriente le fantôme.
     */
    public void setOrientation(){
        if(this.getPosX()>20 && this.getPosX()<480){
            if (this.positionXFinDeplacement - this.getPosX() < 0) this.deplacementActuel = deplacements.GAUCHE;
            else if (this.positionXFinDeplacement - this.getPosX() > 0) this.deplacementActuel = deplacements.DROITE;
            else if (this.positionYFinDeplacement - this.getPosY() < 0) this.deplacementActuel = deplacements.HAUT;
            else if (this.positionYFinDeplacement - this.getPosY() > 0) this.deplacementActuel = deplacements.BAS;
        }
    }

    /**
     * Change la coordonnée actuelle en coordonnée passée et actualise la coordonnee actuelle avec sa coordonnée
     */
    public void updateCoordonnees(){
        this.coordoneePasse = this.coordoneeActuel;
        this.coordoneeActuel = this.getCoordFantome();
    }

    /**
     * Permet de savoir si le fantôme est dans sa base.
     * @return retourne true si le fantome est au spawn
     */
    public boolean estAuSpawn() {
        int x = this.getPosX()/20;
        int y = this.getPosY()/20 ;
        return x>9 && x<15 && y>12 && y<16;
    }

    /**
     * Change le gif du fantôme selon son etat
     */
    public void affichage() {
        super.affichage();
        switch (this.etat) {
            case APPEURE:
                if (this.pacman.freeze) this.setImageView(this.imageBlueGhostGele);
                else if(this.clignote) this.setImageView(this.imageBlueGhostClignote);
                else this.setImageView(this.imageBlueGhost);
                break;
            case MORT:
                if (this.pacman.freeze) this.setImageView(this.imageYeuxGeles);
                else this.setImageView(this.imageMort);
                break;
            default:
                if (this.pacman.freeze) this.setImageView(this.imageFantomeGele);
                else this.setImageView(this.getImage());
                break;
        }
    }

    /**
     * Fais faire au fantôme sur lequel on la méthode est utilisée un demi-tour.
     */
    public void faisDemiTour(){
        this.coordoneeActuel = getCoordFantome();
        this.coordoneePasse = getCoordFantome();
        this.listeCoordoneDeplacementFant.clear();
        List<String> choixPossible = Graphs.neighborListOf(map.getG(), getCoordFantome());
        String coordGauche = ((getPosX()/20)-1) + "/" + (getPosY()/20);
        String coordBas = (getPosX()/20) + "/" + ((getPosY()/20)+1);
        String coordDroite = ((getPosX()/20)+1) + "/" + (getPosY()/20);
        String coordHaut = (getPosX()/20) + "/" + ((getPosY()/20)-1);
        switch (this.deplacementActuel){
            case BAS:
                if(choixPossible.contains(coordHaut)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(), coordHaut);
                else if (choixPossible.contains( coordGauche)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(),  coordGauche);
                else  this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(), coordDroite);
                break;
            case HAUT:
                if(choixPossible.contains(coordBas))  this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(),  coordBas);
                else if (choixPossible.contains(coordGauche)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(), coordGauche);
                else  this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(), coordDroite);
                break;
            case DROITE:
                if(choixPossible.contains(coordGauche)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(), coordGauche);
                else if (choixPossible.contains(coordHaut)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(),  coordHaut);
                else this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(),  coordBas);
                break;
            case GAUCHE:
                if(choixPossible.contains(coordDroite)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(), coordDroite);
                else if (choixPossible.contains(coordHaut)) this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(),  coordHaut);
                else this.listeCoordoneDeplacementFant = dijkstra(true, true, this.getCoordFantome(),  coordBas);
                break;
            default:
                break;
        }
    }

    /**
     * @return la coordonnee de Pac-man
     */
    public String getCoordPacman(){
        return (this.pacman.getPosX() / 20) + "/" + (this.pacman.getPosY() / 20);
    }

    /**
     * @return la coordonnée du fantôme
     */
    public String getCoordFantome(){
        return (this.getPosX() / 20) + "/" + (this.getPosY() / 20);
    }
}