package com.example.demo.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

public class ControllerCredit extends Controller{
    @FXML ScrollPane scrollPane;

    public ControllerCredit() {
        super();
    }

    /**
     * Permet une animation de scroll verticale dans les crédit et la joue indéfiniment.
     */
    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60),new KeyValue(scrollPane.vvalueProperty(), 1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
