package org.vaibhav.rockpaperscissors.service;

import org.springframework.stereotype.Service;
import org.vaibhav.rockpaperscissors.dto.GameResponse;
import org.vaibhav.rockpaperscissors.enums.GameResult;
import org.vaibhav.rockpaperscissors.model.Player;
import org.vaibhav.rockpaperscissors.model.Score;
import org.vaibhav.rockpaperscissors.repository.PlayerRepository;
import org.vaibhav.rockpaperscissors.repository.ScoreRepository;

import java.util.*;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final PlayerRepository playerRepository;

    public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Save the score between two players. Track wins, losses, or ties.
     */
    public Score saveScore(Player player1, Player player2, GameResult result) {
        Optional<Score> optionalScore = scoreRepository.findByPlayer1AndPlayer2(player1, player2);

        Score score;
        // If no match exists between these two players, create a new score record
        score = optionalScore.orElseGet(() -> new Score(player1, player2));

        // Update score based on the result
        switch (result) {
            case TIE:
                score.incrementTies();
                break;
            case PLAYER1:
                score.incrementPlayer1Wins();
                break;
            case PLAYER2:
                score.incrementPlayer2Wins();
                break;
        }

        // Save the updated score
        return scoreRepository.save(score);
    }

    /**
     * Get the score details (wins, losses, ties) for a player against a specific opponent.
     */
    public GameResponse.ScoreResult getPlayerScore(String playerName, String opponentName, boolean isPlayer1) {
        Player player1 = playerRepository.findByName(playerName)
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerName));
        Player player2 = playerRepository.findByName(opponentName)
                .orElseThrow(() -> new IllegalArgumentException("Opponent not found: " + opponentName));

        Optional<Score> scoreOpt = scoreRepository.findByPlayer1AndPlayer2(player1, player2);
        if (scoreOpt.isPresent()) {
            Score score = scoreOpt.get();
            return isPlayer1
                    ? new GameResponse.ScoreResult(score.getPlayer1Wins(), score.getPlayer2Wins(), score.getTies())
                    : new GameResponse.ScoreResult(score.getPlayer2Wins(), score.getPlayer1Wins(), score.getTies());
        }

        return new GameResponse.ScoreResult(0, 0, 0);  // Return empty score if no match exists
    }
    /**
     * Get the match summary for a given player. It shows the player's performance against each opponent.
     */
    public Map<String, String> getPlayerMatchSummary(String playerName) {
        Optional<Player> playerOpt = playerRepository.findByName(playerName);
        if (playerOpt.isEmpty()) {
            throw new RuntimeException("Player not found: " + playerName);
        }

        Player player = playerOpt.get();
        List<Score> matches = scoreRepository.findAllMatchesForPlayer(player);

        Map<String, String> matchSummary = new HashMap<>();

        for (Score match : matches) {
            Player opponent;
            int wins, losses, ties;

            // Determine whether the current player is player1 or player2
            if (match.getPlayer1().equals(player)) {
                opponent = match.getPlayer2();
                wins = match.getPlayer1Wins();
                losses = match.getPlayer2Wins();
                ties = match.getTies();
            } else {
                opponent = match.getPlayer1();
                wins = match.getPlayer2Wins();
                losses = match.getPlayer1Wins();
                ties = match.getTies();
            }

            String result = String.format("%d wins, %d losses, %d ties", wins, losses, ties);
            matchSummary.put(opponent.getName(), result);
        }

        return matchSummary;
    }

    /**
     * Get a map of all scores for every player, which can serve as a leaderboard.
     */
    public Map<String, Integer> getAllScores() {
        List<Score> scores = scoreRepository.findAll();
        Map<String, Integer> playerScores = new HashMap<>();

        for (Score score : scores) {
            // Update Player 1's total wins
            playerScores.put(score.getPlayer1().getName(),
                    playerScores.getOrDefault(score.getPlayer1().getName(), 0) + score.getPlayer1Wins());

            // Update Player 2's total wins
            playerScores.put(score.getPlayer2().getName(),
                    playerScores.getOrDefault(score.getPlayer2().getName(), 0) + score.getPlayer2Wins());
        }

        return playerScores;
    }

    /**
     * Internal helper method to find or create a Player by name.
     */
    public Player findOrCreatePlayer(String playerName) {
        return playerRepository.findByName(playerName)
                .orElseGet(() -> playerRepository.save(new Player(playerName)));
    }
}
