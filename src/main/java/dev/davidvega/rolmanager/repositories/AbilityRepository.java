package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository  extends JpaRepository<Ability, Long> {
}
