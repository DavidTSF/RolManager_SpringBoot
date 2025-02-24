package dev.davidvega.rolmanager.dto.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbilitiesFromCharacterDTO {
    List<AbilityWithLevelDTO> abilities;
    Integer characterId;

    @Data
    public static class AbilityWithLevelDTO {
        AbilityResponseDTO ability;
        int level;

    }

}
