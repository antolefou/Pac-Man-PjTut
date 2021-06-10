package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.Objects;

import pacman.model.Utilisateur;

public class ControllerAmeliorationCompetence extends Controller {
    @FXML public Text description;
    @FXML public ImageView pointTirer;
    @FXML public ImageView pointFreeze;
    @FXML public ImageView pointTeleporteur;

    public ControllerAmeliorationCompetence() {
        this.utilisateur = new Utilisateur();
    }

    @FXML
    public void initialize() {
        this.description.setFont(this.rosemary);
        this.description.setText("Vous avez " + this.utilisateur.pointJoueur + " points\n\nVeuillez améliorer vos compétences, ou cliquer sur valider");
        this.description.setFont(rosemary);
        // Images des points sous les compétences
        if (this.utilisateur.niveauCompetenceTirer>-1) this.pointTirer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_Competence/groupePoint" + this.utilisateur.niveauCompetenceTirer + "Vide.png"))));
        else pointTirer.setImage(null);
        if (this.utilisateur.niveauCompetenceFreeze>-1) this.pointFreeze.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_Competence/groupePoint" + this.utilisateur.niveauCompetenceFreeze + "Vide.png"))));
        else pointFreeze.setImage(null);
        if (this.utilisateur.niveauCompetenceTeleporteur>-1) this.pointTeleporteur.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/ressources/image/Ecran_Competence/groupePoint" + this.utilisateur.niveauCompetenceTeleporteur + "Vide.png"))));
        else pointTeleporteur.setImage(null);
    }

    /**
     * Lorsque l'on clique sur le bouton de compétence dans le shop, améliore la compétence au prochain niveau si il a les points nécessaires.
     * @param actionEvent Récupère les informations sur le bouton qui est cliqué.
     */
    public void ameliorationPouvoir(ActionEvent actionEvent) {
        String image;
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                if ((this.utilisateur.pointJoueur - Integer.parseInt(this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTirer+1][1]))>=0){
                    if (this.utilisateur.niveauCompetenceTirer<3) this.utilisateur.niveauCompetenceTirer ++;
                    setDescriptionTirerGlobal();
                    image = "/pacman/ressources/image/Ecran_Competence/groupePoint" + (this.utilisateur.niveauCompetenceTirer) + "Vide.png";
                    if (this.utilisateur.niveauCompetenceTirer>-1) pointTirer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image))));
                    this.utilisateur.pointJoueur-= Integer.parseInt(this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTirer+1][1]);
                }
                break;
            case "freeze":
                if ((this.utilisateur.pointJoueur - Integer.parseInt(this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceFreeze+1][5]))>=0){
                    if (this.utilisateur.niveauCompetenceFreeze<3) this.utilisateur.niveauCompetenceFreeze ++;
                    setDescriptionFreezeGlobal();
                    image = "/pacman/ressources/image/Ecran_Competence/groupePoint" + (this.utilisateur.niveauCompetenceFreeze) + "Vide.png";
                    if (this.utilisateur.niveauCompetenceFreeze>-1) pointFreeze.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image))));
                    this.utilisateur.pointJoueur-= Integer.parseInt(this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceFreeze+1][5]);
                }
                break;
            case "teleporteur":
                if ((this.utilisateur.pointJoueur - Integer.parseInt(this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTeleporteur+1][9]))>=0){
                    if (this.utilisateur.niveauCompetenceTeleporteur<3) this.utilisateur.niveauCompetenceTeleporteur ++;
                    setDescriptionTeleportGlobal();
                    image = "/pacman/ressources/image/Ecran_Competence/groupePoint" + (this.utilisateur.niveauCompetenceTeleporteur) + "Vide.png";
                    if (this.utilisateur.niveauCompetenceTeleporteur>-1) pointTeleporteur.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image))));
                    this.utilisateur.pointJoueur-= Integer.parseInt(this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTeleporteur+1][9]);
                }
                break;
        }
    }

    /**
     * Remet la description de base dans le shop.
     */
    @FXML
    public void unsetDescription() {
        this.description.setText("Vous avez " + this.utilisateur.pointJoueur + " points\n\nVeuillez améliorer vos compétences, ou cliquer sur valider");
    }

    /**
     * Met la description du sort Tirer dans le shop.
     */
    @FXML
    public void setDescriptionTirerGlobal(){
        String text = this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTirer+1][0];
        if (this.utilisateur.niveauCompetenceTirer < 3) text+= "\n prix : "+this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTirer+1][1];
        this.description.setText(text);
    }

    /**
     * Met la description du sort Geler dans le shop.
     */
    @FXML
    public void setDescriptionFreezeGlobal(){
        String text = this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceFreeze+1][4];
        if (this.utilisateur.niveauCompetenceFreeze < 3) text+= "\n prix : "+this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceFreeze+1][5];
        this.description.setText(text);
    }

    /**
     * Met la description du sort Teleporteur dans le shop.
     */
    @FXML
    public void setDescriptionTeleportGlobal(){
        String text = this.utilisateur.tabCompetence[this.utilisateur.niveauCompetenceTeleporteur+1][8];
        if (this.utilisateur.niveauCompetenceTeleporteur < 3) text+= "\n prix : "+this.utilisateur.tabCompetence[utilisateur.niveauCompetenceTeleporteur+1][9];
        this.description.setText(text);
    }

    @FXML
    public void switchToScene(ActionEvent actionEvent) throws IOException {
        // Il faut sauvegarder les données avant de changer de scène
        this.utilisateur.ecritureUtilisateur();
        super.switchToScene(actionEvent);
    }
}
