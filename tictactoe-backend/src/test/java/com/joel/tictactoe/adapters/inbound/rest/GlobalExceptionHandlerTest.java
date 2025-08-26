package com.joel.tictactoe.adapters.inbound.rest;

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
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected issue occurred", response.getBody());
    }
}