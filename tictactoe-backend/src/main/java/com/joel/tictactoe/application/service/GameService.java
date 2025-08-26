package com.joel.tictactoe.application.service;

import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing games.
 */
@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Starts a new game or joins an existing matchmaking game.
     *
     * @return The game that the player has joined or started.
     */
    public Game startOrJoinGame() {
        Optional<Game> matchmakingGame = gameRepository.findFirstByStatus(GameStatus.MATCHMAKING);
        Game game;

        if (matchmakingGame.isPresent()) {
            // Join the existing matchmaking game
            game = matchmakingGame.get();
            game.start();
        }else{
            // No matchmaking game available, create a new game
            game = GameFactory.createMatchmakingGame();
        }

        return gameRepository.save(game);
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param id The ID of the game to retrieve.
     * @return An Optional containing the game if found, or empty if not found.
     */
    public Optional<Game> getGame(String id) {
        return gameRepository.findById(id);
    }
}