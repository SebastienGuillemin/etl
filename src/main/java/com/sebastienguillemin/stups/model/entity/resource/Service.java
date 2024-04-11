package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Service extends BaseEntity implements ResourceEntity {
    public String nom;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property nomService = model.createProperty(RDFRepository.PREFIX + "nomService");

        resource.addProperty(nomService, this.nom);

        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Service"));
                
        return resource;
    }
}
