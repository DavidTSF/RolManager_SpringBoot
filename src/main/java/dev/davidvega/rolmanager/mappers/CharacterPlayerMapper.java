package dev.davidvega.rolmanager.mappers;

import dev.davidvega.rolmanager.dto.requests.CharacterPlayerRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterPlayerResponseDTO;
import dev.davidvega.rolmanager.dto.responses.user.ReducedUserDetailsDTO;
import dev.davidvega.rolmanager.models.CharacterPlayer;
import dev.davidvega.rolmanager.models.User;
import dev.davidvega.rolmanager.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterPlayerMapper {

    private final UserRepository userRepository;

    public CharacterPlayer toEntity(CharacterPlayerRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        CharacterPlayer characterPlayer = new CharacterPlayer();
        characterPlayer.setPlayerName(dto.getPlayerName());
        characterPlayer.setCharacter(dto.getCharacter());
        characterPlayer.setIdRolUser(
                userRepository.findById(dto.getIdRolUser())
                        .orElseThrow( () -> new RuntimeException("User not found"))
        );

        return characterPlayer;
    }

    public CharacterPlayerResponseDTO toDto(CharacterPlayer entity) {
        if (entity == null) {
            return null;
        }

        CharacterPlayerResponseDTO dto = new CharacterPlayerResponseDTO();
        dto.setId(entity.getId());
        dto.setPlayerName(entity.getPlayerName());
        dto.setCharacter(entity.getCharacter());

        ReducedUserDetailsDTO reducedUserDetailsDTO = new ReducedUserDetailsDTO();
        reducedUserDetailsDTO.setIdRolUser(entity.getId());
        reducedUserDetailsDTO.setUsername(entity.getIdRolUser().getUsername());

        dto.setRolUser(reducedUserDetailsDTO);

        return dto;
    }

    public ReducedUserDetailsDTO toReducedUserDetailsDTO(@NotNull User idRolUser) {
        ReducedUserDetailsDTO reducedUserDetailsDTO = new ReducedUserDetailsDTO();
        reducedUserDetailsDTO.setIdRolUser(Math.toIntExact(idRolUser.getId()));
        reducedUserDetailsDTO.setUsername(idRolUser.getUsername());
        return reducedUserDetailsDTO;
    }
}