package com.meli.mutantapi.repository;

import com.meli.mutantapi.domain.Dna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends JpaRepository<Dna, String> {

    @Query("SELECT COUNT(dna) FROM Dna dna")
    long count ();

    @Query("SELECT COUNT(dna) FROM Dna dna WHERE dna.isMutant = true")
    long countMutants ();
}
