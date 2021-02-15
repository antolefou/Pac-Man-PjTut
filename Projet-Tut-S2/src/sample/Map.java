package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Map {

    public static String[][] initialiseMap2(int numeroNiveau) {
        String[][] Map = new String[30][25];
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
        return Map;
    }

    public static void afficheMap(Pane root, String[][] Map) throws FileNotFoundException, MalformedURLException {
        File fileMur = new File("src/Donnees/Image/Mur.png");
        File fileFond = new File("src/Donnees/Image/Fond.png");
        File filePoint = new File("src/Donnees/Image/Point.png");
        File fileBonus = new File("src/Donnees/Image/Bonus.png");

        for (int i=0; i<30; i++) {
            for (int j=0; j<25; j++) {
                switch (Map[i][j]) {
                    case "M": {
                        String localUrl = fileMur.toURI().toURL().toString();
                        Image image = new Image(localUrl);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(25);
                        imageView.setFitHeight(25);
                        imageView.setLayoutX(25 * j);
                        imageView.setLayoutY(25 * i);
                        root.getChildren().addAll(imageView);
                        break;
                    }
                    case "F": {
                        String localUrl = fileFond.toURI().toURL().toString();
                        Image image = new Image(localUrl);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(25);
                        imageView.setFitHeight(25);
                        imageView.setLayoutX(25 * j);
                        imageView.setLayoutY(25 * i);
                        root.getChildren().addAll(imageView);
                        break;
                    }
                    case "P": {
                        String localUrl = filePoint.toURI().toURL().toString();
                        Image image = new Image(localUrl);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(25);
                        imageView.setFitHeight(25);
                        imageView.setLayoutX(25 * j);
                        imageView.setLayoutY(25 * i);
                        root.getChildren().addAll(imageView);
                        break;
                    }
                    case "B": {
                        String localUrl = fileBonus.toURI().toURL().toString();
                        Image image = new Image(localUrl);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(25);
                        imageView.setFitHeight(25);
                        imageView.setLayoutX(25 * j);
                        imageView.setLayoutY(25 * i);
                        root.getChildren().addAll(imageView);
                        break;
                    }
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
}
