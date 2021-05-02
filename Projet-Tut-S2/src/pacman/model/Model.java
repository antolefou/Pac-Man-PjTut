package pacman.model;

import javafx.scene.paint.Color;

/**
 * Created by fp on 26.02.17.
 */
public class Model {
    private final CircleModel circleModel;

    public Model() {
        this.circleModel = new CircleModel(100, Color.ALICEBLUE);
    }

    public CircleModel getCircleModel() {
        return circleModel;
    }
}