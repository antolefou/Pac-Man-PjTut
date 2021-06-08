package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jfr.Description;
import pacman.Main;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;

public class ControllerCompetence extends Controller{
    @FXML
    public Text description;
//    @FXML description = new Text();
    
    public ControllerCompetence() {
        super();
    }

    public void initialize(){
        description.setFont(this.rosemary);
    }



    public void unsetDescription(MouseEvent mouseEvent) {
        description.setText("Choississez votre compétence");
    }

    public void setDescriptionTeleporteur(MouseEvent mouseEvent) {
        description.setText("Pacman peut poser un téléporteur");
    }

    public void setDescriptionTirer(MouseEvent mouseEvent) {
        description.setText("Pacman tirer");
    }

    public void setDescriptionFrezzz(MouseEvent mouseEvent) {
        description.setText("Pacman fait geler les fantomes");
    }

    public void play(ActionEvent actionEvent) throws IOException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.reinitialiseCompetencesUtilisateur();
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                utilisateur.niveauCompetenceTirer = 0;
                break;
            case "frezze":
                utilisateur.niveauCompetenceFrezze = 0;
                break;
            case "teleporteur":
                utilisateur.niveauCompetenceTeleporteur = 0;
                break;
        }
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
