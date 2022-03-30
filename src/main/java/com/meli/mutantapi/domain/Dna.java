package com.meli.mutantapi.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name="DNA")
public class Dna implements Serializable {

    @Id
    @Column(name="dna")
    private String dna;

    @Column(name="is_mutant")
    private Boolean isMutant;
}
