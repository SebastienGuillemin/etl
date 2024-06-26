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
@Table(name = "lot_echantillon")
@ToString
public class LotEchantillon extends BaseEntity {    
    @ManyToOne
    @JoinColumn(name = "id_composition")
    private Composition composition1;

    @ManyToOne
    @JoinColumn(name = "id_composition_lien")
    private Composition composition2;

    @ManyToOne
    @JoinColumn(name = "id_type_lien")
    private Type typeLien;
}
