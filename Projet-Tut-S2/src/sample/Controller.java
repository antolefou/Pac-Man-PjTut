package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements EventHandler<KeyEvent> {
    @FXML public sample.Pacman pacman;
    @FXML public sample.Map map;
    public Deplacement deplacement;

    public double getBoardWidth() {
        return Map.TAILLE_CASE * this.map.getColumnCount();
    }

    public double getBoardHeight() {
        return Map.TAILLE_CASE * this.map.getRowCount();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (pacman != null && map != null) {
            if (deplacement == null) {
                System.out.println("deplacement null");
                 deplacement = new Deplacement(map, pacman);
                 deplacement.start();
            }

            if (code == KeyCode.UP) {
                deplacement.deplacementFutur = Deplacement.Deplacements.HAUT;
            } else if (code == KeyCode.RIGHT) {
                deplacement.deplacementFutur = Deplacement.Deplacements.DROITE;
            } else if (code == KeyCode.DOWN) {
                deplacement.deplacementFutur = Deplacement.Deplacements.BAS;
            } else if (code == KeyCode.LEFT) {
                deplacement.deplacementFutur = Deplacement.Deplacements.GAUCHE;
            }
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

    @FXML
    public void switchToSceneCredits(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("credit.fxml"));
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
    public void switchToScenePropos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("propos.fxml"));
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