package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.dto.requests.ChangeCharacterPlayerDTO;
import dev.davidvega.rolmanager.dto.requests.CharacterPlayerRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AllCharactersFromPlayerResponseDTO;
import dev.davidvega.rolmanager.dto.responses.CharacterPlayerResponseDTO;
import dev.davidvega.rolmanager.services.CharacterPlayerService;
import dev.davidvega.rolmanager.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character-player")
@RequiredArgsConstructor
public class CharacterPlayerController {

    private final CharacterPlayerService characterPlayerService;
    private final ValidationUtil validationUtil;

    @Operation(summary = "Obtiene un listado de todos los personajes y su usuario")
    @GetMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<CharacterPlayerResponseDTO>> getAllCharacterPlayers() {
        return ResponseEntity.ok(characterPlayerService.getAllCharacterPlayers());
    }

    @Operation(summary = "Obtiene los datos de un personaje y su usuario")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterPlayerResponseDTO> getCharacterPlayerById(@PathVariable Long id) {
        CharacterPlayerResponseDTO characterPlayerResponseDTO = characterPlayerService.getCharacterPlayerById(id);
        return ResponseEntity.status(characterPlayerResponseDTO != null ? 200 : 404).body(characterPlayerResponseDTO);
    }

    @Operation(summary = "Crea un nuevo personaje y asigna un usuario")
    @PostMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterPlayerResponseDTO> createCharacterPlayer(@RequestBody @Valid CharacterPlayerRequestDTO characterPlayerRequestDTO) {
        validationUtil.validate(characterPlayerRequestDTO);
        return ResponseEntity.ok(characterPlayerService.createCharacterPlayer(characterPlayerRequestDTO));
    }

    @Operation(summary = "Actualiza los datos de un personaje y su usuario")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<CharacterPlayerResponseDTO> updateCharacterPlayer(@PathVariable Long id, @RequestBody ChangeCharacterPlayerDTO changeCharacterPlayerDTO) {
        return ResponseEntity.ok(characterPlayerService.updateCharacterPlayer(id, changeCharacterPlayerDTO));
    }

    @Operation(summary = "Elimina un personaje y su usuario")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCharacterPlayer(@PathVariable Long id) {
        if (characterPlayerService.deleteCharacterPlayer(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtiene un listado de todos los personajes de un usuario")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<AllCharactersFromPlayerResponseDTO> getCharacterPlayersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(characterPlayerService.getAllCharactersFromPlayer(userId));
    }

}
