package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.io.*;
import javafx.scene.paint.Color;

public class Main extends Application {
    public String[][] Map = new String[30][30];


    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        Scene scene = new Scene(root, 25*25, 25*30);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        initialiseMap(1);
        afficheMap(root);

    }


    public void initialiseMap(int numeroNiveau) {
        File fichier = new File("src/Donnees/Level/Niveau" +  numeroNiveau + ".txt");
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

    public void afficheMap(Pane root) throws FileNotFoundException, MalformedURLException {
        /*
        for (int i=0; i<30; i++) {
            for (int j=0; j<25; j++) {
                System.out.print(Map[i][j] + " ");
            }
            System.out.println("");
        }*/
        File fileMur = new File("src/Donnees/Image/Mur.png");
        File fileFond = new File("src/Donnees/Image/Fond.png");
        File filePoint = new File("src/Donnees/Image/Point.png");
        File fileBonus = new File("src/Donnees/Image/Bonus.png");
        for (int i=0; i<30; i++) {
            for (int j=0; j<25; j++) {
                if (Map[i][j].equals("M")) {
                    String localUrl = fileMur.toURI().toURL().toString();
                    javafx.scene.image.Image image = new Image(localUrl);
                    javafx.scene.image.ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);
                    imageView.setLayoutX(25*j);
                    imageView.setLayoutY(25*i);
                    root.getChildren().addAll(imageView);
                } else if (Map[i][j].equals("F")) {
                    String localUrl = fileFond.toURI().toURL().toString();
                    javafx.scene.image.Image image = new Image(localUrl);
                    javafx.scene.image.ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);
                    imageView.setLayoutX(25*j);
                    imageView.setLayoutY(25*i);
                    root.getChildren().addAll(imageView);
                } else if (Map[i][j].equals("P")) {
                    String localUrl = filePoint.toURI().toURL().toString();
                    javafx.scene.image.Image image = new Image(localUrl);
                    javafx.scene.image.ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);
                    imageView.setLayoutX(25*j);
                    imageView.setLayoutY(25*i);
                    root.getChildren().addAll(imageView);
                } else if (Map[i][j].equals("B")) {
                    String localUrl = fileBonus.toURI().toURL().toString();
                    javafx.scene.image.Image image = new Image(localUrl);
                    javafx.scene.image.ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);
                    imageView.setLayoutX(25*j);
                    imageView.setLayoutY(25*i);
                    root.getChildren().addAll(imageView);
                }
            }
        }



        Arc pac = new Arc();
        pac.setFill(Color.YELLOW);
        pac.setRadiusX(10);
        pac.setRadiusY(10);
        pac.setLength(270);
        pac.setStartAngle(45);
        pac.setLayoutX(50);
        pac.setLayoutY(50);
        pac.setType(ArcType.ROUND);
        pac.setId("pacman");
        root.getChildren().add(pac);

        pac.setLayoutX(200);
        pac.setLayoutY(200);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
