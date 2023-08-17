package com.sebastienguillemin.stups.model.entity.base;

import java.util.List;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.model.entity.resource.Aspect;
import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.model.entity.resource.PrincipeActif;
import com.sebastienguillemin.stups.model.entity.resource.ProduitCoupage;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Composition extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_aspect")
    public Aspect aspect;

    @ManyToOne
    @JoinColumn(name = "id_aspect_interne")
    public Aspect aspectInterne;

    @ManyToOne
    @JoinColumn(name = "id_unite")
    public Unite unite;

    public Float quantite;
    public String commentaire;

    @OneToMany(mappedBy = "composition")
    private List<Echantillon> echantillons;

    @OneToMany(mappedBy = "composition")
    private List<PrincipeActif> principeActifs;

    @OneToMany(mappedBy = "composition")
    private List<ProduitCoupage> produitCoupages;

    @OneToMany(mappedBy = "composition1")
    public List<LotEchantillon> lotsTete;

    @OneToMany(mappedBy = "composition2")
    public List<LotEchantillon> lotsQueue;

    @OneToMany(mappedBy = "composition")
    public List<Description> descriptions;  
}