package org.vaibhav.rockpaperscissors.enums;

public enum Move {
    rock, paper, scissors;

    public static Move fromString(String move) {
        switch (move.toLowerCase()) {
            case "rock":
                return rock;
            case "paper":
                return paper;
            case "scissors":
                return scissors;
            default:
                throw new IllegalArgumentException("Invalid move: " + move);
        }
    }
}
