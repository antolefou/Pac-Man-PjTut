package pacman.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ControllerMenu extends Controller{
    @FXML ImageView fleche1;
    @FXML ImageView fleche2;
    @FXML ImageView fleche3;
    @FXML ImageView fleche4;
    @FXML ImageView fleche5;

    public ControllerMenu() {

    }

    public void affiche_fleche1(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Accueil/pacman_fleche.png"));
        fleche1.setImage(image);
    }
    public void enlever_fleche1(MouseEvent mouseEvent) {
        fleche1.setImage(null);
    }

    public void affiche_fleche2(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Accueil/pacman_fleche.png"));
        fleche2.setImage(image);
    }
    public void enlever_fleche2(MouseEvent mouseEvent) {
        fleche2.setImage(null);
    }

    public void affiche_fleche3(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Accueil/pacman_fleche.png"));
        fleche3.setImage(image);
    }
    public void enlever_fleche3(MouseEvent mouseEvent) {
        fleche3.setImage(null);
    }

    public void affiche_fleche4(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Accueil/pacman_fleche.png"));
        fleche4.setImage(image);
    }
    public void enlever_fleche4(MouseEvent mouseEvent) {
        fleche4.setImage(null);
    }

    public void affiche_fleche5(MouseEvent mouseEvent) {
        Image image = new Image(getClass().getResourceAsStream("/pacman/ressources/image/Accueil/pacman_fleche.png"));
        fleche5.setImage(image);
    }
    public void enlever_fleche5(MouseEvent mouseEvent) {
        fleche5.setImage(null);
    }

    public void quitter(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }


}
