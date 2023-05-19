package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Scelle extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_saisine")
    public Saisine saisine;

    public Scelle() {
        this.simpleName = "scelle";
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getSimpleName());
        Property estDansSaisine = model.createProperty(RDFRepository.PREFIX + "estDansSaisine");

        resource.addProperty(estDansSaisine, this.saisine.getResource(model));
                
        return resource;
    }
}
