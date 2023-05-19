package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @ManyToOne
    @JoinColumn(name = "id_scelle")
    private Scelle scelle;

    @OneToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;

    public Echantillon() {
        this.simpleName = "echantillon";
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property aPrincipeActif = model.createProperty(RDFRepository.PREFIX + "aPrincipeActif");
        Property aAspectInterne = model.createProperty(RDFRepository.PREFIX + "aAspectInterne");
        Property aAspectExterne = model.createProperty(RDFRepository.PREFIX + "aAspectExterne");

        // TODO : que faire si plusieurs principes actifs ?
        resource.addProperty(aPrincipeActif, this.composition.getPrincipeActifs().get(0).getResource(model));

        Aspect aspectInterne = this.composition.getAspectInterne();
        if (aspectInterne != null)
            resource.addProperty(aAspectInterne, aspectInterne.getResource(model));

        resource.addProperty(aAspectExterne, this.composition.getAspect().getResource(model));

        System.out.println(RDFRepository.PREFIX + this.getResourceName() + " created.");
        return resource;
    }
}
