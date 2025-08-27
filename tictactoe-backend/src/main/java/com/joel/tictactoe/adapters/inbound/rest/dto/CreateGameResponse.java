package com.joel.tictactoe.adapters.inbound.rest.dto;

import com.joel.tictactoe.domain.value.GamePlayers;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateGameResponse {
    private String matchId;
    private GamePlayers playerId;
}