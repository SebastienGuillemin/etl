package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Unite extends BaseEntity {
    public String type;
    public String libelle;

    @Column(name = "lib_short")
    public String libShort;

    @Override
    public Resource getResource(Model model) {
        throw new UnsupportedOperationException("Unimplemented method 'getResource'");
    }
}
