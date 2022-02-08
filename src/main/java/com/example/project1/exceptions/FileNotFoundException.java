package com.example.project1.exceptions;

public class FileNotFoundException extends  OutOfStorageException{

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
