package pacman.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class ControllerOption extends Controller{
//    valeur du son et des fps
    public double valueSon;
    public double valueFPS;
//    slider
    public Slider sliderSon;
    public Slider sliderFPS;

    public ControllerOption() {
        super();
    }

    /**
     * Initialise les options son et fps
     */
    @FXML
    public void initialize(){
        this.utilisateur.lectureUtilisateur();
        this.sliderSon.setValue(this.utilisateur.getSon());
        this.sliderFPS.setValue(this.utilisateur.getFps());
    }

    /**
     * Change la variable du son ou des fps selon la valeur du slider dans le jeu.
     */
    @FXML
    public void valeurSliders() {
        this.valueSon = this.sliderSon.getValue();
        this.valueFPS = this.sliderFPS.getValue();
        this.utilisateur.setSon((int)this.valueSon);
        this.utilisateur.setFps((int)this.valueFPS);
        this.utilisateur.updateFps(this.utilisateur.getFps());
        this.utilisateur.ecritureUtilisateur();
        modelMusic.music("menu").setVolume((float)this.utilisateur.getSon()/100);
    }
}