package pacman.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ControllerGameOver extends Controller{

    @FXML
    public void handle(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("2");
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/jouer.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
        } else if (event.getCode() == KeyCode.SPACE) {
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/menu.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
        } else {
            //            System.out.println("la clef n'est pas reconu");
        }
    }
}
