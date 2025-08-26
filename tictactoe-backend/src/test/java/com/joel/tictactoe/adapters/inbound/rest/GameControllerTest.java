package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.adapters.inbound.rest.dto.CreateGameResponse;
import com.joel.tictactoe.adapters.inbound.rest.dto.GameStatusResponse;
import com.joel.tictactoe.adapters.inbound.rest.exception.ExceptionMessages;
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
    void createGame_shouldReturnGame() {
        // given
        Game mockGame = GameFactory.createMatchmakingGame();
        mockGame.setId("test-id");
        when(gameService.startOrJoinGame()).thenReturn(mockGame);

        // when
        CreateGameResponse result = gameController.createGame();

        // then
        assertNotNull(result, "The result should not be null");
        assertEquals(mockGame.getId(), result.getMatchId(), "The returned game ID should match the mock game ID");
        verify(gameService, times(1)).startOrJoinGame();
    }

    @Test
    void createGame_shouldThrowException_whenServiceThrows() {
        // given
        when(gameService.startOrJoinGame()).thenThrow(new RuntimeException("Service failed"));

        // when & then
        Exception exception = assertThrows(RuntimeException.class, () -> gameController.createGame());
        assertEquals("Service failed", exception.getMessage());
        verify(gameService, times(1)).startOrJoinGame();
    }

    @Test
    void getGameStatus_shouldThrowException_whenGameIdIsNull() {
        // when & then
        Exception exception = assertThrows(Exception.class, () -> gameController.getGameStatus(null));
        assertEquals(ExceptionMessages.GAME_ID_REQUIRED, exception.getMessage());
        verify(gameService, never()).getGame(anyString());
    }

    @Test
    void getGameStatus_shouldThrowException_whenGameNotFound() throws Exception {
        // given
        String gameId = "non-existent-id";
        when(gameService.getGame(gameId)).thenReturn(java.util.Optional.empty());

        // when & then
        Exception exception = assertThrows(Exception.class, () -> gameController.getGameStatus(gameId));
        assertEquals(ExceptionMessages.GAME_NOT_FOUND, exception.getMessage());
        verify(gameService, times(1)).getGame(gameId);
    }

    @Test
    void getGameStatus_shouldReturnGameStatus_whenGameExists() throws Exception {
        // given
        String gameId = "existing-id";
        Game mockGame = GameFactory.createMatchmakingGame();
        mockGame.setId(gameId);
        when(gameService.getGame(gameId)).thenReturn(java.util.Optional.of(mockGame));

        // when
        GameStatusResponse result = gameController.getGameStatus(gameId);

        // then
        assertNotNull(result, "The result should not be null");
        assertEquals(mockGame.getStatus(), result.getStatus(), "The returned status should match the mock game status");
        assertEquals(mockGame.getCurrentTurn(), result.getCurrentTurn(), "The returned current turn should match the mock game current turn");
        assertEquals(mockGame.getWinner(), result.getWinner(), "The returned winner should match the mock game winner");
        verify(gameService, times(1)).getGame(gameId);
    }
}