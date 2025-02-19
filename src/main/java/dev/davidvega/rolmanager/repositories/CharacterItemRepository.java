package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.CharacterAbility;
import dev.davidvega.rolmanager.models.CharacterAbilityId;
import dev.davidvega.rolmanager.models.CharacterItem;
import dev.davidvega.rolmanager.models.CharacterItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterItemRepository  extends JpaRepository<CharacterItem, CharacterItemId> {
}
