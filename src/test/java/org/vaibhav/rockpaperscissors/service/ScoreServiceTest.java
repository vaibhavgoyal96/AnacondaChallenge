package org.vaibhav.rockpaperscissors.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vaibhav.rockpaperscissors.dto.GameResponse;
import org.vaibhav.rockpaperscissors.enums.GameResult;
import org.vaibhav.rockpaperscissors.model.Player;
import org.vaibhav.rockpaperscissors.model.Score;
import org.vaibhav.rockpaperscissors.repository.PlayerRepository;
import org.vaibhav.rockpaperscissors.repository.ScoreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveScore_Player1Wins() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        // Mock the repository to return an empty optional when looking for existing scores
        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.empty());

        // Mock the repository to return the saved score object when save() is called
        when(scoreRepository.save(any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method under test
        Score savedScore = scoreService.saveScore(player1, player2, GameResult.PLAYER1);

        // Assert the results
        assertEquals(1, savedScore.getPlayer1Wins(), "Player 1 should have 1 win");
        assertEquals(0, savedScore.getPlayer2Wins(), "Player 2 should have 0 wins");
        assertEquals(0, savedScore.getTies(), "There should be no ties");

        // Verify that the save method was called only once
        verify(scoreRepository, times(1)).save(any(Score.class));
    }

    @Test
    void testSaveScore_Player2Wins() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.empty());

        // Mock the repository to return the saved score object when save() is called
        when(scoreRepository.save(any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Score savedScore = scoreService.saveScore(player1, player2, GameResult.PLAYER2);

        assertEquals(0, savedScore.getPlayer1Wins());
        assertEquals(1, savedScore.getPlayer2Wins());
        assertEquals(0, savedScore.getTies());

        verify(scoreRepository).save(any(Score.class));
    }

    @Test
    void testSaveScore_Tie() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.empty());

        // Mock the repository to return the saved score object when save() is called
        when(scoreRepository.save(any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Score savedScore = scoreService.saveScore(player1, player2, GameResult.TIE);

        assertEquals(0, savedScore.getPlayer1Wins());
        assertEquals(0, savedScore.getPlayer2Wins());
        assertEquals(1, savedScore.getTies());

        verify(scoreRepository).save(any(Score.class));
    }

    @Test
    void testGetPlayerScore_NoMatch() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        when(playerRepository.findByName("Vaibhav")).thenReturn(Optional.of(player1));
        when(playerRepository.findByName("Dibyo")).thenReturn(Optional.of(player2));
        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.empty());

        GameResponse.ScoreResult scoreResult = scoreService.getPlayerScore("Vaibhav", "Dibyo", true);

        assertEquals(0, scoreResult.getWins());
        assertEquals(0, scoreResult.getLosses());
        assertEquals(0, scoreResult.getTies());
    }
}
