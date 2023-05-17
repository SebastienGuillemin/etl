package com.sebastienguillemin.stups.repository;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class RDFRepository {
    private Model model;
    private String filename;

    public RDFRepository(String filename) {
        this.model = ModelFactory.createDefaultModel();
        this.filename = filename;

        try {
            this.readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void readFile() throws FileNotFoundException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(this.filename);

        if (in == null)
            throw new FileNotFoundException(this.filename);
        
        this.model.read(this.filename, "TTL");
        this.model.write(System.out);
    }
}
