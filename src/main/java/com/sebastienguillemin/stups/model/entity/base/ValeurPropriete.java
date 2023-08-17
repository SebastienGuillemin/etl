package com.sebastienguillemin.stups.model.entity.base;

import com.sebastienguillemin.stups.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "valeur_propriete")
@ToString
public class ValeurPropriete extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_propriete")
    private Propriete propriete;
    private String libelle;    
}
