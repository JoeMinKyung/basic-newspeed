package com.example.springbasicnewspeed.common.exception;

public class CannotLikeOwnPostException extends RuntimeException {
    public CannotLikeOwnPostException(String message) {
        super(message);
    }
}
