package com.joel.tictactoe.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    String statusMessage;
    public CustomException(String statusMessage, String message) {
        super(message);
        this.statusMessage = statusMessage;
    }
}
