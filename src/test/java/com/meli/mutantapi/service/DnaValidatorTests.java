package com.meli.mutantapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DnaValidatorTests {

    private DnaValidator dnaValidator;

    @BeforeEach
    public void setUp () {
        this.dnaValidator = new DnaValidator();
    }

    @Test
    void dnaIsNotMutantInRow () {
        List<String> dna = List.of(
                "XXXX",
                "XXXX",
                "XXXX",
                "XXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertFalse(actual);
    }

    @Test
    void dnaIsMutantInRow () {
        List<String> dna = List.of(
                "XXXX",
                "XXXX",
                "XXXX",
                "AAAA");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }


    @Test
    void dnaIsMutantInCol () {
        List<String> dna = List.of(
                "AXXX",
                "AXXX",
                "AXXX",
                "AXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }

    @Test
    void dnaIsMutantInDiagonal () {
        List<String> dna = List.of(
                "AXXX",
                "XAXX",
                "XXAX",
                "XXXA");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }

    @Test
    void dnaIsMutantInInvDiagonal () {
        List<String> dna = List.of(
                "XXXA",
                "XXAX",
                "XAXX",
                "AXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }
}
