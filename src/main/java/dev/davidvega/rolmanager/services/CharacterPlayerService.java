package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.ChangeCharacterPlayerDTO;
import dev.davidvega.rolmanager.dto.requests.CharacterPlayerRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AllCharactersFromPlayerResponseDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterPlayerResponseDTO;

import java.util.List;

public interface CharacterPlayerService {
    List<CharacterPlayerResponseDTO> getAllCharacterPlayers();
    CharacterPlayerResponseDTO getCharacterPlayerById(Long id);
    CharacterPlayerResponseDTO createCharacterPlayer(CharacterPlayerRequestDTO characterPlayerRequestDTO);
    CharacterPlayerResponseDTO updateCharacterPlayer(Long id, ChangeCharacterPlayerDTO changeCharacterPlayerDTO);
    boolean deleteCharacterPlayer(Long id);

    AllCharactersFromPlayerResponseDTO getAllCharactersFromPlayer(Long userId);

}
