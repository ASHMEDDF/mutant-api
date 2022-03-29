package com.meli.mutantapi.controller;

import com.meli.mutantapi.dto.MutantRequestDto;
import com.meli.mutantapi.dto.MutantResponseDto;
import com.meli.mutantapi.dto.StatsResponseDto;
import com.meli.mutantapi.service.MutantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MutantController {

    private MutantService mutantService;

    @PostMapping("/mutant")
    public ResponseEntity<MutantResponseDto> mutant (@RequestBody MutantRequestDto mutantRequestDto) {
        Boolean mutant = mutantService.isMutant(mutantRequestDto.getDna());
        if (Boolean.TRUE.equals(mutant)){
            return new ResponseEntity<>(new MutantResponseDto(true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MutantResponseDto(mutant), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponseDto> statsMutants () {
        return new ResponseEntity<>(mutantService.getStats(), HttpStatus.OK);
    }
}
