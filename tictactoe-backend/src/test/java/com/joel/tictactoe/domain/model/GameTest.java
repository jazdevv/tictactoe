package com.joel.tictactoe.domain.model;

import com.joel.tictactoe.exception.ExceptionMessages;
import com.joel.tictactoe.domain.factory.GameFactory;
import com.joel.tictactoe.domain.value.BoardConfig;
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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> game.move(BoardConfig.BOARD_MIN_POSITION - 1, 1));
        assertEquals(ExceptionMessages.INVALID_MOVE_POSITION, exception.getMessage());
    }

    @Test
    void move_shouldThrowExceptionWhenPositionIsBiggerThanMax(){
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> game.move(BoardConfig.BOARD_MAX_POSITION + 1, 1));
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
    void addMovement_shouldEndGameWhenPlayerWinsRow() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        // X will win with the first row
        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(2, 1); // X
        game.move(2, 2); // O
        game.move(3, 1); // X wins

        // then
        assertEquals(GameStatus.FINISHED, game.getStatus());
        assertEquals(GameWinner.X, game.getWinner());
        assertFalse(game.isActive());
    }

    @Test
    void addMovement_shouldEndGameWhenPlayerWinsColumn() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        // O will win with the second column
        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(2, 1); // X
        game.move(2, 2); // O
        game.move(3, 3); // X
        game.move(3, 2); // O wins

        // then
        assertEquals(GameStatus.FINISHED, game.getStatus());
        assertEquals(GameWinner.O, game.getWinner());
    }

    @Test
    void addMovement_shouldEndGameWhenPlayerWinsDiagonal() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(2, 2); // X
        game.move(1, 3); // O
        game.move(3, 3); // X wins diagonally

        // then
        assertEquals(GameStatus.FINISHED, game.getStatus());
        assertEquals(GameWinner.X, game.getWinner());
    }

    @Test
    void addMovement_shouldEndGameAsDrawAfterNineMovesAutomatically() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        // Sequence of moves that fills the board without any player winning
        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(1, 3); // X
        game.move(2, 1); // O
        game.move(2, 3); // X
        game.move(2, 2); // O
        game.move(3, 1); // X
        game.move(3, 3); // O
        game.move(3, 2); // X last move -> should trigger draw

        // then
        assertEquals(GameStatus.FINISHED, game.getStatus(), "Game should be finished after 9 moves");
        assertEquals(GameWinner.DRAW, game.getWinner(), "Game should end as DRAW when board is full");
        assertFalse(game.isActive(), "Game should not be active after finishing");
    }

    @Test
    void addMovement_shouldEndGameAsDrawWhenBoardIsFull() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        // Fill the board without any winner
        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(1, 3); // X
        game.move(2, 1); // O
        game.move(2, 3); // X
        game.move(2, 2); // O
        game.move(3, 1); // X
        game.move(3, 3); // O
        game.move(3, 2); // X last move -> draw

        // then
        assertEquals(GameStatus.FINISHED, game.getStatus());
        assertEquals(GameWinner.DRAW, game.getWinner());
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

    @Test
    void checkPlayerWin_shouldReturnTrueForWinningRow() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when
        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(2, 1); // X
        game.move(2, 2); // O
        game.move(3, 1); // X

        // then
        assertTrue(game.checkPlayerWin(GamePlayers.X));
        assertFalse(game.checkPlayerWin(GamePlayers.O));
    }

    @Test
    void checkPlayerWin_shouldReturnTrueForWinningColumn() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when

        game.move(1, 1); // X
        game.move(2, 1); // O
        game.move(1, 2); // X
        game.move(2, 2); // O
        game.move(1, 3); // X

        // then
        assertTrue(game.checkPlayerWin(GamePlayers.X));
    }

    @Test
    void checkPlayerWin_shouldReturnTrueForWinningDiagonal() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when

        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(2, 2); // X
        game.move(1, 3); // O
        game.move(3, 3); // X

        // then
        assertTrue(game.checkPlayerWin(GamePlayers.X));
    }

    @Test
    void checkPlayerWin_shouldReturnTrueForWinningAntiDiagonal() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when

        game.move(1, 3); // X
        game.move(1, 1); // O
        game.move(2, 2); // X
        game.move(2, 1); // O
        game.move(3, 1); // X

        // then
        assertTrue(game.checkPlayerWin(GamePlayers.X));
    }

    @Test
    void checkPlayerWin_shouldReturnFalseWhenNoWinner() {
        // given
        Game game = GameFactory.createMatchmakingGame();
        game.start();

        // when

        game.move(1, 1); // X
        game.move(1, 2); // O
        game.move(2, 2); // X
        game.move(3, 2); // O
        game.move(2, 1); // X

        // then
        assertFalse(game.checkPlayerWin(GamePlayers.X));
        assertFalse(game.checkPlayerWin(GamePlayers.O));
    }



}
