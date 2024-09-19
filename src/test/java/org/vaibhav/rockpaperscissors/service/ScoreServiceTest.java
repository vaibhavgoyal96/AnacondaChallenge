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
import java.util.Map;
import java.util.List;

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
    void testSaveScore_Player1Wins_NewMatch() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.empty());
        when(scoreRepository.save(any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Score savedScore = scoreService.saveScore(player1, player2, GameResult.PLAYER1);

        assertEquals(1, savedScore.getPlayer1Wins());
        assertEquals(0, savedScore.getPlayer2Wins());
        assertEquals(0, savedScore.getTies());

        verify(scoreRepository).save(any(Score.class));
    }

    @Test
    void testSaveScore_Player2Wins_ExistingMatch() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        Score existingScore = new Score(player1, player2);
        existingScore.incrementPlayer1Wins();

        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.of(existingScore));
        when(scoreRepository.save(any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Score updatedScore = scoreService.saveScore(player1, player2, GameResult.PLAYER2);

        assertEquals(1, updatedScore.getPlayer1Wins());
        assertEquals(1, updatedScore.getPlayer2Wins());
        assertEquals(0, updatedScore.getTies());

        verify(scoreRepository).save(any(Score.class));
    }

    @Test
    void testSaveScore_Tie() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.empty());
        when(scoreRepository.save(any(Score.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Score savedScore = scoreService.saveScore(player1, player2, GameResult.TIE);

        assertEquals(0, savedScore.getPlayer1Wins());
        assertEquals(0, savedScore.getPlayer2Wins());
        assertEquals(1, savedScore.getTies());

        verify(scoreRepository).save(any(Score.class));
    }

    @Test
    void testGetPlayerScore_NonExistentPlayer() {
        when(playerRepository.findByName("Vaibhav")).thenReturn(Optional.empty());

        // Expecting an IllegalArgumentException since the player doesn't exist
        assertThrows(IllegalArgumentException.class, () -> {
            scoreService.getPlayerScore("Vaibhav", "Dibyo", true);
        });
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

    @Test
    void testGetPlayerScore_ExistingMatch_Player1Perspective() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        Score score = new Score(player1, player2);
        score.incrementPlayer1Wins();
        score.incrementPlayer1Wins();
        score.incrementPlayer2Wins();

        when(playerRepository.findByName("Vaibhav")).thenReturn(Optional.of(player1));
        when(playerRepository.findByName("Dibyo")).thenReturn(Optional.of(player2));
        when(scoreRepository.findByPlayer1AndPlayer2(player1, player2)).thenReturn(Optional.of(score));

        GameResponse.ScoreResult scoreResult = scoreService.getPlayerScore("Vaibhav", "Dibyo", true);

        assertEquals(2, scoreResult.getWins());
        assertEquals(1, scoreResult.getLosses());
        assertEquals(0, scoreResult.getTies());
    }

    @Test
    void testGetAllScores() {
        Player player1 = new Player("Vaibhav");
        Player player2 = new Player("Dibyo");

        Score score1 = new Score(player1, player2);
        score1.incrementPlayer1Wins();
        score1.incrementPlayer1Wins();
        score1.incrementPlayer1Wins();
        score1.incrementPlayer2Wins();

        when(scoreRepository.findAll()).thenReturn(List.of(score1));

        Map<String, Integer> allScores = scoreService.getAllScores();

        assertEquals(3, allScores.get("Vaibhav"));
        assertEquals(1, allScores.get("Dibyo"));
    }

    @Test
    void testFindOrCreatePlayer_ExistingPlayer() {
        String playerName = "Vaibhav";
        Player existingPlayer = new Player(playerName);
        when(playerRepository.findByName(playerName)).thenReturn(Optional.of(existingPlayer));

        Player result = scoreService.findOrCreatePlayer(playerName);

        assertEquals(existingPlayer, result);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void testFindOrCreatePlayer_NewPlayer() {
        String playerName = "Vaibhav";
        when(playerRepository.findByName(playerName)).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Player result = scoreService.findOrCreatePlayer(playerName);

        assertEquals(playerName, result.getName());
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    void testGetPlayerMatchSummary() {
        Player vaibhav = new Player("Vaibhav");
        Player dibyo = new Player("Dibyo");
        Player rahul = new Player("Rahul");

        Score score1 = new Score(vaibhav, dibyo);
        score1.incrementPlayer1Wins();
        score1.incrementPlayer1Wins();
        score1.incrementPlayer2Wins();

        Score score2 = new Score(vaibhav, rahul);
        score2.incrementPlayer1Wins();
        score2.incrementPlayer2Wins();
        score2.incrementPlayer2Wins();
        score2.incrementTies();

        when(playerRepository.findByName("Vaibhav")).thenReturn(Optional.of(vaibhav));
        when(scoreRepository.findAllMatchesForPlayer(vaibhav)).thenReturn(List.of(score1, score2));

        Map<String, String> matchSummary = scoreService.getPlayerMatchSummary("Vaibhav");

        assertTrue(matchSummary.containsKey("Dibyo"));
        assertEquals("2 wins, 1 losses, 0 ties", matchSummary.get("Dibyo"));

        assertTrue(matchSummary.containsKey("Rahul"));
        assertEquals("1 wins, 2 losses, 1 ties", matchSummary.get("Rahul"));
    }

}