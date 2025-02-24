package dev.davidvega.rolmanager.mappers;

import dev.davidvega.rolmanager.dto.requests.CharacterRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterResponseDTO;
import dev.davidvega.rolmanager.models.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterMapper {
    public CharacterResponseDTO toCharacterResponseDTO(Character character) {
        CharacterResponseDTO characterResponseDTO = new CharacterResponseDTO();
        characterResponseDTO.setId(Math.toIntExact(Long.valueOf(character.getId())));
        characterResponseDTO.setName(character.getName());
        characterResponseDTO.setRace(character.getRace());
        characterResponseDTO.setClassField(character.getClassField());
        characterResponseDTO.setAlignment(character.getAlignment());
        characterResponseDTO.setBiography(character.getBiography());
        characterResponseDTO.setBackstory(character.getBackstory());
        characterResponseDTO.setImageUrl(character.getImageUrl());
        characterResponseDTO.setHitPoints(character.getHitPoints());
        characterResponseDTO.setMaxHitPoints(character.getMaxHitPoints());
        characterResponseDTO.setArmorClass(character.getArmorClass());
        characterResponseDTO.setInitiativeBonus(character.getInitiativeBonus());
        characterResponseDTO.setStrength(character.getStrength());
        characterResponseDTO.setDexterity(character.getDexterity());
        characterResponseDTO.setConstitution(character.getConstitution());
        characterResponseDTO.setIntelligence(character.getIntelligence());
        characterResponseDTO.setWisdom(character.getWisdom());
        characterResponseDTO.setCharisma(character.getCharisma());
        characterResponseDTO.setWealth(character.getWealth());
        characterResponseDTO.setIsAlive(character.getIsAlive());
        characterResponseDTO.setDeity(character.getDeity());
        return characterResponseDTO;
    }

    public Character convertToEntity(CharacterRequestDTO characterRequestDTO) {
        Character character = new Character();
        character.setName(characterRequestDTO.getName());
        character.setRace(characterRequestDTO.getRace());
        character.setClassField(characterRequestDTO.getClassField());
        character.setAlignment(characterRequestDTO.getAlignment());
        character.setBiography(characterRequestDTO.getBiography());
        character.setBackstory(characterRequestDTO.getBackstory());
        character.setImageUrl(characterRequestDTO.getImageUrl());
        character.setHitPoints(characterRequestDTO.getHitPoints());
        character.setMaxHitPoints(characterRequestDTO.getMaxHitPoints());
        character.setArmorClass(characterRequestDTO.getArmorClass());
        character.setInitiativeBonus(characterRequestDTO.getInitiativeBonus());
        character.setStrength(characterRequestDTO.getStrength());
        character.setDexterity(characterRequestDTO.getDexterity());
        character.setConstitution(characterRequestDTO.getConstitution());
        character.setIntelligence(characterRequestDTO.getIntelligence());
        character.setWisdom(characterRequestDTO.getWisdom());
        character.setCharisma(characterRequestDTO.getCharisma());
        character.setWealth(characterRequestDTO.getWealth());
        character.setIsAlive(characterRequestDTO.getIsAlive());
        character.setDeity(characterRequestDTO.getDeity());
        return character;
    }

}
