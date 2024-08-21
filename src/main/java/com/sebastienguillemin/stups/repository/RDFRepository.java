package com.sebastienguillemin.stups.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.jena.ontapi.OntModelFactory;
import org.apache.jena.ontapi.OntSpecification;
import org.apache.jena.ontapi.model.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFWriter;
import org.apache.jena.riot.RIOT;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.util.PropertiesReader;

public class RDFRepository {
    public static final String PREFIX;

    static {
        PropertiesReader propertiesReader = PropertiesReader.getInstance();
        PREFIX = propertiesReader.getPropertyValue("ontology.base.prefix");
    }

    private OntModel model;
    private PropertiesReader propertiesReader;

    public RDFRepository(String baseOntologyFile) {
        this.model = OntModelFactory.createModel( OntSpecification.OWL2_DL_MEM );
        this.propertiesReader = PropertiesReader.getInstance();

        this.readFile(baseOntologyFile);
    }

    private void readFile(String filename) {
        this.model.read(filename, "TTL");
    }

    public void populate(List<Echantillon> echantillons) {
        Resource echantillonResource;
        Property idEchantillon = this.model.createProperty(RDFRepository.PREFIX + "id");
        Property estProcheDe = this.model.createProperty(RDFRepository.PREFIX + "estProcheDe");
        Property estProcheChimiquementDe = null;

        boolean loadEstProcheChimiquementDe = this.propertiesReader
                .getPropertyValueBoolean("ontology.save.estProcheChimiquementDe");
        System.out.println("Load estProcheChimiquementDe : " + loadEstProcheChimiquementDe);

        if (loadEstProcheChimiquementDe)
            estProcheChimiquementDe = this.model.createProperty(RDFRepository.PREFIX + "estProcheChimiquementDe");

        for (Echantillon echantillon : echantillons) {
            echantillonResource = echantillon.getResource(this.model);

            for (Resource neighbor : echantillon.getNeighborsResources(this.model))
                echantillonResource.addProperty(estProcheDe, neighbor);

            if (loadEstProcheChimiquementDe) {
                for (Resource neighbor : echantillon.getChemicalNeighborsResources(this.model))
                    echantillonResource.addProperty(estProcheChimiquementDe, neighbor);
            }

            this.model.add(echantillonResource, idEchantillon, echantillon.getId() + "");
        }
    }

    public void saveOntology(String filename) {
        try {
            OutputStream out = new FileOutputStream(filename);
            String baseURI = this.propertiesReader.getPropertyValue("ontology.base.prefix");

            RDFWriter.source(model)
                    .base(baseURI)
                    .set(RIOT.symTurtleOmitBase, false)
                    .set(RIOT.symTurtleDirectiveStyle, "at")
                    .lang(Lang.TTL)
                    .output(out);

            System.out.println("RDF graph saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
