package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import com.example.demo.model.Utilisateur;

public class ControllerChoixCompetence extends Controller{
    @FXML public Text description;
    
    public ControllerChoixCompetence() {
        super();
    }

    /**
     * Initialise la police de caractère à rosemary et la description puis crée un Utilisateur.
     */
    @FXML
    public void initialize(){
        this.description.setFont(this.rosemary);
        this.description.setText("Veuillez choisir votre compétence");
        this.utilisateur = new Utilisateur();
    }


    /**
     * Met la description de base dans le shop
     */
    @FXML
    public void unsetDescription() {
        this.description.setText("Veuillez choisir votre compétence");
    }

    /**
     * Met la description d'explication de la compétence tirer
     */
    @FXML
    public void setDescriptionTirer() {
        this.description.setText(this.utilisateur.tabCompetence[0][0]);
    }

    /**
     * Met la description d'explication de la compétence geler
     */
    @FXML
    public void setDescriptionFreeze() {
        this.description.setText(this.utilisateur.tabCompetence[0][4]);
    }

    /**
     * Met la description d'explication de la compétence de téléportation
     */
    @FXML
    public void setDescriptionTeleporteur() {
        this.description.setText(this.utilisateur.tabCompetence[0][8]);
    }

    /**
     * Réinitialise les anciennes compétences et confirme la première compétence choisi par l'utilisateur puis charge la scène jouer.
     * @param actionEvent compétence choisie.
     * @throws IOException si le fichier fxml n'est pas trouvé renvoie une exception.
     */
    @FXML
    public void switchToPlay(ActionEvent actionEvent) throws IOException {
        this.utilisateur.reinitialiseCompetencesUtilisateur();
        switch (((Node) actionEvent.getSource()).getId()) {
            case "tirer":
                this.utilisateur.niveauCompetenceTirer = 0;
                break;
            case "freeze":
                this.utilisateur.niveauCompetenceFreeze = 0;
                break;
            case "teleporteur":
                this.utilisateur.niveauCompetenceTeleporteur = 0;
                break;
        }
        this.utilisateur.ecritureUtilisateur();
        this.utilisateur = null;
        // change de scène

        Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("jouer.fxml")));
        if (primaryStage  == null) primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(scoreView));
        primaryStage.sizeToScene();
        primaryStage.show();
        scoreView.requestFocus();
    }
}
