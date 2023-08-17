package com.sebastienguillemin.stups.model.entity.base;

import com.sebastienguillemin.stups.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Description extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;

    @ManyToOne
    @JoinColumn(name = "id_propriete")
    private Propriete propriete;
    
    @ManyToOne
    @JoinColumn(name = "id_valeur_propriete")
    private ValeurPropriete valeurPropriete;    // For tabular values.
    private String valeur;  // For free values.
}
