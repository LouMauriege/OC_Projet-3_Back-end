package com.chatop.api.exception;

public class FileFailedUpload extends RuntimeException {
    public FileFailedUpload (String message) {
        super(message);
    }
}