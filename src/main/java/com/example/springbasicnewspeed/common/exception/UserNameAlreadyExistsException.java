package com.example.springbasicnewspeed.common.exception;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
