package com.meli.mutantapi.service;

import com.meli.mutantapi.domain.Dna;
import com.meli.mutantapi.dto.StatsResponseDto;
import com.meli.mutantapi.repository.MutantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTests {

    @Mock
    private MutantRepository mutantRepository;

    @Mock
    private DnaValidator dnaValidator;

    private MutantService mutantService;

    @BeforeEach
    public void setUp () {
        mutantService = new MutantService(mutantRepository, dnaValidator);
    }

    @Test
    public void isMutantWithNewDna () {
        List<String> dna = List.of("XXXX", "AAAA", "XXXX", "XXXX");
        String dnaId = "XXXX-AAAA-XXXX-XXXX";
        Dna entity = new Dna();
        entity.setDna(dnaId);
        entity.setIsMutant(true);
        when(mutantRepository.findById(dnaId)).thenReturn(Optional.empty());
        when(mutantRepository.save(entity)).thenReturn(entity);
        when(dnaValidator.validateDna(dna)).thenReturn(true);
        Boolean actual = mutantService.isMutant(dna);
        assertTrue(actual);
        verify(mutantRepository, times(1)).findById(dnaId);
        verify(dnaValidator, times(1)).validateDna(dna);
        verify(mutantRepository, times(1)).save(any());
    }

    @Test
    public void isMutantWithRepeatedDna () {
        List<String> dna = List.of("XXXX", "AAAA", "XXXX", "XXXX");
        String dnaId = "XXXX-AAAA-XXXX-XXXX";
        when(mutantRepository.findById(dnaId)).thenReturn(Optional.of(new Dna(dnaId, false)));
        Boolean actual = mutantService.isMutant(dna);
        assertFalse(actual);
        verify(mutantRepository, times(1)).findById(dnaId);
        verify(dnaValidator, times(0)).validateDna(dna);
        verify(mutantRepository, times(0)).save(any());
    }

    @Test
    public void invalidDnaStructure () {
        assertThrows(IllegalArgumentException.class, () -> {
            List<String> dna = List.of("XXXA");
            mutantService.isMutant(dna);
        });
    }

    @Test
    public void getStats () {
        var count = 5L;
        var mutantCount = 3L;
        var humanCount = 2L;
        var ratio = 1.0 * mutantCount / humanCount;
        when(mutantRepository.count()).thenReturn(count);
        when(mutantRepository.countMutants()).thenReturn(mutantCount);
        StatsResponseDto stats = mutantService.getStats();
        assertNotNull(stats);
        assertEquals(mutantCount, stats.getCountMutant());
        assertEquals(humanCount, stats.getCountHuman());
        assertEquals(ratio, stats.getRatio());
        verify(mutantRepository, times(1)).count();
        verify(mutantRepository, times(1)).countMutants();
    }
}
