package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.model.entity.base.Type;
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
@Table(name = "substance")
@ToString
public class Substance extends BaseEntity implements ResourceEntity {
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Type type;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property nomSubstance = model.createProperty(RDFRepository.PREFIX + "nomSubstance");

        resource.addProperty(nomSubstance, this.libelle);

        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Substance"));

        return resource;
    }
}
