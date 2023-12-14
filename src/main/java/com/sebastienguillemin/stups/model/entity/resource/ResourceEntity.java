package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

public interface ResourceEntity {
    public abstract Resource getResource(Model model);
}
