package dev.davidvega.rolmanager.integration.repositories;

import dev.davidvega.rolmanager.models.Ability;
import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.repositories.AbilityRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AbilityRepositoryTest {

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private CharacterRepository characterRepository;

    private Ability ability;
    private Character character;

    @BeforeEach
    void setUp() {
        // Crear y guardar un Character
        character = new Character();
        character.setName("Test Character");
        character = characterRepository.save(character);

        // Crear y guardar una Ability
        ability = new Ability();
        ability.setName("Fireball");
        ability.setDescription("Launches a fireball");
        ability = abilityRepository.save(ability);
    }

    @Test
    void testSaveAbility() {
        // Then: la habilidad tiene un ID asignado
        assertNotNull(ability.getId());
        assertEquals("Fireball", ability.getName());
        assertEquals("Launches a fireball", ability.getDescription());
    }

    @Test
    void testFindAbilityById() {
        // When: buscar la habilidad por ID
        Optional<Ability> found = abilityRepository.findById(Long.valueOf(ability.getId()));

        // Then: debería encontrarla correctamente
        assertTrue(found.isPresent());
        assertEquals("Fireball", found.get().getName());
    }

    @Test
    void testFindAllAbilities() {
        // When: obtener todas las habilidades
        List<Ability> all = abilityRepository.findAll();

        // Then: al menos una habilidad debería estar presente
        assertFalse(all.isEmpty());
        assertEquals(1, all.size());
    }

    @Test
    void testUpdateAbility() {
        // Given: actualizar el nombre de la habilidad
        ability.setName("Ice Blast");
        Ability updated = abilityRepository.save(ability);

        // Then: verificar que el nombre se actualizó
        assertEquals("Ice Blast", updated.getName());
    }

    @Test
    void testDeleteAbility() {
        // When: eliminar la habilidad
        abilityRepository.delete(ability);

        // Then: no debería encontrarse después de eliminarla
        Optional<Ability> deleted = abilityRepository.findById(Long.valueOf(ability.getId()));
        assertFalse(deleted.isPresent());
    }
}
