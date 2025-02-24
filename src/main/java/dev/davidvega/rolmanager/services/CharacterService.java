package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.CharacterRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterResponseDTO;

import java.util.List;

public interface CharacterService {
    List<CharacterResponseDTO> getAllCharacters();

    CharacterResponseDTO getCharacterById(Long id);

    CharacterResponseDTO createCharacter(CharacterRequestDTO characterRequestDTO);

    CharacterResponseDTO updateCharacter(Long id, CharacterRequestDTO characterRequestDTO);

    boolean deleteCharacter(Long id);
}
