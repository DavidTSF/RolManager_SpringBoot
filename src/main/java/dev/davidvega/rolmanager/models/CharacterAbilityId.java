package dev.davidvega.rolmanager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
@AllArgsConstructor
public class CharacterAbilityId implements Serializable {
    @Column(name = "ability_id")
    private Integer abilityId;
    @Column(name = "character_id")
    private Integer characterId;


}