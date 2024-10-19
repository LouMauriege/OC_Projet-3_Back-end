package com.chatop.api.exception;

public class TokenNotFound extends RuntimeException {
    public TokenNotFound(String messsage) {
        super(messsage);
    }
}
