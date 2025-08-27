package com.joel.tictactoe.application.usecase;

import com.joel.tictactoe.adapters.inbound.rest.dto.CreateGameResponse;
import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.model.Game;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.util.LogMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class StartOrJoinGameUseCase {

    private final GameService gameService;

    public StartOrJoinGameUseCase(GameService gameService){
        this.gameService = gameService;
    }

    public CreateGameResponse execute() {
        Optional<Game> matchmakingGame = gameService.findFirstByStatus(GameStatus.MATCHMAKING);
        Game game;
        GamePlayers playerId;

        log.info(LogMessages.ENTERING_MATHCHMAKING);

        if (matchmakingGame.isPresent()) {
            // Join the existing matchmaking game
            game = matchmakingGame.get();
            game.start();
            playerId = GamePlayers.X;
        }else{
            // No matchmaking game available, create a new game
            game = GameFactory.createMatchmakingGame();
            playerId = GamePlayers.O;
        }

        game = gameService.save(game);

        return new CreateGameResponse(game.getId(), playerId);
    }
}
