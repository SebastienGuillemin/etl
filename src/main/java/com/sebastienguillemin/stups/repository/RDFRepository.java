package com.sebastienguillemin.stups.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;

public class RDFRepository {
    public static final String PREFIX = "http://www.stups.fr/ontologies/2023/stups/";

    private Model model;

    public RDFRepository(String baseOntologyFile) {
        this.model = ModelFactory.createDefaultModel();
        this.readFile(baseOntologyFile);
    }
    
    private void readFile(String filename) {        
        this.model.read(filename, "TTL");
    }

    public void populate(List<Echantillon> echantillons) {
        Resource echantillonResource;
        Property idEchantillon = this.model.createProperty(RDFRepository.PREFIX + "id");
        Property estProcheDe = this.model.createProperty(RDFRepository.PREFIX + "estProcheDe");
        Property estProcheChimiquementDe = this.model.createProperty(RDFRepository.PREFIX + "estProcheChimiquementDe");

        
        for (Echantillon echantillon : echantillons) {
            echantillonResource = echantillon.getResource(this.model);

            for (Resource neighbor : echantillon.getNeighbors(this.model))
                echantillonResource.addProperty(estProcheDe, neighbor);

            for (Resource neighbor : echantillon.getChemicalNeighborsResources(this.model))
                echantillonResource.addProperty(estProcheChimiquementDe, neighbor);
            
                this.model.add(echantillonResource, idEchantillon, echantillon.getId() + "");
        }
    }

    public void saveOntology(String filename) {
        try {
            FileWriter out = new FileWriter(filename);
            this.model.write(out, "TTL");

            System.out.println("RDF graph saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
