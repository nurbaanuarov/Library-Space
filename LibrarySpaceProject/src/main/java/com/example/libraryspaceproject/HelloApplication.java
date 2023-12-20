package com.example.libraryspaceproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
            Scanner scan = new Scanner(System.in);
            Scene scene;
            if (scan.nextInt() == 1) {
                FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("systemAdmin.fxml"));
                scene = new Scene(fxmlLoader2.load(), 600, 400);
            } else {
                FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                scene = new Scene(fxmlLoader1.load(), 600, 400);
            }
            stage.setTitle("Hello!");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
