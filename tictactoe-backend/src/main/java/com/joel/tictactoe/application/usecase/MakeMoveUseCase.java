package com.joel.tictactoe.application.usecase;

import com.joel.tictactoe.adapters.inbound.rest.dto.MakeMovementRequest;
import com.joel.tictactoe.adapters.inbound.rest.dto.MakeMovementResponse;
import com.joel.tictactoe.adapters.inbound.rest.exception.CustomException;
import com.joel.tictactoe.adapters.inbound.rest.exception.ExceptionMessages;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.repository.GameRepository;
import com.joel.tictactoe.domain.value.GamePlayers;
import org.springframework.stereotype.Service;


@Service
public class MakeMoveUseCase {
    private final GameRepository gameRepository;

    public MakeMoveUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public MakeMovementResponse execute(MakeMovementRequest request) {
        return execute(request.getMatchId(), request.getPlayerId(),
                request.getSquare().getX(), request.getSquare().getY());
    }

    public MakeMovementResponse execute(String gameId, GamePlayers playerId, int x, int y) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new CustomException(ExceptionMessages.GAME_NOT_FOUND));

        // Validate if the game is active
        if(!game.isActive()){
            throw new CustomException(ExceptionMessages.GAME_NOT_ACTIVE);
        }

        // Validate if it’s the player’s turn
        if (!game.getCurrentTurn().equals(playerId)) {
            throw new CustomException(ExceptionMessages.NOT_PLAYER_TURN);
        }

        game.move(x, y);

        gameRepository.save(game);

        return new MakeMovementResponse();
    }
}
