package dev.davidvega.rolmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "character_items")
public class CharacterItem {

    @EmbeddedId
    private CharacterItemId id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "equipped", nullable = false)
    private Boolean equipped = false;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "character_id")
    @JsonBackReference
    private Character character;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;


}