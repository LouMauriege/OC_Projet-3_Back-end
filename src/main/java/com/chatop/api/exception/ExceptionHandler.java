package com.chatop.api.exception;

import org.apache.catalina.authenticator.SingleSignOnSessionKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({RentalNotFound.class})
    public ResponseEntity<Object> rentalNotFound(RentalNotFound rentalNotFound) {
       return new ResponseEntity<>("Rental non trouvé!", HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserNotFound.class})
    public ResponseEntity<Object> userNotFound(UserNotFound userNotFound) {
        return new ResponseEntity<>("Utilisateur non trouvé!", HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TokenNotFound.class)
    public ResponseEntity<Object> tokenNotFound(TokenNotFound tokenNotFound) {
        return new ResponseEntity<>("Vous n'êtes pas connecté!", HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({FileFailedUpload.class})
    public ResponseEntity<Object> fileFailedUpload(FileFailedUpload fileFailedUpload) {
        return new ResponseEntity<>("Echec dans l'upload du fichier!", HttpStatus.BAD_REQUEST);
    }
}
