package com.sebastienguillemin.stups.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFWriter;
import org.apache.jena.riot.RIOT;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.entity.resource.Aspect;
import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.model.entity.resource.FormeChimique;
import com.sebastienguillemin.stups.model.entity.resource.Saisine;
import com.sebastienguillemin.stups.model.entity.resource.Scelle;
import com.sebastienguillemin.stups.model.entity.resource.Service;
import com.sebastienguillemin.stups.model.entity.resource.ServiceRequerant;
import com.sebastienguillemin.stups.model.entity.resource.Substance;
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
        Property estLieA = null;

        boolean loadEstLieA = this.propertiesReader.getPropertyValueBoolean("ontology.save.estLieA");
                
        System.out.println("Load loadEstLieA : " + loadEstLieA);

        if (loadEstLieA)
            estLieA = this.model.createProperty(RDFRepository.PREFIX + "estLieA");

        for (Echantillon echantillon : echantillons) {
            if (!echantillonFilter.filter(echantillon.getId())) {
                continue;
            }

            echantillonResource = echantillon.getResource(this.model);

            if (loadEstLieA && !this.STUPSevaluation)
                for (Resource neighbor : echantillon.getNeighborsResources(this.model, this.echantillonFilter))
                    echantillonResource.addProperty(estLieA, neighbor);
        }

        this.setAllDifferent();
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

    /**
     * Set that all instances of an OWL classes are different
     */
    private void setAllDifferent() {
        // Echantillon
        RDFList listEchantillon = this.model.createList(Echantillon.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentEchantillonResource = model.createResource();
        allDifferentEchantillonResource.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentEchantillonResource.addProperty(OWL.distinctMembers, listEchantillon);

        // Aspect
        RDFList listAspect = this.model.createList(Aspect.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentAspect = model.createResource();
        allDifferentAspect.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentAspect.addProperty(OWL.distinctMembers, listAspect);

        // Composant
        // RDFList listComposant = this.model.createList(Composant.allEntitiesResources.values().toArray(new Resource[0]));
        // Resource allDifferentComposant = model.createResource();
        // allDifferentComposant.addProperty(RDF.type, OWL.AllDifferent);
        // allDifferentComposant.addProperty(OWL.distinctMembers, listComposant);

        // FormeChimique
        RDFList listFormeChimique = this.model.createList(FormeChimique.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentFormeChimique = model.createResource();
        allDifferentFormeChimique.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentFormeChimique.addProperty(OWL.distinctMembers, listFormeChimique);

        // Saisine
        RDFList listSaisine = this.model.createList(Saisine.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentSaisine = model.createResource();
        allDifferentSaisine.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentSaisine.addProperty(OWL.distinctMembers, listSaisine);

        // Scelle
        RDFList listScelle = this.model.createList(Scelle.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentScelle = model.createResource();
        allDifferentScelle.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentScelle.addProperty(OWL.distinctMembers, listScelle);

        // Service
        RDFList listService = this.model.createList(Service.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentService = model.createResource();
        allDifferentService.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentService.addProperty(OWL.distinctMembers, listService);

        // ServiceRequerant
        RDFList listServiceRequerant = this.model.createList(ServiceRequerant.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentServiceRequerant = model.createResource();
        allDifferentServiceRequerant.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentServiceRequerant.addProperty(OWL.distinctMembers, listServiceRequerant);

        // Substance
        RDFList listSubstance = this.model.createList(Substance.allEntitiesResources.values().toArray(new Resource[0]));
        Resource allDifferentSubstance = model.createResource();
        allDifferentSubstance.addProperty(RDF.type, OWL.AllDifferent);
        allDifferentSubstance.addProperty(OWL.distinctMembers, listSubstance);
    
    }
}
