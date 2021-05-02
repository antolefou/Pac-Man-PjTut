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

/**
 * Created by fp on 26.02.17.
 */
public class View extends StackPane {
    private final Model model;
    private final Controller controller;

    public View(Model model, Controller controller) throws IOException {
        this.model = model;
        this.controller = controller;

        this.getChildren().add(FXMLLoader.load(getClass().getResource( "menu.fxml" )));

        //CirclePane circleView = new CirclePane(model.getCircleModel(), controller.getCircleController());
        //this.getChildren().add(circleView);
        //createListners();
    }

    private void createListners() {
        this.setOnMouseMoved(e -> controller.getCircleController().mousePosChanged(e));
    }
}