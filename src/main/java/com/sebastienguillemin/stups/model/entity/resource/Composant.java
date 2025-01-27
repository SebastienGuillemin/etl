package com.sebastienguillemin.stups.model.entity.resource;

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

    public float getDosage() {
        return (this.dosage != null) ? Float.valueOf(this.dosage) : (this.trace) ? 0.0f : -1.0f;
    }

    protected Resource getPartielResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());

        Property aSubstance = model.createProperty(RDFRepository.PREFIX + "aSubstance");
        resource.addProperty(aSubstance, this.substance.getResource(model));

        // Consider property dosage only if not a Cannabis sample
        if (!this.substance.getType().getLibelle().equals("Cannabis")) {
            float dosageLiteral = this.getDosage();
            if (dosageLiteral != -1.0f) {
                Property dosage = model.createProperty(RDFRepository.PREFIX + "dosage");
                resource.addLiteral(dosage, dosageLiteral);
            }
        }

        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Composant"));

        return resource;
    }
}
