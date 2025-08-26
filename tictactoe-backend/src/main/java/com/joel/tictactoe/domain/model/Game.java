package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class Game {
    /**  * The player who always starts first. */
    private static final GamePlayers FIRST_PLAYER = GamePlayers.X;

    private String id;

    private GameStatus status;

    private GameWinner winner;

    private GamePlayers currentTurn;

    private LocalDateTime createdAt;

    public Game() {}

    /**
     * Starts the game by setting the status to IN_PROGRESS
     * and assigning the first turn to the first player.
     */
    public void start() {
        this.status = GameStatus.IN_PROGRESS;
        this.currentTurn = FIRST_PLAYER;
    }

    /**
     * Switches the current turn to the other player.
     */
    public void switchTurn() {
        if (currentTurn == GamePlayers.X) {
            currentTurn = GamePlayers.O;
        } else {
            currentTurn = GamePlayers.X;
        }
    }

    /**
     * Ends the game by setting the status to FINISHED and
     * assigning the winner.
     *
     * @param winner The player who won the game.
     */
    public void end(GameWinner winner) {
        this.status = GameStatus.FINISHED;
        this.winner = winner;
    }
}