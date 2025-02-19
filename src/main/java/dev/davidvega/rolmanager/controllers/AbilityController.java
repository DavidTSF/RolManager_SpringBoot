package dev.davidvega.rolmanager.controllers;

import dev.davidvega.rolmanager.models.Ability;
import dev.davidvega.rolmanager.repositories.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/abilities")
public class AbilityController {

    @Autowired
    private AbilityRepository abilityRepository;

    @GetMapping
    public List<Ability> getAllAbilities() {
        return abilityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ability> getAbilityById(@PathVariable Long id) {
        Optional<Ability> ability = abilityRepository.findById(id);
        return ability.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ability createAbility(@RequestBody Ability ability) {
        return abilityRepository.save(ability);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ability> updateAbility(@PathVariable Long id, @RequestBody Ability abilityDetails) {
        Optional<Ability> ability = abilityRepository.findById(id);
        if (ability.isPresent()) {
            Ability updatedAbility = ability.get();
            // set fields
            return ResponseEntity.ok(abilityRepository.save(updatedAbility));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbility(@PathVariable Long id) {
        if (abilityRepository.existsById(id)) {
            abilityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}