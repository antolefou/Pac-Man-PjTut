package pacman.controller;

import javafx.stage.Stage;
import pacman.model.Model;


public class Controller {
    private final Model model;
    private final CircleController circleController;

    public Controller(Stage stage, Model model) {
        this.model = model;
        this.circleController = new CircleController(model.getCircleModel());
    }

    public CircleController getCircleController() {
        return circleController;
    }
}