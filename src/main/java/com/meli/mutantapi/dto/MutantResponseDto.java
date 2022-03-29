package com.meli.mutantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class MutantResponseDto {
    @JsonProperty("is_mutant_dna")
    Boolean isMutant;
}
