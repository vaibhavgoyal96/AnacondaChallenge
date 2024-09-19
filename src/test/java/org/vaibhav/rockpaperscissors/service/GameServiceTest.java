package org.vaibhav.rockpaperscissors.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vaibhav.rockpaperscissors.enums.GameResult;
import org.vaibhav.rockpaperscissors.enums.Move;
import org.vaibhav.rockpaperscissors.model.Player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private ScoreService scoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDetermineWinner_Player1Wins() {
        String player1Name = "Vaibhav";
        String player2Name = "Computer";
        Move player1Move = Move.rock;
        Move player2Move = Move.scissors;

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        when(scoreService.findOrCreatePlayer(player1Name)).thenReturn(player1);
        when(scoreService.findOrCreatePlayer(player2Name)).thenReturn(player2);

        GameResult result = gameService.determineWinner(player1Move, player2Move, player1Name, player2Name);

        assertEquals(GameResult.PLAYER1, result);
        verify(scoreService).saveScore(player1, player2, GameResult.PLAYER1);
    }

    @Test
    void testDetermineWinner_Player2Wins() {
        String player1Name = "Vaibhav";
        String player2Name = "Computer";
        Move player1Move = Move.scissors;
        Move player2Move = Move.rock;

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        when(scoreService.findOrCreatePlayer(player1Name)).thenReturn(player1);
        when(scoreService.findOrCreatePlayer(player2Name)).thenReturn(player2);

        GameResult result = gameService.determineWinner(player1Move, player2Move, player1Name, player2Name);

        assertEquals(GameResult.PLAYER2, result);
        verify(scoreService).saveScore(player1, player2, GameResult.PLAYER2);
    }

    @Test
    void testDetermineWinner_Tie() {
        String player1Name = "Vaibhav";
        String player2Name = "Computer";
        Move player1Move = Move.rock;
        Move player2Move = Move.rock;

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        when(scoreService.findOrCreatePlayer(player1Name)).thenReturn(player1);
        when(scoreService.findOrCreatePlayer(player2Name)).thenReturn(player2);

        GameResult result = gameService.determineWinner(player1Move, player2Move, player1Name, player2Name);

        assertEquals(GameResult.TIE, result);
        verify(scoreService).saveScore(player1, player2, GameResult.TIE);
    }

    @Test
    void testGetRandomMove() {
        Move move = gameService.getRandomMove();
        assertTrue(move == Move.rock || move == Move.paper || move == Move.scissors);
    }
}
