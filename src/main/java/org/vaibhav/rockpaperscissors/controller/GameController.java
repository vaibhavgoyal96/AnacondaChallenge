package org.vaibhav.rockpaperscissors.controller;

import org.springframework.web.bind.annotation.*;
import org.vaibhav.rockpaperscissors.constants.ApiPaths;
import org.vaibhav.rockpaperscissors.dto.GameResponse;
import org.vaibhav.rockpaperscissors.enums.GameResult;
import org.vaibhav.rockpaperscissors.enums.Move;
import org.vaibhav.rockpaperscissors.service.GameService;
import org.vaibhav.rockpaperscissors.service.ScoreService;

import java.util.Map;

@RestController
@RequestMapping(ApiPaths.GAME_BASE)
public class GameController {

    private final GameService gameService;
    private final ScoreService scoreService;

    public GameController(GameService gameService, ScoreService scoreService) {
        this.gameService = gameService;
        this.scoreService = scoreService;
    }

    /**
     * Play a game between two players or between a player and the computer.
     * If player2Name is not provided, "Computer" plays with a randomly generated move.
     */
    @GetMapping(ApiPaths.PLAY_GAME)
    public GameResponse playGame(@RequestParam String player1Move,
                                 @RequestParam(required = false) String player2Move,
                                 @RequestParam String player1Name,
                                 @RequestParam(required = false) String player2Name) {

        // If player2Name is not provided, default it to "Computer"
        if (player2Name == null || player2Name.isEmpty()) {
            player2Name = "Computer";
            player2Move = gameService.getRandomMove().name();  // Generate random move for the computer
        }

        // Convert string moves to enum for consistency and type safety
        Move player1MoveEnum = Move.fromString(player1Move);
        Move player2MoveEnum = Move.fromString(player2Move);

        // Determine the result
        gameService.determineWinner(player1MoveEnum, player2MoveEnum, player1Name, player2Name);

        // Get the updated score for both players
        GameResponse.ScoreResult player1Score = scoreService.getPlayerScore(player1Name, player2Name, true);
        GameResponse.ScoreResult player2Score = scoreService.getPlayerScore(player2Name, player1Name, false);

        // Return the response encapsulated in a GameResponse DTO with moves included
        return new GameResponse(player1Name, player2Name, player1Move, player2Move, player1Score, player2Score);
    }


    /**
     * Get the match summary for a specific player, showing their performance against all opponents.
     */
    @GetMapping(ApiPaths.PLAYER_MATCHES)
    public Map<String, String> getPlayerMatchSummary(@PathVariable String playerName) {
        return scoreService.getPlayerMatchSummary(playerName);
    }

    /**
     * Get a list of all players' scores, which can be used as a leaderboard.
     */
    @GetMapping(ApiPaths.SCORES)
    public Map<String, Integer> getAllScores() {
        return scoreService.getAllScores();
    }
}
