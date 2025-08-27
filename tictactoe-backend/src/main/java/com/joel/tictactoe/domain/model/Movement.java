package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.domain.value.GamePlayers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Movement {
    private String id;

    private String gameId;

    private GamePlayers playerId;

    private int x;

    private int y;

    private LocalDateTime createdAt;

    public Movement() {}
}
