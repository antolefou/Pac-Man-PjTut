package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;
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
            Pacman.AvanceHaut();
        } else if (code == KeyCode.RIGHT) {
            Pacman.AvanceDroite();
        } else if (code == KeyCode.DOWN) {
            Pacman.AvanceBas();
        } else if (code == KeyCode.LEFT) {
            Pacman.AvanceGauche();
        }
    }


    @FXML
    public void switchToSceneJouer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pacman.fxml"));
        Parent root = loader.load();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Pac-Man");
        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        double sceneWidth = controller.getBoardWidth() + 20.0;
        double sceneHeight = controller.getBoardHeight() + 100.0;
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
        root.requestFocus();
    }
    @FXML
    public void switchToSceneMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Pac-Man");
        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
        root.requestFocus();
    }
    @FXML
    public void switchToSceneScore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("score.fxml"));
        Parent root = loader.load();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Pac-Man");
        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
        root.requestFocus();
    }
}