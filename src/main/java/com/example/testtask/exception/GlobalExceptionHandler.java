package com.example.testtask.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public ResponseEntity<String> handleException(RuntimeException ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseBody
//    public ResponseEntity<String> handleException(ConstraintViolationException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data entry: " + ex.getMessage());
//    }
//
//    @ExceptionHandler(DataAccessException.class)
//    @ResponseBody
//    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + ex.getMessage());
//    }
//
//    @ExceptionHandler(InvalidUserException.class)
//    @ResponseBody
//    public ResponseEntity<String> handleDataAccessException(InvalidUserException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data: " + ex.getMessage());
//    }
}
