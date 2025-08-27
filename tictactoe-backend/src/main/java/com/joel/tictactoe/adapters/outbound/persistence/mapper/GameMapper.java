package com.joel.tictactoe.adapters.outbound.persistence.mapper;

import com.joel.tictactoe.adapters.inbound.rest.dto.GameStatusResponse;
import com.joel.tictactoe.adapters.inbound.rest.dto.MovementResponse;
import com.joel.tictactoe.adapters.outbound.persistence.entity.GameEntity;
import com.joel.tictactoe.adapters.outbound.persistence.entity.MovementEntity;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.model.Movement;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    private GameMapper() {}

    /**
     * Convert a GameEntity to a Game domain object.
     * @param entity The GameEntity from persistence.
     * @return The Game domain object.
     */
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

    /**
     * Convert a Game domain object to a GameEntity for persistence.
     * @param game The Game domain object.
     * @return The GameEntity for persistence.
     */
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

    /**
     * Convert a Game domain object to a GameStatusResponse DTO.
     * @param game The Game domain object.
     * @return The GameStatusResponse DTO.
     */
    public static GameStatusResponse toGameStatusResponse(Game game) {
        List<MovementResponse> movementResponses = game.getMovements().stream()
                .map(m -> new MovementResponse(m.getPlayerId().toString(), m.getX(), m.getY()))
                .collect(Collectors.toList());

        return new GameStatusResponse(
                game.getStatus(),
                game.getCurrentTurn(),
                game.getWinner(),
                movementResponses
        );
    }
}
