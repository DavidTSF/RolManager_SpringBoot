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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private CharacterItemRepository characterItemRepository;


    private Item item;
    private ItemRequestDTO itemRequestDTO;
    private ItemResponseDTO itemResponseDTO;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1);
        item.setName("Sword");
        item.setDescription("A sharp blade");
        item.setValue(100);

        itemRequestDTO = new ItemRequestDTO();
        itemRequestDTO.setName("Sword");
        itemRequestDTO.setDescription("A sharp blade");
        itemRequestDTO.setValue(100);

        itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setItemId(1);
        itemResponseDTO.setName("Sword");
        itemResponseDTO.setDescription("A sharp blade");
        itemResponseDTO.setValue(100);
    }


    @Test
    void testGetAllItems() {
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Shield");
        item2.setDescription("A sturdy shield");
        item2.setValue(150);

        when(itemRepository.findAll()).thenReturn(Arrays.asList(item, item2));
        when(itemMapper.convertToResponseDTO(item)).thenReturn(itemResponseDTO);

        ItemResponseDTO itemResponseDTO2 = new ItemResponseDTO();
        itemResponseDTO2.setItemId(2);
        itemResponseDTO2.setName("Shield");
        itemResponseDTO2.setDescription("A sturdy shield");
        itemResponseDTO2.setValue(150);

        when(itemMapper.convertToResponseDTO(item2)).thenReturn(itemResponseDTO2);

        List<ItemResponseDTO> result = itemService.getAllItems();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Sword", result.get(0).getName());
        assertEquals("Shield", result.get(1).getName());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testGetItemById_ThrowsException_WhenItemNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemService.getItemById(1L));

        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateItem() {
        when(itemMapper.convertToEntity(itemRequestDTO)).thenReturn(item);
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.convertToResponseDTO(item)).thenReturn(itemResponseDTO);

        ItemResponseDTO result = itemService.createItem(itemRequestDTO);

        assertNotNull(result);
        assertEquals("Sword", result.getName());
        assertEquals("A sharp blade", result.getDescription());
        assertEquals(100, result.getValue());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testGetItemById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemMapper.convertToResponseDTO(item)).thenReturn(itemResponseDTO);

        ItemResponseDTO result = itemService.getItemById(1L);

        assertNotNull(result);
        assertEquals(1, result.getItemId());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateItem() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        item.setName("Updated Sword");
        itemRequestDTO.setName("Updated Sword");

        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> {
            Item updatedItem = invocation.getArgument(0);
            updatedItem.setId(item.getId());
            return updatedItem;
        });

        when(itemMapper.convertToResponseDTO(any(Item.class))).thenAnswer(invocation -> {
            Item updatedItem = invocation.getArgument(0);
            ItemResponseDTO responseDTO = new ItemResponseDTO();
            responseDTO.setItemId(updatedItem.getId());
            responseDTO.setName(updatedItem.getName());
            responseDTO.setDescription(updatedItem.getDescription());
            responseDTO.setValue(updatedItem.getValue());
            return responseDTO;
        });

        ItemResponseDTO result = itemService.updateItem(1L, itemRequestDTO);

        assertNotNull(result);
        assertEquals(1, result.getItemId());
        assertEquals("Updated Sword", result.getName());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testDeleteItem() {
        when(itemRepository.existsById(1L)).thenReturn(true);

        itemService.deleteItem(1L);

        verify(itemRepository, times(1)).existsById(1L);
        verify(itemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddItemToCharacter() {
        Long characterId = 1L;
        Long itemId = 1L;
        Character character = new Character();
        character.setId(Math.toIntExact(characterId));
        Item item = new Item();
        item.setId(Math.toIntExact(itemId));

        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        CharacterItemResponseDTO result = itemService.addItemToCharacter(characterId, itemId);

        assertThat(result).isNotNull();
        assertThat(result.getCharacter()).isEqualTo(character);
        assertThat(result.getItem()).isEqualTo(item);
        assertThat(result.getQuantity()).isEqualTo(1);
        assertThat(result.isEquipped()).isFalse();

        ArgumentCaptor<CharacterItem> characterItemCaptor = ArgumentCaptor.forClass(CharacterItem.class);
        verify(characterItemRepository).save(characterItemCaptor.capture());
        CharacterItem savedCharacterItem = characterItemCaptor.getValue();
        assertThat(savedCharacterItem.getCharacter()).isEqualTo(character);
        assertThat(savedCharacterItem.getItem()).isEqualTo(item);
    }

    @Test
    void testUpdateItemQuantity() {
        Long characterId = 1L;
        Long itemId = 1L;
        int newQuantity = 5;
        CharacterItem characterItem = new CharacterItem();
        characterItem.setId(new CharacterItemId(Math.toIntExact(characterId), Math.toIntExact(itemId)));
        characterItem.setQuantity(1);

        Item item = new Item();
        item.setId(Math.toIntExact(itemId));
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setItemId(item.getId());

        when(characterItemRepository.findById(any())).thenReturn(Optional.of(characterItem));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(itemMapper.convertToResponseDTO(item)).thenReturn(itemResponseDTO);

        ItemResponseDTO result = itemService.updateItemQuantity(characterId, itemId, newQuantity);

        assertThat(result).isNotNull();
        assertThat(result.getItemId()).isEqualTo(item.getId());
        assertThat(characterItem.getQuantity()).isEqualTo(newQuantity);
        verify(characterItemRepository).save(characterItem);
    }

    @Test
    void testDeleteItemFromCharacter() {
        Long characterId = 1L;
        Long itemId = 1L;
        CharacterItem characterItem = new CharacterItem();
        characterItem.setId(new CharacterItemId(Math.toIntExact(characterId), Math.toIntExact(itemId)));

        when(characterItemRepository.findById(any())).thenReturn(Optional.of(characterItem));

        boolean result = itemService.deleteItemFromCharacter(characterId, itemId);

        assertThat(result).isTrue();
        verify(characterItemRepository).delete(characterItem);
    }

    @Test
    void testGetItemsFromCharacter() {
        Long characterId = 1L;
        Character character = new Character();
        character.setId(Math.toIntExact(characterId));

        CharacterItem characterItem1 = new CharacterItem();
        characterItem1.setCharacter(character);
        characterItem1.setItem(new Item());
        CharacterItem characterItem2 = new CharacterItem();
        characterItem2.setCharacter(character);
        characterItem2.setItem(new Item());

        List<CharacterItem> characterItems = List.of(characterItem1, characterItem2);

        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character));
        when(characterItemRepository.findById_CharacterId(characterId)).thenReturn(characterItems);
        when(itemMapper.convertToCharacterItemDTO(any())).thenAnswer(invocation -> new ItemsFromCharacterDTO.CharacterItemDTO());

        ItemsFromCharacterDTO result = itemService.getItemsFromCharacter(characterId);

        assertThat(result).isNotNull();
        assertThat(result.getCharacterId()).isEqualTo(characterId.intValue());
        assertThat(result.getItems()).hasSize(2);

        verify(characterRepository).findById(characterId);
        verify(characterItemRepository).findById_CharacterId(characterId);
    }

}