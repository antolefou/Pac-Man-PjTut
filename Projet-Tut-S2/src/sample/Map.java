package sample;

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

    private int rowCount = 30;
    private int columnCount = 25;

    public ValeurCase[][] grid;
    public ImageView[][] caseMap;
    private Image imagePacmanHaut;
    private Image imagePacmanDroite;
    private Image imagePacmanBas;
    private Image imagePacmanGauche;
    private Image imageFantome1;
    private Image imageFantome2;
    private Image imageFantome3;
    private Image imageFantome4;
    private Image imageFantomeBleu;
    private Image imageMur;
    private Image imageGomme;
    private Image imageSuperGomme;


    /**
     * Initialise les valeurs des images
     */
    public Map() {
        this.initImage();
        this.initialiseMap(1);
        this.afficheMap();
    }


    /**
     * Initialise les images
     */
    public void initImage(){
        this.imagePacmanHaut = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanUp.gif"));
        this.imagePacmanDroite = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanRight.gif"));
        this.imagePacmanBas = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanDown.gif"));
        this.imagePacmanGauche = new Image(getClass().getResourceAsStream("/Donnees/Image/pacmanLeft.gif"));
        this.imageFantome1 = new Image(getClass().getResourceAsStream("/Donnees/Image/ghost1.gif"));
        this.imageFantome2 = new Image(getClass().getResourceAsStream("/Donnees/Image/ghost2.gif"));
        this.imageFantome3 = new Image(getClass().getResourceAsStream("/Donnees/Image/ghost2.gif"));
        this.imageFantome4 = new Image(getClass().getResourceAsStream("/Donnees/Image/ghost2.gif"));
        this.imageFantomeBleu = new Image(getClass().getResourceAsStream("/Donnees/Image/blueghost.gif"));
        this.imageMur = new Image(getClass().getResourceAsStream("/Donnees/Image/wall.png"));
        this.imageGomme = new Image(getClass().getResourceAsStream("/Donnees/Image/Gomme.png"));
        this.imageSuperGomme = new Image(getClass().getResourceAsStream("/Donnees/Image/SuperGomme.png"));
    }


    /**
     * Attribue une image à une coordoné en fonction de la lettre dans le fichier .txt
     */
    public void initialiseMap(int numeroNiveau) {
        this.grid = new ValeurCase[this.rowCount][this.columnCount];
        String pwd = System.getProperty("user.dir");
        File fichier = new File(pwd + "/src/Donnees/Level/Niveau" +  numeroNiveau + ".txt");
        try {
            int compteur = 0;
            Scanner sc = new Scanner(fichier);
            while(sc.hasNextLine()){
                String ligne = sc.nextLine();
                for (int i=0; i<this.columnCount; i++) {
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

    /**
     * Construit une grilles d'Imageview
     */
    public void afficheMap() {
        this.caseMap = new ImageView[this.rowCount][this.columnCount];
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                this.caseMap[row][column] = new ImageView();
                this.caseMap[row][column].setX((double)column * TAILLE_CASE);
                this.caseMap[row][column].setY((double)row * TAILLE_CASE);
                this.caseMap[row][column].setFitWidth(TAILLE_CASE);
                this.caseMap[row][column].setFitHeight(TAILLE_CASE);
                //affichage de la map
                if (this.grid[row][column] == ValeurCase.MUR) {
                    this.caseMap[row][column].setImage(this.imageMur);
                } else if (this.grid[row][column] == ValeurCase.GOMME) {
                    this.caseMap[row][column].setImage(this.imageGomme);
                } else if (this.grid[row][column] == ValeurCase.SUPERGOMME) {
                    this.caseMap[row][column].setImage(this.imageSuperGomme);
                }
                this.getChildren().add(this.caseMap[row][column]);
            }
        }
    }

    public double getColumnCount() {
        return this.columnCount;
    }
    public double getRowCount() {
        return this.rowCount;
    }
}
