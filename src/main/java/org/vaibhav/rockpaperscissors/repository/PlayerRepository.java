package org.vaibhav.rockpaperscissors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaibhav.rockpaperscissors.model.Player;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
}
