package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.model.ResourceEntity;
import com.sebastienguillemin.stups.model.entity.base.Composition;
import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Constituant extends ResourceEntity {
    @ManyToOne
    @JoinColumn(name = "id_composition")
    protected Composition composition;

    @ManyToOne
    @JoinColumn(name = "id_substance")
    protected Substance substance;

    @Column(name = "taux")
    protected String dosage;

    protected boolean trace;

    public float getDosage() {
        // TODO : que faire si pas de dosage et pas de en trace ?
        return (this.dosage != null) ? Float.valueOf(this.dosage) : (this.trace) ? 0 : 0;
    }

    protected Resource getPartielResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property aSubstance = model.createProperty(RDFRepository.PREFIX + "aSubstance");
        Property dosage = model.createProperty(RDFRepository.PREFIX + "dosage");

        resource.addProperty(aSubstance, this.substance.getResource(model));
        resource.addLiteral(dosage, this.getDosage());

        return resource;
    }
}
