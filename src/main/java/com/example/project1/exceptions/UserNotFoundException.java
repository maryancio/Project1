package com.example.project1.exceptions;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException (String message ) {
        super(message);
    }
}
