package pacman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import pacman.model.Model;
import pacman.controller.Controller;
import pacman.view.View;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        View view = new View(model);

        Scene scene = new Scene(view);
        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}