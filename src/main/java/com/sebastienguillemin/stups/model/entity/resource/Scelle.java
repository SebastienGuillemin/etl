package com.sebastienguillemin.stups.model.entity.resource;

import java.math.BigInteger;
import java.util.HashMap;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.BaseEntity;
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
public class Scelle extends BaseEntity implements ResourceEntity {
    public static final HashMap<String, Resource> allEntitiesResources = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "id_saisine")
    public Saisine saisine;

    @Override
    public Resource getResource(Model model) {
        String resourceName = RDFRepository.PREFIX + this.getResourceName();
        if (Scelle.allEntitiesResources.containsKey(resourceName))
            return Scelle.allEntitiesResources.get(resourceName);
        
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Scelle.allEntitiesResources.put(resourceName, resource);

        Property idProperty = model.createProperty(RDFRepository.PREFIX + "id");
        resource.addLiteral(idProperty, BigInteger.valueOf(this.id));

        Property estDansSaisine = model.createProperty(RDFRepository.PREFIX + "estDansSaisine");
        resource.addProperty(estDansSaisine, this.saisine.getResource(model));
                
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Scelle"));

        return resource;
    }
}
