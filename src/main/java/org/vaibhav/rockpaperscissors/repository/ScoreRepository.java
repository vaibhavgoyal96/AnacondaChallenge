package org.vaibhav.rockpaperscissors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vaibhav.rockpaperscissors.model.Player;
import org.vaibhav.rockpaperscissors.model.Score;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    // Find a score record between two players
    @Query("SELECT s FROM Score s WHERE (s.player1 = :player1 AND s.player2 = :player2) OR (s.player1 = :player2 AND s.player2 = :player1)")
    Optional<Score> findByPlayer1AndPlayer2(@Param("player1") Player player1, @Param("player2") Player player2);

    // Find all matches for a player
    @Query("SELECT s FROM Score s WHERE s.player1 = :player OR s.player2 = :player")
    List<Score> findAllMatchesForPlayer(@Param("player") Player player);
}
