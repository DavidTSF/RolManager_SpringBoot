package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.dto.requests.ItemRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterItemResponseDTO;
import dev.davidvega.rolmanager.dto.responses.ItemsFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.ItemResponseDTO;
import dev.davidvega.rolmanager.mappers.ItemMapper;
import dev.davidvega.rolmanager.repositories.ItemRepository;
import dev.davidvega.rolmanager.services.ItemService;
import dev.davidvega.rolmanager.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ValidationUtil validationUtil;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Operation(summary = "Obtiene un listado de todos los items")
    @GetMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItems());
    }

    @Operation(summary = "Obtiene los datos de un item")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemById(id));
    }

    @Operation(summary = "Crea un nuevo item")
    @PostMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        validationUtil.validate(itemRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemRequestDTO));
    }

    @Operation(summary = "Actualiza los datos de un item")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<ItemResponseDTO> updateItem(@PathVariable Long id, @RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id, itemRequestDTO));
    }

    @Operation(summary = "Elimina un item")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemService.deleteItem(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtiene un listado de todos los items que tiene un personaje")
    @GetMapping("/character/{characterId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<ItemsFromCharacterDTO> getItemsFromCharacter(@PathVariable Long characterId) {
        ItemsFromCharacterDTO itemsFromCharacter = itemService.getItemsFromCharacter(characterId);
        return ResponseEntity.ok(itemsFromCharacter);
    }

    @Operation(summary = "Añade un item a un personaje")
    @PostMapping("/character/{characterId}/{itemId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterItemResponseDTO> addItemToCharacter(@PathVariable Long characterId, @PathVariable Long itemId) {
        CharacterItemResponseDTO itemResponseDTO = itemService.addItemToCharacter(characterId, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponseDTO);
    }

    @Operation(summary = "Actualiza la cantidad de un item que tiene un personaje")
    @PatchMapping("/character/{characterId}/{itemId}/quantity/{quantity}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<ItemResponseDTO> updateItemQuantity(
            @PathVariable Long characterId,
            @PathVariable Long itemId,
            @PathVariable int quantity) {
        ItemResponseDTO itemResponseDTO = itemService.updateItemQuantity(characterId, itemId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponseDTO);
    }

    @Operation(summary = "Elimina un item de un personaje")
    @DeleteMapping("/character/{characterId}/{itemId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteItemFromCharacter(@PathVariable Long characterId, @PathVariable Long itemId) {
        if (itemService.deleteItemFromCharacter(characterId, itemId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Devuelve todos los items con mayor valor del especificado")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    @GetMapping("/value-greater-than/{minValue}")
    public ResponseEntity<List<ItemResponseDTO>> getItemsWithValueGreaterThan(@PathVariable int minValue) {
        List<ItemResponseDTO> items = itemRepository.findItemsWithQuantityGreaterThan(minValue).stream().map(
                itemMapper::convertToResponseDTO
        ).toList();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Devuelve la cantidad de diferentes items por su tipo")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    @GetMapping("/count-by-type")
    public ResponseEntity<Map<String, Long>> getItemCountGroupedByType() {
        List<Object[]> result = itemRepository.countItemsGroupedByType();

        // Convertir los resultados en un mapa
        Map<String, Long> itemCountByType = result.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0], // tipo de ítem
                        row -> (Long) row[1]     // cantidad de ítems
                ));

        return ResponseEntity.ok(itemCountByType);
    }

}