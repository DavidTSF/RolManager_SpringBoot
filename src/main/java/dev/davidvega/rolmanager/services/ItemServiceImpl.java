package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.ItemRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterItemResponseDTO;
import dev.davidvega.rolmanager.dto.responses.ItemResponseDTO;
import dev.davidvega.rolmanager.dto.responses.ItemsFromCharacterDTO;
import dev.davidvega.rolmanager.mappers.ItemMapper;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.models.CharacterItem;
import dev.davidvega.rolmanager.models.CharacterItemId;
import dev.davidvega.rolmanager.models.Item;
import dev.davidvega.rolmanager.repositories.CharacterItemRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import dev.davidvega.rolmanager.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final CharacterRepository characterRepository;
    private final CharacterItemRepository characterItemRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemResponseDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(itemMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDTO getItemById(Long id) {
        return itemRepository.findById(id).map(itemMapper::convertToResponseDTO).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO) {
        Item item = itemMapper.convertToEntity(itemRequestDTO);
        Item savedItem = itemRepository.save(item);
        return itemMapper.convertToResponseDTO(savedItem);
    }

    @Override
    public ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO) {
        return itemRepository.findById(id).map(item -> {
            item.setName(itemRequestDTO.getName());
            item.setDescription(itemRequestDTO.getDescription());
            item.setType(itemRequestDTO.getType());
            item.setValue(itemRequestDTO.getValue());
            item.setWeight(itemRequestDTO.getWeight());
            Item updatedItem = itemRepository.save(item);
            return itemMapper.convertToResponseDTO(updatedItem);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public boolean deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public CharacterItemResponseDTO addItemToCharacter(Long characterId, Long itemId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        CharacterItem characterItem = new CharacterItem();
        characterItem.setCharacter(character); // Asignar el character
        characterItem.setItem(item); // Asignar el item
        characterItem.setQuantity(1);
        characterItem.setEquipped(false);

        CharacterItemId characterItemId = new CharacterItemId();
        characterItemId.setCharacterId(Math.toIntExact(characterId));
        characterItemId.setItemId(Math.toIntExact(itemId));
        characterItem.setId(characterItemId);

        characterItemRepository.save(characterItem);

        CharacterItemResponseDTO characterItemResponseDTO = new CharacterItemResponseDTO();
        characterItemResponseDTO.setCharacter(character);
        characterItemResponseDTO.setItem(item);
        characterItemResponseDTO.setEquipped(false);
        characterItemResponseDTO.setQuantity(1);

        return characterItemResponseDTO;
    }

    @Override
    public ItemResponseDTO updateItemQuantity(Long characterId, Long itemId, int quantity) {
        CharacterItem characterItem = characterItemRepository.findById(new CharacterItemId(Math.toIntExact(characterId) , Math.toIntExact(itemId) ))
                .orElseThrow(() -> new RuntimeException("CharacterItem not found"));

        characterItem.setQuantity(quantity);
        characterItemRepository.save(characterItem);

        return itemRepository.findById(itemId)
                .map(itemMapper::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public boolean deleteItemFromCharacter(Long characterId, Long itemId) {
        CharacterItem characterItem = characterItemRepository.findById(new CharacterItemId(Math.toIntExact(characterId) , Math.toIntExact(itemId) ))
                .orElseThrow(() -> new RuntimeException("CharacterItem not found"));

        characterItemRepository.delete(characterItem);
        return true;
    }

    @Override
    public ItemsFromCharacterDTO getItemsFromCharacter(Long characterId) {
        characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        List<CharacterItem> characterItems = characterItemRepository.findById_CharacterId(characterId);
        ItemsFromCharacterDTO itemsFromCharacterDTO = new ItemsFromCharacterDTO();
        itemsFromCharacterDTO.setCharacterId(Math.toIntExact(characterId));
        itemsFromCharacterDTO.setItems(
                characterItems.stream()
                        .map(itemMapper::convertToCharacterItemDTO)
                        .collect(Collectors.toList())
        );


        return itemsFromCharacterDTO;
    }



}