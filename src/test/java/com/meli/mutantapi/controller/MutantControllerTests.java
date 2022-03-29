package com.meli.mutantapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.meli.mutantapi.dto.MutantRequestDto;
import com.meli.mutantapi.repository.MutantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MutantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MutantRepository mutantRepository;

    @BeforeEach
    public void setUp () {
        mutantRepository.deleteAll();
    }

    @Test
    @JsonDeserialize
    public void shouldReturnStatsMessage () throws Exception {
        this.mockMvc.perform(get("/stats")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"count_mutant_dna\":0,\"count_human_dna\":0,\"ratio\":0.0}"));
    }

    @Test
    @JsonDeserialize
    public void shouldReturnMutantsMessage () throws Exception {
        List<String> dna = List.of("XXXX", "AAAA", "XXXX", "XXXX");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                .content(asJsonString(new MutantRequestDto(dna)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"is_mutant_dna\":true}", true));
    }

    public static String asJsonString (final MutantRequestDto requestDto) {
        try {
            return new ObjectMapper().writeValueAsString(requestDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
