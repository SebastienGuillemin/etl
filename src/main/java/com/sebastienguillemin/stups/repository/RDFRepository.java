package com.sebastienguillemin.stups.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFWriter;
import org.apache.jena.riot.RIOT;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.repository.filtering.EchantillonFilter;
import com.sebastienguillemin.stups.util.PropertiesReader;

public class RDFRepository {
    public static final String PREFIX;

    static {
        PropertiesReader propertiesReader = PropertiesReader.getInstance();
        PREFIX = propertiesReader.getPropertyValue("ontology.base.prefix");
    }

    private Model model;
    private PropertiesReader propertiesReader;
    private EchantillonFilter echantillonFilter;
    private boolean STUPSevaluation;

    public RDFRepository(String baseOntologyFile, boolean STUPSevaluation) {
        this.model = ModelFactory.createDefaultModel();
        this.propertiesReader = PropertiesReader.getInstance();
        this.echantillonFilter = new EchantillonFilter(STUPSevaluation);
        this.STUPSevaluation = STUPSevaluation;

        this.readFile(baseOntologyFile);
    }

    private void readFile(String filename) {
        this.model.read(filename, "TTL");
    }

    public void populate(List<Echantillon> echantillons) {
        Resource echantillonResource;
        Property idEchantillon = this.model.createProperty(RDFRepository.PREFIX + "id");
        Property estLieA = null;
        Property estProcheChimiquementDe = null;

        boolean loadEstLieA = this.propertiesReader.getPropertyValueBoolean("ontology.save.estLieA");
        boolean loadEstProcheChimiquementDe = this.propertiesReader.getPropertyValueBoolean("ontology.save.estProcheChimiquementDe");
                
        System.out.println("Load loadEstLieA : " + loadEstLieA);
        System.out.println("Load estProcheChimiquementDe : " + loadEstProcheChimiquementDe + "\n");

        if (loadEstLieA)
            estLieA = this.model.createProperty(RDFRepository.PREFIX + "estLieA");

        if (loadEstProcheChimiquementDe)
            estProcheChimiquementDe = this.model.createProperty(RDFRepository.PREFIX + "estProcheChimiquementDe");

        for (Echantillon echantillon : echantillons) {
            if (!echantillonFilter.filter(echantillon.getId())) {
                continue;
            }

            echantillonResource = echantillon.getResource(this.model);

            if (loadEstLieA && !this.STUPSevaluation)
                for (Resource neighbor : echantillon.getNeighborsResources(this.model, this.echantillonFilter))
                    echantillonResource.addProperty(estLieA, neighbor);

            if (loadEstProcheChimiquementDe && !this.STUPSevaluation)
                for (Resource neighbor : echantillon.getChemicalNeighborsResources(this.model, this.echantillonFilter))
                    echantillonResource.addProperty(estProcheChimiquementDe, neighbor);

            this.model.add(echantillonResource, idEchantillon, echantillon.getId() + "");
        }
    }

    public void saveOntology(String filename) {
        try {
            System.out.println(filename);
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
