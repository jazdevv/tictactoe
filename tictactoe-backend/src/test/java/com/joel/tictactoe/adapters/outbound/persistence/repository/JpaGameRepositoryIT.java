package com.joel.tictactoe.adapters.outbound.persistence.repository;

import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JpaGameRepositoryIT {

    @Autowired
    private SpringDataGameRepository springDataGameRepository;

    private JpaGameRepository jpaGameRepository;

    @BeforeEach
    void setUp() {
        jpaGameRepository = new JpaGameRepository(springDataGameRepository);
        springDataGameRepository.deleteAll();
    }

    @Test
    void save_persistsGameAndFindyById_returnsSavedGame() {
        // given
        Game game = GameFactory.createMatchmakingGame();

        // when
        game = jpaGameRepository.save(game);

        // then
        Optional<Game> retrievedGame = jpaGameRepository.findById(game.getId());

        assertTrue(retrievedGame.isPresent(), "Game should be found after saving");
        assertEquals(game.getId(), retrievedGame.get().getId(), "Retrieved game ID should match saved game ID");
        assertEquals(game.getStatus(), retrievedGame.get().getStatus(), "Retrieved game status should match saved status");
        assertEquals(game.getCreatedAt(), retrievedGame.get().getCreatedAt(), "Retrieved game createdAt should match saved createdAt");
        assertEquals(GameStatus.MATCHMAKING, game.getStatus(), "Game should start with MATCHMAKING status");
    }

    @Test
    void findAll_returnsAllSavedGames() {
        // given
        List<Game> initialGames = jpaGameRepository.findAll();
        assertTrue(initialGames.isEmpty(), "Repository should be empty initially");

        Game game1 = GameFactory.createMatchmakingGame();
        Game game2 = GameFactory.createMatchmakingGame();
        jpaGameRepository.save(game1);
        jpaGameRepository.save(game2);

        // when
        List<Game> games = jpaGameRepository.findAll();

        // then
        assertEquals(2, games.size(), "Repository should contain exactly 2 games after saving two");
    }

    @Test
    void findFirstByStatus_returnsFirstMatchmakingGame() {
        // given
        Game game1 = GameFactory.createMatchmakingGame();
        Game game2 = GameFactory.createMatchmakingGame();
        game1 = jpaGameRepository.save(game1);
        jpaGameRepository.save(game2);

        // when
        Optional<Game> matchmakingGame = jpaGameRepository.findFirstByStatus(GameStatus.MATCHMAKING);

        // then
        assertTrue(matchmakingGame.isPresent(), "There should be a game with MATCHMAKING status");
        assertEquals(GameStatus.MATCHMAKING, matchmakingGame.get().getStatus(), "Retrieved game status should be MATCHMAKING");
        assertEquals(game1.getId(), matchmakingGame.get().getId(), "First saved game should be retrieved by findFirstByStatus");
    }
}
