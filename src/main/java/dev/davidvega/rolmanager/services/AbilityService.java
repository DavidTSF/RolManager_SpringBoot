package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.AbilityRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AbilitiesFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.AbilityResponseDTO;

import java.util.List;

public interface AbilityService {
    List<AbilityResponseDTO> getAllAbilities();
    AbilityResponseDTO getAbilityById(Long id);
    AbilityResponseDTO createAbility(AbilityRequestDTO abilityRequestDTO);
    AbilityResponseDTO updateAbility(Long id, AbilityRequestDTO abilityRequestDTO);

    AbilitiesFromCharacterDTO getAbilitiesFromCharacter(Long characterId);

    boolean deleteAbility(Long id);

    AbilityResponseDTO addAbilityToCharacter(Long characterId, Long abilityId);

    AbilitiesFromCharacterDTO.AbilityWithLevelDTO updateAbilityLevel(Long characterId, Long abilityId, int level);
    boolean deleteAbilityFromCharacter(Long characterId, Long abilityId);

}