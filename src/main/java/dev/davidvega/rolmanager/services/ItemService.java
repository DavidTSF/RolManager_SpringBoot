package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.ItemRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterItemResponseDTO;
import dev.davidvega.rolmanager.dto.responses.ItemsFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.ItemResponseDTO;

import java.util.List;

public interface ItemService {
    List<ItemResponseDTO> getAllItems();
    ItemResponseDTO getItemById(Long id);
    ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO);
    ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO);
    boolean deleteItem(Long id);

    CharacterItemResponseDTO addItemToCharacter(Long characterId, Long itemId);

    ItemResponseDTO updateItemQuantity(Long characterId, Long itemId, int quantity);

    boolean deleteItemFromCharacter(Long characterId, Long itemId);

    ItemsFromCharacterDTO getItemsFromCharacter(Long characterId);

}