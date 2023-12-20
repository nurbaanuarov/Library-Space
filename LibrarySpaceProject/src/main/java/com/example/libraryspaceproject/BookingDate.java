package com.example.libraryspaceproject;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.*;

public class BookingDate implements Serializable {
    private int date;
    private Floor[] floors;
    public void setDate(int date) {
        this.date = date;
    }
    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }
    public int getDate() {
        return date;
    }
    public Room[] getRooms(int floor) {
        return floors[floor].getRooms();
    }
    public void setBooking(int floorNumber, int roomIndex, Pair<Integer, Integer> order) {
        floors[floorNumber].makeOrder(roomIndex, order);
    }
    public List<List<Integer>> getBookings(int floorNumber) {
        return floors[floorNumber].getRoomOrders();
    }
}
