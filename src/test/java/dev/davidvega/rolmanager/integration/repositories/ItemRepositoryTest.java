package dev.davidvega.rolmanager.integration.repositories;

import dev.davidvega.rolmanager.models.Item;
import dev.davidvega.rolmanager.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setName("Magic Sword");
        item.setDescription("A sword infused with magical powers");
        item.setWeight(BigDecimal.valueOf(20));
        item.setValue(1000);
        item.setType("Weapon");
        item = itemRepository.save(item);
    }

    @Test
    void testSaveItem() {
        assertNotNull(item.getId());
        assertEquals("Magic Sword", item.getName());
        assertEquals("A sword infused with magical powers", item.getDescription());
    }

    @Test
    void testFindItemById() {
        Optional<Item> found = itemRepository.findById(Long.valueOf(item.getId()));

        assertTrue(found.isPresent());
        assertEquals("Magic Sword", found.get().getName());
    }

    @Test
    void testFindAllItems() {

        List<Item> all = itemRepository.findAll();

        assertFalse(all.isEmpty());
        assertEquals(1, all.size());
    }

    @Test
    void testUpdateItem() {
        item.setName("Enchanted Shield");
        Item updated = itemRepository.save(item);

        assertEquals("Enchanted Shield", updated.getName());
    }

    @Test
    void testDeleteItem() {
        itemRepository.delete(item);

        Optional<Item> deleted = itemRepository.findById(Long.valueOf(item.getId()));
        assertFalse(deleted.isPresent());
    }
}
