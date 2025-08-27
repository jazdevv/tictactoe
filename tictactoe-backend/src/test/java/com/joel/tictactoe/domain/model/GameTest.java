package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.exception.ExceptionMessages;
import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.value.BoardPositions;
import com.joel.tictactoe.domain.value.GamePlayers;
import com.joel.tictactoe.domain.value.GameStatus;
import com.joel.tictactoe.domain.value.GameWinner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void start_setsStatusToInProgress(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assertEquals(GameStatus.MATCHMAKING, game.getStatus(), "Initial game status should be MATCHMAKING");

        // when
        game.start();

        // then
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus(), "Game status should be IN_PROGRESS after starting");
    }

    @Test
    void switchTurn_alternatesBetweenPlayersXAndO(){
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
    void end_setsStatusToFinishedAndAssignsWinner(){
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

    @Test
    void move_makeAPlayerMovementAndSwitchTurn(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assertEquals(GameStatus.MATCHMAKING, game.getStatus(), "Game should be in MATCHMAKING status");

        // when
        game.start();
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus(), "Game should be in IN_PROGRESS status after starting");
        assertEquals(GamePlayers.X, game.getCurrentTurn(), "First turn should be assigned to player X");

        game.move(1, 1);

        // then
        assertEquals(1, game.getMovements().size(), "There should be one movement recorded");
        Movement movement = game.getMovements().get(0);
        assertEquals(1, movement.getX(), "Movement X coordinate should be recorded correctly");
        assertEquals(1, movement.getY(), "Movement Y coordinate should be recorded correctly");
        assertEquals(GamePlayers.O, game.getCurrentTurn(), "Turn should switch to player O after the move");
    }

    @Test
    void move_shouldThrowExceptionWhenGameNotInProgress(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assertEquals(GameStatus.MATCHMAKING, game.getStatus(), "Game should be in MATCHMAKING status");

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> game.move(1, 1));
        assertEquals(ExceptionMessages.MOVE_NOT_ALLOWED_WHEN_GAME_NOT_IN_PROGRESS, exception.getMessage());
    }

    @Test
    void move_shouldThrowExceptionWhenPositionIsSmallerThanMin(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

       // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> game.move(BoardPositions.BOARD_MIN_POSITION - 1, 1));
        assertEquals(ExceptionMessages.INVALID_MOVE_POSITION, exception.getMessage());
    }

    @Test
    void move_shouldThrowExceptionWhenPositionIsBiggerThanMax(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> game.move(BoardPositions.BOARD_MAX_POSITION + 1, 1));
        assertEquals(ExceptionMessages.INVALID_MOVE_POSITION, exception.getMessage());
    }

    @Test
    void addMovement_shouldThrowExceptionWhenPositionAlreadyTaken(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();
        game.move(1, 1);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> game.move(1, 1));
        assertEquals(ExceptionMessages.POSITION_ALREADY_TAKEN, exception.getMessage());
    }

    @Test
    void addMovement_shouldAddMovement(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();
        assertEquals(GamePlayers.X, game.getCurrentTurn(), "First turn should be assigned to player X");

        // when
        game.move(1, 1);

        // then
        assertEquals(1, game.getMovements().size(), "There should be one movement recorded");
        Movement movement = game.getMovements().get(0);
        assertEquals(1, movement.getX(), "Movement X coordinate should be recorded correctly");
        assertEquals(1, movement.getY(), "Movement Y coordinate should be recorded correctly");
    }

    @Test
    void hasMovementAt_shouldReturnTrueIfMovementAtThatPosition(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        game.move(1, 1);

        // then
        assertTrue(game.hasMovementAt(1, 1), "There should be a movement at position (1,1)");
        assertFalse(game.hasMovementAt(2, 2), "There should not be a movement at position (2,2)");
    }

    @Test
    void isActive_shouldReturnTrueWhenGameInProgress(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assertFalse(game.isActive(), "Game should not be active in MATCHMAKING status");

        // when
        game.start();

        // then
        assertTrue(game.isActive(), "Game should be active in IN_PROGRESS status");

        // when
        game.end(GameWinner.X);

        // then
        assertFalse(game.isActive(), "Game should not be active in FINISHED status");
    }

    @Test
    void isActive_shouldReturnFalseWhenGameNotInProgress(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        assertFalse(game.isActive(), "Game should not be active in MATCHMAKING status");

        // when
        game.end(GameWinner.X);

        // then
        assertFalse(game.isActive(), "Game should not be active in FINISHED status");
    }
}
