package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.exception.CustomException;
import com.joel.tictactoe.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleAllExceptions_shouldReturnInternalServerErrorAndGenericMessage() {
        // given
        Exception testException = new RuntimeException("Test exception");

        // when
        ResponseEntity<String> response = exceptionHandler.handleAllExceptions(testException);

        // then
        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value(), "Status code should be 500");
        assertEquals("Unexpected issue occurred", response.getBody(), "Response body should match the generic message");
    }

    @Test
    void handleCustomException_shouldReturnBadRequestAndExceptionMessage() {
        // given
        String errorMessage = "Custom error occurred";
        CustomException customException = new CustomException(errorMessage);

        // when
        ResponseEntity<String> response = exceptionHandler.handleCustomException(customException);

        // then
        assertNotNull(response, "Response should not be null");
        assertEquals(400, response.getStatusCode().value(), "Status code should be 400");
        assertEquals(errorMessage, response.getBody(), "Response body should match the exception message");
    }
}