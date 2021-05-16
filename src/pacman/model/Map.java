package pacman.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Map extends Group {
    public final static double TAILLE_CASE = 20.0;

    public enum ValeurCase { VIDE, GOMME, SUPERGOMME, MUR, BOOST, INTERDIT}

    private int nbCaseX = 25;
    private int nbCaseY = 30;

    public MapGenerator mapGenerator;

    public String[][] mapGeneree;
    public ValeurCase[][] grid;
    public ImageView[][] caseMap;
    public Image imagePacmanHaut;
    public Image imagePacmanDroite;
    public Image imagePacmanBas;
    public Image imagePacmanGauche;
    public Image imageFantome1;
    public Image imageFantome2;
    public Image imageFantome3;
    public Image imageFantome4;
    public Image imageFantomeBleu;
    public Image imageMur;
    public Image imageGomme;
    public Image imageSuperGomme;
    public Image imageFond;
    public Image imageBoost;
    public Image imageMurFantome;
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
        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/pacmanLeft.gif"));
        this.imageFantome1 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost1.gif"));
        this.imageFantome2 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost2.gif"));
        this.imageFantome3 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost2.gif"));
        this.imageFantome4 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/ghost2.gif"));
        this.imageFantomeBleu = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/blueghost.gif"));
        this.imageMur = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/wall.png"));
        this.imageGomme = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/Gomme.png"));
        this.imageSuperGomme = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/SuperGomme.png"));
        this.imageFond = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/Fond.png"));
        this.imageBoost = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/boost.png"));
        this.imageMurFantome = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_jouer/labyrinthe/murFantome.png"));

        mapGenerator = new MapGenerator();
        mapGenerator.initObjet(5, 3);
        mapGeneree = mapGenerator.getMap();
        initialiseMapGeneree();
        initGraph();
        afficheMap();
    }

    private void initialiseMapGeneree() {
        grid = new ValeurCase[this.nbCaseX][this.nbCaseY];
        for (int i=0; i<25; i++) {
            for (int j=0; j<30; j++) {
                switch (mapGeneree[i][j]) {
                    case "M":
                        this.grid[i][j] = ValeurCase.MUR;
                        break;
                    case "G":
                        this.grid[i][j] = ValeurCase.GOMME;
                        break;
                    case "S":
                        this.grid[i][j] = ValeurCase.SUPERGOMME;
                        break;
                    case "I":
                        this.grid[i][j] = ValeurCase.INTERDIT;
                        break;
                    case "B":
                        this.grid[i][j] = ValeurCase.BOOST;
                        break;
                }
            }
        }
    }

    public void initGraph(){
        // Création du Graph
        this.g = new SimpleGraph<>(DefaultEdge.class);
        this.grilleGraph = new String[25][30];
        for (int i=0; i<25; i++) {
            for (int j=0; j<30; j++) {
                if (grid[i][j] != ValeurCase.MUR) {
                    grilleGraph[i][j] = i+"/"+j;
                    g.addVertex(grilleGraph[i][j]);
                }else{
                    grilleGraph[i][j] = "Mur"+i+"/"+j;
                    g.addVertex(grilleGraph[i][j]);
                }
            }
        }
        for (int i=0; i<25; i++) {
            for (int j=0; j<30; j++) {
                if (grilleGraph[i][j].equals(i + "/" + j) && grilleGraph[(i + 1) % 25][j].equals((i + 1) % 25 + "/" + j)) {
                    g.addEdge(grilleGraph[i][j], grilleGraph[((i+1)%25)][j]);
                }
                if (grilleGraph[i][j].equals(i + "/" + j) && grilleGraph[i][(j + 1) % 30].equals(i + "/" + (j + 1) % 30)) {
                    g.addEdge(grilleGraph[i][j], grilleGraph[i][(j+1)%30]);
                }
            }
        }

//        System.out.println(g.toString());
//        // @example:toString:end
//        System.out.println();
//        // Fin de création du Graph
//        System.out.println((DijkstraShortestPath.findPathBetween(g, grilleGraph[1][1] , grilleGraph[23][28] )));

    }
    /**
     * Construit une grilles d'Imageview
     */
    public void afficheMap() {
        this.caseMap = new ImageView[this.nbCaseX][this.nbCaseY];
        for (int i = 0; i < this.nbCaseX; i++) {
            for (int j = 0; j < this.nbCaseY; j++) {
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
                    this.caseMap[i][j].setImage(this.imageFond);
                } else if (this.grid[i][j] == ValeurCase.BOOST) {
                    this.caseMap[i][j].setImage(this.imageBoost);
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
        for (int i = 0; i < this.nbCaseX; i++) {
            for (int j = 0; j < this.nbCaseY; j++) {
                //affichage de la map
                if (this.grid[i][j] == ValeurCase.MUR) {
                    this.caseMap[i][j].setImage(this.imageMur);
                } else if (this.grid[i][j] == ValeurCase.GOMME) {
                    this.caseMap[i][j].setImage(this.imageGomme);
                } else if (this.grid[i][j] == ValeurCase.SUPERGOMME) {
                    this.caseMap[i][j].setImage(this.imageSuperGomme);
                } else if (this.grid[i][j] == ValeurCase.INTERDIT) {
                    this.caseMap[i][j].setImage(this.imageFond);
                } else if (this.grid[i][j] == ValeurCase.BOOST) {
                    this.caseMap[i][j].setImage(this.imageBoost);
                }
            }
        }
        this.caseMap[12][13].setImage(this.imageMurFantome);
    }

    public boolean aGagne() {
        for (int i = 0; i < this.nbCaseX; i++) {
            for (int j = 0; j < this.nbCaseY; j++) {
                if (grid[i][j] != ValeurCase.INTERDIT &&  grid[i][j] != ValeurCase.MUR &&  grid[i][j] != ValeurCase.VIDE) {
                    return false;
                }
            }
        }
        return true;
    }

    public void recommence() {
        mapGenerator = new MapGenerator();
        mapGenerator.initObjet(5, 3);
        mapGeneree = mapGenerator.getMap();
        initialiseMapGeneree();
        miseAJourMap();
        initGraph();
    }
}
