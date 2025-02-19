package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.CharacterPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterPlayerRepository extends JpaRepository<CharacterPlayer, Long> {
}
