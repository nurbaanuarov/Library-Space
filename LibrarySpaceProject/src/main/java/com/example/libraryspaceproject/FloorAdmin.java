package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class FloorAdmin {

    @FXML
    private Text floor;

    @FXML
    private AnchorPane pane;
    HashMap<Circle, Integer> circles = new HashMap<>();
    Chair[] chairs;
    int floorNumber;
    private Socket socket;
    private DataInputStream in;
    @FXML
    void paneAction() {
        pane.setOnMouseEntered(event -> {
            if (socket == null) {
                try {
                    System.out.println("Connection of floor admin");
                    socket = new Socket("localhost",8000);
                    in = new DataInputStream(socket.getInputStream());
                    floorNumber = in.readInt();
                    floor.setText(floor.getText()+floorNumber);
                    in.close();
                    socket.close();
                    createChairs();
                } catch (IOException e) {
                    System.out.println("not found");
                }
            }
        });
    }
    void createChairs() {
        Library lib = Library.getInstance();
        Floor[] floors = lib.getFloors();
        chairs = floors[floorNumber].getChairs();
        for (int c=0;c<floors[floorNumber].getNumberOfChairs();c++) {
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setStroke(Color.BLACK);
            circle.setCenterX(chairs[c].getCoordinate().getKey());
            circle.setCenterY(chairs[c].getCoordinate().getValue());
            pane.getChildren().add(circle);
            setMouseHandlers(circle);
            circle.setFill(Paint.valueOf("#09ff00"));
            circles.put(circle, c);
        }
    }
    private void setMouseHandlers(Circle circle) {
        circle.setOnMouseDragged(event -> {
            int index = circles.get(circle);
            chairs[index].setCoordinate((int) event.getX(), (int) event.getY());
            circle.setCenterX(chairs[index].getCoordinate().getKey());
            circle.setCenterY(chairs[index].getCoordinate().getValue());
        });
        circle.setOnMouseClicked(event -> {
                int index = circles.get(circle);
                chairs[index].change();
                if (chairs[index].isFree()) {
                    circle.setFill(Paint.valueOf("#09ff00"));
                } else {
                    circle.setFill(Paint.valueOf("#ff3535"));
                }
        });
    }
}
