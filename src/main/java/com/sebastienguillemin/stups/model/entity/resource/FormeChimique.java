package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.BaseEntity;
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
public class FormeChimique extends BaseEntity implements ResourceEntity {
    private String libelle;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property libelleFormeChimique = model.createProperty(RDFRepository.PREFIX + "libelleFormeChimique");

        resource.addProperty(libelleFormeChimique, this.libelle);
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "FormeChimique"));
                
        return resource;
    }

    @Override
    public String getResourceName() {
        return StringUtils.stripAccents(this.simpleName + "_" + this.libelle).toLowerCase().replace(' ', '_').replaceAll("&", "et");
    } 
}
