package dev.davidvega.rolmanager.dto.requests;

import dev.davidvega.rolmanager.models.Character;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterPlayerRequestDTO {
    @NotNull(message = "El nombre del jugador no puede ser nulo")
    private String playerName;
    @NotNull(message = "El personaje no puede ser nulo")
    private Character character;
    @NotNull(message = "El id del usuaro no puede ser nulo")
    private Long idRolUser;

}
