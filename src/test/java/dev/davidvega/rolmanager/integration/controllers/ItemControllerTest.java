package dev.davidvega.rolmanager.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davidvega.rolmanager.dto.requests.ItemRequestDTO;
import dev.davidvega.rolmanager.dto.responses.ItemResponseDTO;
import dev.davidvega.rolmanager.services.ItemService;
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
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ItemService itemService;

    private ItemRequestDTO itemRequestDTO;
    private ItemResponseDTO itemResponseDTO;

    @BeforeEach
    void setUp() {
        // Configuración de DTOs
        itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setItemId(1);
        itemResponseDTO.setName("Magic Sword");
        itemResponseDTO.setDescription("A sword infused with magical powers");

        itemRequestDTO = new ItemRequestDTO();
        itemRequestDTO.setName("Magic Sword");
        itemRequestDTO.setDescription("A sword infused with magical powers");
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetAllItems() throws Exception {
        List<ItemResponseDTO> items = new ArrayList<>();
        items.add(itemResponseDTO);

        when(itemService.getAllItems()).thenReturn(items);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemId").value(1))
                .andExpect(jsonPath("$[0].name").value("Magic Sword"));
    }

    @Test
    @WithMockUser(username = "player", authorities = {"PLAYER"})
    public void testGetItemById() throws Exception {
        Long itemId = 1L;
        when(itemService.getItemById(itemId)).thenReturn(itemResponseDTO);

        mockMvc.perform(get("/items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(1))
                .andExpect(jsonPath("$.name").value("Magic Sword"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateItem() throws Exception {
        when(itemService.createItem(any(ItemRequestDTO.class))).thenReturn(itemResponseDTO);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Magic Sword"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdateItem() throws Exception {
        Long itemId = 1L;
        when(itemService.updateItem(eq(itemId), any(ItemRequestDTO.class))).thenReturn(itemResponseDTO);

        mockMvc.perform(patch("/items/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Magic Sword"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteItem() throws Exception {
        Long itemId = 1L;
        when(itemService.deleteItem(itemId)).thenReturn(true);

        mockMvc.perform(delete("/items/{id}", itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteItem_NotFound() throws Exception {
        Long itemId = 99L;
        when(itemService.deleteItem(itemId)).thenReturn(false);

        mockMvc.perform(delete("/items/{id}", itemId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllItems_Forbidden() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isForbidden());
    }
}
