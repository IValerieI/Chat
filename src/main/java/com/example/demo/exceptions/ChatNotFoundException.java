package com.example.demo.exceptions;

public class ChatNotFoundException extends RuntimeException {

    private static final long serialVerisionUID = 3;

    public ChatNotFoundException(String message) {
        super(message);
    }
}
