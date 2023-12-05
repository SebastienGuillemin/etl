package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

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
public class ProduitCoupage extends Constituant {
    @Override
    public Resource getResource(Model model) {
        return super.getPartielResource(model);
    }    
}
