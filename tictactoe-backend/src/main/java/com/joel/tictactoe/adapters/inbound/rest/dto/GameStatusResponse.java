package com.joel.tictactoe.adapters.inbound.rest.dto;

import com.joel.tictactoe.domain.model.Movement;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameStatusResponse {
    private GameStatus status;
    private GamePlayers currentTurn;
    private GameWinner winner;
    private List<MovementResponse> movements;
}
