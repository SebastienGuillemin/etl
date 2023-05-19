package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "substance")
@ToString
public class Substance extends BaseEntity {
    private String libelle;

    public Substance() {
        this.simpleName = "substance";
    }

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getSimpleName());
        
        // TODO : faire comme dans le bd et supprimer le concept de molécule de l'ontologie ?
        Resource molecule = model.createResource(RDFRepository.PREFIX + this.libelle);
        Property nomMolecule = model.createProperty(RDFRepository.PREFIX + "nomMolecule");
        Property aMolecule = model.createProperty(RDFRepository.PREFIX + "aMolecule");

        molecule.addProperty(nomMolecule, this.libelle);
        resource.addProperty(aMolecule, molecule);
                
        return resource;
    }
}
