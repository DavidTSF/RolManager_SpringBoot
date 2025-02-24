package dev.davidvega.rolmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"character\"")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_id_gen")
    @SequenceGenerator(name = "character_id_gen", sequenceName = "character_id_seq", allocationSize = 1)
    @Column(name = "id_character", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "race")
    private String race;

    @Size(max = 255)
    @Column(name = "class")
    private String classField;

    @Size(max = 255)
    @Column(name = "alignment")
    private String alignment;

    @Column(name = "biography", length = Integer.MAX_VALUE)
    private String biography;

    @Column(name = "backstory", length = Integer.MAX_VALUE)
    private String backstory;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "hit_points")
    private Integer hitPoints;

    @Column(name = "max_hit_points")
    private Integer maxHitPoints;

    @Column(name = "armor_class")
    private Integer armorClass;

    @Column(name = "initiative_bonus")
    private Integer initiativeBonus;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "dexterity")
    private Integer dexterity;

    @Column(name = "constitution")
    private Integer constitution;

    @Column(name = "intelligence")
    private Integer intelligence;

    @Column(name = "wisdom")
    private Integer wisdom;

    @Column(name = "charisma")
    private Integer charisma;

    @Column(name = "wealth")
    private Integer wealth;

    @Column(name = "is_alive")
    private Boolean isAlive;

    @Size(max = 255)
    @Column(name = "deity")
    private String deity;

}