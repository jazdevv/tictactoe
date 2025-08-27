package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.adapters.inbound.rest.exception.ExceptionMessages;
import com.joel.tictactoe.domain.value.BoardPositions;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    private List<Movement> movements = new ArrayList<>();


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

    /**
     * Makes a move for the current player at the given coordinates.
     *
     * @param x The x coordinate of the move.
     * @param y The y coordinate of the move.
     */
    public void move(int x, int y) {
        if(this.status != GameStatus.IN_PROGRESS){
            throw new IllegalStateException(ExceptionMessages.MOVE_NOT_ALLOWED_WHEN_GAME_NOT_IN_PROGRESS);
        }

        Movement movement = new Movement();
        movement.setGameId(this.id);
        movement.setPlayerId(this.currentTurn);
        movement.setX(x);
        movement.setY(y);
        movement.setCreatedAt(LocalDateTime.now());

        addMovement(movement);
    }

    /**
     * Adds a movement to the game and switches the turn.
     *
     * @param movement The movement to add.
     */
    public void addMovement(Movement movement) {
        if(movement.getX() < BoardPositions.BOARD_MIN_POSITION
                || movement.getX() > BoardPositions.BOARD_MAX_POSITION
                || movement.getY() < BoardPositions.BOARD_MIN_POSITION
                || movement.getY() > BoardPositions.BOARD_MAX_POSITION) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_MOVE_POSITION);
        }

        boolean exists = hasMovementAt(movement.getX(), movement.getY());
        if (exists) {
            throw new IllegalArgumentException(ExceptionMessages.POSITION_ALREADY_TAKEN);
        }

        movements.add(movement);
        switchTurn();
    }

    /**
     * Checks if there is a movement at the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return true if there is a movement at the given coordinates, false otherwise.
     */
    public boolean hasMovementAt(int x, int y) {
        return movements.stream().anyMatch(m -> m.getX() == x && m.getY() == y);
    }

    /**
     * Checks if the game is currently active (in progress).
     *
     * @return true if the game is in progress, false otherwise.
     */
    public boolean isActive() {
        return this.status == GameStatus.IN_PROGRESS;
    }
}