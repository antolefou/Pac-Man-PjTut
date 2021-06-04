package pacman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import pacman.model.deplacement.*;

import java.io.IOException;
import java.util.Objects;

import pacman.model.*;

public class ControllerJouer extends Controller {
    @FXML public Pacman pacman;
    @FXML public FantomeGroup fantomeGroup;
    @FXML public Map map;
    @FXML public Label meilleurscore;
    @FXML public Label labelScore;
    @FXML public ImageView vie1;
    @FXML public ImageView vie2;
    @FXML public ImageView vie3;
    @FXML public ImageView vie4;
    @FXML public ImageView vie5;
    ImageView[] tabVie;

    private UpdateRender updateRender;
    public ScoreModel scoreModel;

    public ControllerJouer() {
        super();
        this.stopAllMusic();
        this.playMusic("theme", true);
        this.playMusic("chomp", true);
    }

    public void initialize() {
        this.updateRender = new UpdateRender(this, this.utilisateur, labelScore,  map, pacman, fantomeGroup);
        updateRender.jouer();

        // affichage meileur score
        this.scoreModel = new ScoreModel();
        this.scoreModel.lectureTxt();
        meilleurscore.setText(String.valueOf(scoreModel.getMeilleurScore()));
        //vie
        tabVie = new ImageView[]{vie1, vie2, vie3, vie4, vie5};
    }

    /**
     * Regarde le nombre de vie de pac-man,
     * affiche son nombre de vie sur l'écran et charge l'écran game over quand il n'en a plus
     * @throws IOException exeption lancer a la ligne 71 par load
     */
    public void viePac() throws IOException {
        if (pacman.nbVie < 5 && pacman.nbVie > -1) tabVie[pacman.nbVie].setImage(null);

        if (pacman.nbVie == -1) {
            // suppression
            updateRender.PACMAN.enVie = false;
            updateRender.update.stop();
            updateRender.render.stop();
            updateRender = null;
            // affichage game over
            scoreModel.TriScore(this.pacman);
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/gameOver.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
            }
        }

    /**
     * Gère les actions sur le clavier du joueur: déplacement pacman et recommencer la partie (r)
     * @param keyEvent évènement de la touche
     * @throws IOException renvoie une exception en cas d'erreur
     */
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP || code == KeyCode.Z) {
            pacman.deplacementFutur = Deplacement.deplacements.HAUT;
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            pacman.deplacementFutur = Deplacement.deplacements.DROITE;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            pacman.deplacementFutur = Deplacement.deplacements.BAS;
        } else if (code == KeyCode.LEFT || code == KeyCode.Q) {
            pacman.deplacementFutur = Deplacement.deplacements.GAUCHE;
        }else if (code == KeyCode.R){
            updateRender.PACMAN.enVie = false;
            updateRender.update.stop();
            updateRender.render.stop();
            updateRender = null;

            Parent scoreView = null;
            try {
                scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/jouer.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
        }

        else if (code == KeyCode.A) {
            if (pacman.competenceAPrete) pacman.competenceTeleportation();
        }

        else if (code == KeyCode.E) {
            if (pacman.competenceEPrete) pacman.competenceFreeze();
        }

        else if (code == KeyCode.SPACE) {
            if (pacman.competenceTPrete && !pacman.projectileLance) {
                pacman.competenceProjectile();
            }

        }
    }


    @Override
    public void switchToScene(ActionEvent event) throws IOException {
        super.switchToScene(event);
//         suprime les trucs pas utils par la suite
        updateRender.update.stop();
        updateRender.render.stop();
        updateRender = null;
    }
}