package pacman.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pacman.model.ScoreModel;
import pacman.model.deplacement.Pacman;

import java.io.IOException;
import java.util.Objects;

public class ControllerGameOver extends Controller{

    public TextField txtpseudo;
    public ScoreModel scoreModel = new ScoreModel();
    public Button valider;

    @FXML
    public void handle(KeyEvent event) throws IOException {

        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("2");
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/jouer.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreView.requestFocus();
            scoreModel.lectureTxt();
            utilisateur.setPseudoUtilisateur(txtpseudo.getText());
            for(int i =0; i<scoreModel.tab.length; i++){
                if (scoreModel.tab[i][0].equals("pseudoUt") ){
                    scoreModel.tab[i][0] = utilisateur.getPseudoUtilisateur();
                    scoreModel.reecritureTxt();
                }
            }

        } else if (event.getCode() == KeyCode.ALT) {
            Parent scoreView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/menu.fxml")));
            primaryStage.setScene(new Scene(scoreView));
            primaryStage.sizeToScene();
            primaryStage.show();
            scoreModel.lectureTxt();
            utilisateur.setPseudoUtilisateur(txtpseudo.getText());
            for(int i =0; i<scoreModel.tab.length; i++){
                if (scoreModel.tab[i][0].equals("pseudoUt") ){
                    scoreModel.tab[i][0] = utilisateur.getPseudoUtilisateur();
                    scoreModel.reecritureTxt();
                }
            }

            scoreView.requestFocus();

        } else {
            //            System.out.println("la clef n'est pas reconu");
        }
    }
}
