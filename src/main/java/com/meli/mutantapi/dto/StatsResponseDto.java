package com.meli.mutantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class StatsResponseDto {
    @JsonProperty("count_mutant_dna")
    Long countMutant;
    @JsonProperty("count_human_dna")
    Long countHuman;
    @JsonProperty("ratio")
    Double ratio;
}
