package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "constituant")
@ToString
public class ProduitCoupage extends Composant {
    @Override
    public Resource getResource(Model model) {
        Resource resource = super.getPartielResource(model);
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "ProduitCoupage"));

        return resource;
    }    
}
