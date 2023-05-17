package com.sebastienguillemin.stups.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import com.sebastienguillemin.stups.model.Echantillon;

public class RDFRepository {
    private static final String PREFIX = "http://www.stups.fr/ontologies/2023/stups/";

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
        for (Echantillon echantillon : echantillons) {
            model.createResource(PREFIX + echantillon.getResourceName());
        }

        System.out.println("Resource : " + model.getResource(PREFIX + echantillons.get(0).getResourceName()));

        this.writeFile();
    }

    private void writeFile() {
        try {
            FileWriter out = new FileWriter(this.filename + "_new");
            this.model.write(out, "TTL");

            System.out.println("RDF graph saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
