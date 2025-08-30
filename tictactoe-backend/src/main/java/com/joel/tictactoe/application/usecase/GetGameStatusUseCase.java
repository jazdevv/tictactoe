package com.joel.tictactoe.application.usecase;

import com.joel.tictactoe.adapters.inbound.rest.dto.GameStatusResponse;
import com.joel.tictactoe.adapters.outbound.persistence.mapper.GameMapper;
import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.exception.CustomException;
import com.joel.tictactoe.exception.ExceptionMessages;
import com.joel.tictactoe.exception.ExceptionStatusMessage;
import org.springframework.stereotype.Service;

@Service
public class GetGameStatusUseCase {

    private final GameService gameService;

    public GetGameStatusUseCase(GameService gameService) {
        this.gameService = gameService;
    }

    public GameStatusResponse execute(String gameId) throws CustomException {
        if (gameId == null || gameId.isBlank()) {
            throw new CustomException(ExceptionStatusMessage.GAME_ID_REQUIRED, ExceptionMessages.GAME_ID_REQUIRED);
        }

        Game game = gameService.findById(gameId)
                .orElseThrow(() -> new CustomException(ExceptionStatusMessage.GAME_NOT_FOUND, ExceptionMessages.GAME_NOT_FOUND));

        return GameMapper.toGameStatusResponse(game);
    }
}
