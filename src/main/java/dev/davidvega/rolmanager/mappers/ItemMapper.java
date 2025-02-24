package dev.davidvega.rolmanager.mappers;

import dev.davidvega.rolmanager.dto.requests.ItemRequestDTO;
import dev.davidvega.rolmanager.dto.responses.ItemsFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.ItemResponseDTO;
import dev.davidvega.rolmanager.models.CharacterItem;
import dev.davidvega.rolmanager.models.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemResponseDTO convertToResponseDTO(Item item) {
        ItemResponseDTO responseDTO = new ItemResponseDTO();
        responseDTO.setItemId(item.getId());
        responseDTO.setName(item.getName());
        responseDTO.setDescription(item.getDescription());
        responseDTO.setType(item.getType());
        responseDTO.setValue(item.getValue());
        responseDTO.setWeight(item.getWeight());
        return responseDTO;
    }

    public Item convertToEntity(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();
        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());
        item.setType(itemRequestDTO.getType());
        item.setValue(itemRequestDTO.getValue());
        item.setWeight(itemRequestDTO.getWeight());
        return item;
    }

    public ItemsFromCharacterDTO.CharacterItemDTO convertToCharacterItemDTO(CharacterItem characterItem) {
        ItemsFromCharacterDTO.CharacterItemDTO characterItemDTO = new ItemsFromCharacterDTO.CharacterItemDTO();
        characterItemDTO.setItem(characterItem.getItem());
        characterItemDTO.setQuantity(characterItem.getQuantity());
        return characterItemDTO;
    }


}