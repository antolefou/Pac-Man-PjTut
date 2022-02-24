package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.util.Objects;

import com.example.demo.model.deplacement.*;
import com.example.demo.model.*;

public class ControllerJouer extends Controller {
//    map, fantomes et pacman
    @FXML public Pacman pacman;
    @FXML public FantomeGroup fantomeGroup;
    @FXML public Map map;
//    scores
    @FXML public Label meilleurscore;
    @FXML public Label labelScore;
//    ImageView des vies stockés dans un tableau
    public ImageView[] tabVie;
    @FXML public ImageView vie1;
    @FXML public ImageView vie2;
    @FXML public ImageView vie3;
    @FXML public ImageView vie4;
    @FXML public ImageView vie5;
//     Competences: Tirer-Freeze-Teleporteur
//    group - image competence - prix - cooldown
    @FXML public Group groupCompetenceTirer;
    @FXML public Group groupCompetenceFreeze;
    @FXML public Group groupCompetenceTeleporteur;
    @FXML public ImageView imageCompetenceTirer;
    @FXML public Label prixCompetenceTirer;
    @FXML public Label cooldownCompetenceTirer;
    @FXML public ImageView imageCompetenceFreeze;
    @FXML public Label prixCompetenceFreeze;
    @FXML public Label cooldownCompetenceFreeze;
    @FXML public ImageView imageCompetenceTeleporteur;
    @FXML public Label prixCompetenceTeleporteur;
    @FXML public Label cooldownCompetenceTeleporteur;
//    Fin des compétences
    private UpdateRender updateRender;
    public ScoreModel scoreModel;

    public ControllerJouer() {
        super();
        this.stopAllMusic();
        this.playMusic("theme", true);
        this.playMusic("chomp", true);
    }

    @FXML
    public void initialize() {
        this.utilisateur = new Utilisateur();
        this.updateRender = new UpdateRender(this, this.utilisateur, this.labelScore,  this.map, this.pacman, this.fantomeGroup);
        this.updateRender.jouer();
        this.pacman.score = this.utilisateur.pointJoueur;
        // affichage meileur score
        this.scoreModel = new ScoreModel();
        this.scoreModel.lectureTxt();
        this.meilleurscore.setText(String.valueOf(this.scoreModel.getMeilleurScore()));
        //vie
        this.tabVie = new ImageView[]{this.vie1, this.vie2, this.vie3, this.vie4, this.vie5};
        //affichage competence
        this.utilisateur.niveauUtilisateur ++;
        if (this.utilisateur.niveauCompetenceTirer < 0) this.groupCompetenceTirer.setVisible(false);
        if (this.utilisateur.niveauCompetenceFreeze < 0) this.groupCompetenceFreeze.setVisible(false);
        if (this.utilisateur.niveauCompetenceTeleporteur < 0) this.groupCompetenceTeleporteur.setVisible(false);
        this.cooldownCompetenceTirer.setText("");
        this.cooldownCompetenceFreeze.setText("");
        this.cooldownCompetenceTeleporteur.setText("");

        this.setAffichagePrixComp();
        this.setPrixCompDansPac();
    }

    /**
     * Regarde le nombre de vie de pac-man,
     * affiche son nombre de vie sur l'écran et charge l'écran game over quand il n'en a plus
     * @throws IOException exeption lancer a la ligne 71 par load
     */
    public void viePac() throws IOException {
        if (this.pacman.nbVie < 5 && this.pacman.nbVie > -1) this.tabVie[this.pacman.nbVie].setImage(null);
        if (this.pacman.nbVie == -1) {
            // reinitialise les points
            this.utilisateur.reinitialiseCompetencesUtilisateur();
            this.utilisateur.ecritureUtilisateur();
            // suppression
            this.updateRender.update.stop();
            this.updateRender.render.stop();
            this.updateRender = null;
            // affichage game over
            this.scoreModel.TriScore(this.pacman);
//            changement de scène vers game over
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameOver.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
            }
        }

    /**
     * Gère les actions sur le clavier du joueur: déplacement pacman et recommencer la partie (r)
     * @param keyEvent évènement de la touche
     */
    @FXML
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP || code == KeyCode.Z) {
            this.pacman.deplacementFutur = Deplacement.deplacements.HAUT;
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            this.pacman.deplacementFutur = Deplacement.deplacements.DROITE;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            this.pacman.deplacementFutur = Deplacement.deplacements.BAS;
        } else if (code == KeyCode.LEFT || code == KeyCode.Q) {
            this.pacman.deplacementFutur = Deplacement.deplacements.GAUCHE;
        }else if (code == KeyCode.L) {                     // touche competence teleporteur
            if (this.pacman.competenceTeleporteurDeverouillee) this.pacman.competenceTeleportation();
        } else if (code == KeyCode.K) {                   // touche competence freeze
            if (this.pacman.score >= this.pacman.pertePointsFreeze && this.pacman.competenceFreezePrete && this.pacman.competenceFreezeDeverouillee) this.pacman.competenceFreeze();
        } else if (code == KeyCode.SPACE) {               // touche competence tirer
            if (this.pacman.score >= this.pacman.pertePointsTirer && this.pacman.competenceTirerPrete && !this.pacman.projectileLance && this.pacman.competenceTirerDeverouillee) this.pacman.competenceProjectile();
        }
        /*/***********************************************************
         *     MODE  DEV -> pour tester plus facilement              *
         *     mettre sur la methode handle -> throws IOException    *
         * ***********************************************************
        }else if (code == KeyCode.R){               // regénère la map
            this.updateRender.PACMAN.enVie = false;
            this.updateRender.update.stop();
            this.updateRender.render.stop();
            this.updateRender = null;
            Parent scoreView = null;
            try {
                this.scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/jouer.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(new Scene(Objects.requireNonNull(scoreView)));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
        } else if (code == KeyCode.M) {
            this.utilisateur.pointJoueur = pacman.score;
            this.utilisateur.ecritureUtilisateur();
            this.switchToSceneAmelioration();
            if (pacman.score >= pacman.pertePointsTirer && pacman.competenceTirerPrete && !pacman.projectileLance && pacman.competenceTeleporteurDeverouillee) {
                this.pacman.competenceProjectile();
            }
        } else if (code == KeyCode.I) {
            this.pacman.score += 10000;
        }*/
    }

    /**
     * Affiche le prix de la compétence dans le jeu (graphiquement)
     */
    public void setAffichagePrixComp(){
        int niveauTirer = this.utilisateur.niveauCompetenceTirer;
        int niveauFreeze = this.utilisateur.niveauCompetenceFreeze;
        int niveauTeleporteur = this.utilisateur.niveauCompetenceTeleporteur;
        if (niveauTirer > -1){
            this.prixCompetenceTirer.setText(this.utilisateur.tabCompetence[niveauTirer][2]);
        }
        if (niveauFreeze > -1){
            this.prixCompetenceFreeze.setText(this.utilisateur.tabCompetence[niveauFreeze][6]);

        }
        if (niveauTeleporteur > -1){
            this.prixCompetenceTeleporteur.setText(this.utilisateur.tabCompetence[niveauTeleporteur][10]);
        }

    }

    /**
     * Change le prix des compétences dans les variables d'instances de pacman.
     */
    public void setPrixCompDansPac(){
        int niveauTirer = this.utilisateur.niveauCompetenceTirer;
        int niveauFreeze = this.utilisateur.niveauCompetenceFreeze;
        int niveauTeleporteur = this.utilisateur.niveauCompetenceTeleporteur;
        if (niveauTirer > -1) this.pacman.pertePointsTirer = Integer.parseInt(this.utilisateur.tabCompetence[niveauTirer][2]);
        if (niveauFreeze > -1) this.pacman.pertePointsFreeze = Integer.parseInt(this.utilisateur.tabCompetence[niveauFreeze][6]);
        if (niveauTeleporteur > -1) this.pacman.pertePointsTeleporte = Integer.parseInt(this.utilisateur.tabCompetence[niveauTeleporteur][10]);
    }

    /**
     * Change la scène suivant le bouton qui est pressé
     * @param event évènement du bouton
     * @throws IOException retourne une exception si l'on arrive pas à trouver la scène
     */
    @Override
    public void switchToScene(ActionEvent event) throws IOException {
        super.switchToScene(event);
//         suprime les trucs pas utils par la suite
        this.updateRender.update.stop();
        this.updateRender.render.stop();
        this.updateRender = null;
    }

    /**
     * Si l'utilisateur a déjà amélioré toutes ces compétences n'affiche pas le shop et change de niveau sinon affiche le shop d'amélioration.
     * @throws IOException retourne une exception si l'on arrive pas à trouver la scène
     */
    @FXML
    public void switchToSceneAmelioration() throws IOException {
        this.updateRender.update.stop();
        this.updateRender.render.stop();
        this.updateRender = null;
        if (!(this.utilisateur.niveauCompetenceTirer ==3 && this.utilisateur.niveauCompetenceFreeze ==3 && this.utilisateur.niveauCompetenceTeleporteur ==3)) {
            Parent ameliorationView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ameliorationCompetence.fxml")));
            primaryStage.setScene(new Scene(ameliorationView));
            primaryStage.sizeToScene();
            primaryStage.show();
            ameliorationView.requestFocus();
        } else {
            Parent jouerView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("jouer.fxml")));
            primaryStage.setScene(new Scene(jouerView));
            primaryStage.sizeToScene();
            primaryStage.show();
            jouerView.requestFocus();
        }
    }
}