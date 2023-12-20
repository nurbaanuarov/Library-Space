package com.example.libraryspaceproject;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.*;

public class Room implements Serializable {
    private final int index;
    private final ArrayList<Integer> roomSchedule;
    public Room(int index) {
        this.index = index;
        roomSchedule = new ArrayList<>();
        roomSchedule.add(9);
        roomSchedule.add(18);
    }
    public List<Integer> getSchedule() {
        return roomSchedule;
    }
    public int getIndex() {
        return index;
    }
    void makeOrder(Pair<Integer, Integer> order) {
        boolean ordered = false;
        for (int c=0;c<roomSchedule.size();c++) {
            if (ordered && roomSchedule.get(c) >= order.getValue()) {
                if (roomSchedule.get(c-1) == 9 || !roomSchedule.get(c-1).equals(order.getKey())) {
                    roomSchedule.add(c,order.getKey());
                } else {
                    roomSchedule.remove(--c);
                    c--;
                }
                if (!order.getValue().equals(roomSchedule.get(++c))) roomSchedule.add(c,order.getValue());
                break;
            }
            ordered = !ordered;
        }
        Library.saveToFile();
    }
    void removeOrder(Pair<Integer,Integer> order) {
        boolean ordered = false;
        for (int c=0;c<roomSchedule.size();c++) {
            if (!ordered && roomSchedule.get(c) >= order.getValue()) {
                if (roomSchedule.get(c-1).equals(order.getKey())) {
                    roomSchedule.remove(--c);
                    System.out.println("remove key");
                } else {
                    roomSchedule.add(c,order.getKey());
                    c++;
                    System.out.println("add key");
                }
                if (roomSchedule.get(c).equals(order.getValue())) {
                    roomSchedule.remove(c);
                    System.out.println("remove value");
                } else {
                    System.out.println("add value");
                    roomSchedule.add(c,order.getValue());
                }
                break;
            }
            ordered = !ordered;
        }
        Library.saveToFile();
    }
}
