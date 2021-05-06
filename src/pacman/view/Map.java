package pacman.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map extends Group {
    public final static double TAILLE_CASE = 20.0;

    public enum ValeurCase { VIDE, GOMME, SUPERGOMME, MUR}

    private int nbCaseX = 25;
    private int nbCaseY = 30;

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


    /**
     * Initialise les valeurs des images
     * map code:
     *      M  -> mur
     *      G  -> gomme
     *      S  -> super gomme
     */
    public Map() {
        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacmanLeft.gif"));
        this.imageFantome1 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/ghost1.gif"));
        this.imageFantome2 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/ghost2.gif"));
        this.imageFantome3 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/ghost2.gif"));
        this.imageFantome4 = new Image(getClass().getResourceAsStream("/pacman/ressources/image/ghost2.gif"));
        this.imageFantomeBleu = new Image(getClass().getResourceAsStream("/pacman/ressources/image/blueghost.gif"));
        this.imageMur = new Image(getClass().getResourceAsStream("/pacman/ressources/image/wall.png"));
        this.imageGomme = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Gomme.png"));
        this.imageSuperGomme = new Image(getClass().getResourceAsStream("/pacman/ressources/image/SuperGomme.png"));
        this.imageFond = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Fond.png"));

/*
        this.initialiseMap(1);
        this.afficheMap();
        */
        MapGenerator map = new MapGenerator();
        mapGeneree = map.getMap();
        initialiseMapGeneree();
        afficheMap();
    }

    public void initialiseMap(int numeroNiveau) {
        this.grid = new ValeurCase[this.nbCaseX][this.nbCaseY];
        String pwd = System.getProperty("user.dir");
        File fichier = new File(pwd + "/src/pacman/ressources/level(temporaire/niveau1.txt");
        try {
            int compteur = 0;
            Scanner sc = new Scanner(fichier);
            while(sc.hasNextLine()){
                String ligne = sc.nextLine();
                for (int i=0; i<this.nbCaseX; i++) {
                    //Map[compteur][i] = String.valueOf(ligne.charAt(i));
                    switch (String.valueOf(ligne.charAt(i))) {
                        case "M":
                            this.grid[compteur][i] = ValeurCase.MUR;
                            break;
                        case "G":
                            this.grid[compteur][i] = ValeurCase.GOMME;
                            break;
                        case "S":
                            this.grid[compteur][i] = ValeurCase.SUPERGOMME;
                            break;
                    }
                }
                compteur ++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("FICHIER NON TROUVÉ");
        }
    }

    private void initialiseMapGeneree() {
        grid = new ValeurCase[this.nbCaseX][this.nbCaseY];
        for (int i=0; i<25; i++) {
            for (int j=0; j<30; j++) {
                System.out.println(mapGeneree[i][j]);
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
                }
            }
        }
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
                }
                this.getChildren().add(this.caseMap[i][j]);
            }
        }
    }
}
