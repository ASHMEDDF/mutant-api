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
    void dnaIsNotMutant () {
        List<String> dna = List.of(
                "XXXX",
                "XXXX",
                "XXXX",
                "XXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertFalse(actual);
    }

    @Test
    void dnaIsNotMutantSingleBase () {
        List<String> dna = List.of(
                "AAAA",
                "XXXX",
                "XXXX",
                "XXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertFalse(actual);
    }

    @Test
    void dnaIsMutantInRow () {
        List<String> dna = List.of(
                "AAAA",
                "XXXX",
                "XXXX",
                "AAAA");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }


    @Test
    void dnaIsMutantInCol () {
        List<String> dna = List.of(
                "AXXC",
                "AXXC",
                "AXXC",
                "AXXC");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }

    @Test
    void dnaIsMutantInDiagonal () {
        List<String> dna = List.of(
                "XXXAX",
                "XXAAX",
                "XAAXX",
                "AAXXX",
                "AXXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }

    @Test
    void dnaIsMutantInInvDiagonal () {
        List<String> dna = List.of(
                "CXXXX",
                "CCXXX",
                "XCCXX",
                "XXCCX",
                "XXXCX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }

    @Test
    void dnaIsMutantSameBaseRepeated () {
        List<String> dna = List.of(
                "AAAAAAAA",
                "XXXXXXXX",
                "XXXXXXXX",
                "XXXXXXXX",
                "XXXXXXXX",
                "XXXXXXXX",
                "XXXXXXXX",
                "XXXXXXXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertTrue(actual);
    }

    @Test
    void dnaIsNotMutantInOneLine () {
        List<String> dna = List.of(
                "XXAA",
                "AAXX",
                "XXAA",
                "AAXX");
        Boolean actual = this.dnaValidator.validateDna(dna);
        assertFalse(actual);
    }
}
