package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.ChangeCharacterPlayerDTO;
import dev.davidvega.rolmanager.dto.requests.CharacterPlayerRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterPlayerResponseDTO;
import dev.davidvega.rolmanager.mappers.CharacterPlayerMapper;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.models.CharacterPlayer;
import dev.davidvega.rolmanager.models.User;
import dev.davidvega.rolmanager.repositories.CharacterPlayerRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import dev.davidvega.rolmanager.repositories.UserRepository;
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
public class CharacterPlayerServiceImplTest {

    @Mock
    private CharacterPlayerRepository characterPlayerRepository;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CharacterPlayerMapper characterPlayerMapper;

    @InjectMocks
    private CharacterPlayerServiceImpl characterPlayerService;

    private CharacterPlayer characterPlayer;
    private Character character;
    private User user;
    private CharacterPlayerRequestDTO requestDTO;
    private CharacterPlayerResponseDTO responseDTO;

    private ChangeCharacterPlayerDTO changeCharacterPlayerDTO;

    @BeforeEach
    void setUp() {

        character = new Character();
        character.setId(1);
        character.setName("Warrior");

        user = new User();
        user.setId(1L);
        user.setUsername("player1");
        user.setPassword("password");
        user.setRole( User.UserRole.PLAYER );

        characterPlayer = new CharacterPlayer();
        characterPlayer.setId(1);
        characterPlayer.setCharacter(character);
        characterPlayer.setPlayerName("player1");
        characterPlayer.setIdRolUser(user);

        requestDTO = new CharacterPlayerRequestDTO();
        requestDTO.setPlayerName("player1");
        requestDTO.setCharacter(character);
        requestDTO.setIdRolUser(user.getId());

        responseDTO = new CharacterPlayerResponseDTO();
        responseDTO.setId(1);
        responseDTO.setPlayerName("player1");
        responseDTO.setCharacter(character);
        responseDTO.setRolUser(null);

        changeCharacterPlayerDTO = new ChangeCharacterPlayerDTO();
        changeCharacterPlayerDTO.setPlayerName("player2");
        changeCharacterPlayerDTO.setCharacterId(1L);
        changeCharacterPlayerDTO.setPlayerId(1L);



    }

    @Test
    void testGetAllCharacterPlayers() {
        when(characterPlayerRepository.findAll()).thenReturn(List.of(characterPlayer));
        when(characterPlayerMapper.toDto(characterPlayer)).thenReturn(responseDTO);

        List<CharacterPlayerResponseDTO> result = characterPlayerService.getAllCharacterPlayers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("player1", result.get(0).getPlayerName());
        verify(characterPlayerRepository, times(1)).findAll();
    }

    @Test
    void testGetCharacterPlayerById() {
        when(characterPlayerRepository.findById(1L)).thenReturn(Optional.of(characterPlayer));
        when(characterPlayerMapper.toDto(characterPlayer)).thenReturn(responseDTO);

        CharacterPlayerResponseDTO result = characterPlayerService.getCharacterPlayerById(1L);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("player1", result.getPlayerName());
        verify(characterPlayerRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCharacterPlayer() {
        lenient().when(characterRepository.findById(Long.valueOf(character.getId()))).thenReturn(Optional.of(character));
        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(characterPlayerMapper.toEntity(requestDTO)).thenReturn(characterPlayer);
        when(characterPlayerMapper.toDto(characterPlayer)).thenReturn(responseDTO);

        when(characterPlayerRepository.save(any(CharacterPlayer.class))).thenReturn(characterPlayer);


        CharacterPlayerResponseDTO result = characterPlayerService.createCharacterPlayer(requestDTO);

        assertNotNull(result);
        assertEquals("player1", result.getPlayerName());
        verify(characterPlayerRepository, times(1)).save(any(CharacterPlayer.class));
    }

    @Test
    void testUpdateCharacterPlayer() {
        when(characterPlayerRepository.findById(1L)).thenReturn(Optional.of(characterPlayer));
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character)); // Asegúrate de que el Character sea encontrado
        when(characterPlayerRepository.save(any(CharacterPlayer.class))).thenReturn(characterPlayer);
        when(characterPlayerMapper.toDto(characterPlayer)).thenReturn(responseDTO);
        when(userRepository.findById(changeCharacterPlayerDTO.getPlayerId())).thenReturn(Optional.of(user));


        CharacterPlayerResponseDTO result = characterPlayerService.updateCharacterPlayer(
                Long.valueOf(characterPlayer.getCharacter().getId()),
                changeCharacterPlayerDTO
        );

        assertNotNull(result);
        assertEquals("player1", result.getPlayerName());
        verify(characterPlayerRepository, times(1)).findById(1L);
        verify(characterRepository, times(1)).findById(1L); // Verifica que el Character sea buscado
        verify(characterPlayerRepository, times(1)).save(any(CharacterPlayer.class));
    }

    @Test
    void testDeleteCharacterPlayer() {
        when(characterPlayerRepository.existsById(1L)).thenReturn(true);

        boolean result = characterPlayerService.deleteCharacterPlayer(1L);

        assertTrue(result);
        verify(characterPlayerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCharacterPlayer_NotFound() {
        when(characterPlayerRepository.existsById(1L)).thenReturn(false);

        boolean result = characterPlayerService.deleteCharacterPlayer(1L);

        assertFalse(result);
        verify(characterPlayerRepository, never()).deleteById(1L);
    }
}
