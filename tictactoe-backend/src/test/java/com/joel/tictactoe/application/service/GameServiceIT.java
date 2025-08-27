package com.joel.tictactoe.application.service;

import com.joel.tictactoe.adapters.outbound.persistence.repository.JpaGameRepository;
import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceIT {

    @Autowired
    private JpaGameRepository gameRepository;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository);
        gameRepository.deleteAll();
    }

    @Test
    void startOrJoinGame_createsNewGameIfNoneExists() {
        // when
        Game result = gameService.startOrJoinGame();

        // then
        assertNotNull(result.getId(), "Game should have an ID after being saved");
        assertEquals(GameStatus.MATCHMAKING, result.getStatus());
        assertTrue(gameRepository.findById(result.getId()).isPresent(), "Game should be persisted in the repository");
    }

    @Test
    void startOrJoinGame_joinsExistingMatchmakingGame() {
        // given
        Game existingGame = GameFactory.createMatchmakingGame();
        existingGame = gameRepository.save(existingGame);

        // when
        Game result = gameService.startOrJoinGame();

        // then
        assertEquals(existingGame.getId(), result.getId(), "Should join the existing matchmaking game");
        assertEquals(GameStatus.IN_PROGRESS, result.getStatus(), "Game status should be IN_PROGRESS after starting");
    }

    @Test
    void findByIdById() {
        // given
        Game savedGame = gameRepository.save(GameFactory.createMatchmakingGame());

        // when
        Optional<Game> foundGame = gameService.findById(savedGame.getId());

        // then
        assertTrue(foundGame.isPresent(), "Game should be found by ID");
        assertEquals(savedGame.getId(), foundGame.get().getId(), "Found game ID should match saved game ID");
    }
}
