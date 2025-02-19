package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.models.CharacterPlayer;
import dev.davidvega.rolmanager.repositories.CharacterPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/character-players")
public class CharacterPlayerController {

    @Autowired
    private CharacterPlayerRepository characterPlayerRepository;

    @GetMapping
    public List<CharacterPlayer> getAllCharacterPlayers() {
        return characterPlayerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterPlayer> getCharacterPlayerById(@PathVariable Long id) {
        Optional<CharacterPlayer> characterPlayer = characterPlayerRepository.findById(id);
        return characterPlayer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CharacterPlayer createCharacterPlayer(@RequestBody CharacterPlayer characterPlayer) {
        return characterPlayerRepository.save(characterPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterPlayer> updateCharacterPlayer(@PathVariable Long id, @RequestBody CharacterPlayer characterPlayerDetails) {
        Optional<CharacterPlayer> characterPlayer = characterPlayerRepository.findById(id);
        if (characterPlayer.isPresent()) {
            CharacterPlayer updatedCharacterPlayer = characterPlayer.get();
            // set fields
            return ResponseEntity.ok(characterPlayerRepository.save(updatedCharacterPlayer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacterPlayer(@PathVariable Long id) {
        if (characterPlayerRepository.existsById(id)) {
            characterPlayerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}