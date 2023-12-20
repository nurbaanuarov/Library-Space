package com.example.libraryspaceproject;

import java.io.*;

public class Library implements Serializable {
    private static Library instance;
    private Floor[] floors;
    private BookingDate[] bookingDates = new BookingDate[7];
    private Library() {}
    public void setFloors(Floor[] floors) {
        this.floors = floors;
        saveToFile();
    }
    public void setBookingDates(BookingDate[] bookingDates) {
        this.bookingDates = bookingDates;
        saveToFile();
    }
    public Floor[] getFloors() {
        return floors;
    }
    public BookingDate[] getBookingDates() {
        return bookingDates;
    }
    public static Library getInstance() {
        if (instance == null) {
            synchronized (Library.class) {
                try {
                    update();
                } catch (Exception e) {
                    System.out.println("libraryClass file not found");
                    if (instance == null) {
                        instance = new Library();
                    }
                }
            }
        }
        return instance;
    }
    public static void update(){
        try {
            FileInputStream fileInputStream = new FileInputStream("libraryClass.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            instance = (Library) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveToFile() {
        new Thread(() -> {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream("libraryClass.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.flush();
                objectOutputStream.writeObject(Library.getInstance());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int c=0;c<floors.length;c++) {
            s.append(c);
            s.append(" floor:\n");
            s.append(floors[c].toString());
            s.append("\n\n");
        }
        return s.toString();
    }
}
