package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
