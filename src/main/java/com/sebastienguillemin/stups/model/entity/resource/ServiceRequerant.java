package com.sebastienguillemin.stups.model.entity.resource;

import java.util.HashMap;

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
@ToString
@Table(name = "service_requerant")
public class ServiceRequerant extends BaseEntity implements ResourceEntity {
    public static final HashMap<String, Resource> allEntitiesResources = new HashMap<>();
    
    public String libelle;

    @Override
    public Resource getResource(Model model) {
        // Creating sample resource
        String resourceName = RDFRepository.PREFIX + this.getResourceName();
        if (ServiceRequerant.allEntitiesResources.containsKey(resourceName))
            return ServiceRequerant.allEntitiesResources.get(resourceName);
        
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        ServiceRequerant.allEntitiesResources.put(resourceName, resource);

        Property nomService = model.createProperty(RDFRepository.PREFIX + "nomService");

        resource.addProperty(nomService, this.libelle);
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "ServiceRequerant"));

                
        return resource;
    }
}
