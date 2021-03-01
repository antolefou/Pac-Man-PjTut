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
import java.net.URL;
import java.util.Scanner;
import java.io.*;
import javafx.scene.paint.Color;

public class Main extends Application {
    public String[][] Map = new String[30][25];

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Pane root = loader.load();
        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        Scene scene = new Scene(root, 25*25, 25*30);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        Map = sample.Map.initialiseMap2(1);
        sample.Map.afficheMap(root, Map);
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
