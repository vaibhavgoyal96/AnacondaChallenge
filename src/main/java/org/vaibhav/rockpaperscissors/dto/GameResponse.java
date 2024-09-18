package org.vaibhav.rockpaperscissors.dto;

public class GameResponse {
    private String player1Name;
    private String player2Name;
    private String player1Move;  // Add player1Move
    private String player2Move;  // Add player2Move
    private ScoreResult player1Score;
    private ScoreResult player2Score;

    public GameResponse(String player1Name, String player2Name, String player1Move, String player2Move, ScoreResult player1Score, ScoreResult player2Score) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Move = player1Move;
        this.player2Move = player2Move;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    // Getters and setters

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer1Move() {
        return player1Move;
    }

    public void setPlayer1Move(String player1Move) {
        this.player1Move = player1Move;
    }

    public String getPlayer2Move() {
        return player2Move;
    }

    public void setPlayer2Move(String player2Move) {
        this.player2Move = player2Move;
    }

    public ScoreResult getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(ScoreResult player1Score) {
        this.player1Score = player1Score;
    }

    public ScoreResult getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(ScoreResult player2Score) {
        this.player2Score = player2Score;
    }

    public static class ScoreResult {
        private int wins;
        private int losses;
        private int ties;

        public ScoreResult(int wins, int losses, int ties) {
            this.wins = wins;
            this.losses = losses;
            this.ties = ties;
        }

        // Getters and setters
        public int getWins() {
            return wins;
        }

        public void setWins(int wins) {
            this.wins = wins;
        }

        public int getLosses() {
            return losses;
        }

        public void setLosses(int losses) {
            this.losses = losses;
        }

        public int getTies() {
            return ties;
        }

        public void setTies(int ties) {
            this.ties = ties;
        }
    }
}
