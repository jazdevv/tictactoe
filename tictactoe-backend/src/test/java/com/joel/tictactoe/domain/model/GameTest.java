package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    void testStartGame(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assertEquals(GameStatus.MATCHMAKING, game.getStatus(), "Initial game status should be MATCHMAKING");

        // when
        game.start();

        // then
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus(), "Game status should be IN_PROGRESS after starting");
    }

    @Test
    void testSwitchTurn(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();
        assertEquals(GamePlayers.X, game.getCurrentTurn(), "First turn should be assigned to player X");

        // when
        game.switchTurn();

        // then
        assertEquals(GamePlayers.O, game.getCurrentTurn(), "Turn should switch to player O");

        // when
        game.switchTurn();

        // then
        assertEquals(GamePlayers.X, game.getCurrentTurn(), "Turn should switch back to player X");
    }

    @Test
    void testEndGame(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus(), "Game should be IN_PROGRESS before ending");

        // when
        game.end(GameWinner.X);

        // then
        assertEquals(GameStatus.FINISHED, game.getStatus(), "Game status should be FINISHED after ending");
        assertEquals(GameWinner.X, game.getWinner(), "Winner should be assigned correctly");
    }
}
