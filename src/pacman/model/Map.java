package pacman.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Map extends Group  {
    public final static double TAILLE_CASE = 20.0;

    public enum ValeurCase { VIDE, MUR, GOMME, SUPERGOMME, BOOST, INTERDIT
                            , CERISE, FRAISE, ORANGE, POMME, MELON, VAISSEAU
                            , CLOCHE, CLEF}

    private final int NB_CASE_X = 25;
    private final int NB_CASE_Y = 30;

    public MapGenerator mapGenerator;

    public String[][] mapGeneree;
    public ValeurCase[][] grid;
    public ImageView[][] caseMap;
//    Images
    public Image imageMur;
    public Image imageGomme;
    public Image imageSuperGomme;
    public Image imageBoost;
    public Image imageMurFantome;
    public Image imageCerise;
    public Image imageFraise;
    public Image imageOrange;
    public Image imagePomme;
    public Image imageMelon;
    public Image imageVaisseau;
    public Image imageCloche;
    public Image imageClef;
//    Graphe
    public Graph<String, DefaultEdge> g;
    public String[][] grilleGraph;



    /**
     * Initialise les valeurs des images
     * map code:
     *      M  -> mur
     *      G  -> gomme
     *      S  -> super gomme
     */
    public Map() {
        String src = "/pacman/ressources/image/Ecran_jouer/labyrinthe/";
        this.imageMur = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src + "wall.png")));
        this.imageGomme = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "Gomme.png")));
        this.imageSuperGomme = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "SuperGomme.png")));
        this.imageBoost = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "boost.png")));
        this.imageMurFantome = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "murFantome.png")));
        this.imageCerise = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "cerise.png")));
        this.imageFraise = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "fraise.png")));
        this.imageOrange = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "orange.png")));
        this.imagePomme = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "pomme.png")));
        this.imageMelon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "melon.png")));
        this.imageVaisseau = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "vaisseau.png")));
        this.imageCloche = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "cloche.png")));
        this.imageClef = new Image(Objects.requireNonNull(getClass().getResourceAsStream(src +  "clef.png")));

        mapGenerator = new MapGenerator();
        mapGenerator.initObjet(5, 3, 1);
        mapGeneree = mapGenerator.getMap();
//        Vide mapGeneree
        mapGenerator = null;

        initialiseMapGeneree();
        initGraph();

        while (!estConnexe()){
            mapGenerator = new MapGenerator();
            mapGenerator.initObjet(5, 3, 1);
            mapGeneree = mapGenerator.getMap();
//        Vide mapGeneree
            mapGenerator = null;


            initialiseMapGeneree();
            initGraph();
//            System.out.println("###############################est PASSER DANS LA PUTAIN DE BOUCLE##########################");
        }
//        System.out.println(estConnexe());
//        System.out.println("-----------------------------");
        afficheMap();
    }

    private void initialiseMapGeneree() {
        grid = new ValeurCase[this.NB_CASE_X][this.NB_CASE_Y];
        for (int i=0; i<NB_CASE_X; i++) {
            for (int j=0; j<NB_CASE_Y; j++) {
                switch (mapGeneree[i][j]) {
                    case "V":
                        this.grid[i][j] = ValeurCase.VIDE;
                        break;
                    case "M":
                        this.grid[i][j] = ValeurCase.MUR;
                        break;
                    case "G":
                        this.grid[i][j] = ValeurCase.GOMME;
                        break;
                    case "S":
                        this.grid[i][j] = ValeurCase.SUPERGOMME;
                        break;
                    case "B":
                        this.grid[i][j] = ValeurCase.BOOST;
                        break;
                    case "I":
                        this.grid[i][j] = ValeurCase.INTERDIT;
                        break;
                    case "C":
                        this.grid[i][j] = ValeurCase.CERISE;
                        break;
                    case "F":
                        this.grid[i][j] = ValeurCase.FRAISE;
                        break;
                    case "O":
                        this.grid[i][j] = ValeurCase.ORANGE;
                        break;
                    case "P":
                        this.grid[i][j] = ValeurCase.POMME;
                        break;
                    case "ME":
                        this.grid[i][j] = ValeurCase.MELON;
                        break;
                    case "VA":
                        this.grid[i][j] = ValeurCase.VAISSEAU;
                        break;
                    case "CLO":
                        this.grid[i][j] = ValeurCase.CLOCHE;
                        break;
                    case "CLE":
                        this.grid[i][j] = ValeurCase.CLEF;
                        break;
                }
            }
        }
    }

    public void initGraph(){
        // Création du Graph
        this.g = new SimpleGraph<>(DefaultEdge.class);
        this.grilleGraph = new String[NB_CASE_X][NB_CASE_Y];
        for (int i=0; i<NB_CASE_X; i++) {
            for (int j=0; j<NB_CASE_Y; j++) {
                if (grid[i][j] != ValeurCase.MUR) {
                    grilleGraph[i][j] = i+"/"+j;
                    g.addVertex(grilleGraph[i][j]);
                }else{
                    grilleGraph[i][j] = "Mur"+i+"/"+j;
                    g.addVertex(grilleGraph[i][j]);
                }
            }
        }
        for (int i=0; i<NB_CASE_X; i++) {
            for (int j=0; j<NB_CASE_Y; j++) {
                if (grilleGraph[i][j].equals(i + "/" + j) && grilleGraph[(i + 1) % NB_CASE_X][j].equals((i + 1) % NB_CASE_X + "/" + j)) {
                    g.addEdge(grilleGraph[i][j], grilleGraph[((i+1)%NB_CASE_X)][j]);
                }
                if (grilleGraph[i][j].equals(i + "/" + j) && grilleGraph[i][(j + 1) % NB_CASE_Y].equals(i + "/" + (j + 1) % NB_CASE_Y)) {
                    g.addEdge(grilleGraph[i][j], grilleGraph[i][(j+1)%NB_CASE_Y]);
                }
            }
        }

//        System.out.println(g.toString());
//        // @example:toString:end
//        System.out.println();
//        // Fin de création du Graph
//        System.out.println((DijkstraShortestPath.findPathBetween(g, grilleGraph[1][1] , grilleGraph[23][28] )));
    }

    public boolean estConnexe(){
        ConnectivityInspector<String, DefaultEdge> gTestConnecti = new ConnectivityInspector<>(this.g);
        List<Set<String>> listeSousGraphe = gTestConnecti.connectedSets();
        int count = 0;
        for (int k = 0; k < listeSousGraphe.size(); k++){
            if (listeSousGraphe.get(k).size() > 1){
//                System.out.println(listeSousGraphe.get(k));
                count++;
            }
        }
        return count == 1;
    }

    /**
     * Construit une grilles d'Imageview
     */
    public void afficheMap() {
        this.caseMap = new ImageView[this.NB_CASE_X][this.NB_CASE_Y];
        for (int i = 0; i < this.NB_CASE_X; i++) {
            for (int j = 0; j < this.NB_CASE_Y; j++) {
                this.caseMap[i][j] = new ImageView();
                this.caseMap[i][j].setX((double)i * TAILLE_CASE);
                this.caseMap[i][j].setY((double)j * TAILLE_CASE);
                this.caseMap[i][j].setFitWidth(TAILLE_CASE);
                this.caseMap[i][j].setFitHeight(TAILLE_CASE);
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
                this.getChildren().add(this.caseMap[i][j]);
            }
        }
        this.caseMap[12][13].setImage(this.imageMurFantome);
    }

    /**
     * Construit une grilles d'Imageview
     */
    public void miseAJourMap() {
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

    public boolean aGagne() {
        for (int i = 0; i < this.NB_CASE_X; i++) {
            for (int j = 0; j < this.NB_CASE_Y; j++) {
                if (grid[i][j] != ValeurCase.INTERDIT &&  grid[i][j] != ValeurCase.MUR &&  grid[i][j] != ValeurCase.VIDE) {
                    return false;
                }
            }
        }
        return true;
    }

    public void recommence(int i) {
        mapGenerator = new MapGenerator();
        mapGenerator.initObjet(5, 4, i);
        mapGeneree = mapGenerator.getMap();
        initialiseMapGeneree();
        miseAJourMap();
        initGraph();
    }

    public Graph<String, DefaultEdge> getG() {
        return g;
    }

    public String[][] getGrilleGraph() {
        return grilleGraph;
    }
}
