package dev.davidvega.rolmanager.dto.responses;


import dev.davidvega.rolmanager.models.Item;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsFromCharacterDTO {
    Integer characterId;
    List<CharacterItemDTO> items;

    @Data
    public static class CharacterItemDTO {
        Item item;
        int quantity;
    }

}
