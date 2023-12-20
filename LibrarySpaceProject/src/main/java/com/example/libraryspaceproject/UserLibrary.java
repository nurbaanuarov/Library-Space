package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UserLibrary {

    @FXML
    private Button bookings;

    @FXML
    private Button chairs;

    @FXML
    private ChoiceBox<Integer> floorChoiceBox;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button rooms;
    LibraryAccessFacade facade;
    @FXML
    void bookButton() {
        bookings.setOnMouseClicked(event -> {
            try {
                UserInitializer userInitializer = new UserInitializer();
                userInitializer.logMessage(7,new String[]{});
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("my-booking.fxml"));
                fxmlLoader.load();
                Parent par = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setResizable(false);
                stage.setScene(new Scene(par));
                stage.show();
                pane.getScene().getWindow().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void chairButton() {
        chairs.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("user-chairs.fxml"));
                fxmlLoader.load();
                Parent par = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setResizable(false);
                stage.setScene(new Scene(par));
                stage.show();

                HelloController.user.setFloorOfChairs(floorChoiceBox.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void paneAction() {
        pane.setOnMouseEntered(event -> {
            if (facade == null) {
                facade = new LibraryAccessFacade();
                int k = facade.getNumberOfFloors();
                for (int c = 0; c < k; c++) {
                    floorChoiceBox.getItems().add(c);
                }
            }
        });
    }

    @FXML
    void roomButton() {
        rooms.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("roomBooking.fxml"));
                fxmlLoader.load();
                Parent par = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setResizable(false);
                stage.setScene(new Scene(par));
                stage.show();
                pane.getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    void floorAction() {
    }
    @FXML
    void initialize() {
        floorAction();
        roomButton();
        chairButton();
        paneAction();
    }
}
