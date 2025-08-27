package com.joel.tictactoe.adapters.outbound.persistence.entity;

import com.joel.tictactoe.domain.value.GamePlayers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Getter
@Setter
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Enumerated(EnumType.STRING)
    private GamePlayers playerId;

    private int x;

    private int y;

    private LocalDateTime createdAt;

    public MovementEntity() {}
}
