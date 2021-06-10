package pacman.model;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.BlockCutpointGraph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class Map extends Group  {

    public final static int TAILLE_CASE = 20;
    private final int NB_CASE_X = 25;
    private final int NB_CASE_Y = 30;
//    valeur possible de chaque case de la map
    public enum ValeurCase { VIDE, MUR, GOMME, SUPERGOMME, BOOST, INTERDIT, TELEPORTEUR, CERISE, FRAISE, ORANGE, POMME, MELON, VAISSEAU, CLOCHE, CLEF}

    public MapGenerator mapGenerator;

    public String[][] mapGeneree;
    public ValeurCase[][] grid;
    public ImageView[][] caseMap;
//    Images
    private final Image imageMur;
    private final Image imageGomme;
    private final Image imageSuperGomme;
    private final Image imageBoost;
    private final Image imageMurFantome;
    private final Image imageTeleporteur;
    private final Image imageCerise;
    private final Image imageFraise;
    private final Image imageOrange;
    private final Image imagePomme;
    private final Image imageMelon;
    private final Image imageVaisseau;
    private final Image imageCloche;
    private final Image imageClef;
//    Graphe
    public Graph<String, DefaultEdge> g;
    public String[][] grilleGraph;

    static ArrayList<String> theme;



    /**
     * Initialise les valeurs des images
     * map code:
     *      M  -> mur
     *      G  -> gomme
     *      S  -> super gomme
     */
    public Map() {
        initListeThemeMap();
        shuffleTheme();
        String src = "/pacman/ressources/image/Ecran_jouer/labyrinthe/";
        this.imageMur = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src + theme.get(1))));
        this.imageGomme = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "Gomme.png")));
        this.imageSuperGomme = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "SuperGomme.png")));
        this.imageBoost = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "boost.png")));
        this.imageMurFantome = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "murFantome.png")));
        this.imageTeleporteur = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "teleporteur.gif")));
        this.imageCerise = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "cerise.png")));
        this.imageFraise = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "fraise.png")));
        this.imageOrange = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "orange.png")));
        this.imagePomme = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "pomme.png")));
        this.imageMelon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "melon.png")));
        this.imageVaisseau = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "vaisseau.png")));
        this.imageCloche = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "cloche.png")));
        this.imageClef = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "clef.png")));
//        init
        this.grid = new ValeurCase[this.NB_CASE_X][this.NB_CASE_Y];
//         crée et affiche la map
        Utilisateur utilisateur = new Utilisateur();
        Platform.runLater(() -> creeMapAleatoire(utilisateur.niveauUtilisateur));
    }

    /**
     * Crée une map aléatoire et recommence si jamais la map contient des zones isolées
     */
    private void creeMapAleatoire() {
        this.creeMapAleatoire(1);
    }

    /**
     * Crée une map aléatoire et recommence si jamais la map contient des zones isolées
     * @param niveau niveau de la map
     */
    public void creeMapAleatoire(int niveau) {
        // crée une nouvelle map et recommence si jamais la map contient des zones isolées
        do {
            mapGenerator = new MapGenerator();
            mapGenerator.initObjet(5, 3, niveau);
            mapGeneree = mapGenerator.getMap();
            mapGenerator = null; // Vide mapGeneree

            initialiseMapGeneree();
            initGraph();
        } while (!estConnexe() || isArticulationPoint(this.g));
        if (this.caseMap == null) initialiseCaseMap();
        this.afficheMap();
    }

    /**
     * Vérifie si la map a un sommet qui quand on l'enlève ajoute une composante connexe
     * @param g graph à vérifier
     * @return true si il y en a un, sinon false
     */
    public boolean isArticulationPoint(Graph<String, DefaultEdge> g){
        BlockCutpointGraph<String,DefaultEdge> test = new BlockCutpointGraph<>(g);
        boolean pointArticulation = false;
        int x = 0;
        while (x<NB_CASE_X && !pointArticulation) {
            int y = 0;
            while (y < NB_CASE_Y && !pointArticulation){
                if(grilleGraph[x][y].equals(x+"/"+y) && test.isCutpoint(grilleGraph[x][y]) && !grilleGraph[x][y].equals(12+"/"+12) && !grilleGraph[x][y].equals(12+"/"+13) && !grilleGraph[x][y].equals(12+"/"+14)) {
                    pointArticulation = true;
                }
                y++;
            }
            x++;
        }
        return pointArticulation;
    }

    /**
     * Initialise la map générée
     */
    private void initialiseMapGeneree() {
        for (int x=0; x<this.NB_CASE_X; x++) {
            for (int y=0; y<this.NB_CASE_Y; y++) {
                switch (mapGeneree[x][y]) {
                    case "V":
                        this.grid[x][y] = ValeurCase.VIDE;
                        break;
                    case "M":
                        this.grid[x][y] = ValeurCase.MUR;
                        break;
                    case "G":
                        this.grid[x][y] = ValeurCase.GOMME;
                        break;
                    case "S":
                        this.grid[x][y] = ValeurCase.SUPERGOMME;
                        break;
                    case "B":
                        this.grid[x][y] = ValeurCase.BOOST;
                        break;
                    case "I":
                        this.grid[x][y] = ValeurCase.INTERDIT;
                        break;
                    case "C":
                        this.grid[x][y] = ValeurCase.CERISE;
                        break;
                    case "F":
                        this.grid[x][y] = ValeurCase.FRAISE;
                        break;
                    case "O":
                        this.grid[x][y] = ValeurCase.ORANGE;
                        break;
                    case "P":
                        this.grid[x][y] = ValeurCase.POMME;
                        break;
                    case "ME":
                        this.grid[x][y] = ValeurCase.MELON;
                        break;
                    case "VA":
                        this.grid[x][y] = ValeurCase.VAISSEAU;
                        break;
                    case "CLO":
                        this.grid[x][y] = ValeurCase.CLOCHE;
                        break;
                    case "CLE":
                        this.grid[x][y] = ValeurCase.CLEF;
                        break;
                    default:
                        System.out.println("Erreur: La valeur de la case est inconnu");
                }
            }
        }
    }

    /**
     * Initialise le graph en fonction de la map générée
     */
    public void initGraph(){
        // Création du Graph
        this.g = new SimpleGraph<>(DefaultEdge.class);
        this.grilleGraph = new String[this.NB_CASE_X][this.NB_CASE_Y];
        for (int x=0; x<this.NB_CASE_X; x++) {
            for (int y=0; y<this.NB_CASE_Y; y++) {
                if (grid[x][y] != ValeurCase.MUR) {
                    grilleGraph[x][y] = x+"/"+y;
                }else{
                    grilleGraph[x][y] = "Mur"+x+"/"+y;
                }
                g.addVertex(grilleGraph[x][y]);
            }
        }
        for (int x=0; x<this.NB_CASE_X; x++) {
            for (int y=0; y<this.NB_CASE_Y; y++) {
                if (grilleGraph[x][y].equals(x + "/" + y) && grilleGraph[(x + 1) % this.NB_CASE_X][y].equals((x + 1) % this.NB_CASE_X + "/" + y)) {
                    g.addEdge(grilleGraph[x][y], grilleGraph[((x+1)%this.NB_CASE_X)][y]);
                }
                if (grilleGraph[x][y].equals(x + "/" + y) && grilleGraph[x][(y + 1) % this.NB_CASE_Y].equals(x + "/" + (y + 1) % this.NB_CASE_Y)) {
                    g.addEdge(grilleGraph[x][y], grilleGraph[x][(y+1)%this.NB_CASE_Y]);
                }
            }
        }
    }

    /**
     * Renvoie true si le graph est connexe
     * @return true si le graph est connexe
     */
    public boolean estConnexe(){
        ConnectivityInspector<String, DefaultEdge> gTestConnecti = new ConnectivityInspector<>(this.g);
        List<Set<String>> listeSousGraphe = gTestConnecti.connectedSets();
        int count = 0;
        for (Set<String> strings : listeSousGraphe) {
            if (strings.size() > 1) {
                count++;
            }
        }
        return count == 1;
    }

    /**
     * Initialise les cases de la map, cette méthode est appelée qu'une seule fois.
     */
    private void initialiseCaseMap() {
        this.caseMap = new ImageView[this.NB_CASE_X][this.NB_CASE_Y];
        for (int i = 0; i < this.NB_CASE_X; i++){
            for (int j = 0; j < this.NB_CASE_Y; j++) {
                this.caseMap[i][j] = new ImageView();
                this.caseMap[i][j].setX((double) i * TAILLE_CASE);
                this.caseMap[i][j].setY((double) j * TAILLE_CASE);
                this.caseMap[i][j].setFitWidth(TAILLE_CASE);
                this.caseMap[i][j].setFitHeight(TAILLE_CASE);
                this.getChildren().add(this.caseMap[i][j]);
            }
        }
    }

    /**
     * Construit une grilles d'Imageview et met les images en fonction de la map générée
     */
    public void afficheMap() {
        for (int i = 0; i < this.NB_CASE_X; i++) {
            for (int j = 0; j < this.NB_CASE_Y; j++) {
                //affichage de la map
                if (this.grid[i][j] == ValeurCase.MUR) {
                    this.caseMap[i][j].setImage(this.imageMur);
                } else if (this.grid[i][j] == ValeurCase.GOMME) {
                    this.caseMap[i][j].setImage(this.imageGomme);
                } else if (this.grid[i][j] == ValeurCase.SUPERGOMME) {
                    this.caseMap[i][j].setImage(this.imageSuperGomme);
                } else if (this.grid[i][j] == ValeurCase.INTERDIT) {
                    this.caseMap[i][j].setImage(null);
                } else if (this.grid[i][j] == ValeurCase.BOOST) {
                    this.caseMap[i][j].setImage(this.imageBoost);
                } else if (this.grid[i][j] == ValeurCase.CERISE) {
                    this.caseMap[i][j].setImage(this.imageCerise);
                } else if (this.grid[i][j] == ValeurCase.FRAISE) {
                    this.caseMap[i][j].setImage(this.imageFraise);
                } else if (this.grid[i][j] == ValeurCase.ORANGE) {
                    this.caseMap[i][j].setImage(this.imageOrange);
                } else if (this.grid[i][j] == ValeurCase.POMME) {
                    this.caseMap[i][j].setImage(this.imagePomme);
                } else if (this.grid[i][j] == ValeurCase.MELON) {
                    this.caseMap[i][j].setImage(this.imageMelon);
                } else if (this.grid[i][j] == ValeurCase.VAISSEAU) {
                    this.caseMap[i][j].setImage(this.imageVaisseau);
                } else if (this.grid[i][j] == ValeurCase.CLOCHE) {
                    this.caseMap[i][j].setImage(this.imageCloche);
                } else if (this.grid[i][j] == ValeurCase.CLEF) {
                    this.caseMap[i][j].setImage(this.imageClef);
                }
            }
        }
        this.caseMap[12][13].setImage(this.imageMurFantome);
    }

    public void miseAJourMap() {
        for (int i = 0; i < this.NB_CASE_X; i++) {
            for (int j = 0; j < this.NB_CASE_Y; j++) {
                //mise à jour de la map
                if (this.grid[i][j] == ValeurCase.VIDE) this.caseMap[i][j].setImage(null);
                else if (this.grid[i][j] == ValeurCase.TELEPORTEUR) this.caseMap[i][j].setImage(this.imageTeleporteur);
            }
        }
    }

    /**
     * Permet de savoir si le joueur a mangé tous les objets qui se trouvaient sur la map
     * @return true si le joueur a gagné
     */
    public boolean aGagne() {
        for (int i = 0; i < this.NB_CASE_X; i++) {
            for (int j = 0; j < this.NB_CASE_Y; j++) {
                if (grid[i][j] != ValeurCase.INTERDIT &&  grid[i][j] != ValeurCase.MUR &&  grid[i][j] != ValeurCase.VIDE &&  grid[i][j] != ValeurCase.TELEPORTEUR) {
                    return false;
                }
            }
        }
        return true;
    }


    ////////////////////////////
    /*    GETTER ET SETTER    */
    ////////////////////////////
    public Graph<String, DefaultEdge> getG() {
        return g;
    }

    public String[][] getGrilleGraph() {
        return grilleGraph;
    }
    public static void initListeThemeMap(){
        theme = new ArrayList<>();
        theme.add("wall.png");
        theme.add("illusion.gif");
    }
    public static void shuffleTheme(){
        Collections.shuffle(theme);
    }
}


