package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.AbilityRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AbilitiesFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.AbilityResponseDTO;
import dev.davidvega.rolmanager.mappers.AbilityMapper;
import dev.davidvega.rolmanager.models.Ability;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.models.CharacterAbility;
import dev.davidvega.rolmanager.models.CharacterAbilityId;
import dev.davidvega.rolmanager.repositories.AbilityRepository;
import dev.davidvega.rolmanager.repositories.CharacterAbilityRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbilityServiceImpl implements AbilityService {


    private final AbilityRepository abilityRepository;
    private final CharacterRepository characterRepository;
    private final AbilityMapper abilityMapper;
    private final CharacterAbilityRepository characterAbilityRepository;

    @Override
    public List<AbilityResponseDTO> getAllAbilities() {
        return abilityRepository.findAll().stream()
                .map(abilityMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AbilityResponseDTO getAbilityById(Long id) {
        return abilityRepository.findById(id).map(abilityMapper::convertToResponseDTO).get();
    }

    @Override
    public AbilityResponseDTO createAbility(AbilityRequestDTO abilityRequestDTO) {
        Ability ability = abilityMapper.convertToEntity(abilityRequestDTO);
        Ability savedAbility = abilityRepository.save(ability);
        return abilityMapper.convertToResponseDTO(savedAbility);
    }

    @Override
    public AbilityResponseDTO updateAbility(Long id, AbilityRequestDTO abilityRequestDTO) {
        return abilityRepository.findById(id).map(ability -> {
            ability.setName(abilityRequestDTO.getName());
            ability.setDescription(abilityRequestDTO.getDescription());
            ability.setKeyAbility(abilityRequestDTO.getKeyAbility());
            Ability updatedAbility = abilityRepository.save(ability);
            return abilityMapper.convertToResponseDTO(updatedAbility);
        }).get();
    }

    @Override
    public AbilitiesFromCharacterDTO getAbilitiesFromCharacter(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        List<AbilitiesFromCharacterDTO.AbilityWithLevelDTO> abilities = characterAbilityRepository.findByCharacterId(characterId)
                .stream()
                .map(abilityMapper::convertToAbilityWithLevel)
                .collect(Collectors.toList());

        AbilitiesFromCharacterDTO dto = new AbilitiesFromCharacterDTO();
        dto.setCharacterId(character.getId());

        dto.setAbilities(abilities);
        return dto;
    }
    @Override
    public boolean deleteAbility(Long id) {
        if (abilityRepository.existsById(id)) {
            abilityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AbilityResponseDTO addAbilityToCharacter(Long characterId, Long abilityId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Ability ability = abilityRepository.findById(abilityId)
                .orElseThrow(() -> new RuntimeException("Ability not found"));

        CharacterAbility characterAbility = new CharacterAbility();
        characterAbility.setCharacter(character);
        characterAbility.setAbility(ability);
        characterAbility.setLevel(0);

        CharacterAbilityId characterAbilityId = new CharacterAbilityId();
        characterAbilityId.setCharacterId(Math.toIntExact(characterId));
        characterAbilityId.setAbilityId(Math.toIntExact(abilityId));
        characterAbility.setId(characterAbilityId);

        characterAbilityRepository.save(characterAbility);

        return abilityMapper.convertToResponseDTO(ability);
    }

    @Override
    public AbilitiesFromCharacterDTO.AbilityWithLevelDTO updateAbilityLevel(Long characterId, Long abilityId, int level) {

        CharacterAbility characterAbility = characterAbilityRepository.findById(new CharacterAbilityId(Math.toIntExact(abilityId), Math.toIntExact(characterId)))
                .orElseThrow(() -> new RuntimeException("CharacterAbility not found"));

        characterAbility.setLevel(level);
        characterAbilityRepository.save(characterAbility);

        AbilitiesFromCharacterDTO.AbilityWithLevelDTO abilityWithLevelDTO = new AbilitiesFromCharacterDTO.AbilityWithLevelDTO();

        abilityWithLevelDTO.setAbility( abilityMapper.convertToResponseDTO(characterAbility.getAbility()) );
        abilityWithLevelDTO.setLevel(characterAbility.getLevel());

        return abilityWithLevelDTO;
    }

    @Override
    public boolean deleteAbilityFromCharacter(Long characterId, Long abilityId) {
        CharacterAbilityId characterAbilityId = new CharacterAbilityId(Math.toIntExact(abilityId), Math.toIntExact(characterId));
        if (characterAbilityRepository.existsById(characterAbilityId)) {
            characterAbilityRepository.deleteById(characterAbilityId);
            return true;
        } else {
            return false;
        }
    }


}