package dev.davidvega.rolmanager.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davidvega.rolmanager.dto.requests.CharacterRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterResponseDTO;
import dev.davidvega.rolmanager.services.CharacterService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CharacterService characterService;

    private CharacterRequestDTO characterRequestDTO;
    private CharacterResponseDTO characterResponseDTO;

    @BeforeEach
    void setUp() {
        characterRequestDTO = new CharacterRequestDTO();
        characterRequestDTO.setName("Test Character");

        characterResponseDTO = new CharacterResponseDTO();
        characterResponseDTO.setId(1);
        characterResponseDTO.setName("Test Character");
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetAllCharacters() throws Exception {
        List<CharacterResponseDTO> characters = new ArrayList<>();
        characters.add(characterResponseDTO);

        when(characterService.getAllCharacters()).thenReturn(characters);

        mockMvc.perform(get("/characters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test Character"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetCharacterById() throws Exception {
        Long characterId = 1L;
        when(characterService.getCharacterById(characterId)).thenReturn(characterResponseDTO);

        mockMvc.perform(get("/characters/{id}", characterId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(characterId))
                .andExpect(jsonPath("$.name").value("Test Character"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testCreateCharacter() throws Exception {
        when(characterService.createCharacter(any(CharacterRequestDTO.class))).thenReturn(characterResponseDTO);

        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Character"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testUpdateCharacter() throws Exception {
        Long characterId = 1L;
        when(characterService.updateCharacter(any(Long.class), any(CharacterRequestDTO.class))).thenReturn(characterResponseDTO);

        mockMvc.perform(patch("/characters/{id}", characterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Character"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteCharacter() throws Exception {
        Long characterId = 1L;
        when(characterService.deleteCharacter(characterId)).thenReturn(true);
        mockMvc.perform(delete("/characters/{id}", characterId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllCharactersForbidden() throws Exception {
        mockMvc.perform(get("/characters"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateCharacterForbidden() throws Exception {
        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterRequestDTO)))
                .andExpect(status().isForbidden());
    }
}
