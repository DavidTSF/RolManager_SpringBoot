package dev.davidvega.rolmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "character_player")
public class CharacterPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_player_id_gen")
    @SequenceGenerator(name = "character_player_id_gen", sequenceName = "character_player_id_character_player_seq", allocationSize = 1)
    @Column(name = "id_character_player", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @Size(max = 255)
    @NotNull
    @Column(name = "player_name", nullable = false)
    private String playerName;

    @NotNull
    @Column(name = "backstory", nullable = false, length = Integer.MAX_VALUE)
    private String backstory;

    @NotNull
    @Column(name = "hit_pointsbigint", nullable = false)
    private Integer hitPointsbigint;

    @NotNull
    @Column(name = "max_hit_points", nullable = false)
    private Integer maxHitPoints;

    @NotNull
    @Column(name = "armor_class", nullable = false)
    private Integer armorClass;

    @NotNull
    @Column(name = "initiative_bonus", nullable = false)
    private Integer initiativeBonus;

    @NotNull
    @Column(name = "strength", nullable = false)
    private Integer strength;

    @NotNull
    @Column(name = "dexterity", nullable = false)
    private Integer dexterity;

    @NotNull
    @Column(name = "constitution", nullable = false)
    private Integer constitution;

    @NotNull
    @Column(name = "intelligence", nullable = false)
    private Integer intelligence;

    @NotNull
    @Column(name = "wisdom", nullable = false)
    private Integer wisdom;

    @NotNull
    @Column(name = "charisma", nullable = false)
    private Integer charisma;

    @NotNull
    @Column(name = "wealth", nullable = false)
    private Integer wealth;

}