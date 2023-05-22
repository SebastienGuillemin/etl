package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "substance")
@ToString
public class Substance extends BaseEntity {
    private String libelle;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property nomSubstance = model.createProperty(RDFRepository.PREFIX + "nomSubstance");

        resource.addProperty(nomSubstance, this.libelle);
                
        return resource;
    }
}
