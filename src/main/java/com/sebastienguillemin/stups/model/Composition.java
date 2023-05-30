package com.sebastienguillemin.stups.model;

import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

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

    @OneToMany(mappedBy = "composition")
    public List<LotEchantillon> lotEntrants;

    @OneToMany(mappedBy = "composition_lien")
    public List<LotEchantillon> lotSortants;

    @Override
    public Resource getResource(Model model) {
        return null;
    }    
}