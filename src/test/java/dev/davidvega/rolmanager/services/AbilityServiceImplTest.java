package dev.davidvega.rolmanager.services;

import dev.davidvega.rolmanager.dto.requests.AbilityRequestDTO;
import dev.davidvega.rolmanager.dto.responses.AbilityResponseDTO;
import dev.davidvega.rolmanager.mappers.AbilityMapper;
import dev.davidvega.rolmanager.models.Ability;
import dev.davidvega.rolmanager.repositories.AbilityRepository;
import dev.davidvega.rolmanager.repositories.CharacterAbilityRepository;
import dev.davidvega.rolmanager.repositories.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AbilityServiceImplTest {

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private CharacterAbilityRepository characterAbilityRepository;

    @Mock
    private AbilityMapper abilityMapper;

    @InjectMocks
    private AbilityServiceImpl abilityService;

    private Ability ability;
    private AbilityRequestDTO requestDTO;
    private AbilityResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        ability = new Ability();
        ability.setId(1);
        ability.setName("Fireball");
        ability.setDescription("Launches a fireball");
        ability.setKeyAbility("Intelligence");

        requestDTO = new AbilityRequestDTO();
        requestDTO.setName("Fireball");
        requestDTO.setDescription("Launches a fireball");
        requestDTO.setKeyAbility("Intelligence");

        responseDTO = new AbilityResponseDTO();
        responseDTO.setAbilityId(1);
        responseDTO.setName("Fireball");
        responseDTO.setDescription("Launches a fireball");
        responseDTO.setKeyAbility("Intelligence");
    }

    @Test
    void testGetAllAbilities() {
        when(abilityRepository.findAll()).thenReturn(List.of(ability));
        when(abilityMapper.convertToResponseDTO(ability)).thenReturn(responseDTO);

        List<AbilityResponseDTO> result = abilityService.getAllAbilities();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Fireball", result.get(0).getName());
        verify(abilityRepository, times(1)).findAll();
    }

    @Test
    void testGetAbilityById() {
        when(abilityRepository.findById(1L)).thenReturn(Optional.of(ability));
        when(abilityMapper.convertToResponseDTO(ability)).thenReturn(responseDTO);

        AbilityResponseDTO result = abilityService.getAbilityById(1L);

        assertNotNull(result);
        assertEquals(1, result.getAbilityId());
        assertEquals("Fireball", result.getName());
        verify(abilityRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateAbility() {
        when(abilityMapper.convertToEntity(any(AbilityRequestDTO.class))).thenReturn(ability);
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        when(abilityMapper.convertToResponseDTO(any(Ability.class))).thenReturn(responseDTO);

        AbilityResponseDTO result = abilityService.createAbility(requestDTO);

        assertNotNull(result);
        assertEquals("Fireball", result.getName());
        verify(abilityRepository, times(1)).save(any(Ability.class));
    }

    @Test
    void testUpdateAbility() {
        when(abilityRepository.findById(1L)).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        when(abilityMapper.convertToResponseDTO(any(Ability.class))).thenReturn(responseDTO);

        AbilityResponseDTO result = abilityService.updateAbility(1L, requestDTO);

        assertNotNull(result);
        assertEquals("Fireball", result.getName());
        verify(abilityRepository, times(1)).findById(1L);
        verify(abilityRepository, times(1)).save(any(Ability.class));
    }

    @Test
    void testDeleteAbility() {
        when(abilityRepository.existsById(1L)).thenReturn(true);

        boolean result = abilityService.deleteAbility(1L);

        assertTrue(result);
        verify(abilityRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAbility_NotFound() {
        when(abilityRepository.existsById(1L)).thenReturn(false);

        boolean result = abilityService.deleteAbility(1L);

        assertFalse(result);
        verify(abilityRepository, never()).deleteById(1L);
    }

}
