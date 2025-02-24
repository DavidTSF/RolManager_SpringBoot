package dev.davidvega.rolmanager.integration.repositories;

import dev.davidvega.rolmanager.models.Character;
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
public class CharacterRepositoryTest {

    @Autowired
    private CharacterRepository characterRepository;

    private Character character;

    @BeforeEach
    void setUp() {
        character = new Character();
        character.setName("Test Character");
        character.setAlignment("Good");
    }

    @Test
    void testSaveCharacter() {

        Character savedCharacter = characterRepository.save(character);


        assertNotNull(savedCharacter.getId(), "El personaje debe tener un ID asignado");
        assertEquals("Test Character", savedCharacter.getName());
    }

    @Test
    void testFindCharacterById() {

        Character savedCharacter = characterRepository.save(character);


        Optional<Character> foundCharacter = characterRepository.findById(Long.valueOf(savedCharacter.getId()));


        assertTrue(foundCharacter.isPresent());
        assertEquals("Test Character", foundCharacter.get().getName());
    }

    @Test
    void testUpdateCharacter() {

        Character savedCharacter = characterRepository.save(character);
        savedCharacter.setName("Updated Character");

        Character updatedCharacter = characterRepository.save(savedCharacter);


        assertEquals("Updated Character", updatedCharacter.getName());
    }

    @Test
    void testDeleteCharacter() {
        Character savedCharacter = characterRepository.save(character);

        // When: Lo eliminamos
        characterRepository.delete(savedCharacter);

        Optional<Character> deletedCharacter = characterRepository.findById(Long.valueOf(savedCharacter.getId()));
        assertFalse(deletedCharacter.isPresent());
    }

    @Test
    void testFindAllCharacters() {
        Character character1 = new Character();
        character1.setName("Character 1");
        character1.setAlignment("Good");

        Character character2 = new Character();
        character2.setName("Character 2");
        character2.setAlignment("Evil");

        characterRepository.save(character1);
        characterRepository.save(character2);

        List<Character> characters = characterRepository.findAll();

        assertEquals(2, characters.size());
        assertTrue(characters.stream().anyMatch(c -> c.getName().equals("Character 1")));
        assertTrue(characters.stream().anyMatch(c -> c.getName().equals("Character 2")));
    }
}
