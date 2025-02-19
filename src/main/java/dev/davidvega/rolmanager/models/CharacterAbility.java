package dev.davidvega.rolmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.security.auth.Subject;

@Getter
@Setter
@Entity
@Table(name = "character_abilities")
public class CharacterAbility {
    @SequenceGenerator(name = "character_abilities_id_gen", sequenceName = "character_player_id_character_player_seq", allocationSize = 1)
    @EmbeddedId
    private CharacterAbilityId id;


    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

}