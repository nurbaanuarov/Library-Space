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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logIn;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUp,forgotPassword;
    @FXML
    VBox vBox,vBox1,vBoxP;
    @FXML
    private CheckBox rememberMe;
    boolean bool = false,bool1 = false;
    @FXML
    void logInAction() {
        logIn.setOnMouseClicked(mouseEvent -> {
            UserLoginChecker userLoginChecker = new UserLoginChecker();
            UserInitializer userInitializer = new UserInitializer();
            userLoginChecker.setNextLogger(userInitializer);

            HelloController.user = new User(login.getText());
            switch (userLoginChecker.logMessage(2,new String[]{login.getText(),password.getText()})) {
                case (0) -> {
                    if (rememberMe.selectedProperty().get()) {
                        try{
                            File file = new File("remember");
                            file.delete();
                            file.createNewFile();
                            RandomAccessFile raf = new RandomAccessFile("remember","rw");
                            raf.seek(0);
                            raf.writeBytes(login.getText()+"\n"+password.getText());
                            raf.close();
                        } catch (IOException io){
                            System.out.println("IO Exception in logIn with remember me");
                        }
                    } else {
                        try{
                            RandomAccessFile raf = new RandomAccessFile("remember","rw");
                            raf.seek(0);
                            byte[] bytes = new byte[(int) raf.length()];
                            raf.read(bytes);
                            raf.close();
                            String s = login.getText()+"\n"+password.getText();
                            if (s.equals(new String(bytes))) {
                                File file = new File("remember");
                                file.delete();
                                file.createNewFile();
                            }
                        } catch (IOException io){
                            System.out.println("IO Exception in logIn");
                        }
                    }
                    login.getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(LogInController.class.getResource("loading.fxml"));
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
                }
                case (1) -> {
                    if (vBoxP.getChildren().size()==4) vBoxP.getChildren().remove(3);
                    Text text = new Text("User has not found");
                    text.setFill(Color.RED);
                    if (vBoxP.getChildren().size()==6) vBoxP.getChildren().remove(5);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Username error");
                    alert.showAndWait();
                }
                case (2) -> {
                    if (vBoxP.getChildren().size()==4) vBoxP.getChildren().remove(3);
                    Text text = new Text("Wrong password");
                    text.setFill(Color.RED);
                    if (vBoxP.getChildren().size()==6) vBoxP.getChildren().remove(5);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Password error");
                    alert.showAndWait();
                }
                case (3) -> {
                    if (vBoxP.getChildren().size()==4) vBoxP.getChildren().remove(3);
                    Text text = new Text("One of the boxes is empty");
                    text.setFill(Color.RED);
                    if (vBoxP.getChildren().size()==6) vBoxP.getChildren().remove(5);
                    vBoxP.getChildren().add(text);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(text.getText());
                    alert.setTitle("Empty box error");
                    alert.showAndWait();
                }
            }
        });
    }
    @FXML
    void loginTextAction() {
        String[] s;
        try {
            RandomAccessFile raf = new RandomAccessFile("remember","rw");
            raf.seek(0);
            byte[] bytes = new byte[(int) raf.length()];
            raf.read(bytes);
            String so = new String(bytes);
            s = so.split("\n");
            if (s.length!=0) login.setText(s[0]);
            raf.close();
        } catch (IOException io) {
            System.out.println("IO exception");
        }
        login.setOnMouseEntered(mouseEvent -> {
            if (!bool) {
                vBox.getChildren().add(new Text("Example: nurba2005"));
                bool = true;
            }
        });
        login.setOnMouseExited(mouseEvent -> {
            if (bool) {
                vBox.getChildren().remove(2);
                bool = false;
            }
        });
    }
    @FXML
    void signUpAction(){
        signUp.setOnMouseClicked(mouseEvent -> {
            signUp.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(LogInController.class.getResource("sign-up.fxml"));
            try {
                fxmlLoader.load();
            }catch (IOException e){
                e.printStackTrace();
            }

            Parent par = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(par));
            stage.show();

        });
    }
    @FXML
    void passwordTextAction() {
        String[] s;
        try {
            RandomAccessFile raf = new RandomAccessFile("remember","rw");
            raf.seek(0);
            byte[] bytes = new byte[(int) raf.length()];
            raf.read(bytes);
            String so = new String(bytes);
            if (so.length()!=0) {
                s = so.split("\n");
                password.setText(s[1]);
            }
            raf.close();
        } catch (IOException io) {
            System.out.println("IO exception");
        }
        password.setOnMouseEntered(mouseEvent -> {
            if (!bool1) {
                vBox1.getChildren().add(new Text("There should be 8 characters"));
                bool1 = true;
            }
        });
        password.setOnMouseExited(mouseEvent -> {
            if (bool1) {
                vBox1.getChildren().remove(2);
                bool1 = false;
            }
        });
    }
    @FXML
    void forgotPasswordAction(){
        forgotPassword.setOnMouseClicked(mouseEvent -> {
            forgotPassword.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(LogInController.class.getResource("forgot-password.fxml"));
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
        passwordTextAction();
        loginTextAction();
        forgotPasswordAction();
    }
}
