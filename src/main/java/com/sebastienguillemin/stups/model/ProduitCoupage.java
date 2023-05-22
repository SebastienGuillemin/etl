package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

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
@Table(name = "constituant")
@ToString
public class ProduitCoupage extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;

    @ManyToOne
    @JoinColumn(name = "id_substance")
    private Substance substance;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property aSubstance = model.createProperty(RDFRepository.PREFIX + "aSubstance");

        resource.addProperty(aSubstance, this.substance.getResource(model));
                
        return resource;
    }    
}
