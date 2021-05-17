package pacman.controller;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pacman.model.FantomeGroup;
import pacman.model.Map;
import pacman.model.Pacman;
import pacman.model.ScoreModel;

import java.io.IOException;
import java.util.Objects;

public class ControllerJouer extends Controller  implements EventHandler<KeyEvent> {

    @FXML public Pacman pacman;
    @FXML public Map map;
    public ControllerDeplacement deplacement;
    @FXML public Label meilleurscore;
    @FXML public Label affichageScore;
    @FXML public FantomeGroup fantomeGroup;
    @FXML public ImageView vie1;
    @FXML public ImageView vie2;
    @FXML public ImageView vie3;
    @FXML public ImageView vie4;
    @FXML public ImageView vie5;

    public ControllerJouer() {

        modelMusic.music("chomp", true);
    }

    @FXML
    public void initialize() throws IOException {
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.lectureTxt();
        scoreModel.triTab();
        scoreModel.reecritureTxt();
        int MS = scoreModel.getMeilleurScore();
        meilleurscore.setText(String.valueOf(MS));
    }

    @FXML
    public void viePac(){
        ImageView tabVie[] = {vie1,vie2,vie3,vie4,vie5};
        ImageView vie;

        pacman.nbVie--;
        vie = tabVie[pacman.nbVie];
        vie.setImage(null);

    }



    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (pacman != null && map != null) {
            if (deplacement == null) {
//                System.out.println("deplacement null");
                deplacement = new ControllerDeplacement(map, pacman, fantomeGroup, this);
                deplacement.start();
            }
            if (code == KeyCode.UP) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.HAUT;
            } else if (code == KeyCode.RIGHT) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.DROITE;
            } else if (code == KeyCode.DOWN) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.BAS;
            } else if (code == KeyCode.LEFT) {
                deplacement.deplacementFutur = ControllerDeplacement.Deplacements.GAUCHE;
            }
        }
    }
}
