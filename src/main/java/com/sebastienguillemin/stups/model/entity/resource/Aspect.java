package com.sebastienguillemin.stups.model.entity.resource;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
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
public class Aspect extends BaseEntity implements ResourceEntity {
    public static final HashMap<String, Resource> allEntitiesResources = new HashMap<>();

    public String libelle;
    @Override
    public Resource getResource(Model model) {
        // Creating sample resource
        String resourceName = RDFRepository.PREFIX + this.getResourceName();
        if (Aspect.allEntitiesResources.containsKey(resourceName))
            return Aspect.allEntitiesResources.get(resourceName);
        
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Aspect.allEntitiesResources.put(resourceName, resource);

        Aspect.allEntitiesResources.put(resourceName, resource);
        Property libelleAspect = model.createProperty(RDFRepository.PREFIX + "libelleAspect");

        resource.addProperty(libelleAspect, this.libelle);
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Aspect"));
                
        return resource;
    }

    @Override
    public String getResourceName() {        
        return StringUtils.stripAccents(this.simpleName + "_" + this.libelle).toLowerCase().replace(' ', '_');
    }
}
