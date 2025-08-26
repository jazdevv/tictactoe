package com.joel.tictactoe.domain.factory;

import org.junit.jupiter.api.Test;

public class GameFactoryTest {

    @Test
    void testCreateMatchmakingGame() {
        // when
        var game = GameFactory.createMatchmakingGame();

        // then
        assert game != null;
        assert game.getStatus() == com.joel.tictactoe.domain.value.GameStatus.MATCHMAKING;
        assert game.getCreatedAt() != null;
    }
}
