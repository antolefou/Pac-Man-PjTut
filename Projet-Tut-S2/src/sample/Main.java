package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import javafx.scene.paint.Color;

import javax.swing.text.html.ImageView;

public class Main extends Application {
    public String[][] Map = new String[30][30];


    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 600);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        initialiseMap(1);
        afficheMap(root);
    }


    public void initialiseMap(int numeroNiveau) throws IOException {
        File fichier = new File("src/Donnees/Level/Niveau1.txt");
        try {
            int compteur = 0;
            Scanner sc = new Scanner(fichier);

            while(sc.hasNextLine()){
                String ligne = sc.nextLine();
                for (int i=0; i<25; i++) {
                    Map[compteur][i] = String.valueOf(ligne.charAt(i));
                }
                compteur ++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("FICHIER NON TROUVÉ");
        }
    }

    public void afficheMap(Pane root) throws FileNotFoundException {
        for (int i=0; i<30; i++) {
            for (int j=0; j<25; j++) {
                System.out.print(Map[i][j] + " ");
            }
            System.out.println("");
        }
        /*
        Arc pac = new Arc();
        pac.setFill(Color.YELLOW);
        pac.setRadiusX(30);
        pac.setRadiusY(30);
        pac.setLength(270);
        pac.setStartAngle(45);
        pac.setLayoutX(50);
        pac.setLayoutY(50);
        pac.setType(ArcType.ROUND);

        root.getChildren().add(pac);*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}
