package com.sebastienguillemin.stups.model.entity.resource;

import java.math.BigDecimal;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.BaseEntity;
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
public abstract class Composant extends BaseEntity implements ResourceEntity {
    @ManyToOne
    @JoinColumn(name = "id_composition")
    protected Composition composition;

    @ManyToOne
    @JoinColumn(name = "id_substance")
    protected Substance substance;

    @Column(name = "taux")
    protected String dosage;

    protected boolean trace;

    public BigDecimal getDosage() {
        // TODO : que faire si pas de dosage et pas de en trace ?
        float dosageFloat = (this.dosage != null) ? Float.valueOf(this.dosage) : (this.trace) ? 0.0f : 0.0f;
        return BigDecimal.valueOf(dosageFloat);
    }

    protected Resource getPartielResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property aSubstance = model.createProperty(RDFRepository.PREFIX + "aSubstance");
        Property dosage = model.createProperty(RDFRepository.PREFIX + "dosage");

        resource.addProperty(aSubstance, this.substance.getResource(model));
        resource.addLiteral(dosage, this.getDosage());
        
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Composant"));

        return resource;
    }
}
