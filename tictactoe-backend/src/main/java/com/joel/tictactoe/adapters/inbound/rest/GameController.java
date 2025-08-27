package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.adapters.inbound.rest.dto.*;
import com.joel.tictactoe.application.usecase.GetGameStatusUseCase;
import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.application.usecase.MakeMoveUseCase;
import com.joel.tictactoe.domain.model.Game;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameService gameService;
    private final MakeMoveUseCase makeMoveUseCase;
    private final GetGameStatusUseCase getGameStatusUseCase;

    public GameController(
            GameService gameService,
            MakeMoveUseCase makeMoveUseCase,
            GetGameStatusUseCase getGameStatusUseCase)
    {
        this.gameService = gameService;
        this.makeMoveUseCase = makeMoveUseCase;
        this.getGameStatusUseCase = getGameStatusUseCase;
    }

    /**
     * Create a new game or join an existing one.
     *
     * @return A response entity containing the created game's ID.
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
     */
    @GetMapping("/status")
    public ResponseEntity<GameStatusResponse> getGameStatus(@RequestParam(required = false) String gameId) {
        GameStatusResponse response = getGameStatusUseCase.execute(gameId);

        return ResponseEntity.ok(response);
    }

    /**
     * Make a move in the game.
     * @param request The move request containing game ID, player ID, and square coordinates.
     * @return A response entity with no content.
     */
    @PostMapping("/move")
    public ResponseEntity<Void> makeMove(@Valid @RequestBody MakeMovementRequest request) {
        makeMoveUseCase.execute(request);

        return ResponseEntity.noContent().build();
    }
}
