package com.joel.tictactoe.domain.repository;

import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;

import java.util.Optional;
import java.util.List;

public interface GameRepository {
    Game save(Game game);
    Optional<Game> findById(String id);
    List<Game> findAll();
    Optional<Game> findFirstByStatus(GameStatus status);
}
