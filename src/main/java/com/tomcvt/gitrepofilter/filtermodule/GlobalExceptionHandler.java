package com.tomcvt.gitrepofilter.filtermodule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        System.out.println("Exception caught: " + ex.getMessage());
        return "An error occurred: " + ex.getMessage();
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        System.out.println("User not found: " + ex.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponse(404, "User not found"));
    }
}
