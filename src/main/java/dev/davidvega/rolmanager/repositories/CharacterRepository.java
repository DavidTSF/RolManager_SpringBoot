package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
