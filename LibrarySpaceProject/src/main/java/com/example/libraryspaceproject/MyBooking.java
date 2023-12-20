package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MyBooking {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button back;

    @FXML
    private Button cancel;

    @FXML
    private Text date;

    @FXML
    private Text floor;

    @FXML
    private Text hours;

    @FXML
    private Text room;
    User user;
    Text message;
    void updatePage() {
        if (user.hasBook()) {
            date.setText("Date: "+user.getBookDate());
            floor.setText("Floor: "+user.getBookFloor());
            room.setText("Room: "+user.getBookRoom());
            StringBuilder hoursText = new StringBuilder("Hours: ");
            for (int hour: user.getBookHours()) {
                hoursText.append(hour).append(":00 - ").append(hour + 1).append(":00, ");
            }
            hoursText.delete(hoursText.length()-2,hoursText.length());
            hours.setText(hoursText.toString());
            anchorpane.getChildren().remove(message);
        } else {
            date.setText("Date: ");
            floor.setText("Floor: ");
            room.setText("Room: ");
            hours.setText("Hours: ");
            message = new Text("You do not have booking");
            message.setFont(Font.font("verdana", FontWeight.BOLD, 30));
            message.setFill(Color.BROWN);
            message.setLayoutX(75);
            message.setLayoutY(290);
            anchorpane.getChildren().add(message);
        }
    }
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
    void cancelButton() {
        cancel.setOnMouseClicked(mouseEvent -> {
            LibraryAccessFacade facade = new LibraryAccessFacade();
            facade.cancelMyBooking();
            user.setHasBook(false);
            UserInitializer userInitializer = new UserInitializer();
            userInitializer.logMessage(8,new String[]{});
            updatePage();
        });
    }

    @FXML
    void paneAction() {
        anchorpane.setOnMouseEntered(mouseEvent -> {
            if (user == null) {
                user = HelloController.user;
                updatePage();
            }
        });
    }
    @FXML
    void initialize() {
        backButton();
        cancelButton();
        paneAction();
    }
}
