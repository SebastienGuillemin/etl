package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "lot_echantillon")
@ToString
public class LotEchantillon extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;

    @ManyToOne
    @JoinColumn(name = "id_composition_lien")
    private Composition composition_lien;    
    
    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Property id = model.createProperty(RDFRepository.PREFIX + "id");
        resource.addProperty(id, this.id + "");

        return resource;
    }
    
}
