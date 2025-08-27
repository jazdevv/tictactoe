package com.joel.tictactoe.application.service;

import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing games.
 */
@Slf4j
@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param id The ID of the game to retrieve.
     * @return An Optional containing the game if found, or empty if not found.
     */
    public Optional<Game> findById(String id) {
        return gameRepository.findById(id);
    }

    /**
     * Retrieves all games.
     *
     * @return A list of all games.
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Saves a game.
     *
     * @param game The game to save.
     * @return The saved game.
     */
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    /**
     * Deletes all games.
     */
    public void deleteAllGames() {
        gameRepository.deleteAll();
    }

    /**
     * Finds the first game with the specified status.
     *
     * @param status The status to search for.
     * @return An Optional containing the first game with the specified status, or empty if none found.
     */
    public Optional<Game> findFirstByStatus(GameStatus status) {
        return gameRepository.findFirstByStatus(status);
    }
}