package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.exception.*;
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
        String errorMessage = "Test exception";
        Exception testException = new RuntimeException(errorMessage);

        // when
        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleAllExceptions(testException);

        // then
        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value(), "Status code should be 500");
        assertEquals(ExceptionMessages.UNEXPECTED_ERROR, response.getBody().getMessage(), "Response body should match the generic message");
        assertEquals(ExceptionStatusMessage.UNEXPECTED_ERROR, response.getBody().getStatusMessage(), "Response body should match the status message");
    }

    @Test
    void handleCustomException_shouldReturnBadRequestAndExceptionMessage() {
        // given
        String errorMessage = "Custom error occurred";
        String errorStatusMessage = "ERROR";
        CustomException customException = new CustomException(errorStatusMessage, errorMessage);

        // when
        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleCustomException(customException);

        // then
        assertNotNull(response, "Response should not be null");
        assertEquals(400, response.getStatusCode().value(), "Status code should be 400");
        assertEquals(errorMessage, response.getBody().getMessage(), "Response body should match the exception message");
        assertEquals(errorStatusMessage, response.getBody().getStatusMessage(), "Response body should match the status message");
    }
}