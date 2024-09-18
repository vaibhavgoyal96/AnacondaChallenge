package org.vaibhav.rockpaperscissors.model;

import jakarta.persistence.*;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Player player2;

    private int player1Wins;
    private int player2Wins;
    private int ties; // New field to store the number of ties

    // Constructors, Getters, and Setters
    public Score() {}

    public Score(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Wins = 0;
        this.player2Wins = 0;
        this.ties = 0; // Initialize ties to zero
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    public int getTies() {
        return ties;
    }

    public void incrementPlayer1Wins() {
        this.player1Wins++;
    }

    public void incrementPlayer2Wins() {
        this.player2Wins++;
    }

    public void incrementTies() {
        this.ties++;
    }
}
