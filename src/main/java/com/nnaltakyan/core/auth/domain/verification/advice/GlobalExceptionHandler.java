package com.nnaltakyan.core.auth.domain.verification.advice;

import com.nnaltakyan.core.auth.domain.user.exceptions.UserNotFoundException;
import com.nnaltakyan.core.auth.domain.verification.exceptions.VerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String VERIFICATION_EXCEPTION_MESSAGE = "Verification failed";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User not found";
    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<String> handleException(VerificationException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(VERIFICATION_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(UserNotFoundException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}
