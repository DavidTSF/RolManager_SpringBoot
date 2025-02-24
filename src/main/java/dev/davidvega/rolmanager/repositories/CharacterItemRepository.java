package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.CharacterItem;
import dev.davidvega.rolmanager.models.CharacterItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterItemRepository  extends JpaRepository<CharacterItem, CharacterItemId> {

    List<CharacterItem> findById_CharacterId(Long characterId);
}
