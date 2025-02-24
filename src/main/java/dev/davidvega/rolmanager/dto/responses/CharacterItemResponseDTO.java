package dev.davidvega.rolmanager.dto.responses;

import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.models.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterItemResponseDTO {
    Character character;
    Item item;
    int quantity;
    boolean equipped;
}
