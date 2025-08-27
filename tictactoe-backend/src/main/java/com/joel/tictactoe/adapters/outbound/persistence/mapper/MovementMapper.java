package com.joel.tictactoe.adapters.outbound.persistence.mapper;

import com.joel.tictactoe.adapters.outbound.persistence.entity.MovementEntity;
import com.joel.tictactoe.domain.model.Movement;

public class MovementMapper {

    private MovementMapper() {}

    public static Movement toDomain(MovementEntity entity) {
        Movement movement = new Movement();
        movement.setId(entity.getId());
        movement.setGameId(entity.getGame().getId());
        movement.setPlayerId(entity.getPlayerId());
        movement.setX(entity.getX());
        movement.setY(entity.getY());
        movement.setCreatedAt(entity.getCreatedAt());
        return movement;
    }

    public static MovementEntity toEntity(Movement movement) {
        MovementEntity entity = new MovementEntity();
        entity.setId(movement.getId());
        entity.setX(movement.getX());
        entity.setY(movement.getY());
        entity.setPlayerId(movement.getPlayerId());
        entity.setCreatedAt(movement.getCreatedAt());
        return entity;
    }
}
