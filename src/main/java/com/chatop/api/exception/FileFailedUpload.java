package com.chatop.api.exception;

import java.io.IOException;

public class FileFailedUpload extends RuntimeException {
    public FileFailedUpload (String message) {
        super(message);
    }
}