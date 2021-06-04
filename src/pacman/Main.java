package pacman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

import pacman.model.Utilisateur;
import pacman.view.View;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
//         Affichage de la vue
        View view = new View();
        Utilisateur utilisateur = new Utilisateur(); // à ne pas supprimer même si il n'est pas utilisé
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