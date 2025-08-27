package com.joel.tictactoe.domain.factory;

import com.joel.tictactoe.domain.value.GameStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameFactoryTest {

    @Test
    void createMatchmakingGame_returnsGameWithMatchmakingStatusAndCreatedAt() {
        // when
        var game = GameFactory.createMatchmakingGame();

        // then
        assertNotNull(game, "Game should not be null");
        assertEquals(GameStatus.MATCHMAKING, game.getStatus(), "Game status should be MATCHMAKING");
        assertNotNull(game.getCreatedAt(), "Game should have a creation timestamp");
    }
}
