package com.sebastienguillemin.stups.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Column;
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
@Table(name = "echantillon")
@ToString
public class Echantillon extends BaseEntity {
    @Column(name = "num_echantillon")
    private String num;

    @ManyToOne
    @JoinColumn(name = "id_scelle")
    private Scelle scelle;

    @ManyToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;

    public List<Resource> getNeighbors(Model model) {
        List<Resource> resources = new ArrayList<>();

        List<LotEchantillon> lotsTete = this.composition.getLotsTete();

        for (LotEchantillon lot : lotsTete) {
            List<Echantillon> echantillons = lot.getComposition2().getEchantillons();
            for (Echantillon echantillon : echantillons)
                resources.add(echantillon.getResource(model));
        }

        return resources;
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property property = model.createProperty(RDFRepository.PREFIX + "id");
        Property aPrincipeActif = model.createProperty(RDFRepository.PREFIX + "aPrincipeActif");
        Property aProduitCoupage = model.createProperty(RDFRepository.PREFIX + "aProduitCoupage");
        Property aAspectExterne = model.createProperty(RDFRepository.PREFIX + "aAspectExterne");
        Property numeroEchantillon = model.createProperty(RDFRepository.PREFIX + "numeroEchantillon");
        Property typeDrogue = model.createProperty(RDFRepository.PREFIX + "typeDrogue");
        Property provientDe = model.createProperty(RDFRepository.PREFIX + "provientDe");
        
        resource.addProperty(property, this.id + "");
        
        for (PrincipeActif principeActif : this.composition.getPrincipeActifs()) {
            resource.addProperty(typeDrogue, principeActif.getSubstance().getType().getLibelle());
            resource.addProperty(aPrincipeActif, principeActif.getResource(model));
        }

        for (ProduitCoupage produitCoupage : this.composition.getProduitCoupages())
            resource.addProperty(aProduitCoupage, produitCoupage.getResource(model));

        Aspect aspectInterne = this.composition.getAspectInterne();
        if (aspectInterne != null) {
            Property aAspectInterne = model.createProperty(RDFRepository.PREFIX + "aAspectInterne");
            resource.addProperty(aAspectInterne, aspectInterne.getResource(model));
        }

        resource.addProperty(aAspectExterne, this.composition.getAspect().getResource(model));
        resource.addProperty(numeroEchantillon, this.num);
        resource.addProperty(provientDe, this.scelle.getResource(model));

        String commentaire = this.composition.getCommentaire();
        if (commentaire != null) {
            Property commmentaire = model.createProperty(RDFRepository.PREFIX + "commentaire");
            resource.addProperty(commmentaire, commentaire);
        }
        
        return resource;
    }
}
