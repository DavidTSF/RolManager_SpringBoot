package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.CharacterRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterResponseDTO;
import dev.davidvega.rolmanager.mappers.CharacterMapper;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public List<CharacterResponseDTO> getAllCharacters() {
        return characterRepository.findAll().stream()
                .map(characterMapper::toCharacterResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterResponseDTO getCharacterById(Long id) {
        return characterRepository.findById(id)
                .map(characterMapper::toCharacterResponseDTO)
                .orElseThrow(() -> new RuntimeException("Character not found"));
    }

    @Override
    public CharacterResponseDTO createCharacter(CharacterRequestDTO characterRequestDTO) {
        Character character = characterMapper.convertToEntity(characterRequestDTO);
        Character savedCharacter = characterRepository.save(character);
        return characterMapper.toCharacterResponseDTO(savedCharacter);
    }

    @Override
    public CharacterResponseDTO updateCharacter(Long id, CharacterRequestDTO characterRequestDTO) {
        return characterRepository.findById(id).map(character -> {
            if (characterRequestDTO.getName() != null) {
                character.setName(characterRequestDTO.getName());
            }
            if (characterRequestDTO.getRace() != null) {
                character.setRace(characterRequestDTO.getRace());
            }
            if (characterRequestDTO.getClassField() != null) {
                character.setClassField(characterRequestDTO.getClassField());
            }
            if (characterRequestDTO.getAlignment() != null) {
                character.setAlignment(characterRequestDTO.getAlignment());
            }
            if (characterRequestDTO.getBiography() != null) {
                character.setBiography(characterRequestDTO.getBiography());
            }
            if (characterRequestDTO.getBackstory() != null) {
                character.setBackstory(characterRequestDTO.getBackstory());
            }
            if (characterRequestDTO.getImageUrl() != null) {
                character.setImageUrl(characterRequestDTO.getImageUrl());
            }
            if (characterRequestDTO.getHitPoints() != null) {
                character.setHitPoints(characterRequestDTO.getHitPoints());
            }
            if (characterRequestDTO.getMaxHitPoints() != null) {
                character.setMaxHitPoints(characterRequestDTO.getMaxHitPoints());
            }
            if (characterRequestDTO.getArmorClass() != null) {
                character.setArmorClass(characterRequestDTO.getArmorClass());
            }
            if (characterRequestDTO.getInitiativeBonus() != null) {
                character.setInitiativeBonus(characterRequestDTO.getInitiativeBonus());
            }
            if (characterRequestDTO.getStrength() != null) {
                character.setStrength(characterRequestDTO.getStrength());
            }
            if (characterRequestDTO.getDexterity() != null) {
                character.setDexterity(characterRequestDTO.getDexterity());
            }
            if (characterRequestDTO.getConstitution() != null) {
                character.setConstitution(characterRequestDTO.getConstitution());
            }
            if (characterRequestDTO.getIntelligence() != null) {
                character.setIntelligence(characterRequestDTO.getIntelligence());
            }
            if (characterRequestDTO.getWisdom() != null) {
                character.setWisdom(characterRequestDTO.getWisdom());
            }
            if (characterRequestDTO.getCharisma() != null) {
                character.setCharisma(characterRequestDTO.getCharisma());
            }
            if (characterRequestDTO.getWealth() != null) {
                character.setWealth(characterRequestDTO.getWealth());
            }
            if (characterRequestDTO.getIsAlive() != null) {
                character.setIsAlive(characterRequestDTO.getIsAlive());
            }
            if (characterRequestDTO.getDeity() != null) {
                character.setDeity(characterRequestDTO.getDeity());
            }
            Character updatedCharacter = characterRepository.save(character);
            return characterMapper.toCharacterResponseDTO(updatedCharacter);
        }).orElseThrow(() -> new RuntimeException("Character not found"));
    }

    @Override
    public boolean deleteCharacter(Long id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}