package com.example.libraryspaceproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemAdmin {
    @FXML
    private Button button;

    @FXML
    private AnchorPane pane;
    @FXML
    private Text text;

    @FXML
    private TextField textField;
    boolean bool = false;
    DataOutputStream out;
    DataInputStream in;
    Socket socket;
    ServerSocket server;
    @FXML
    void paneAction() {
        pane.setOnMouseEntered(event -> {
            if (!bool) {
                bool = true;
                new Thread(() -> {
                    LibraryBuilder libBuilder = new LibraryBuilder();
                    libBuilder.setFloors();
                    libBuilder.setRoomsAndChairs();
                    clearFiles();
                }).start();
                while (true) {
                    try {
                        if (socket == null) {
                            socket = new Socket("localhost", 8002);
                            System.out.println("Connection established");
                            in = new DataInputStream(socket.getInputStream());
                            out = new DataOutputStream(socket.getOutputStream());
                        }
                        text.setText(in.readUTF());
                        break;
                    } catch (Exception e) {
                        System.out.println("not connected");
                    }
                }
            }
        });
    }
    @FXML
    void buttonClicked() {
        if(button != null) button.setOnMouseClicked(ev -> {
            try {
                out.writeUTF(textField.getText());
                text.setText(in.readUTF());


                if (text.getText().equals("Library system created successfully")) {
                    socket.close();
                    server = new ServerSocket(8000);
                    Library lib = Library.getInstance();
                    Floor[] floors = lib.getFloors();
                    int count = floors.length;
                    for (int c = 0; c < count; c++) {

                        FXMLLoader fxmlLoader = new FXMLLoader(SystemAdmin.class.getResource("floor_Admin.fxml"));
                        fxmlLoader.load();
                        Parent par = fxmlLoader.getRoot();
                        Stage stage = new Stage();
                        stage.setTitle("Hello!");
                        stage.setResizable(false);
                        stage.setScene(new Scene(par));
                        stage.show();

                        connect(c);
                        System.out.println("Opened admin of floor " + c);

                    }

                    pane.getScene().getWindow().hide();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void connect(int c) {
        new Thread(() ->{
            try {
                System.out.println("Waiting for floor admin");
                Socket socket2 = server.accept();
                System.out.println("FloorAdmin connected");
                DataOutputStream out1 = new DataOutputStream(socket2.getOutputStream());
                out1.writeInt(c);
            } catch (IOException e){
                System.out.println("Exception on connection");
            }
        }).start();
    }
    void clearFiles() {
        try {
            Path path1 = Paths.get("logins");
            Path path2 = Paths.get("users.txt");
            Path path3 = Paths.get("remember");
            Files.write(path1, new byte[0]);
            Files.write(path2, new byte[0]);
            Files.write(path3, new byte[0]);

            System.out.println("Files cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        buttonClicked();
        paneAction();
    }
}