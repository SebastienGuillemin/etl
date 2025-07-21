package com.sebastienguillemin.stups.model.entity.resource;

import java.util.HashMap;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

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
    public static final HashMap<String, Resource> allEntitiesResources = new HashMap<>();

    private String libelle;

    @Override
    public Resource getResource(Model model) {
        String resourceName = RDFRepository.PREFIX + this.getResourceName();
        if (FormeChimique.allEntitiesResources.containsKey(resourceName))
            return FormeChimique.allEntitiesResources.get(resourceName);
        
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        FormeChimique.allEntitiesResources.put(resourceName, resource);
        return resource;
    }

    @Override
    public String getResourceName() {
        switch (this.libelle) {
            // Need to rewrite these two labels
            case "Base & HCl":
                return "BaseEtHCL";

            case "Indéterminée":
                return "Indeterminee";

            default:
                return this.libelle;
        }
    }
}
