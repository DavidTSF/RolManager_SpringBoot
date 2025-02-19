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
public class CharacterItemId implements java.io.Serializable {
    private static final long serialVersionUID = -2494036008971177195L;
    @NotNull
    @Column(name = "character_id", nullable = false)
    private Integer characterId;

    @NotNull
    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CharacterItemId entity = (CharacterItemId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.characterId, entity.characterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, characterId);
    }

}