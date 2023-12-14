package com.sebastienguillemin.stups.model.entity.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.model.entity.base.Composition;
import com.sebastienguillemin.stups.model.entity.base.Description;
import com.sebastienguillemin.stups.model.entity.base.LotEchantillon;
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
public class Echantillon extends BaseEntity implements ResourceEntity {
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

        for (LotEchantillon lot : this.composition.getLotsTete()) {
            if (lot.getTypeLien().getLibelle().equals("Macroscopique") || lot.getTypeLien().getLibelle().equals("Composition atypique")) {
                
                for (Echantillon echantillon : lot.getComposition2().getEchantillons())
                    resources.add(echantillon.getResource(model));
            }
        }

        return resources;
    }
    
    public List<Echantillon> getChemicalNeighbors(Model model) {
        List<Echantillon> neighbors = new ArrayList<>();

        for (LotEchantillon lot : this.composition.getLotsTete()) {
            if (lot.getTypeLien().getLibelle().equals("Profilage")) {
                
                for (Echantillon echantillon : lot.getComposition2().getEchantillons())
                    neighbors.add(echantillon);
            }
        }

        return neighbors;
    }


    public List<Resource> getChemicalNeighborsResources(Model model) {
        List<Resource> resources = new ArrayList<>();

        for (LotEchantillon lot : this.composition.getLotsTete()) {
            if (lot.getTypeLien().getLibelle().equals("Profilage")) {
                
                for (Echantillon echantillon : lot.getComposition2().getEchantillons())
                    resources.add(echantillon.getResource(model));
            }
        }

        return resources;
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property idProperty = model.createProperty(RDFRepository.PREFIX + "id");
        Property aPrincipeActif = model.createProperty(RDFRepository.PREFIX + "aPrincipeActif");
        Property aProduitCoupage = model.createProperty(RDFRepository.PREFIX + "aProduitCoupage");
        Property aAspectExterne = model.createProperty(RDFRepository.PREFIX + "aAspectExterne");
        Property numeroEchantillon = model.createProperty(RDFRepository.PREFIX + "numeroEchantillon");
        Property typeDrogue = model.createProperty(RDFRepository.PREFIX + "typeDrogue");
        Property provientDe = model.createProperty(RDFRepository.PREFIX + "provientDe");
        
        resource.addLiteral(idProperty, this.id + "");
        
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

        for (Description description : this.composition.getDescriptions()) {
            Property descriptionProperty = model.createProperty(RDFRepository.PREFIX + description.getPropriete().getLibelle());
            
            String valeur;
            if ((valeur = description.getValeur()) == null && description.getValeurPropriete() != null)
                valeur = description.getValeurPropriete().getLibelle();

            if (valeur != null)
                resource.addLiteral(descriptionProperty, valeur);
        }
        
        return resource;
    }
}
