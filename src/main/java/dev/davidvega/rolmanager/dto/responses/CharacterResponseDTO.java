package dev.davidvega.rolmanager.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterResponseDTO {
    private Integer id;
    private String name;
    private String race;
    private String classField;
    private String alignment;
    private String biography;
    private String backstory;
    private String imageUrl;
    private Integer hitPoints;
    private Integer maxHitPoints;
    private Integer armorClass;
    private Integer initiativeBonus;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;
    private Integer wealth;
    private Boolean isAlive;
    private String deity;
}
