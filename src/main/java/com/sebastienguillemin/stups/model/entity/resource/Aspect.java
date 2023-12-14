package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

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
    public String libelle;
    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property libelleAspect = model.createProperty(RDFRepository.PREFIX + "libelleAspect");

        resource.addProperty(libelleAspect, this.libelle);
                
        return resource;
    }

    @Override
    public String getResourceName() {        
        return StringUtils.stripAccents(this.simpleName + "_" + this.libelle).toLowerCase().replace(' ', '_');
    }
}
