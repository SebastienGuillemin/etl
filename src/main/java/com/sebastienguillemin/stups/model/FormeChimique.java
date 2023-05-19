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
@Table(name = "forme_chimique")
@ToString
public class FormeChimique extends BaseEntity {
    private String libelle;

    public FormeChimique() {
        this.simpleName = "forme_chimique";
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getSimpleName());
        Property libelleFormeChimique = model.createProperty(RDFRepository.PREFIX + "libelleFormeChimique");

        resource.addProperty(libelleFormeChimique, this.libelle);
                
        return resource;
    }
}
