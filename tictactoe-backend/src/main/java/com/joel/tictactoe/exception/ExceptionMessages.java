package com.joel.tictactoe.exception;

public class ExceptionMessages {
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
    public static final String GAME_ID_REQUIRED = "gameId parameter is required";
    public static final String GAME_NOT_FOUND = "Game not found";
    public static final String MOVE_NOT_ALLOWED_WHEN_GAME_NOT_IN_PROGRESS = "Cannot make a move when the game is not in progress.";
    public static final String INVALID_MOVE_POSITION = "Move is outside the board.";
    public static final String POSITION_ALREADY_TAKEN = "Position is already taken.";
    public static final String GAME_NOT_ACTIVE = "Game is not active";
    public static final String NOT_PLAYER_TURN = "It is not player's turn";
}