package com.joel.tictactoe.adapters.outbound.persistence.repository;

import com.joel.tictactoe.adapters.outbound.persistence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataGameRepository extends JpaRepository<GameEntity, String> {
}