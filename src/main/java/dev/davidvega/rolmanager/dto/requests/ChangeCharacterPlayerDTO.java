package dev.davidvega.rolmanager.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeCharacterPlayerDTO {
    Long characterId;
    Long playerId;
    String playerName;
}
