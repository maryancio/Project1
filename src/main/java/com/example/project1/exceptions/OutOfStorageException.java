package com.example.project1.exceptions;

public class OutOfStorageException extends  RuntimeException{

    public OutOfStorageException(String message) {
        super(message);
    }

    public OutOfStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
