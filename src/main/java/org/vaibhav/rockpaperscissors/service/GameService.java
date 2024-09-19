package org.vaibhav.rockpaperscissors.service;

import org.springframework.stereotype.Service;
import org.vaibhav.rockpaperscissors.enums.GameResult;
import org.vaibhav.rockpaperscissors.enums.Move;
import org.vaibhav.rockpaperscissors.model.Player;

import java.util.Random;

@Service
public class GameService {


    private final ScoreService scoreService;
    private static final Move[] MOVES = Move.values();

    public GameService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * Randomly move generator function for a computer move.
     */
    public Move getRandomMove() {
        Random random = new Random();
        return MOVES[random.nextInt(MOVES.length)];
    }

    /**
     * Determine the winner between two players and record the result.
     * Returns the result as an enum (PLAYER1, PLAYER2, or TIE).
     */
    public GameResult determineWinner(Move player1Move, Move player2Move, String player1Name, String player2Name) {

        Player player1 = scoreService.findOrCreatePlayer(player1Name);
        Player player2 = scoreService.findOrCreatePlayer(player2Name);

        if (player1Move == player2Move) {
            scoreService.saveScore(player1, player2, GameResult.TIE);
            return GameResult.TIE;
        }

        boolean isPlayer1Winner = (player1Move == Move.rock && player2Move == Move.scissors) ||
                (player1Move == Move.scissors && player2Move == Move.paper) ||
                (player1Move == Move.paper && player2Move == Move.scissors);

        GameResult result = isPlayer1Winner ? GameResult.PLAYER1 : GameResult.PLAYER2;
        scoreService.saveScore(player1, player2, result);

        return result;
    }
}
