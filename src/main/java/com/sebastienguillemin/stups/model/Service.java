package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Service extends BaseEntity {
    public String nom;

    public Service() {
        this.simpleName = "service";
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getSimpleName());
        Property nomService = model.createProperty(RDFRepository.PREFIX + "nomService");

        resource.addProperty(nomService, this.nom);
                
        return resource;
    }
}
