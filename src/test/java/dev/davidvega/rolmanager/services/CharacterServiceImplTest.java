package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.CharacterRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterResponseDTO;
import dev.davidvega.rolmanager.mappers.CharacterMapper;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceImplTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private CharacterMapper characterMapper;

    @InjectMocks
    private CharacterServiceImpl characterService;

    private Character character;
    private CharacterRequestDTO characterRequestDTO;
    private CharacterResponseDTO characterResponseDTO;

    @BeforeEach
    void setUp() {
        character = new Character();
        character.setId(1);
        character.setName("Warrior");
        character.setRace("Human");

        characterRequestDTO = new CharacterRequestDTO();
        characterRequestDTO.setName("Warrior");
        characterRequestDTO.setRace("Human");

        characterResponseDTO = new CharacterResponseDTO();
        characterResponseDTO.setId(1);
        characterResponseDTO.setName("Warrior");
        characterResponseDTO.setRace("Human");
    }

    @Test
    void testGetAllCharacters() {
        when(characterRepository.findAll()).thenReturn(List.of(character));
        when(characterMapper.toCharacterResponseDTO(character)).thenReturn(characterResponseDTO);

        List<CharacterResponseDTO> result = characterService.getAllCharacters();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Warrior", result.get(0).getName());
        verify(characterRepository, times(1)).findAll();
    }

    @Test
    void testGetCharacterById() {
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(characterMapper.toCharacterResponseDTO(character)).thenReturn(characterResponseDTO);

        CharacterResponseDTO result = characterService.getCharacterById(1L);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Warrior", result.getName());
        verify(characterRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCharacterById_NotFound() {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            characterService.getCharacterById(1L);
        });

        assertEquals("Character not found", exception.getMessage());
        verify(characterRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCharacter() {
        when(characterMapper.convertToEntity(characterRequestDTO)).thenReturn(character);
        doReturn(character).when(characterRepository).save(any(Character.class));
        when(characterMapper.toCharacterResponseDTO(character)).thenReturn(characterResponseDTO);


        CharacterResponseDTO result = characterService.createCharacter(characterRequestDTO);

        assertNotNull(result);
        assertEquals("Warrior", result.getName());
        verify(characterRepository, times(1)).save(any(Character.class));
        verify(characterMapper, times(1)).toCharacterResponseDTO(character);
    }

    @Test
    void testUpdateCharacter() {
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(characterRepository.save(any(Character.class))).thenReturn(character);
        when(characterMapper.toCharacterResponseDTO(character)).thenReturn(characterResponseDTO);

        CharacterResponseDTO result = characterService.updateCharacter(1L, characterRequestDTO);

        assertNotNull(result);
        assertEquals("Warrior", result.getName());
        verify(characterRepository, times(1)).findById(1L);
        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void testUpdateCharacter_NotFound() {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            characterService.updateCharacter(1L, characterRequestDTO);
        });

        assertEquals("Character not found", exception.getMessage());
        verify(characterRepository, times(1)).findById(1L);
        verify(characterRepository, never()).save(any(Character.class));
    }

    @Test
    void testDeleteCharacter() {
        when(characterRepository.existsById(1L)).thenReturn(true);

        boolean result = characterService.deleteCharacter(1L);

        assertTrue(result);
        verify(characterRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCharacter_NotFound() {
        when(characterRepository.existsById(1L)).thenReturn(false);

        boolean result = characterService.deleteCharacter(1L);

        assertFalse(result);
        verify(characterRepository, never()).deleteById(1L);
    }
}