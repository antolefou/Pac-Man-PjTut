package com.example.demo.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.Objects;


public class View extends StackPane {

    public View() throws IOException {
        this.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml"))));
    }
}