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
public class Aspect extends BaseEntity {
    public String libelle;

    public Aspect() {
        this.simpleName = "aspect";
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getSimpleName());
        Property libelleAspect = model.createProperty(RDFRepository.PREFIX + "libelleAspect");

        resource.addProperty(libelleAspect, this.libelle);
                
        return resource;
    }
}
