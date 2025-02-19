package dev.davidvega.rolmanager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CharacterAbilityId implements java.io.Serializable {
    private static final long serialVersionUID = -6432793200763154045L;
    @NotNull
    @Column(name = "ability_id", nullable = false)
    private Integer abilityId;

    @NotNull
    @Column(name = "character_id", nullable = false)
    private Integer characterId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CharacterAbilityId entity = (CharacterAbilityId) o;
        return Objects.equals(this.abilityId, entity.abilityId) &&
                Objects.equals(this.characterId, entity.characterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abilityId, characterId);
    }

}