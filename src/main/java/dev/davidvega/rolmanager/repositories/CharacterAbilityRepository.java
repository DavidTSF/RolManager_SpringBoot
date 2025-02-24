package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.CharacterAbility;
import dev.davidvega.rolmanager.models.CharacterAbilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterAbilityRepository extends JpaRepository<CharacterAbility, CharacterAbilityId> {
    List<CharacterAbility> findByCharacterId(Long characterId);

}
