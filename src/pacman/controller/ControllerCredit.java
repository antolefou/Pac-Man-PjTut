package pacman.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ControllerCredit extends Controller{

    public ControllerCredit() {
        super();
    }
    public void scroll(MouseEvent mouseEvent) {
        ScrollPane scrollPane = (ScrollPane) mouseEvent.getSource();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30),new KeyValue(scrollPane.vvalueProperty(), 1)));
        //timeline.setCycleCount(Animation.INDEFINITE);
        Animation animation = timeline;
        animation.play();
    }
}
