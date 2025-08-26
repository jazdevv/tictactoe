package com.joel.tictactoe.adapters.outbound.persistence.mapper;

import com.joel.tictactoe.adapters.outbound.persistence.entity.GameEntity;
import com.joel.tictactoe.domain.model.Game;

public class GameMapper {

    private GameMapper() {}

    public static Game toDomain(GameEntity entity) {
        Game game = new Game();
        game.setId(entity.getId());
        game.setStatus(entity.getStatus());
        game.setWinner(entity.getWinner());
        game.setCurrentTurn(entity.getCurrentTurn());
        game.setCreatedAt(entity.getCreatedAt());
        return game;
    }

    public static GameEntity toEntity(Game game) {
        GameEntity entity = new GameEntity();
        entity.setId(game.getId());
        entity.setStatus(game.getStatus());
        entity.setWinner(game.getWinner());
        entity.setCurrentTurn(game.getCurrentTurn());
        entity.setCreatedAt(game.getCreatedAt());
        return entity;
    }
}
