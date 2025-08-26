package com.joel.tictactoe.adapters.inbound.rest.dto;

import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameStatusResponse {
    private GameStatus status;
    private GamePlayers currentTurn;
    private GameWinner winner;
}
