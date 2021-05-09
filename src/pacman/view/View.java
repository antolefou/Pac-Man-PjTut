package pacman.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import pacman.controller.Controller;
import pacman.model.Model;

import java.io.IOException;
import java.util.Objects;


public class View extends StackPane {
    private final Model model;

    public View(Model model) throws IOException {
        this.model = model;
        this.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml"))));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
//        Parent root = loader.load();


    }
}