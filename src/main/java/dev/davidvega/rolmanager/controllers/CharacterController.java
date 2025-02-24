package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.dto.requests.CharacterRequestDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterResponseDTO;
import dev.davidvega.rolmanager.services.CharacterService;
import dev.davidvega.rolmanager.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final ValidationUtil validationUtil;

    @Operation(summary = "Obtiene un listado de todos los personajes")
    @GetMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<CharacterResponseDTO>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }

    @Operation(summary = "Obtiene los datos de un personaje por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable Long id) {
        return ResponseEntity.ok(characterService.getCharacterById(id));
    }

    @Operation(summary = "Crea un nuevo personaje")
    @PostMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterResponseDTO> createCharacter(@RequestBody CharacterRequestDTO characterRequestDTO) {

        validationUtil.validate(characterRequestDTO);
        return ResponseEntity.ok(characterService.createCharacter(characterRequestDTO));
    }

    @Operation(summary = "Actualiza los datos de un personaje")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterResponseDTO> updateCharacter(@PathVariable Long id, @RequestBody CharacterRequestDTO characterRequestDTO) {
        return ResponseEntity.ok(characterService.updateCharacter(id, characterRequestDTO));
    }

    @Operation(summary = "Elimina un personaje")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        if (characterService.deleteCharacter(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}