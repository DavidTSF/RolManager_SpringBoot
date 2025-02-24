package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
