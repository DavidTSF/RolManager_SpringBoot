package dev.davidvega.rolmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "character_items")
public class CharacterItem {


    @EmbeddedId
    private CharacterItemId id;


    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "character_id")
    private Character character;


    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item item;


    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @NotNull
    @Column(name = "equipped", nullable = false)
    private Boolean equipped = false;

}