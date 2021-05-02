package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMenu {
    @FXML ImageView fleche1;
    @FXML ImageView fleche2;
    @FXML ImageView fleche3;
    @FXML ImageView fleche4;
    @FXML ImageView fleche5;


    public void affiche_fleche1(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacman_fleche.png"));
        fleche1.setImage(image);
    }
    public void enlever_fleche1(MouseEvent mouseEvent) {
        fleche1.setImage(null);
    }

    public void affiche_fleche2(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacman_fleche.png"));
        fleche2.setImage(image);
    }
    public void enlever_fleche2(MouseEvent mouseEvent) {
        fleche2.setImage(null);
    }

    public void affiche_fleche3(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacman_fleche.png"));
        fleche3.setImage(image);
    }
    public void enlever_fleche3(MouseEvent mouseEvent) {
        fleche3.setImage(null);
    }

    public void affiche_fleche4(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacman_fleche.png"));
        fleche4.setImage(image);
    }
    public void enlever_fleche4(MouseEvent mouseEvent) {
        fleche4.setImage(null);
    }

    public void affiche_fleche5(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/pacman_fleche.png"));
        fleche5.setImage(image);
    }
    public void enlever_fleche5(MouseEvent mouseEvent) {
        fleche5.setImage(null);
    }

    @FXML
    public void switchToSceneScore(ActionEvent event) throws IOException {
        System.out.println("changement de scene");

        /*                     NE FONCTIONNE PAS
        Parent root = FXMLLoader.load(((Node) event.getSource()).getClass().getResource( "menu.fxml" ));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
        root.requestFocus();*/


    }
}
