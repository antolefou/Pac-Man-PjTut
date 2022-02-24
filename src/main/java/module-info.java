module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.jgrapht.core;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.model to javafx.fxml;
    opens com.example.demo.model.deplacement to javafx.fxml;

    exports com.example.demo;
    exports com.example.demo.controller;
}