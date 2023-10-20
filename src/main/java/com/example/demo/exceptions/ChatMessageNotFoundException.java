package com.example.demo.exceptions;

public class ChatMessageNotFoundException extends RuntimeException {

    private static final long serialVerisionUID = 2;

    public ChatMessageNotFoundException(String message) {
        super(message);
    }
}
