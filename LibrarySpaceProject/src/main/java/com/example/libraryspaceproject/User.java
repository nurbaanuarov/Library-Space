package com.example.libraryspaceproject;

import java.io.*;
import java.util.List;

public class User implements Serializable {
    private final String name;
    private boolean hasBook = false;
    private int bookDate;
    private int bookFloor;
    private int bookRoom;
    private int floorOfChairs;
    private List<Integer> bookHours;
    public String getName() {
        return name;
    }
    public User(String name) {
        this.name = name;
    }

    public void setFloorOfChairs(int floorOfChairs) {
        this.floorOfChairs = floorOfChairs;
    }

    public int getFloorOfChairs() {
        return floorOfChairs;
    }

    public void setBookDate(int bookDate) {
        this.bookDate = bookDate;
    }
    public int getBookDate() {
        return bookDate;
    }
    public void setBookFloor(int bookFloor) {
        this.bookFloor = bookFloor;
    }
    public int getBookFloor() {
        return bookFloor;
    }
    public void setBookRoom(int bookRoom) {
        this.bookRoom = bookRoom;
    }
    public int getBookRoom() {
        return bookRoom;
    }
    public void setHasBook(boolean hasBook) {
        this.hasBook = hasBook;
    }
    public void setBookHours(List<Integer> bookHours) {
        this.bookHours = bookHours;
    }
    public List<Integer> getBookHours() {
        return bookHours;
    }
    public boolean hasBook() {
        return hasBook;
    }
}
