package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private TextField login;

    @FXML
    private Button okButton;
    @FXML
    private VBox vBox;
    @FXML
    void okButtonAction() {
        okButton.setOnMouseClicked(mouseEvent -> {
            UserLoginChecker userLoginChecker = new UserLoginChecker();
            if (userLoginChecker.logMessage(4, new String[]{login.getText()})==0 && userLoginChecker.logMessage(3, new String[]{login.getText(),email.getText()})==0) {
                okButton.getScene().getWindow().hide();
                try {
                    RandomAccessFile raf = new RandomAccessFile("loginOfPas","rw");
                    raf.seek(0);
                    raf.writeBytes(login.getText());
                    raf.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(ForgotPassword.class.getResource("reset-password.fxml"));
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (userLoginChecker.logMessage(4, new String[]{login.getText()})==1) {
                Text text = new Text("Login doesn't exist");
                text.setFill(Color.RED);
                if (vBox.getChildren().size()==5) vBox.getChildren().add(text);
            } else {
                Text text = new Text("Email doesn't exist");
                text.setFill(Color.RED);
                if (vBox.getChildren().size()==5) vBox.getChildren().add(text);
            }
        });
    }
    @FXML
    void initialize() {
        okButtonAction();
    }
}
