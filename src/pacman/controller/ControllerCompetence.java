package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;

public class ControllerCompetence extends Controller{
    @FXML Text description;
    
    public ControllerCompetence() {
        super();
    }


    public void unsetDescription(MouseEvent mouseEvent) {
        description.setText("Choississez votre compétence");
    }

    public void setDescriptionTeleporteur(MouseEvent mouseEvent) {
        description.setText("Pacman peut poser un téléporteur");
    }

    public void setDescriptionRobot(MouseEvent mouseEvent) {
        description.setText("Pacman fait apparaitre un robot qui ramasse les pac-gomme");
    }

    public void setDescriptionFrezzz(MouseEvent mouseEvent) {
        description.setText("Pacman fait gelé les fantomes");
    }

    public void play(ActionEvent actionEvent) throws IOException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.competenceDepart = ((Node) actionEvent.getSource()).getId();
        utilisateur.ecritureUtilisateur();
        utilisateur = null;

        Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/jouer.fxml")));
        if (primaryStage  == null) primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(scoreView));
        primaryStage.sizeToScene();
        primaryStage.show();
        scoreView.requestFocus();
    }
}
