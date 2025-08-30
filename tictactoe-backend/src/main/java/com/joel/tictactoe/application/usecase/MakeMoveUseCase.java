package com.joel.tictactoe.application.usecase;

import com.joel.tictactoe.adapters.inbound.rest.dto.MakeMovementRequest;
import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.exception.CustomException;
import com.joel.tictactoe.exception.ExceptionMessages;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.exception.ExceptionStatusMessage;
import org.springframework.stereotype.Service;


@Service
public class MakeMoveUseCase {
    private final GameService gameService;

    public MakeMoveUseCase(GameService gameService) {
        this.gameService = gameService;
    }

    public void execute(MakeMovementRequest request) {
        execute(request.getMatchId(), request.getPlayerId(),
                request.getSquare().getX(), request.getSquare().getY());
    }

    public void execute(String gameId, GamePlayers playerId, int x, int y) throws CustomException {
        Game game = gameService.findById(gameId).orElseThrow(() -> new CustomException(ExceptionStatusMessage.GAME_NOT_FOUND, ExceptionMessages.GAME_NOT_FOUND));

        // Validate if the game is active
        if(!game.isActive()){
            throw new CustomException(ExceptionStatusMessage.GAME_NOT_ACTIVE, ExceptionMessages.GAME_NOT_ACTIVE);
        }

        // Validate if it’s the player’s turn
        if (!game.getCurrentTurn().equals(playerId)) {
            throw new CustomException(ExceptionStatusMessage.NOT_PLAYER_TURN, ExceptionMessages.NOT_PLAYER_TURN);
        }

        game.move(x, y);

        gameService.save(game);
    }
}
