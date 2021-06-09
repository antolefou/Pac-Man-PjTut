package pacman.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import pacman.model.Utilisateur;

public class ControllerAmeliorationCompetence extends Controller {
    public Utilisateur utilisateur;

    public ControllerAmeliorationCompetence() {
        utilisateur = new Utilisateur();
    }

    public void ameliorationPouvoir(ActionEvent actionEvent) {
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                break;
            case "freeze":
                break;
            case "teleporteur":
                break;
        }
    }

    public void unsetDescription(MouseEvent mouseEvent) {
    }

    public void setDescriptionTirer(MouseEvent mouseEvent) {
    }

    public void setDescriptionFreeze(MouseEvent mouseEvent) {
    }

    public void setDescriptionTeleporteur(MouseEvent mouseEvent) {
    }
}
