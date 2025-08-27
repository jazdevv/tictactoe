package com.joel.tictactoe.adapters.outbound.persistence.entity;

import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Enumerated(EnumType.STRING)
    private GameWinner winner;

    @Column(length = 1)
    private GamePlayers currentTurn;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MovementEntity> movements = new ArrayList<>();

    private LocalDateTime createdAt;

    public GameEntity() {}

}