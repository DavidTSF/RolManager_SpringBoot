package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.ChangeCharacterPlayerDTO;
import dev.davidvega.rolmanager.dto.requests.CharacterPlayerRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AllCharactersFromPlayerResponseDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterPlayerResponseDTO;
import dev.davidvega.rolmanager.mappers.CharacterPlayerMapper;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.models.CharacterPlayer;
import dev.davidvega.rolmanager.repositories.CharacterPlayerRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import dev.davidvega.rolmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterPlayerServiceImpl implements CharacterPlayerService {

    private final CharacterPlayerRepository characterPlayerRepository;
    private final CharacterPlayerMapper characterPlayerMapper;
    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    @Override
    public List<CharacterPlayerResponseDTO> getAllCharacterPlayers() {
        return characterPlayerRepository.findAll().stream()
                .map(characterPlayerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterPlayerResponseDTO getCharacterPlayerById(Long id) {
        return characterPlayerRepository.findById(id)
                .map(characterPlayerMapper::toDto)
                .orElseThrow(() -> new RuntimeException("CharacterPlayer not found"));
    }

    @Override
    public CharacterPlayerResponseDTO createCharacterPlayer(CharacterPlayerRequestDTO characterPlayerRequestDTO) {
        CharacterPlayer characterPlayer = characterPlayerMapper.toEntity(characterPlayerRequestDTO);

        if (characterPlayer.getCharacter().getId() == null) {
            Character savedCharacter = characterRepository.save(characterPlayer.getCharacter());
            characterPlayer.setCharacter(savedCharacter);
        }

        CharacterPlayer savedCharacterPlayer = characterPlayerRepository.save(characterPlayer);

        return characterPlayerMapper.toDto(savedCharacterPlayer);
    }

@Override
public CharacterPlayerResponseDTO updateCharacterPlayer(Long id, ChangeCharacterPlayerDTO changeCharacterPlayerDTO) {
    CharacterPlayer characterPlayer = characterPlayerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CharacterPlayer not found"));

    characterPlayer.setCharacter(characterRepository.findById(changeCharacterPlayerDTO.getCharacterId())
            .orElseThrow(() -> new RuntimeException("Character not found")));
    characterPlayer.setIdRolUser(userRepository.findById(changeCharacterPlayerDTO.getPlayerId())
            .orElseThrow(() -> new RuntimeException("User not found")));
    characterPlayer.setPlayerName(changeCharacterPlayerDTO.getPlayerName());



    return characterPlayerMapper.toDto(characterPlayerRepository.save(characterPlayer));
}

    @Override
    public boolean deleteCharacterPlayer(Long id) {
        if (characterPlayerRepository.existsById(id)) {
            characterPlayerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AllCharactersFromPlayerResponseDTO getAllCharactersFromPlayer(Long userId) {
        List<CharacterPlayer> characterPlayers = characterPlayerRepository.findByIdRolUser_Id(userId);
        if (characterPlayers.isEmpty()) {
            throw new RuntimeException("No characters found for the user");
        }

        CharacterPlayer firstCharacterPlayer = characterPlayers.get(0);
        AllCharactersFromPlayerResponseDTO responseDTO = new AllCharactersFromPlayerResponseDTO();
        responseDTO.setPlayerName(firstCharacterPlayer.getPlayerName());
        responseDTO.setCharacters(characterPlayers.stream().map(CharacterPlayer::getCharacter).collect(Collectors.toList()));
        responseDTO.setRolUser(characterPlayerMapper.toReducedUserDetailsDTO(firstCharacterPlayer.getIdRolUser()));

        return responseDTO;
    }

}