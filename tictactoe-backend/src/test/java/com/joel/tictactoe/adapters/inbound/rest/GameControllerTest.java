package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private GameService gameService;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        gameController = new GameController(gameService);
    }

    @Test
    void createGame_shouldReturnGame() throws Exception {
        // given
        Game mockGame = GameFactory.createMatchmakingGame();
        when(gameService.startOrJoinGame()).thenReturn(mockGame);

        // when
        Game result = gameController.createGame();

        // then
        assertNotNull(result);
        assertEquals(mockGame, result);
        verify(gameService, times(1)).startOrJoinGame();
    }

    @Test
    void createGame_shouldThrowException_whenServiceThrows() throws Exception {
        // given
        when(gameService.startOrJoinGame()).thenThrow(new RuntimeException("Service failed"));

        // when & then
        Exception exception = assertThrows(RuntimeException.class, () -> gameController.createGame());
        assertEquals("Service failed", exception.getMessage());
        verify(gameService, times(1)).startOrJoinGame();
    }
}