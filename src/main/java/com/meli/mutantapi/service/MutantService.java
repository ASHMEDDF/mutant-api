package com.meli.mutantapi.service;

import com.meli.mutantapi.domain.Dna;
import com.meli.mutantapi.dto.StatsResponseDto;
import com.meli.mutantapi.repository.MutantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MutantService {

    private MutantRepository mutantRepository;
    private DnaValidator dnaValidator;

    public Boolean isMutant (List<String> dna) {

        validateDnaStructure(dna);
        var dnaId = String.join("-", dna);
        return mutantRepository.findById(dnaId).map(Dna::getIsMutant).orElseGet(() -> {
            Boolean isMutant = dnaValidator.validateDna(dna);
            return saveDna(dnaId, isMutant).getIsMutant();
        });
    }

    public StatsResponseDto getStats () {
        var count = mutantRepository.count();
        var countMutant = mutantRepository.countMutants();
        var countHuman = count - countMutant;
        var ratio = countHuman > 0 ? 1.0 * countMutant / countHuman : 0.0;
        return new StatsResponseDto(countMutant, countHuman, ratio);
    }

    private void validateDnaStructure (List<String> dna) {
        var n = dna.size();
        dna.forEach(dnaPart -> {
            if (dnaPart.length() != n) {
                throw new IllegalArgumentException("Invalid dna structure");
            }
        });
    }

    public Dna saveDna (String dnaId, Boolean isMutant) {
        var dna = new Dna();
        dna.setDna(dnaId);
        dna.setIsMutant(isMutant);
        return mutantRepository.save(dna);
    }
}
