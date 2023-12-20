package com.example.libraryspaceproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class UserLoginChecker implements UserLoggerChain{
    UserLoggerChain nextLogger;
    private int loggingIn(String name, String password){
        HelloController.user = new User(name);
        if (name.equals("") || password.equals("")) return 3;
        if (isLoginExist(name)) {
            try {
                String s = readFile();
                if (s.contains("name: " + name + "\npassword: " + password+"\n")) {
                    nextLogger.logMessage(7, new String[]{name, password});
                    return 0;
                }
                else return 2;
            } catch (FileNotFoundException f){
                System.out.println("File not found");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 1;
    }
    private int createFolder(String name, String email, String password, String reppasword) {
        HelloController.user = new User(name);
        try {
            if (name.equals("") || email.equals("") || password.equals("")) return 3;
            if (name.contains(" ") || email.contains(" ")) return 4;
            RandomAccessFile randomAccessFile = new RandomAccessFile("logins","rw");
            String user = "name: "+name+"\npassword: "+password+"\nemail: "+email+"\n";
            randomAccessFile.seek(randomAccessFile.length());
            if (isLoginExist(name)) return 1;
            if (!isPasswordMatches(password,reppasword) || logic(name,password)) return 2;
            randomAccessFile.write(user.getBytes());
            randomAccessFile.close();
            countLines();
        } catch (FileNotFoundException f){
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nextLogger.logMessage(6, new String[]{name, password});
        return 0;
    }
    private void countLines() throws IOException {
        String[] s = readFile().split("\n");
        System.out.println(s.length+" - count of lines");
    }
    private boolean isLoginExist(String name){
        try {
            String s = readFile();
            if (s.contains("name: "+name+"\n")) return true;
        } catch (FileNotFoundException f){
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean isPasswordMatches(String password, String reppassword) {
        return password.length() == 8 && password.equals(reppassword);
    }
    private int forgotPassword(String name, String email) {
        try {
            String s = readFile();
            if (s.contains(name)) {
                Scanner scan = new Scanner(s.substring(s.indexOf(name+"\n")));
                scan.nextLine();
                scan.nextLine();
                String so = scan.nextLine()+"\n";
                if (so.contains("email: "+email+"\n")) return 0;
                return 1;
            }
        } catch (FileNotFoundException f){
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    private String readFile() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("logins","rw");
        randomAccessFile.seek(0);
        byte[] bytes = new byte[(int) (randomAccessFile.length())];
        randomAccessFile.read(bytes);
        String s = new String(bytes);
        randomAccessFile.close();
        return s;
    }
    private boolean logic(String name, String password) {
        return name.equals(password);
    }

    @Override
    public void setNextLogger(UserLoggerChain nextLogger) {
        this.nextLogger = nextLogger;
    }

    @Override
    public int logMessage(int operation, String[] login) {
        if (operation == 1) {
            return createFolder(login[0],login[1], login[2],login[3]);
        } else if (operation == 2){
            return loggingIn(login[0], login[1]);
        } else if (operation == 3) {
            return forgotPassword(login[0], login[1]);
        } else if (operation == 4) {
            return isLoginExist(login[0])?0:1;
        }
        else {
            return nextLogger.logMessage(operation, login);
        }
    }
}
