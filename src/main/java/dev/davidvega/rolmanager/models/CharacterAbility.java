package dev.davidvega.rolmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "character_abilities")
public class CharacterAbility {
    @EmbeddedId
    private CharacterAbilityId id;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "character_id")
    @JsonBackReference
    private Character character;

    @ManyToOne
    @MapsId("abilityId")
    @JoinColumn(name = "ability_id")
    private Ability ability;



}