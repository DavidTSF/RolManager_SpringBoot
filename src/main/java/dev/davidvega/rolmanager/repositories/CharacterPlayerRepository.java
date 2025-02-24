package dev.davidvega.rolmanager.repositories;

import dev.davidvega.rolmanager.models.CharacterPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterPlayerRepository extends JpaRepository<CharacterPlayer, Long> {


    List<CharacterPlayer> findByIdRolUser_Id(Long userId);

}
