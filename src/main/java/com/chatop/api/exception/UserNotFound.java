package com.chatop.api.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound (String message) {
        super(message);
    }
}
