package com.example.springbasicnewspeed.common.exception;

public class UnauthorizedPostException extends RuntimeException {
    public UnauthorizedPostException(String message) {
        super(message);
    }
}
