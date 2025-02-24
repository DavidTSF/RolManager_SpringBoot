package dev.davidvega.rolmanager.dto.responses;

import dev.davidvega.rolmanager.dto.responses.user.ReducedUserDetailsDTO;
import dev.davidvega.rolmanager.models.Character;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllCharactersFromPlayerResponseDTO {
    private String playerName;
    private List<Character> characters;
    private ReducedUserDetailsDTO rolUser;



}
