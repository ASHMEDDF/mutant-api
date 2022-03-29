package com.meli.mutantapi.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class DnaValidator {

    private static final List<String> BASES = List.of("AAAA", "TTTT", "CCCC", "GGGG");

    public Boolean validateDna (List<String> dna) {
        int n = dna.size();
        List<StringBuilder> cols = IntStream.range(0, n).mapToObj(StringBuilder::new).collect(Collectors.toList());
        Map<Integer, StringBuilder> diagonal = new HashMap<>();
        Map<Integer, StringBuilder> invDiagonal = new HashMap<>();

        for (var rowIndex = 0; rowIndex < n; rowIndex++) {
            String row = dna.get(rowIndex);
            if (isMutantDnaPart(row)) {
                return true;
            }
            for (var colIndex = 0; colIndex < n; colIndex++) {
                var element = row.charAt(colIndex);
                cols.get(colIndex).append(element);
                var diagonalIndex = colIndex - rowIndex; // col = row + b => b = col - row
                diagonal.computeIfPresent(diagonalIndex, (k, v) -> v.append(element));
                diagonal.computeIfAbsent(diagonalIndex, k -> new StringBuilder().append(element));
                var invDiagonalIndex = rowIndex + colIndex; // col = -row + b => b = row + col
                invDiagonal.computeIfPresent(invDiagonalIndex, (k, v) -> v.append(element));
                invDiagonal.computeIfAbsent(invDiagonalIndex, k -> new StringBuilder().append(element));
            }
        }
        boolean colValidation = validateDnaParts(cols.stream());
        boolean diagonalValidation = validateDnaParts(diagonal.values().stream());
        boolean invDiagonalValidation = validateDnaParts(invDiagonal.values().stream());
        return colValidation || diagonalValidation || invDiagonalValidation;
    }

    private boolean validateDnaParts (Stream<StringBuilder> dnaParts) {
        return dnaParts.map(StringBuilder::toString)
                .map(this::isMutantDnaPart)
                .anyMatch(result -> result.equals(true));
    }

    private boolean isMutantDnaPart (String dnaPart) {
        return BASES.stream().map(dnaPart::contains).anyMatch(result -> result.equals(true));
    }
}
