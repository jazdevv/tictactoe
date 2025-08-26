package com.joel.tictactoe.adapters.outbound.persistence.repository;

import com.joel.tictactoe.adapters.outbound.persistence.mapper.GameMapper;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.repository.GameRepository;
import com.joel.tictactoe.adapters.outbound.persistence.entity.GameEntity;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaGameRepository implements GameRepository {

    private final SpringDataGameRepository springDataGameRepository;

    public JpaGameRepository(SpringDataGameRepository springDataGameRepository) {
        this.springDataGameRepository = springDataGameRepository;
    }

    @Override
    public Game save(Game game) {
        GameEntity entity = GameMapper.toEntity(game);
        return GameMapper.toDomain(springDataGameRepository.save(entity));
    }

    @Override
    public Optional<Game> findById(String id) {
        return springDataGameRepository.findById(id).map(GameMapper::toDomain);
    }

    @Override
    public List<Game> findAll() {
        return springDataGameRepository.findAll().stream()
                .map(GameMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Game> findFirstByStatus(GameStatus status) {
        return springDataGameRepository.findAll().stream()
                .filter(entity -> entity.getStatus() == status)
                .findFirst()
                .map(GameMapper::toDomain);
    }
}
