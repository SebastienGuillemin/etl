package com.sebastienguillemin.stups;

import java.util.List;

import org.hibernate.Session;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.repository.EchantillonRepository;
import com.sebastienguillemin.stups.repository.RDFRepository;
import com.sebastienguillemin.stups.session.SessionProvider;
import com.sebastienguillemin.stups.util.PropertiesReader;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        int dayCount = 1000;
        boolean STUPSevaluation = false;
        int limite = -1;

        if (args.length == 1 && args[0].equals("stups_evaluation_qualitative")) {
            System.out.println("[ETL] STUPS evaluation mode.");
            STUPSevaluation = true;
        }
        if (args.length == 2 && args[0].equals("stups_evaluation_quantitative")) {
            System.out.println("[ETL] STUPS qunatitative evaluation mode. Limite = " + args[1]);
            STUPSevaluation = true;
            limite = Integer.parseInt(args[1]);
            System.out.println("limite = " + limite);
        }
            
        PropertiesReader propertiesReader = PropertiesReader.getInstance();
        RDFRepository rdfRepository = new RDFRepository(propertiesReader.getPropertyValue("ontology.base.name"), STUPSevaluation);
        
        Session session = SessionProvider.getSession();
        
        EchantillonRepository repository = new EchantillonRepository();
        List<Echantillon> echantillons = repository.loadData(session, dayCount, STUPSevaluation, limite);
        rdfRepository.populate(echantillons);

        if(STUPSevaluation)
            if (limite == -1)
                rdfRepository.saveOntology(propertiesReader.getPropertyValue("ontology.save.evaluation_name"));
            else {
                String[] namePart = propertiesReader.getPropertyValue("ontology.save.evaluation_name").split("\\.");
                rdfRepository.saveOntology(namePart[0] + "_" + limite + ".ttl");
            }
        else
            rdfRepository.saveOntology(propertiesReader.getPropertyValue("ontology.save.name"));

        session.getTransaction().commit();
        session.close();
    }
}