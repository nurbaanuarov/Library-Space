package com.example.libraryspaceproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloController {
    static User user;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logIn;

    @FXML
    private Button signUp;
    @FXML
    public void logInAction(){
        logIn.setOnMouseClicked(mouseEvent -> {
            logIn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(com.example.libraryspaceproject.HelloApplication.class.getResource("log-in.fxml"));
            try {
                fxmlLoader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            Parent par = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(par));
            stage.show();
        });
    }
    @FXML
    public void signUpAction(){
        signUp.setOnMouseClicked(mouseEvent -> {
            signUp.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(com.example.libraryspaceproject.HelloApplication.class.getResource("sign-up.fxml"));
            try {
                fxmlLoader.load();
            }catch (IOException e){
                e.printStackTrace();
            }

            Parent par = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(par));
            stage.show();

        });
    }
    @FXML
    void initialize() {
        signUpAction();
        logInAction();
    }

}
