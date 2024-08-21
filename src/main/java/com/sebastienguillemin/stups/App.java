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
        if (args.length > 0)
            dayCount = Integer.parseInt(args[0]);

        EchantillonRepository repository = new EchantillonRepository();
        RDFRepository rdfRepository = new RDFRepository("STUPS.ttl");

        Session session = SessionProvider.getSession();

        List<Echantillon> echantillons = repository.loadData(session, dayCount);
        rdfRepository.populate(echantillons);
        
        PropertiesReader propertiesReader = PropertiesReader.getInstance();

        rdfRepository.saveOntology(propertiesReader.getPropertyValue("ontology.name"));

        session.getTransaction().commit();
        session.close();
    }
}
