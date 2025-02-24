package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityRepository  extends JpaRepository<Ability, Long> {
}
