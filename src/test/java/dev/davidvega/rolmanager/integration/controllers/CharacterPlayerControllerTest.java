package dev.davidvega.rolmanager.integration.controllers;

import dev.davidvega.rolmanager.dto.requests.CharacterPlayerRequestDTO;
import dev.davidvega.rolmanager.dto.requests.ChangeCharacterPlayerDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterPlayerResponseDTO;
import dev.davidvega.rolmanager.dto.responses.user.ReducedUserDetailsDTO;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.services.CharacterPlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterPlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CharacterPlayerService characterPlayerService;

    private CharacterPlayerResponseDTO responseDTO;
    private CharacterPlayerRequestDTO requestDTO;
    private Character character;
    private ReducedUserDetailsDTO userDetails;

    @BeforeEach
    void setUp() {
        // Simulación de Character
        character = new Character();
        character.setId(1);
        character.setName("Warrior");

        // Simulación de ReducedUserDetailsDTO
        userDetails = new ReducedUserDetailsDTO();
        userDetails.setIdRolUser(1);
        userDetails.setUsername("player1");

        // Datos de respuesta
        responseDTO = new CharacterPlayerResponseDTO();
        responseDTO.setId(1);
        responseDTO.setPlayerName("player1");
        responseDTO.setCharacter(character);
        responseDTO.setRolUser(userDetails);

        // Datos de petición
        requestDTO = new CharacterPlayerRequestDTO();
        requestDTO.setPlayerName("player1");
        requestDTO.setCharacter(character);
        requestDTO.setIdRolUser(1L);
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetAllCharacterPlayers() throws Exception {
        List<CharacterPlayerResponseDTO> responseList = new ArrayList<>();
        responseList.add(responseDTO);

        when(characterPlayerService.getAllCharacterPlayers()).thenReturn(responseList);

        mockMvc.perform(get("/character-player"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].playerName").value("player1"))
                .andExpect(jsonPath("$[0].character.id").value(1))
                .andExpect(jsonPath("$[0].character.name").value("Warrior"))
                .andExpect(jsonPath("$[0].rolUser.idRolUser").value(1))
                .andExpect(jsonPath("$[0].rolUser.username").value("player1"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetCharacterPlayerById() throws Exception {
        Long id = 1L;
        when(characterPlayerService.getCharacterPlayerById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/character-player/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.playerName").value("player1"))
                .andExpect(jsonPath("$.character.id").value(1))
                .andExpect(jsonPath("$.rolUser.username").value("player1"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetCharacterPlayerById_NotFound() throws Exception {
        Long id = 99L;
        when(characterPlayerService.getCharacterPlayerById(id)).thenReturn(null);

        mockMvc.perform(get("/character-player/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testCreateCharacterPlayer() throws Exception {
        when(characterPlayerService.createCharacterPlayer(any(CharacterPlayerRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/character-player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.playerName").value("player1"))
                .andExpect(jsonPath("$.character.name").value("Warrior"))
                .andExpect(jsonPath("$.rolUser.username").value("player1"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testCreateCharacterPlayer_BadRequest() throws Exception {
        requestDTO.setPlayerName(null); // Simula un campo obligatorio faltante

        mockMvc.perform(post("/character-player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testUpdateCharacterPlayer() throws Exception {
        Long id = 1L;
        ChangeCharacterPlayerDTO updateDTO = new ChangeCharacterPlayerDTO();
        updateDTO.setPlayerName("player2");

        when(characterPlayerService.updateCharacterPlayer(eq(id), any(ChangeCharacterPlayerDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(patch("/character-player/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerName").value("player1"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteCharacterPlayer() throws Exception {
        Long id = 1L;
        when(characterPlayerService.deleteCharacterPlayer(id)).thenReturn(true);

        mockMvc.perform(delete("/character-player/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteCharacterPlayer_NotFound() throws Exception {
        Long id = 99L;
        when(characterPlayerService.deleteCharacterPlayer(id)).thenReturn(false);

        mockMvc.perform(delete("/character-player/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/character-player"))
                .andExpect(status().isForbidden());
    }
}
