package com.joel.tictactoe.adapters.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovementResponse {
    private String playerId;
    private int x;
    private int y;
}
