package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.value > :minValue")
    List<Item> findItemsWithQuantityGreaterThan(@Param("minValue") int minValue);

    @Query("SELECT i.type, COUNT(i) FROM Item i GROUP BY i.type")
    List<Object[]> countItemsGroupedByType();

}
