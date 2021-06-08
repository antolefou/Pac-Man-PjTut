package pacman.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class ControllerOption extends Controller{
    //valeur
    public double valueSon;
    public double valueFPS;
    //slider
    public Slider sliderSon;
    public Slider sliderFPS;
    //bouton
    public Button valider;


    public ControllerOption() {
        super();

    }
    public void initialize(){
        utilisateur.lectureUtilisateur();
        sliderSon.setValue(utilisateur.getSon());
        sliderFPS.setValue(utilisateur.getFps());
    }

    public void valeurSliderSon() {
        valueSon = sliderSon.getValue();
        valueFPS = sliderFPS.getValue();
        utilisateur.setSon((int)valueSon);
        utilisateur.setFps((int)valueFPS);
        utilisateur.updateFps(utilisateur.getFps());
        utilisateur.ecritureUtilisateur();
        modelMusic.music("menu").setVolume((float)utilisateur.getSon()/100);
    }
}


