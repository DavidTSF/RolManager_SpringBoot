package dev.davidvega.rolmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter
@Setter
@Entity
@Table(name = "character_player")
public class CharacterPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_player_id_gen")
    @SequenceGenerator(name = "character_player_id_gen", sequenceName = "character_player_id_seq", allocationSize = 1)
    @Column(name = "id_character_player", nullable = false)
    private Integer id;

    @NotNull
    @OneToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @Size(max = 255)
    @NotNull
    @Column(name = "player_name", nullable = false)
    private String playerName;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rol_user", nullable = false)
    private User idRolUser;

}