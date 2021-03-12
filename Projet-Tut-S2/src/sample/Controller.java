package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {
    @FXML public sample.Pacman Pacman;
    @FXML private sample.Map Map;

    public double getBoardWidth() {
        return Map.TAILLE_CASE * this.Map.getColumnCount();
    }

    public double getBoardHeight() {
        return Map.TAILLE_CASE * this.Map.getRowCount();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        Map.initialiseMap(1);
        Map.InitialiseCaseMap();
        Map.AfficheMap();

        if (code == KeyCode.UP) {
            System.out.println("up");
            Pacman.AvanceHaut();
        } else if (code == KeyCode.RIGHT) {
            System.out.println("right");
            Pacman.AvanceDroite();
        } else if (code == KeyCode.DOWN) {
            System.out.println("down");
            Pacman.AvanceBas();
        } else if (code == KeyCode.LEFT) {
            System.out.println("left");
            Pacman.AvanceGauche();
        }
    }
}