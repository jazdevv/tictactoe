package com.joel.tictactoe.domain.factory;

import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;

import java.time.LocalDateTime;


public class GameFactory {

    private GameFactory() {
    }

    /** Creates a new Game instance with status set to MATCHMAKING.
     *
     * @return a new Game instance
     */
    public static Game createMatchmakingGame() {
        Game game = new Game();
        game.setStatus(GameStatus.MATCHMAKING);
        game.setCreatedAt(LocalDateTime.now());
        return game;
    }
}