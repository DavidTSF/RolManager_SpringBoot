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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Test
    void testFindItemsWithQuantityGreaterThan() {
        Item cheapItem = new Item();
        cheapItem.setName("Wooden Stick");
        cheapItem.setDescription("A simple stick");
        cheapItem.setWeight(BigDecimal.valueOf(5));
        cheapItem.setValue(50);
        cheapItem.setType("Weapon");
        itemRepository.save(cheapItem);

        Item expensiveItem = new Item();
        expensiveItem.setName("Golden Crown");
        expensiveItem.setDescription("A crown made of gold");
        expensiveItem.setWeight(BigDecimal.valueOf(10));
        expensiveItem.setValue(1500);
        expensiveItem.setType("Accessory");
        itemRepository.save(expensiveItem);

        List<Item> expensiveItems = itemRepository.findItemsWithQuantityGreaterThan(500);

        assertFalse(expensiveItems.isEmpty());
        assertEquals(2, expensiveItems.size());
        assertTrue(expensiveItems.stream().anyMatch(i -> i.getName().equals("Magic Sword")));
        assertTrue(expensiveItems.stream().anyMatch(i -> i.getName().equals("Golden Crown")));
    }

    @Test
    void testCountItemsGroupedByType() {
        Item accessory = new Item();
        accessory.setName("Silver Ring");
        accessory.setDescription("A shiny silver ring");
        accessory.setWeight(BigDecimal.valueOf(2));
        accessory.setValue(500);
        accessory.setType("Accessory");
        itemRepository.save(accessory);

        Item weapon = new Item();
        weapon.setName("Iron Axe");
        weapon.setDescription("A heavy iron axe");
        weapon.setWeight(BigDecimal.valueOf(30));
        weapon.setValue(300);
        weapon.setType("Weapon");
        itemRepository.save(weapon);

        List<Object[]> result = itemRepository.countItemsGroupedByType();

        Map<String, Long> itemCountByType = result.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));

        assertEquals(2, itemCountByType.get("Weapon"));
        assertEquals(1, itemCountByType.get("Accessory"));
    }


}
