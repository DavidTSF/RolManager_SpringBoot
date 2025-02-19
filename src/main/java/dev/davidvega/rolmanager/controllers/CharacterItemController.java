package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.models.CharacterItem;
import dev.davidvega.rolmanager.models.CharacterItemId;
import dev.davidvega.rolmanager.repositories.CharacterItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/character-items")
public class CharacterItemController {

    @Autowired
    private CharacterItemRepository characterItemRepository;

    @GetMapping
    public List<CharacterItem> getAllCharacterItems() {
        return characterItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterItem> getCharacterItemById(@PathVariable CharacterItemId id) {
        Optional<CharacterItem> characterItem = characterItemRepository.findById(id);
        return characterItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CharacterItem createCharacterItem(@RequestBody CharacterItem characterItem) {
        return characterItemRepository.save(characterItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterItem> updateCharacterItem(@PathVariable CharacterItemId id, @RequestBody CharacterItem characterItemDetails) {
        Optional<CharacterItem> characterItem = characterItemRepository.findById(id);
        if (characterItem.isPresent()) {
            CharacterItem updatedCharacterItem = characterItem.get();
            // set fields
            return ResponseEntity.ok(characterItemRepository.save(updatedCharacterItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacterItem(@PathVariable CharacterItemId id) {
        if (characterItemRepository.existsById(id)) {
            characterItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}