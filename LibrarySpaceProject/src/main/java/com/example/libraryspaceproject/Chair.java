package com.example.libraryspaceproject;

import javafx.util.Pair;

import java.io.Serializable;

public class Chair implements Serializable {
    private final int index;
    private int x,y;
    private boolean isFree;
    boolean isFree() {
        return isFree;
    }
    int getIndex() {
        return index;
    }
    void change() {
        isFree = !isFree;
        Library.saveToFile();
    }
    void setCoordinate(int x, int y) {
        if (x<10) x = 10;
        else if ((x-10)%20>=10) {
            x = ((x-10)/20+1)*20+10;
        } else x=(x-10)/20*20+10;
        if (y<70) y = 70;
        else if ((y-70)%20>=10) {
            y = ((y-70)/20+1)*20+70;
        } else y=(y-70)/20*20+70;
        this.x = x;
        this.y = y;
        Library.saveToFile();
    }
    Pair<Integer, Integer> getCoordinate() {
        return new Pair<>(x,y);
    }
    public Chair(int index) {
        this.index = index;
        this.isFree = true;
    }
}
