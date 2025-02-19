package dev.davidvega.rolmanager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"character\"")
public class Character {
    @Id
    @ColumnDefault("nextval('character_id_character_seq')")
    @Column(name = "id_character", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
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

    @Size(max = 255)
    @Column(name = "biography")
    private String biography;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "is_alive", nullable = false)
    private Boolean isAlive = false;

    @Size(max = 255)
    @NotNull
    @Column(name = "deity", nullable = false)
    private String deity;

}