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
import pacman.Main;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;

public class ControllerChoixCompetence extends Controller{
    @FXML public Text description;
    public Utilisateur utilisateur;
    
    public ControllerChoixCompetence() {
        super();
    }

    public void initialize(){
        description.setFont(this.rosemary);
        description.setText("Veuillez choisir votre compétence");
        utilisateur = new Utilisateur();
    }



    public void unsetDescription(MouseEvent mouseEvent) {
        description.setText("Veuillez choisir votre compétence");
    }

    public void setDescriptionTirer(MouseEvent mouseEvent) {
        description.setText(utilisateur.tabCompetence[0][0]);
    }

    public void setDescriptionFreeze(MouseEvent mouseEvent) {
        description.setText(utilisateur.tabCompetence[0][4]);
    }

    public void setDescriptionTeleporteur(MouseEvent mouseEvent) {
        description.setText(utilisateur.tabCompetence[0][8]);
    }


    public void play(ActionEvent actionEvent) throws IOException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.reinitialiseCompetencesUtilisateur();
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                utilisateur.niveauCompetenceTirer = 0;
                break;
            case "freeze":
                utilisateur.niveauCompetenceFreeze = 0;
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
