package com.joel.tictactoe.adapters.inbound.rest;

import com.joel.tictactoe.application.service.GameService;
import com.joel.tictactoe.domain.model.Game;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(
            GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/create")
    public Game createGame() throws Exception {
        return gameService.startOrJoinGame();
    }
}
