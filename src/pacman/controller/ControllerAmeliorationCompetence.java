package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import pacman.model.Utilisateur;

import java.io.IOException;

public class ControllerAmeliorationCompetence extends Controller {
    public Utilisateur utilisateur;
    public Text description;

    public ControllerAmeliorationCompetence() {
        utilisateur = new Utilisateur();
    }

    @FXML
    public void initialize() {
        description.setText("Veuillez améliorer vos compétences, ou cliquer sur valider");
    }

    public void ameliorationPouvoir(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                if (utilisateur.niveauCompetenceTirer<3) utilisateur.niveauCompetenceTirer ++;
                description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceTirer+1][0]);
                break;
            case "freeze":
                if (utilisateur.niveauCompetenceFreeze<3) utilisateur.niveauCompetenceFreeze ++;
                description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceFreeze+1][4]);
                break;
            case "teleporteur":
                if (utilisateur.niveauCompetenceTeleporteur<3) utilisateur.niveauCompetenceTeleporteur ++;
                description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceTeleporteur+1][8]);
                break;
        }
    }

    public void unsetDescription(MouseEvent mouseEvent) {
        description.setText("Veuillez améliorer vos compétences, ou cliquer sur valider");
    }

    public void setDescriptionTirer(MouseEvent mouseEvent) {
        description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceTirer+1][0]);
    }

    public void setDescriptionFreeze(MouseEvent mouseEvent) {
        description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceFreeze+1][4]);
    }

    public void setDescriptionTeleporteur(MouseEvent mouseEvent) {
        description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceTeleporteur+1][8]);
    }

    public void switchToScene(ActionEvent actionEvent) throws IOException {
        super.switchToScene(actionEvent);
        utilisateur.ecritureUtilisateur();
    }
}
