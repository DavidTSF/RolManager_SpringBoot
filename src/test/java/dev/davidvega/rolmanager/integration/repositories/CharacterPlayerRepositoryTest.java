package dev.davidvega.rolmanager.integration.repositories;

import dev.davidvega.rolmanager.models.Character;
import dev.davidvega.rolmanager.models.CharacterPlayer;
import dev.davidvega.rolmanager.models.User;
import dev.davidvega.rolmanager.repositories.CharacterPlayerRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import dev.davidvega.rolmanager.repositories.UserRepository;
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
public class CharacterPlayerRepositoryTest {

    @Autowired
    private CharacterPlayerRepository characterPlayerRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserRepository userRepository;

    private Character character;
    private User user;
    private CharacterPlayer characterPlayer;

    @BeforeEach
    void setUp() {

        character = new Character();
        character.setName("Test Character");
        character = characterRepository.save(character);


        user = new User();
        user.setUsername("player1");
        user.setPassword("password123");
        user.setRole(User.UserRole.PLAYER);
        user = userRepository.save(user);

        characterPlayer = new CharacterPlayer();
        characterPlayer.setCharacter(character);
        characterPlayer.setPlayerName("player1");
        characterPlayer.setIdRolUser(user);
        characterPlayer = characterPlayerRepository.save(characterPlayer);
    }

    @Test
    void testSaveCharacterPlayer() {

        assertNotNull(characterPlayer.getId());
        assertEquals("player1", characterPlayer.getPlayerName());
        assertEquals(character.getId(), characterPlayer.getCharacter().getId());
        assertEquals(user.getId(), characterPlayer.getIdRolUser().getId());
    }

    @Test
    void testFindCharacterPlayerById() {

        Optional<CharacterPlayer> found = characterPlayerRepository.findById(Long.valueOf(characterPlayer.getId()));

        assertTrue(found.isPresent());
        assertEquals("player1", found.get().getPlayerName());
    }

    @Test
    void testFindAllCharacterPlayers() {
        List<CharacterPlayer> all = characterPlayerRepository.findAll();

        assertFalse(all.isEmpty());
        assertEquals(1, all.size());
    }

    @Test
    void testUpdateCharacterPlayer() {
        characterPlayer.setPlayerName("player2");
        CharacterPlayer updated = characterPlayerRepository.save(characterPlayer);

        assertEquals("player2", updated.getPlayerName());
    }

    @Test
    void testDeleteCharacterPlayer() {
        characterPlayerRepository.delete(characterPlayer);

        Optional<CharacterPlayer> deleted = characterPlayerRepository.findById(Long.valueOf(characterPlayer.getId()));
        assertFalse(deleted.isPresent());
    }
}
