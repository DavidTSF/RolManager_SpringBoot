package dev.davidvega.rolmanager.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davidvega.rolmanager.dto.requests.AbilityRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AbilitiesFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.AbilityResponseDTO;
import dev.davidvega.rolmanager.services.AbilityService;
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
public class AbilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AbilityService abilityService;

    private AbilityRequestDTO abilityRequestDTO;
    private AbilityResponseDTO abilityResponseDTO;

    @BeforeEach
    void setUp() {

        abilityResponseDTO = new AbilityResponseDTO();
        abilityResponseDTO.setAbilityId(1);
        abilityResponseDTO.setName("Fireball");
        abilityResponseDTO.setDescription("Launches a fireball");


        abilityRequestDTO = new AbilityRequestDTO();
        abilityRequestDTO.setName("Fireball");
        abilityRequestDTO.setDescription("Launches a fireball");
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetAllAbilities() throws Exception {
        List<AbilityResponseDTO> abilities = new ArrayList<>();
        abilities.add(abilityResponseDTO);

        when(abilityService.getAllAbilities()).thenReturn(abilities);

        mockMvc.perform(get("/abilities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].abilityId").value(1))
                .andExpect(jsonPath("$[0].name").value("Fireball"));
    }


    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetAbilityById() throws Exception {
        Long abilityId = 1L;
        when(abilityService.getAbilityById(abilityId)).thenReturn(abilityResponseDTO);

        mockMvc.perform(get("/abilities/{id}", abilityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abilityId").value(1))
                .andExpect(jsonPath("$.name").value("Fireball"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateAbility() throws Exception {
        when(abilityService.createAbility(any(AbilityRequestDTO.class))).thenReturn(abilityResponseDTO);

        mockMvc.perform(post("/abilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(abilityRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fireball"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdateAbility() throws Exception {
        Long abilityId = 1L;
        when(abilityService.updateAbility(eq(abilityId), any(AbilityRequestDTO.class))).thenReturn(abilityResponseDTO);

        mockMvc.perform(patch("/abilities/{id}", abilityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(abilityRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fireball"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteAbility() throws Exception {
        Long abilityId = 1L;
        when(abilityService.deleteAbility(abilityId)).thenReturn(true);

        mockMvc.perform(delete("/abilities/{id}", abilityId))
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteAbility_NotFound() throws Exception {
        Long abilityId = 99L;
        when(abilityService.deleteAbility(abilityId)).thenReturn(false);

        mockMvc.perform(delete("/abilities/{id}", abilityId))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetAbilitiesFromCharacter() throws Exception {
        Long characterId = 1L;
        AbilitiesFromCharacterDTO abilitiesFromCharacterDTO = new AbilitiesFromCharacterDTO();
        abilitiesFromCharacterDTO.setCharacterId(Math.toIntExact(characterId));

        when(abilityService.getAbilitiesFromCharacter(characterId)).thenReturn(abilitiesFromCharacterDTO);

        mockMvc.perform(get("/abilities/character/{characterId}", characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characterId").value(1));
    }


    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testAddAbilityToCharacter() throws Exception {
        Long characterId = 1L;
        Long abilityId = 1L;

        when(abilityService.addAbilityToCharacter(characterId, abilityId)).thenReturn(abilityResponseDTO);

        mockMvc.perform(post("/abilities/character/{characterId}/{abilityId}", characterId, abilityId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fireball"));
    }


    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testDeleteAbilityFromCharacter() throws Exception {
        Long characterId = 1L;
        Long abilityId = 1L;

        when(abilityService.deleteAbilityFromCharacter(characterId, abilityId)).thenReturn(true);

        mockMvc.perform(delete("/abilities/character/{characterId}/{abilityId}", characterId, abilityId))
                .andExpect(status().isNoContent());
    }


    @Test
    public void testGetAllAbilities_Forbidden() throws Exception {
        mockMvc.perform(get("/abilities"))
                .andExpect(status().isForbidden());
    }
}
