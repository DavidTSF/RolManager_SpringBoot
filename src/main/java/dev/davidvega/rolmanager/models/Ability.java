package dev.davidvega.rolmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ability")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ability_id_gen")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "key_ability", length = Integer.MAX_VALUE)
    private String keyAbility;

}