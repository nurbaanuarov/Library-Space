package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomBooking1 {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button back;

    @FXML
    private Button confirm;
    @FXML
    private AnchorPane innerPane;
    private Rectangle[] roomRectangles;
    private Rectangle[][] hourRectangles;
    private Text[] roomTexts;
    private Text[][] hourTexts;
    private LibraryAccessFacade facade;
    private Room[] rooms;
    private List<Rectangle> chosen = new ArrayList<>();
    private final HashMap<Rectangle, Pair<Integer, Integer>> hm = new HashMap<>();
    @FXML
    private Text roomtxt;

    @FXML
    void backButton() {
        back.setOnMouseClicked(ev -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("roomBooking.fxml"));
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
        if (chosen.size() != 0) try {
            User user = HelloController.user;
            List<Integer> books = new ArrayList<>();
            for (Rectangle rectangle : chosen) {
                books.add(hm.get(rectangle).getValue() + 9);
            }
            user.setBookRoom(hm.get(chosen.get(0)).getKey());
            user.setBookHours(books);
            if (facade.makeBooking(user.getBookDate(), user.getBookFloor(), user.getBookRoom(), user.getBookHours())) {
                user.setHasBook(true);
                UserInitializer userInitializer = new UserInitializer();
                userInitializer.logMessage(8, new String[]{});
                FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("roomBooking.fxml"));
                fxmlLoader.load();
                Parent par = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setResizable(false);
                stage.setScene(new Scene(par));
                stage.show();
                anchorpane.getScene().getWindow().hide();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void paneAction() {
        anchorpane.setOnMouseEntered(mouseEvent -> {
            if (facade == null) {
                facade = new LibraryAccessFacade();
                rooms = facade.getRooms(HelloController.user.getBookDate(), HelloController.user.getBookFloor());
                int countOfRooms = rooms.length;
                if (countOfRooms == 0) return;
                roomRectangles = new Rectangle[countOfRooms];
                hourRectangles = new Rectangle[countOfRooms][10];
                roomTexts = new Text[countOfRooms];
                hourTexts = new Text[countOfRooms][10];

                int roomRec_y = 28, roomText_y = 60, hoursRec_x = 115, hoursRec_y = 34, hoursText_x = 122, hoursText_y = 60;
                roomRectangles[0] = new Rectangle();
                roomRectangles[0].setWidth(900);
                roomRectangles[0].setHeight(50);
                roomRectangles[0].setLayoutX(0);
                roomRectangles[0].setLayoutY(roomRec_y);
                roomRectangles[0].setFill(Paint.valueOf("#f59700"));

                roomTexts[0] = new Text();
                roomTexts[0].setText("Room: " + rooms[0].getIndex());
                roomTexts[0].setLayoutX(7);
                roomTexts[0].setLayoutY(roomText_y);
                roomTexts[0].setFont(Font.font("verdana", FontWeight.BOLD, 22));
                innerPane.getChildren().add(roomRectangles[0]);
                innerPane.getChildren().add(roomTexts[0]);

                List<Integer> schedule = rooms[0].getSchedule();
                boolean isFree = true;

                for (int z = 0; z < schedule.size(); z++) {
                    if (z != schedule.size() - 1) {
                        if (!isFree) {
                            for (int i = schedule.get(z); i < schedule.get(z + 1); i++) {
                                hourRectangles[0][i - 9] = new Rectangle();
                                hourRectangles[0][i - 9].setLayoutX(hoursRec_x + 85 * (i - 9));
                                hourRectangles[0][i - 9].setWidth(80);
                                hourRectangles[0][i - 9].setLayoutY(hoursRec_y);
                                hourRectangles[0][i - 9].setHeight(40);
                                hourRectangles[0][i - 9].setFill(Paint.valueOf("#ff4b00"));

                                hourTexts[0][i - 9] = new Text();
                                hourTexts[0][i - 9].setText(i + ":00");
                                hourTexts[0][i - 9].setFont(Font.font("verdana", 22));
                                hourTexts[0][i - 9].setLayoutX(hoursText_x + 85 * (i - 9));
                                hourTexts[0][i - 9].setLayoutY(hoursText_y);

                                setMouseHandler(hourRectangles[0][i-9]);
                                hm.put(hourRectangles[0][i-9], new Pair<>(0,i-9));
                                innerPane.getChildren().add(hourRectangles[0][i - 9]);
                                innerPane.getChildren().add(hourTexts[0][i - 9]);
                            }
                        } else {
                            for (int i = schedule.get(z); i < schedule.get(z + 1); i++) {
                                hourRectangles[0][i - 9] = new Rectangle();
                                hourRectangles[0][i - 9].setLayoutX(hoursRec_x + 85 * (i - 9));
                                hourRectangles[0][i - 9].setWidth(80);
                                hourRectangles[0][i - 9].setLayoutY(hoursRec_y);
                                hourRectangles[0][i - 9].setHeight(40);
                                hourRectangles[0][i - 9].setFill(Paint.valueOf("#ffdd00"));

                                hourTexts[0][i - 9] = new Text();
                                hourTexts[0][i - 9].setText(i + ":00");
                                hourTexts[0][i - 9].setFont(Font.font("verdana", 22));
                                hourTexts[0][i - 9].setLayoutX(hoursText_x + 85 * (i - 9));
                                hourTexts[0][i - 9].setLayoutY(hoursText_y);

                                setMouseHandler(hourRectangles[0][i-9]);
                                hm.put(hourRectangles[0][i-9], new Pair<>(0,i-9));
                                innerPane.getChildren().add(hourRectangles[0][i - 9]);
                                innerPane.getChildren().add(hourTexts[0][i - 9]);
                            }
                        }
                    }
                    isFree = !isFree;
                }

                for (int c = 1; c < countOfRooms; c++) {
                    roomRectangles[c] = clone(roomRectangles[0]);
                    roomRectangles[c].setLayoutY(roomRec_y + 60 * c);

                    roomTexts[c] = new Text();
                    roomTexts[c].setText("Room: " + rooms[c].getIndex());
                    roomTexts[c].setLayoutX(7);
                    roomTexts[c].setLayoutY(roomText_y + 60 * c);
                    roomTexts[c].setFont(Font.font("verdana", FontWeight.BOLD, 22));
                    innerPane.getChildren().add(roomRectangles[c]);
                    innerPane.getChildren().add(roomTexts[c]);

                    schedule = rooms[c].getSchedule();
                    isFree = true;

                    for (int z = 0; z < schedule.size(); z++) {
                        if (z != schedule.size() - 1) {
                            if (!isFree) {
                                for (int i = schedule.get(z); i < schedule.get(z + 1); i++) {
                                    hourRectangles[c][i - 9] = clone(hourRectangles[0][i - 9]);
                                    hourRectangles[c][i - 9].setLayoutY(hoursRec_y + 60 * c);
                                    hourRectangles[c][i - 9].setFill(Paint.valueOf("#ff4b00"));

                                    hourTexts[c][i - 9] = new Text();
                                    hourTexts[c][i - 9].setText(i + ":00");
                                    hourTexts[c][i - 9].setFont(Font.font("verdana", 22));
                                    hourTexts[c][i - 9].setLayoutX(hoursText_x + 85 * (i - 9));
                                    hourTexts[c][i - 9].setLayoutY(hoursText_y + 60 * c);

                                    setMouseHandler(hourRectangles[c][i-9]);
                                    hm.put(hourRectangles[c][i-9], new Pair<>(c,i-9));
                                    innerPane.getChildren().add(hourRectangles[c][i - 9]);
                                    innerPane.getChildren().add(hourTexts[c][i - 9]);
                                }
                            } else {
                                for (int i = schedule.get(z); i < schedule.get(z + 1); i++) {
                                    hourRectangles[c][i - 9] = clone(hourRectangles[0][i - 9]);
                                    hourRectangles[c][i - 9].setLayoutY(hoursRec_y + 60 * c);
                                    hourRectangles[c][i - 9].setFill(Paint.valueOf("#ffdd00"));

                                    hourTexts[c][i - 9] = new Text();
                                    hourTexts[c][i - 9].setText(i + ":00");
                                    hourTexts[c][i - 9].setFont(Font.font("verdana", 22));
                                    hourTexts[c][i - 9].setLayoutX(hoursText_x + 85 * (i - 9));
                                    hourTexts[c][i - 9].setLayoutY(hoursText_y + 60 * c);

                                    setMouseHandler(hourRectangles[c][i-9]);
                                    hm.put(hourRectangles[c][i-9], new Pair<>(c,i-9));
                                    innerPane.getChildren().add(hourRectangles[c][i - 9]);
                                    innerPane.getChildren().add(hourTexts[c][i - 9]);
                                }
                            }
                        }
                        isFree = !isFree;
                    }
                }
            }
        });
    }

    private Rectangle clone(Rectangle orig) {
        Rectangle rec = new Rectangle();
        rec.setHeight(orig.getHeight());
        rec.setWidth(orig.getWidth());
        rec.setFill(orig.getFill());
        rec.setLayoutY(orig.getLayoutY());
        rec.setLayoutX(orig.getLayoutX());
        return rec;
    }
    private void setMouseHandler(Rectangle rectangle) {
        rectangle.setOnMouseClicked(mouseEvent -> {
            if (!rectangle.getFill().equals(Paint.valueOf("#ff4b00"))) {
                boolean added = true;
                System.out.println(hm.size());
                Pair<Integer, Integer> index;
                if (chosen.contains(rectangle)) {
                    index = hm.get(rectangle);
                    chosen.remove(rectangle);
                    hourRectangles[index.getKey()][index.getValue()].setFill(Paint.valueOf("#ffdd00"));
                    added = false;
                }
                if (added) {
                    if (chosen.size() > 0 && !hm.get(chosen.get(0)).getKey().equals(hm.get(rectangle).getKey())) {
                        for (Rectangle value : chosen) {
                            index = hm.get(value);
                            hourRectangles[index.getKey()][index.getValue()].setFill(Paint.valueOf("#ffdd00"));
                        }
                        chosen = new ArrayList<>();
                    } else if (chosen.size() == 3) {
                        index = hm.get(chosen.get(0));
                        hourRectangles[index.getKey()][index.getValue()].setFill(Paint.valueOf("#ffdd00"));
                        chosen.remove(chosen.get(0));
                    }
                    index = hm.get(rectangle);
                    chosen.add(rectangle);
                    hourRectangles[index.getKey()][index.getValue()].setFill(Color.BROWN);
                }}
        });
    }
    @FXML
    void initialize() {
        paneAction();
        confirmButton();
        backButton();
    }
}
