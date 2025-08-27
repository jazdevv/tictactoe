package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.adapters.inbound.rest.dto.*;
import com.joel.tictactoe.adapters.inbound.rest.exception.CustomException;
import com.joel.tictactoe.adapters.inbound.rest.exception.ExceptionMessages;
import com.joel.tictactoe.adapters.outbound.persistence.mapper.GameMapper;
import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.application.usecase.MakeMoveUseCase;
import com.joel.tictactoe.domain.model.Game;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class GameController {

    private final GameService gameService;
    private final MakeMoveUseCase makeMoveUseCase;

    public GameController(
            GameService gameService,
            MakeMoveUseCase makeMoveUseCase)
    {
        this.gameService = gameService;
        this.makeMoveUseCase = makeMoveUseCase;
    }

    /**
     * Create a new game or join an existing one.
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<CreateGameResponse> createGame() {
        Game game = gameService.startOrJoinGame();
        return ResponseEntity.ok(new CreateGameResponse(game.getId()));
    }

    /**
     * Get the status of a game by its ID.
     * @param gameId The ID of the game.
     * @return The status of the game.
     * @throws Exception if the game is not found.
     */
    @GetMapping("/status")
    public ResponseEntity<GameStatusResponse> getGameStatus(@RequestParam(required = false) String gameId) throws Exception {
        if (gameId == null) {
            throw new CustomException(ExceptionMessages.GAME_ID_REQUIRED);
        }

        Optional<Game> game = gameService.getGame(gameId);

        if(game.isEmpty()){
            throw new CustomException(ExceptionMessages.GAME_NOT_FOUND);
        }else{
            Game gameInstance = game.get();

            return ResponseEntity.ok(GameMapper.toGameStatusResponse(gameInstance));
        }
    }

    @PostMapping("/move")
    public ResponseEntity<Void> makeMove(@Valid @RequestBody MakeMovementRequest request) throws Exception {
        this.makeMoveUseCase.execute(request);

        return ResponseEntity.noContent().build();
    }
}
