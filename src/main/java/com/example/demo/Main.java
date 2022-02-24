package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Platform;

import com.example.demo.model.Utilisateur;
import com.example.demo.view.View;

public class Main extends Application {

    Font baseFont = new Font("utkal medium",18);
    Font rosemary;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        rosemary = Font.loadFont(
                "./Rosemary_Roman.ttf",
                10
        );

//         Affichage de la vue
        View view = new View();
        Utilisateur utilisateur = new Utilisateur(); // à ne pas supprimer même si il n'est pas utilisé car utilisé au niveau global

//         Création d'une scène sans initialiser la vue
        Scene scene = new Scene(view);
        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.requestFocus();

//         stop le programme lorsque l'on quitte la fenêtre
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });


    }
}

//
//package com.example.demo;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import javafx.application.Platform;
//
//import com.example.demo.model.Utilisateur;
//import com.example.demo.view.View;
//
//public class Main extends Application {
//    public static Stage primaryStage;
//
//
//    public static Scene scene_amelioration_competence;
//    public static Scene scene_choix_competence;
//    public static Scene scene_credit;
//    public static Scene scene_game_over;
//    public static Scene scene_jouer;
//    public static Scene scene_menu;
//    public static Scene scene_option;
//    public static Scene scene_score;
//
//
//    Font baseFont = new Font("utkal medium",18);
//    Font rosemary;
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    public void start(Stage stage) throws Exception {
//        primaryStage = stage;
//
//        rosemary = Font.loadFont(
//                "./Rosemary_Roman.ttf",
//                10
//        );
//
////         Affichage de la vue
////        View view = new View();
//        Utilisateur utilisateur = new Utilisateur(); // à ne pas supprimer même si il n'est pas utilisé
//
////         Création d'une scène sans initialiser la vue
////        Scene scene = new Scene(view);
//
//
//
//        // Préchargement des scènes
////        FXMLLoader loader = new FXMLLoader();
////        loader.setLocation(getClass().getResource("/view/menu.fxml"));
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
//        Parent root = loader.load();
//        System.out.println(root);
//        scene_menu = new Scene(root, 1600, 800);
//
//
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
////
////
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
////
////
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
////
////
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
////
////
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
////
////
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
////
////
////        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
////        Parent root_accueil = fxmlLoaderAccueil.load();
//
//
//
//        // SCENES PRINCIPALES
////        scene_menu = new Scene(root_accueil, 1600, 800);
////        scene_amelioration_competence = new Scene(root_accueil, 1440, 1024);
////        scene_choix_competence = new Scene(root_accueil, 1440, 1024);
////        scene_credit = new Scene(root_accueil, 1440, 1024);
////        scene_game_over = new Scene(root_accueil, 1440, 1024);
////        scene_jouer = new Scene(root_accueil, 1440, 1024);
////        scene_option = new Scene(root_accueil, 1440, 1024);
////        scene_score = new Scene(root_accueil, 1440, 1024);
//
//
//        primaryStage.setTitle("Pacman");
//        primaryStage.setScene(scene_menu);
//        primaryStage.show();
//        primaryStage.requestFocus();
//
////         stop le programme lorsque l'on quitte la fenêtre
//        primaryStage.setOnCloseRequest(t -> {
//            Platform.exit();
//            System.exit(0);
//        });
//
//
//    }
//}