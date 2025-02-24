package dev.davidvega.rolmanager.mappers;

import dev.davidvega.rolmanager.dto.requests.AbilityRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AbilitiesFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.AbilityResponseDTO;
import dev.davidvega.rolmanager.models.Ability;
import dev.davidvega.rolmanager.models.CharacterAbility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AbilityMapper {
    public AbilityResponseDTO convertToResponseDTO(Ability ability) {
        AbilityResponseDTO responseDTO = new AbilityResponseDTO();
        responseDTO.setAbilityId(ability.getId());
        responseDTO.setName(ability.getName());
        responseDTO.setDescription(ability.getDescription());
        responseDTO.setKeyAbility(ability.getKeyAbility());
        return responseDTO;
    }

    public AbilitiesFromCharacterDTO.AbilityWithLevelDTO convertToAbilityWithLevel(CharacterAbility characterAbility ) {
        AbilitiesFromCharacterDTO.AbilityWithLevelDTO dto = new AbilitiesFromCharacterDTO.AbilityWithLevelDTO();
        dto.setAbility( convertToResponseDTO( characterAbility.getAbility() ));
        dto.setLevel(characterAbility.getLevel());
        return dto;
    }

    public Ability convertToEntity(AbilityRequestDTO abilityRequestDTO) {
        Ability ability = new Ability();
        ability.setName(abilityRequestDTO.getName());
        ability.setDescription(abilityRequestDTO.getDescription());
        ability.setKeyAbility(abilityRequestDTO.getKeyAbility());
        return ability;
    }

}
