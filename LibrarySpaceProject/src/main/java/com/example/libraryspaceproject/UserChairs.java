package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;

public class UserChairs implements Serializable {

    @FXML
    private Text chairs;

    @FXML
    private Text floor;

    @FXML
    private AnchorPane pane;
    private int numberOfFloor = -1;
    void getDataOfChairs() {
        LibraryAccessFacade facade = new LibraryAccessFacade();
        createCircles(facade.getChairs(numberOfFloor));
    }
    void createCircles(Chair[] chair) {
        for (Chair value : chair) {
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setStroke(Color.BLACK);
            circle.setCenterX(value.getCoordinate().getKey());
            circle.setCenterY(value.getCoordinate().getValue());
            pane.getChildren().add(circle);
            if (value.isFree()) circle.setFill(Paint.valueOf("#09ff00"));
            else circle.setFill(Paint.valueOf("#ff3535"));
        }
    }
    @FXML
    void paneAction() {
        pane.setOnMouseEntered(event -> {
            if (numberOfFloor == -1) {
                numberOfFloor = HelloController.user.getFloorOfChairs();
                floor.setText(floor.getText() + numberOfFloor);
                getDataOfChairs();
            }
        });
    }
    @FXML
    void initialize() {
        paneAction();
    }
}
