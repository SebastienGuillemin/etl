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
    private String filename;

    public RDFRepository(String filename) {
        this.model = ModelFactory.createDefaultModel();
        this.filename = filename;

        this.readFile();
    }
    
    private void readFile() {        
        this.model.read(this.filename, "TTL");
    }

    public void populate(List<Echantillon> echantillons) {
        Resource echantillonResource;
        Property idEchantillon = model.createProperty(RDFRepository.PREFIX + "id");
        Property estProcheDe = model.createProperty(RDFRepository.PREFIX + "estProcheDe");
        Property estProcheChimiquementDe = model.createProperty(RDFRepository.PREFIX + "estProcheChimiquementDe");

        
        for (Echantillon echantillon : echantillons) {
            echantillonResource = echantillon.getResource(model);

            for (Resource neighbor : echantillon.getNeighbors(model))
                echantillonResource.addProperty(estProcheDe, neighbor);

            for (Resource neighbor : echantillon.getChemicalNeighbors(model))
                echantillonResource.addProperty(estProcheChimiquementDe, neighbor);
            
            model.add(echantillonResource, idEchantillon, echantillon.getId() + "");
        }

        this.writeFile();
    }

    private void writeFile() {
        try {
            FileWriter out = new FileWriter("new_" + this.filename);
            this.model.write(out, "TTL");

            System.out.println("RDF graph saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
