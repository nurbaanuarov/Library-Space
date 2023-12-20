package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class signUpController {
    @FXML
    private VBox vBox1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private Button logIn;
    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField reppassword;

    @FXML
    private Button signUp;
    @FXML
    private CheckBox maleCheckBox, femaleCheckBox;
    @FXML
    private VBox vBox2, vBox3, vBox4, vBoxP;
    private boolean bool = false, bool1 = false, bool2 = false, bool3 = false;
    @FXML
    public void logInAction(){
        logIn.setOnAction(mouseEvent -> {
            logIn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(signUpController.class.getResource("log-in.fxml"));
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
    void loginTextAction() {
        loginTextField.setOnMouseEntered(mouseEvent -> {
            if (!bool) {
                vBox1.getChildren().add(new Text("Example: nurba2005"));
                bool = true;
            }
        });
        loginTextField.setOnMouseExited(mouseEvent -> {
            if (bool) {
                vBox1.getChildren().remove(2);
                bool = false;
            }
        });
    }
    @FXML
    void emailTextAction() {
        email.setOnMouseEntered(mouseEvent -> {
            if (!bool1) {
                vBox2.getChildren().add(new Text("Example: nrk@gmail.com"));
                bool1 = true;
            }
        });
        email.setOnMouseExited(mouseEvent -> {
            if (bool1) {
                vBox2.getChildren().remove(2);
                bool1 = false;
            }
        });
    }
    @FXML
    void passwordTextAction(){
        password.setOnMouseEntered(mouseEvent -> {
            if (!bool2) {
                vBox3.getChildren().add(new Text("There should be 8 characters"));
                bool2 = true;
            }
        });
        password.setOnMouseExited(mouseEvent -> {
            if (bool2) {
                vBox3.getChildren().remove(2);
                bool2 = false;
            }
        });
    }
    @FXML
    void reppasswordTextAction() {
        reppassword.setOnMouseEntered(mouseEvent -> {
            if (!bool3) {
                vBox4.getChildren().add(new Text("Should be repeated from the box above"));
                bool3 = true;
            }
        });
        reppassword.setOnMouseExited(mouseEvent -> {
            if (bool3) {
                vBox4.getChildren().remove(2);
                bool3 = false;
            }
        });
    }
    @FXML
    void maleAction(){
        maleCheckBox.setOnMouseClicked(mouseEvent -> {
            femaleCheckBox.setSelected(false);
        });
    }
    @FXML
    void femaleAction(){
        femaleCheckBox.setOnMouseClicked(mouseEvent -> {
            maleCheckBox.setSelected(false);
        });
    }
    @FXML
    void signUpAction(){
        signUp.setOnMouseClicked(mouseEvent -> {
            HelloController.user = new User(loginTextField.getText());
            
            UserLoginChecker userLoginChecker = new UserLoginChecker();
            UserInitializer userInitializer = new UserInitializer();
            userLoginChecker.setNextLogger(userInitializer);
            int c = userLoginChecker.logMessage(1, new String[]{loginTextField.getText(),email.getText(),password.getText(),reppassword.getText()});

            switch (c){
                case (0) -> {
                    signUp.getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(signUpController.class.getResource("loading.fxml"));
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
                }
                case (1) -> {
                    if (vBoxP.getChildren().size()==7) vBoxP.getChildren().remove(6);
                    Text text = new Text("User - " + loginTextField.getText() + " exists");
                    text.setFill(Color.RED);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Username error");
                    alert.showAndWait();
                }
                case (2) -> {
                    if (vBoxP.getChildren().size()==7) vBoxP.getChildren().remove(6);
                    Text text = new Text("Password doesn't match");
                    text.setFill(Color.RED);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Password error");
                    alert.showAndWait();
                }
                case (3) -> {
                    if (vBoxP.getChildren().size()==7) vBoxP.getChildren().remove(6);
                    Text text = new Text("One of the boxes is empty");
                    text.setFill(Color.RED);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Empty box error");
                    alert.showAndWait();
                }
                case (4) -> {
                    if (vBoxP.getChildren().size()==7) vBoxP.getChildren().remove(6);
                    Text text = new Text("Name or email contains space");
                    text.setFill(Color.RED);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Information error");
                    alert.showAndWait();
                }
            }
        });
    }
    @FXML
    void initialize() {
        emailTextAction();
        loginTextAction();
        logInAction();
        passwordTextAction();
        reppasswordTextAction();
        maleAction();
        femaleAction();
    }


}
