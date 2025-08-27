package com.joel.tictactoe.adapters.outbound.persistence.mapper;

import com.joel.tictactoe.adapters.outbound.persistence.entity.GameEntity;
import com.joel.tictactoe.adapters.outbound.persistence.entity.MovementEntity;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.model.Movement;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    private GameMapper() {}

    public static Game toDomain(GameEntity entity) {
        Game game = new Game();
        game.setId(entity.getId());
        game.setStatus(entity.getStatus());
        game.setWinner(entity.getWinner());
        game.setCurrentTurn(entity.getCurrentTurn());
        game.setCreatedAt(entity.getCreatedAt());

        List<Movement> domainMovements = entity.getMovements().stream()
                .map(MovementMapper::toDomain)
                .collect(Collectors.toList());

        game.setMovements(domainMovements);

        return game;
    }

    public static GameEntity toEntity(Game game) {
        GameEntity entity = new GameEntity();
        entity.setId(game.getId());
        entity.setStatus(game.getStatus());
        entity.setWinner(game.getWinner());
        entity.setCurrentTurn(game.getCurrentTurn());
        entity.setCreatedAt(game.getCreatedAt());

        List<MovementEntity> movementEntities = game.getMovements().stream()
                .map(m -> {
                    MovementEntity me = MovementMapper.toEntity(m);
                    me.setGame(entity);
                    return me;
                })
                .collect(Collectors.toList());

        entity.setMovements(movementEntities);

        return entity;
    }
}
