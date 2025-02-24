package dev.davidvega.rolmanager.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbilityResponseDTO {
    private Integer abilityId;
    private String name;
    private String description;
    private String keyAbility;
}