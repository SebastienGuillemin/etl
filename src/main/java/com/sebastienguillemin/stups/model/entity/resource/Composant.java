package com.sebastienguillemin.stups.model.entity.resource;

import jakarta.persistence.Transient;

import java.util.HashMap;

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
/**
 * TODO : reset "resource" when an attribute is updated
 */
public abstract class Composant extends BaseEntity implements ResourceEntity {
    public final static float DETECTION_LIMIT = 0.03f;
    public final static float MISSING_RATE_IMPUTATION_VALUE = DETECTION_LIMIT / 2.0f;

    public static final HashMap<String, Resource> allEntitiesResources = new HashMap<>();


    @ManyToOne
    @JoinColumn(name = "id_composition")
    protected Composition composition;

    @ManyToOne
    @JoinColumn(name = "id_substance")
    protected Substance substance;

    @Column(name = "taux")
    protected String dosage;

    protected boolean trace;

    @Transient
    protected Resource resource;

    public float getDosage() {
        return (this.dosage != null) ? Float.valueOf(this.dosage) : (this.trace) ? 0.0f : MISSING_RATE_IMPUTATION_VALUE;
    }

    protected Resource getPartielResource(Model model) {
        // Creating sample resource
        String resourceName = RDFRepository.PREFIX + this.getResourceName();
        if (Composant.allEntitiesResources.containsKey(resourceName))
            return Composant.allEntitiesResources.get(resourceName);
        
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Composant.allEntitiesResources.put(resourceName, resource);

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
