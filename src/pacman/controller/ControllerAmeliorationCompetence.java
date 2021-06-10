package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import pacman.model.Utilisateur;

import java.io.IOException;
import java.util.Objects;

public class ControllerAmeliorationCompetence extends Controller {
    public Utilisateur utilisateur;
    public Text description;
    public ImageView pointTirer;
    public ImageView pointFreeze;
    public ImageView pointTeleporteur;

    public ControllerAmeliorationCompetence() {
        utilisateur = new Utilisateur();
    }

    @FXML
    public void initialize() {
        description.setFont(this.rosemary);
        description.setText("Veuillez améliorer vos compétences, ou cliquer sur valider");
        description.setFont(rosemary);
        if (utilisateur.niveauCompetenceTirer>-1) pointTirer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_Competence/groupePoint" + utilisateur.niveauCompetenceTirer + "Vide.png"))));
        else pointTirer.setImage(null);
        if (utilisateur.niveauCompetenceFreeze>-1)pointFreeze.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_Competence/groupePoint" + utilisateur.niveauCompetenceFreeze + "Vide.png"))));
        else pointFreeze.setImage(null);
        if (utilisateur.niveauCompetenceTeleporteur>-1)pointTeleporteur.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_Competence/groupePoint" + utilisateur.niveauCompetenceTeleporteur + "Vide.png"))));
        else pointTeleporteur.setImage(null);
    }

    public void ameliorationPouvoir(ActionEvent actionEvent) {
        String image;
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                if (utilisateur.niveauCompetenceTirer<3) utilisateur.niveauCompetenceTirer ++;
                description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceTirer+1][0]);
                image = "/pacman/ressources/image/Ecran_Competence/groupePoint" + (utilisateur.niveauCompetenceTirer) + "Vide.png";
                if (utilisateur.niveauCompetenceTirer>-1) pointTirer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image))));
                break;
            case "freeze":
                if (utilisateur.niveauCompetenceFreeze<3) utilisateur.niveauCompetenceFreeze ++;
                description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceFreeze+1][4]);
                image = "/pacman/ressources/image/Ecran_Competence/groupePoint" + (utilisateur.niveauCompetenceFreeze) + "Vide.png";
                if (utilisateur.niveauCompetenceFreeze>-1) pointFreeze.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image))));
                break;
            case "teleporteur":
                if (utilisateur.niveauCompetenceTeleporteur<3) utilisateur.niveauCompetenceTeleporteur ++;
                description.setText(utilisateur.tabCompetence[utilisateur.niveauCompetenceTeleporteur+1][8]);
                image = "/pacman/ressources/image/Ecran_Competence/groupePoint" + (utilisateur.niveauCompetenceTeleporteur) + "Vide.png";
                if (utilisateur.niveauCompetenceTeleporteur>-1) pointTeleporteur.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image))));
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
        utilisateur.ecritureUtilisateur();
        super.switchToScene(actionEvent);
    }
}
