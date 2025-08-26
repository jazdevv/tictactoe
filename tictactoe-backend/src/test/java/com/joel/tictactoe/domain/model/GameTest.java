package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void testStartGame(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assert game.getStatus() == GameStatus.MATCHMAKING;

        // when
        game.start();

        // then
        assert game.getStatus() == GameStatus.IN_PROGRESS;
    }

    @Test
    void testSwitchTurn(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();
        assert game.getCurrentTurn().equals(GamePlayers.X);

        // when
        game.switchTurn();

        // then
        assert game.getCurrentTurn().equals(GamePlayers.O);

        // when
        game.switchTurn();

        // then
        assert game.getCurrentTurn().equals(GamePlayers.X);
    }

    @Test
    void testEndGame(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();
        assert(game.getStatus() == GameStatus.IN_PROGRESS);

        // when
        game.end(com.joel.tictactoe.domain.value.GameWinner.X);

        // then
        assert game.getStatus() == GameStatus.FINISHED;
        assert game.getWinner() == com.joel.tictactoe.domain.value.GameWinner.X;
    }
}
