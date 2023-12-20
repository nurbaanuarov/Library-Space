package com.example.libraryspaceproject;

import java.time.LocalDateTime;

public class BookingDateBuilder implements Builder{
    private BookingDate[] bookingDates = new BookingDate[7];
    private final Library library = Library.getInstance();
    @Override
    public void reset() {
        bookingDates = null;
    }
    @Override
    public BookingDate[] getResult() {
        return bookingDates;
    }
    public void setDate(LocalDateTime date) {
        int k = date.getDayOfMonth();
        for(int c=0;c<7;c++){
            bookingDates[c] = new BookingDate();
            bookingDates[c].setDate(k+c+1);
        }
    }
    @Override
    public void setFloors() {
        Floor[] libFloors = library.getFloors();
        for (int c=0;c<7;c++){
            Floor[] floors = new Floor[libFloors.length];
            for (int z = 0; z < libFloors.length; z++) {
                floors[z] = libFloors[z].clone();
            }
            bookingDates[c].setFloors(floors);
        }
    }
}
