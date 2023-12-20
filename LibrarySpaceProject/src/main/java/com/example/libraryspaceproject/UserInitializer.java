package com.example.libraryspaceproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserInitializer implements UserLoggerChain{
    User user;
    private void update() {
        try {
            user = HelloController.user;
            FileInputStream fileInputStream = new FileInputStream("users.txt");
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            Object object = in.readObject();
            if (object != null) {
                boolean found = false;
                List<User> users = (List<User>) object;
                for (User us: users) {
                    if (us.getName().equals(user.getName())) {
                        System.out.println("changing user");
                        us.setHasBook(user.hasBook());
                        us.setBookRoom(user.getBookRoom());
                        us.setBookFloor(user.getBookFloor());
                        us.setBookDate(user.getBookDate());
                        us.setBookHours(user.getBookHours());
                        found = true;
                    }
                }
                if (!found) users.add(user);
                FileOutputStream fileOutputStream = new FileOutputStream("users.txt");
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(users);
            }
        } catch (Exception e) {
            System.out.println("file is empty");
        }
    }
    private void findFromList() {
        try {
            user = HelloController.user;
            FileInputStream fileInputStream = new FileInputStream("users.txt");
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            Object object = in.readObject();
            if (object != null) {
                List<User> users = (List<User>) object;
                System.out.println("users - "+users.size());
                for (User us: users) {
                    if (us.getName().equals(user.getName())) {
                        System.out.println("found this user");
                        if (us.hasBook()) {
                            user.setHasBook(true);
                            user.setBookDate(us.getBookDate());
                            user.setBookFloor(us.getBookFloor());
                            user.setBookRoom(us.getBookRoom());
                            user.setBookHours(us.getBookHours());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("file not found");
        }
    }
    private void addUserToList() {
        user = HelloController.user;
        List<User> users = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("users.txt");
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            Object object = in.readObject();

            if (object != null) {
                users = (List<User>) object;
            }
            users.add(user);
        } catch (Exception e) {
            System.out.println("file not found");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("users.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(users);
        } catch (Exception e) {
            System.out.println("file not found");
        }
    }
    @Override
    public void setNextLogger(UserLoggerChain nextLogger) {
    }

    @Override
    public int logMessage(int operation, String[] login) {
        if (operation == 6) {
            addUserToList();
        } else if (operation == 7) {
            findFromList();
        } else {
            update();
        }
        return 0;
    }
}
