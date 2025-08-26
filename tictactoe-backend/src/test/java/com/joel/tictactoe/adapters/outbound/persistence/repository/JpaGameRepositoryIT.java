package com.joel.tictactoe.adapters.outbound.persistence.repository;

import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
    void testSaveGameAndFindById(){
        // given
        Game game = GameFactory.createMatchmakingGame();

        // when
        game = jpaGameRepository.save(game);

        // then
        Optional<Game> retrievedGame = jpaGameRepository.findById(game.getId());
        assert retrievedGame.isPresent();
        assert retrievedGame.get().getId().equals(game.getId());
        assert retrievedGame.get().getStatus().equals(game.getStatus());
        assert retrievedGame.get().getCreatedAt().equals(game.getCreatedAt());
        assert game.getStatus().equals(GameStatus.MATCHMAKING);
    }

    @Test
    void testFindAll(){

        // given
        List<Game> initialGames = jpaGameRepository.findAll();
        assert initialGames.isEmpty();

        Game game1 = GameFactory.createMatchmakingGame();
        Game game2 = GameFactory.createMatchmakingGame();
        jpaGameRepository.save(game1);
        jpaGameRepository.save(game2);

        // when
        List<Game> games = jpaGameRepository.findAll();

        // then
        assert games.size() == 2;
    }

    @Test
    void testFindFirstByStatus() {
        // given
        Game game1 = GameFactory.createMatchmakingGame();
        Game game2 = GameFactory.createMatchmakingGame();
        game1 = jpaGameRepository.save(game1);
        jpaGameRepository.save(game2);

        // when
        Optional<Game> matchmakingGame = jpaGameRepository.findFirstByStatus(GameStatus.MATCHMAKING);

        // then
        assert matchmakingGame.isPresent();
        assert matchmakingGame.get().getStatus() == GameStatus.MATCHMAKING;
        assert matchmakingGame.get().getId().equals(game1.getId());
    }
}
