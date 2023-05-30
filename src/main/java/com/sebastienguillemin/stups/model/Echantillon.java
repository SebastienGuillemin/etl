package com.sebastienguillemin.stups.model;

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

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property aPrincipeActif = model.createProperty(RDFRepository.PREFIX + "aPrincipeActif");
        Property aProduitCoupage = model.createProperty(RDFRepository.PREFIX + "aProduitCoupage");
        Property aAspectExterne = model.createProperty(RDFRepository.PREFIX + "aAspectExterne");
        Property numeroEchantillon = model.createProperty(RDFRepository.PREFIX + "numeroEchantillon");
        Property typeDrogue = model.createProperty(RDFRepository.PREFIX + "typeDrogue");
        Property provientDe = model.createProperty(RDFRepository.PREFIX + "provientDe");
        
        // TODO : que faire si plusieurs principes actifs ?
        PrincipeActif principeActif = this.composition.getPrincipeActifs().get(0);
        resource.addProperty(aPrincipeActif, principeActif.getResource(model));

        for (ProduitCoupage produitCoupage : this.composition.getProduitCoupages())
            resource.addProperty(aProduitCoupage, produitCoupage.getResource(model));

        Aspect aspectInterne = this.composition.getAspectInterne();
        if (aspectInterne != null) {
            Property aAspectInterne = model.createProperty(RDFRepository.PREFIX + "aAspectInterne");
            resource.addProperty(aAspectInterne, aspectInterne.getResource(model));
        }

        resource.addProperty(aAspectExterne, this.composition.getAspect().getResource(model));
        resource.addProperty(numeroEchantillon, this.num);
        resource.addProperty(typeDrogue, principeActif.getSubstance().getType().getLibelle());
        resource.addProperty(provientDe, this.scelle.getResource(model));

        String commentaire = this.composition.getCommentaire();
        if (commentaire != null) {
            Property commmentaire = model.createProperty(RDFRepository.PREFIX + "commentaire");
            resource.addProperty(commmentaire, commentaire);
        }

        List<LotEchantillon> lotEntrants = this.composition.getLotEntrants();
        List<LotEchantillon> lotSortants = this.composition.getLotEntrants();
        Lot lot = new Lot();

        if (lotEntrants != null) {
        }
        
        if (lot.size() > 0) {
            for(LotEchantillon lot : lots) {
                resource.addProperty(estDansLot, lot.getResource(model));
            }
            Property estDansLot = model.createProperty(RDFRepository.PREFIX + "estDansLot");

        }
        return resource;
    }
}
