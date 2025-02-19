package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.models.CharacterAbility;
import dev.davidvega.rolmanager.models.CharacterAbilityId;
import dev.davidvega.rolmanager.repositories.CharacterAbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/character-abilities")
public class CharacterAbilityController {

    @Autowired
    private CharacterAbilityRepository characterAbilityRepository;

    @GetMapping
    public List<CharacterAbility> getAllCharacterAbilities() {
        return characterAbilityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterAbility> getCharacterAbilityById(@PathVariable CharacterAbilityId id) {
        Optional<CharacterAbility> characterAbility = characterAbilityRepository.findById(id);
        return characterAbility.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CharacterAbility createCharacterAbility(@RequestBody CharacterAbility characterAbility) {
        return characterAbilityRepository.save(characterAbility);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterAbility> updateCharacterAbility(@PathVariable CharacterAbilityId id, @RequestBody CharacterAbility characterAbilityDetails) {
        Optional<CharacterAbility> characterAbility = characterAbilityRepository.findById(id);
        if (characterAbility.isPresent()) {
            CharacterAbility updatedCharacterAbility = characterAbility.get();
            // set fields
            return ResponseEntity.ok(characterAbilityRepository.save(updatedCharacterAbility));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacterAbility(@PathVariable CharacterAbilityId id) {
        if (characterAbilityRepository.existsById(id)) {
            characterAbilityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}