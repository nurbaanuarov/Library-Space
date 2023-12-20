package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField reppasword;
    @FXML
    private VBox vBox, vBox2, vBox3;
    @FXML
    void okButtonAction() {
        okButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (password.getText().equals(reppasword.getText()) && password.getText().length()==8) {
                    RandomAccessFile raf = new RandomAccessFile("loginOfPas","rw");
                    raf.seek(0);
                    byte[] b = new byte[(int) (raf.length())];
                    raf.read(b);
                    String name = new String(b);
                    raf.close();

                    RandomAccessFile randomAccessFile = new RandomAccessFile("logins","rw");
                    randomAccessFile.seek(0);
                    byte[] bytes = new byte[(int) (randomAccessFile.length())];
                    randomAccessFile.read(bytes);
                    String s = new String(bytes);
                    randomAccessFile.close();

                    deleteFile("logins");
                    String pref = s.substring(0,s.indexOf("name: "+name+"\n"))+"name: "+name+"\npassword: "+password.getText()+"\n";
                    s = s.substring(s.indexOf("name: "+name+"\n"));
                    s = s.substring(s.indexOf("\n")+1);
                    s = s.substring(s.indexOf("\n")+1);
                    s = pref+s;
                    RandomAccessFile randomAccessFile1 = new RandomAccessFile("logins","rw");
                    randomAccessFile1.seek(0);
                    randomAccessFile1.writeBytes(s);
                    randomAccessFile1.close();

                    deleteFile("loginOfPas");

                    okButton.getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(ResetPassword.class.getResource("hello-view.fxml"));
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
                else {
                    Text text = new Text("Password is not correct");
                    text.setFill(Color.RED);
                    vBox.getChildren().add(text);
                }
            } catch (FileNotFoundException f){
                System.out.println("File not found");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    void deleteFile(String s) throws IOException {
        File file = new File(s);
        file.delete();
        file.createNewFile();
    }
    private boolean bool2 = false, bool3 = false;
    @FXML
    void passwordTextAction(){
        password.setOnMouseEntered(mouseEvent -> {
            if (!bool2) {
                vBox2.getChildren().add(new Text("There should be 8 characters"));
                bool2 = true;
            }
        });
        password.setOnMouseExited(mouseEvent -> {
            if (bool2) {
                vBox2.getChildren().remove(2);
                bool2 = false;
            }
        });
    }
    @FXML
    void reppaswordTextAction() {
        reppasword.setOnMouseEntered(mouseEvent -> {
            if (!bool3) {
                vBox3.getChildren().add(new Text("Should be repeated from the box above"));
                bool3 = true;
            }
        });
        reppasword.setOnMouseExited(mouseEvent -> {
            if (bool3) {
                vBox3.getChildren().remove(2);
                bool3 = false;
            }
        });
    }
    @FXML
    void initialize() {
        okButtonAction();
        passwordTextAction();
        reppaswordTextAction();
    }

}
