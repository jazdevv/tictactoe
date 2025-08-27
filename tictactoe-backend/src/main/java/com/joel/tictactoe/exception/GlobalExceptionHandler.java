package com.joel.tictactoe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to catch unhandled exceptions and log them.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle all unhandled exceptions.
     *
     * @param ex the exception
     * @return a generic error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // Log full stack trace
        log.error("Unhandled exception caught: ", ex);

        // Return generic message to client
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected issue occurred");
    }

    /**
     * Handle custom exceptions.
     *
     * @param ex the custom exception
     * @return the exception message
     */
    @ExceptionHandler({CustomException.class, IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleCustomException(Exception ex) {
        // Log full stack trace
        log.error("Custom exception caught: ", ex);

        // Return the exception message to client
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Handle validation exceptions.
     *
     * @param ex the validation exception
     * @return a map of field errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Extract each field error
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        // Log the validation errors
        log.error("Validation errors: {}", errors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}