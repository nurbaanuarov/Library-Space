package com.example.libraryspaceproject;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Loading {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView iv;
    @FXML
    private StackPane stackPane;
    private boolean bool = false;
    @FXML
    void loadingAction() {
        stackPane.setOnMouseEntered(mouseEvent -> {
            if (!bool) {
                bool = true;
                RotateTransition pt = new RotateTransition(Duration.seconds(2),iv);
                pt.setToAngle(360);
                pt.play();
                pt.setOnFinished(event -> {
                    iv.getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(Loading.class.getResource("user-library.fxml"));
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent par = fxmlLoader.getRoot();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setScene(new Scene(par));
                    stage.show();
                });
            }
        });
    }
    @FXML
    void initialize() {
        loadingAction();
    }

}
