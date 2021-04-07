package sample;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import sample.PacManModel.CellValue;

public class Map extends Group {
    public final static double TAILLE_CASE = 20.0;

    public enum ValeurCase { VIDE, GOMME, SUPERGOMME, MUR}

    @FXML private int rowCount;
    @FXML private int columnCount;

    private ValeurCase[][] grid;
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

        this.initialiseMap(1);
        this.InitialiseCaseMap();
        this.AfficheMap();
        System.out.println("fknkefn");
    }

    /**
     * Construit une grilles d'Imageview
     */
    public void InitialiseCaseMap() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.caseMap = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * TAILLE_CASE);
                    imageView.setY((double)row * TAILLE_CASE);
                    imageView.setFitWidth(TAILLE_CASE);
                    imageView.setFitHeight(TAILLE_CASE);
                    this.caseMap[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }
    public void initialiseMap(int numeroNiveau) {
        this.grid = new ValeurCase[this.rowCount][this.columnCount];
        File fichier = new File("/home/julien/Documents/Git/Pac-Man-PjTut/Projet-Tut-S2/src/Donnees/Level/Niveau" +  numeroNiveau + ".txt");
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
     * Affiche les images
     */
    public void AfficheMap() {
        for (int ligne=0; ligne<this.rowCount; ligne++) {
            for (int colonne=0; colonne<this.columnCount; colonne++) {
                if (this.grid[ligne][colonne] == ValeurCase.MUR) {
                    this.caseMap[ligne][colonne].setImage(this.imageMur);
                } else if (this.grid[ligne][colonne] == ValeurCase.GOMME) {
                    this.caseMap[ligne][colonne].setImage(this.imageGomme);
                } else if (this.grid[ligne][colonne] == ValeurCase.SUPERGOMME) {
                    this.caseMap[ligne][colonne].setImage(this.imageSuperGomme);
                }
            }
        }
    }




    public int getRowCount() {
        return this.rowCount;
    }
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.InitialiseCaseMap();
    }
    public int getColumnCount() {
        return this.columnCount;
    }
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.InitialiseCaseMap();
    }

}
