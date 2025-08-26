package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.adapters.inbound.rest.dto.CreateGameResponse;
import com.joel.tictactoe.adapters.inbound.rest.dto.GameStatusResponse;
import com.joel.tictactoe.adapters.inbound.rest.exception.CustomException;
import com.joel.tictactoe.adapters.inbound.rest.exception.ExceptionMessages;
import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.domain.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(
            GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Create a new game or join an existing one.
     * @return
     */
    @PostMapping("/create")
    public CreateGameResponse createGame() {
        Game game = gameService.startOrJoinGame();
        return new CreateGameResponse(game.getId());
    }

    /**
     * Get the status of a game by its ID.
     * @param gameId The ID of the game.
     * @return The status of the game.
     * @throws Exception if the game is not found.
     */
    @GetMapping("/status")
    public GameStatusResponse getGameStatus(@RequestParam(required = false) String gameId) throws Exception {
        if (gameId == null) {
            throw new CustomException(ExceptionMessages.GAME_ID_REQUIRED);
        }

        Optional<Game> game = gameService.getGame(gameId);

        if(game.isEmpty()){
            throw new CustomException(ExceptionMessages.GAME_NOT_FOUND);
        }else{
            Game g = game.get();
            return new GameStatusResponse(
                g.getStatus(),
                    g.getCurrentTurn(),
                    g.getWinner()
            );
        }
    }
}
