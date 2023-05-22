package com.sebastienguillemin.stups.model;

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
@Table(name = "type")
@ToString
public class Type extends BaseEntity {
    private String code;
    private String categorie;
    private String libelle;
    private int rang;

    @Override
    public Resource getResource(Model model) {
        return null;
    }
    
}
