package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomBooking {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button back;

    @FXML
    private Button confirm;

    @FXML
    private ChoiceBox<Integer> dateChoiceBox;

    @FXML
    private Text datetxt;

    @FXML
    private ChoiceBox<Integer> floorChoiceBox;

    @FXML
    private Text floortxt;
    private LibraryAccessFacade facade;

    @FXML
    void backButton() {
        back.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("user-library.fxml"));
                fxmlLoader.load();
                Parent par = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setResizable(false);
                stage.setScene(new Scene(par));
                stage.show();
                anchorpane.getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void confirmButton() {
        confirm.setOnMouseClicked(event -> {
            try {
                HelloController.user.setBookDate(dateChoiceBox.getValue());
                HelloController.user.setBookFloor(floorChoiceBox.getValue());
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("roomBooking1.fxml"));
                fxmlLoader.load();
                Parent par = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setResizable(false);
                stage.setScene(new Scene(par));
                stage.show();
                anchorpane.getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void paneAction() {
        anchorpane.setOnMouseEntered(ev -> {
            if (facade == null) {
                facade = new LibraryAccessFacade();
                int k = facade.getNumberOfFloors();
                for (int c = 0; c < k; c++) {
                    floorChoiceBox.getItems().add(c);
                }
                int[] dates = facade.getDates();
                for (int c: dates) {
                    dateChoiceBox.getItems().add(c);
                }
            }
        });
    }
    @FXML
    void initialize() {
        backButton();
        confirmButton();
        paneAction();
    }
}
