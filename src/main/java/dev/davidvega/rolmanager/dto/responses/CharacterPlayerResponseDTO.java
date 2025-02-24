package dev.davidvega.rolmanager.dto.responses;

import dev.davidvega.rolmanager.dto.responses.user.ReducedUserDetailsDTO;
import dev.davidvega.rolmanager.models.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterPlayerResponseDTO {
    private Integer id;
    private String playerName;
    private Character character;
    private ReducedUserDetailsDTO rolUser;


}
