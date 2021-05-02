package pacman.view;

import javafx.scene.shape.Circle;
import pacman.controller.CircleController;
import pacman.model.CircleModel;
import pacman.model.Model;
import pacman.model.Model;

/**
 * Created by fp on 26.02.17.
 */
public class CirclePane extends Circle {

    public CirclePane(CircleModel circleModel, CircleController circleController) {
        this.setRadius(circleModel.getRadius());
        circleModel.addObserver((o, arg) -> {
            this.setRadius(circleModel.getRadius());
            this.setFill(circleModel.getColor());
        });
        this.setOnMouseClicked(circleController::mouseClicked);


    }
}