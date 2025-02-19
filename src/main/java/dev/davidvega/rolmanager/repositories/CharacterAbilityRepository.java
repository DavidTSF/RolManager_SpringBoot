package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.CharacterAbility;
import dev.davidvega.rolmanager.models.CharacterAbilityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterAbilityRepository extends JpaRepository<CharacterAbility, CharacterAbilityId> {
}
