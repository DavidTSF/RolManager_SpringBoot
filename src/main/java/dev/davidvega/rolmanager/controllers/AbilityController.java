package dev.davidvega.rolmanager.controllers;


import dev.davidvega.rolmanager.dto.requests.AbilityRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AbilitiesFromCharacterDTO;
import dev.davidvega.rolmanager.dto.responses.AbilityResponseDTO;
import dev.davidvega.rolmanager.services.AbilityService;
import dev.davidvega.rolmanager.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abilities")
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;
    private final ValidationUtil validationUtil;

    @Operation(summary = "Obtener todas las habilidades")
    @GetMapping
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<AbilityResponseDTO>> getAllAbilities() {
        return ResponseEntity.status(HttpStatus.OK).body(abilityService.getAllAbilities());
    }

    @Operation(summary = "Obtener una habilidad por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<AbilityResponseDTO> getAbilityById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(abilityService.getAbilityById(id));
    }

    @Operation(summary = "Crear una nueva habilidad")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AbilityResponseDTO> createAbility(@RequestBody AbilityRequestDTO abilityRequestDTO) {
        validationUtil.validate(abilityRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(abilityService.createAbility(abilityRequestDTO));
    }

    @Operation(summary = "Actualizar una habilidad existente")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AbilityResponseDTO> updateAbility(@PathVariable Long id, @RequestBody AbilityRequestDTO abilityRequestDTO) {
        AbilityResponseDTO abilityResponseDTO = abilityService.updateAbility(id, abilityRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(abilityResponseDTO);
    }

    @Operation(summary = "Eliminar una habilidad")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAbility(@PathVariable Long id) {
        if (abilityService.deleteAbility(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener las habilidades de un personaje")
    @GetMapping("/character/{characterId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<AbilitiesFromCharacterDTO> getAbilitiesFromCharacter(@PathVariable Long characterId) {
        AbilitiesFromCharacterDTO abilitiesFromCharacterDTO = abilityService.getAbilitiesFromCharacter(characterId);
        return ResponseEntity.ok(abilitiesFromCharacterDTO);
    }

    @Operation(summary = "Añadir una habilidad a un personaje")
    @PostMapping("/character/{characterId}/{abilityId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<AbilityResponseDTO> addAbilityToCharacter(@PathVariable Long characterId, @PathVariable Long abilityId) {
        validationUtil.validate(characterId);
        AbilityResponseDTO abilityResponseDTO = abilityService.addAbilityToCharacter(characterId, abilityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(abilityResponseDTO);
    }


    @Operation(summary = "Actualizar el nivel de una habilidad de un personaje")
    @PatchMapping("/character/{characterId}/{abilityId}/level/{level}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<AbilitiesFromCharacterDTO.AbilityWithLevelDTO> updateAbilityLevel(
            @PathVariable Long characterId,
            @PathVariable Long abilityId,
            @PathVariable int level) {
        AbilitiesFromCharacterDTO.AbilityWithLevelDTO abilitiesFromCharacterDTO = abilityService.updateAbilityLevel(characterId, abilityId, level);

        return ResponseEntity.status(HttpStatus.OK).body(abilitiesFromCharacterDTO);
    }

    @Operation(summary = "Eliminar una habilidad de un personaje")
    @DeleteMapping("/character/{characterId}/{abilityId}")
    @PreAuthorize("hasAuthority('PLAYER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAbilityFromCharacter(@PathVariable Long characterId, @PathVariable Long abilityId) {
        if (abilityService.deleteAbilityFromCharacter(characterId, abilityId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}