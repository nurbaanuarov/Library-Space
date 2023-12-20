package com.example.libraryspaceproject;

import javafx.util.Pair;

import java.util.List;

public class LibraryAccessFacade{
    private Library lib = Library.getInstance();
    private BookingDate[] dates = lib.getBookingDates();
    private Floor[] floors = lib.getFloors();
    public Room[] getRooms(int date, int floor) {
        update();
        for (BookingDate d: dates){
            if (d.getDate()==date) return d.getRooms(floor);
        }
        return dates[0].getRooms(floor);
    }
    public boolean makeBooking(int date, int floor, int room, List<Integer> hours) {
        if (HelloController.user.hasBook()) return false;
        update();
        for (BookingDate bookingDate : dates) {
            if (bookingDate.getDate() == date) {
                List<List<Integer>> arr = bookingDate.getBookings(floor);
                boolean free = true;
                for (int z = 1; z < arr.get(room).size(); z++) {
                    if (!free) {
                        System.out.println(arr.get(room).get(z-1)+" "+arr.get(room).get(z));
                        for (int hour: hours)
                            if (hour>arr.get(room).get(z-1) && hour<arr.get(room).get(z)) return false;
                    }
                    free = !free;
                }
                for (Integer hour : hours) bookingDate.setBooking(floor,room, new Pair<>(hour, hour + 1));
                break;
            }
        }
        Library.saveToFile();
        return true;
    }
    public void cancelMyBooking() {
        update();
        User user = HelloController.user;
        int date = user.getBookDate();
        int floor = user.getBookFloor();
        int room = user.getBookRoom();
        List<Integer> booking = user.getBookHours();
        for (int hour: booking) {
            for (BookingDate bookingDate : dates) {
                if (bookingDate.getDate() == date) {
                    bookingDate.getRooms(floor)[room].removeOrder(new Pair<>(hour, hour + 1));
                }
            }
        }
        Library.saveToFile();
    }
    public int getNumberOfFloors() {
        update();
        return floors.length;
    }
    public int[] getDates() {
        update();
        int[] arr = new int[dates.length];
        for (int c=0;c<arr.length;c++) {
            arr[c] = dates[c].getDate();
        }
        return arr;
    }
    public Chair[] getChairs(int numberOfFloor) {
        update();
        return floors[numberOfFloor].getChairs();
    }
    void update() {
        Library.update();
        lib = Library.getInstance();
        dates = lib.getBookingDates();
        floors = lib.getFloors();
    }
}
