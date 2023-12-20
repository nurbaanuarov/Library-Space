package com.example.libraryspaceproject;

public interface UserLoggerChain {
    void setNextLogger(UserLoggerChain nextLogger);
    int logMessage(int operation, String[] login);
}
