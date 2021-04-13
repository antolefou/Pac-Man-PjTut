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
    @FXML private sample.Map map;

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
            if (code == KeyCode.UP) {
                if (peutAvancerVerticale(-1))
                    pacman.avanceHaut();
            } else if (code == KeyCode.RIGHT) {
                if (peutAvancerHorizontalement(1))
                pacman.avanceDroite();
            } else if (code == KeyCode.DOWN) {
                if (peutAvancerVerticale(1))
                    pacman.avanceBas();
            } else if (code == KeyCode.LEFT) {
                if (peutAvancerHorizontalement(-1))
                pacman.avanceGauche();
            }
        }
    }
    private boolean peutAvancerVerticale(int i) {
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanX % 20 == 1) {
            if ((pacmanY % 20 != 1) || (map.grid[((int)pacmanY/20)+i][(int)pacmanX/20] != Map.ValeurCase.MUR)) {
                return true;
            }
        }
        return false;
    }
    private boolean peutAvancerHorizontalement(int i) {
        double pacmanX = pacman.getPacmanX();
        double pacmanY = pacman.getPacmanY();
        if (pacmanY % 20 == 1) {
            if ((pacmanX % 20 != 1) || (map.grid[(int)pacmanY/20][((int)pacmanX/20)+i] != Map.ValeurCase.MUR)) {
                return true;
            }
        }
        return false;
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