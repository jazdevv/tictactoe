package com.joel.tictactoe.adapters.inbound.rest.dto;

import com.joel.tictactoe.domain.value.BoardPositions;
import com.joel.tictactoe.domain.value.GamePlayers;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class MakeMovementRequest {
    @NotNull(message = "{movement.matchId.required}")
    private String matchId;

    @NotNull(message = "{movement.playerId.required}")
    private GamePlayers playerId;

    @NotNull(message = "{movement.square.required}")
    @Valid
    private Square square;

    @Getter
    @Setter
    public static class Square {

        @Min(value = BoardPositions.BOARD_MIN_POSITION, message = "{movement.square.x.range}")
        @Max(value = BoardPositions.BOARD_MAX_POSITION, message = "{movement.square.x.range}")
        private int x;

        @Min(value = BoardPositions.BOARD_MIN_POSITION, message = "{movement.square.y.range}")
        @Max(value = BoardPositions.BOARD_MAX_POSITION, message = "{movement.square.y.range}")
        private int y;
    }
}
