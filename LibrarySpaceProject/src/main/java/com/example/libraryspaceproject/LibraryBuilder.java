package com.example.libraryspaceproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class LibraryBuilder implements Builder {
    private final Library library = Library.getInstance();
    private Floor[] floors;
    private DataInputStream in;
    DataOutputStream out;
    ServerSocket server;
    Socket socket;
    @Override
    public void setFloors() {
        try {
            server = new ServerSocket(8002);
            server.setReuseAddress(true);
            socket = server.accept();
            System.out.println("SystemAdmin connected");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("Print the number of floors:");
            int count = Integer.parseInt(in.readUTF());
            System.out.println(count);
            floors = new Floor[count];
            for (int c=0;c<count;c++) {
                floors[c] = new Floor();
            }
        } catch (IOException e) {
            System.out.println("Server wasn't found");
        }
    }
    public void setRoomsAndChairs() {
        try {
            for (int c=0;c<floors.length;c++) {
                Room[] rooms;
                Chair[] chairs;
                out.writeUTF("Print number of rooms on "+c+" floor:");
                int count = Integer.parseInt(in.readUTF());
                System.out.println(count);
                rooms = new Room[count];
                for (int z = 0; z < count; z++) {
                    rooms[z] = new Room(z);
                }
                out.writeUTF("Print number of chair on "+c+" floor:");
                count = Integer.parseInt(in.readUTF());
                System.out.println(count);
                chairs = new Chair[count];
                int x = 10, y = 70;
                for (int z = 0; z < count; z++) {
                    chairs[z] = new Chair(z);
                    chairs[z].setCoordinate(x,y);
                    if (x==590) {
                        x=10;y+=20;
                    } else x+=20;
                }
                floors[c].setRooms(rooms);
                floors[c].setChairs(chairs);
            }
            library.setFloors(floors);
            BookingDateBuilder bookingDateBuilder = new BookingDateBuilder();
            bookingDateBuilder.setDate(LocalDateTime.now());
            bookingDateBuilder.setFloors();
            library.setBookingDates(bookingDateBuilder.getResult());


            out.writeUTF("Library system created successfully");
        } catch (IOException e) {
            System.out.println("Server wasn't found");
        }
    }

    public Library getResult() {
        return library;
    }
    public void reset() {
        library.setFloors(null);
    }
}