package com.meli.mutantapi.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class DnaValidator {

    private static final List<String> BASES = List.of("AAAA", "TTTT", "CCCC", "GGGG");
    private static int MUTANT_BASE_TIMES = 2;

    public Boolean validateDna (List<String> dna) {
        int n = dna.size();
        List<StringBuilder> cols = IntStream.range(0, n).mapToObj(StringBuilder::new).collect(Collectors.toList());
        Map<Integer, StringBuilder> diagonal = new HashMap<>();
        Map<Integer, StringBuilder> invDiagonal = new HashMap<>();
        for (var rowIndex = 0; rowIndex < n; rowIndex++) {
            String row = dna.get(rowIndex);
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
        Stream<String> dnaParts = Stream.of(
                dna.stream(),
                cols.stream().map(Objects::toString),
                diagonal.values().stream().map(Objects::toString),
                invDiagonal.values().stream().map(Objects::toString))
                .flatMap(Function.identity());
        return validateDnaParts(dnaParts);
    }

    private boolean validateDnaParts (Stream<String> dnaParts) {
        return dnaParts.unordered().parallel()
                .map(this::countTimesBasesInDnaPart)
                .filter(count -> count > 0)
                .flatMap(timesBaseFound -> IntStream.range(0, timesBaseFound).mapToObj(i -> true))
                .limit(MUTANT_BASE_TIMES).count() == MUTANT_BASE_TIMES;
    }

    private int countTimesBasesInDnaPart (String dnaPart) {
        return BASES.stream()
                .map(base -> StringUtils.countOccurrencesOf(dnaPart, base))
                .reduce(Integer::sum).orElse(0);
    }
}
