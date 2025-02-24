package dev.davidvega.rolmanager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
@RequiredArgsConstructor
@AllArgsConstructor
public class CharacterItemId implements Serializable {

    @Column(name = "character_id")
    private Integer characterId;

    @Column(name = "item_id")
    private Integer itemId;

}