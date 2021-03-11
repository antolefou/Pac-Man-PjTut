package sample;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Controller implements EventHandler<KeyEvent> {



    @FXML private Arc pacman;
    @FXML private Arc pac;
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP) {
            pacman.setLayoutY(pacman.getLayoutY()-5);
            System.out.println("up");
        } else if (code == KeyCode.RIGHT) {
            pacman.setLayoutX(pacman.getLayoutX()+5);
            System.out.println("right");
        } else if (code == KeyCode.DOWN) {
            pacman.setLayoutY(pacman.getLayoutY()+5);
            System.out.println("down");
        } else if (code == KeyCode.LEFT) {
            pacman.setLayoutX(pacman.getLayoutX()-5);
            System.out.println("left");
        }
    }
}
