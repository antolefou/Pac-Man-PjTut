package sample;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP) {
            System.out.println("up");
        } else if (code == KeyCode.RIGHT) {
            System.out.println("right");
        } else if (code == KeyCode.DOWN) {
            System.out.println("down");
        } else if (code == KeyCode.LEFT) {
            System.out.println("left");
        }
    }
}
