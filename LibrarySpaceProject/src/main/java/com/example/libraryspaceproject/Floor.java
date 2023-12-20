package com.example.libraryspaceproject;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Floor implements Prototype, Serializable {
    private Room[] rooms;
    private Chair[] chairs;
    void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }
    void setChairs(Chair[] chairs) {
        this.chairs = chairs;
    }
    void makeOrder(int index, Pair<Integer, Integer> order) {
        for (Room r: rooms) {
            if (r.getIndex() == index) {
                r.makeOrder(order);
                break;
            }
        }
    }
    public Chair[] getChairs()
    {
        return chairs;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public int getNumberOfChairs() {
        return chairs.length;
    }
    public List<List<Integer>> getRoomOrders() {
        List<List<Integer>> roomOrders = new ArrayList<>();
        for (Room room: rooms) {
            roomOrders.add(room.getSchedule());
        }
        return roomOrders;
    }
    @Override
    public Floor clone() {
        Floor newFloor = new Floor();
        Room[] newRooms = new Room[rooms.length];
        for (int c=0;c<rooms.length;c++) {
            newRooms[c] = new Room(rooms[c].getIndex());
        }
        newFloor.setRooms(newRooms);
        return newFloor;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("rooms - {\n");
        for (Room room : rooms) {
            s.append(room.getIndex());
            s.append(" - ");
            s.append(room.getSchedule());
            s.append(",\n");
        }
        s.delete(s.length()-2,s.length());
        s.append("\n");
        s.append("}\nchairs - {\n");
        for (Chair chair : chairs) {
            s.append(chair.getIndex());
            s.append(" - ");
            s.append(chair.isFree());
            s.append(", ");
        }
        s.delete(s.length()-2,s.length());
        s.append("\n}");
        return s.toString();
    }
}
