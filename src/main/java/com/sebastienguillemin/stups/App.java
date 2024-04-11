package com.sebastienguillemin.stups;

import java.util.List;

import org.hibernate.Session;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.repository.EchantillonRepository;
import com.sebastienguillemin.stups.repository.RDFRepository;
import com.sebastienguillemin.stups.session.SessionProvider;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        int batchSize = -1;
        if (args.length > 0)
            batchSize = Integer.parseInt(args[0]);

        EchantillonRepository repository = new EchantillonRepository();
        RDFRepository rdfRepository = new RDFRepository("STUPS.ttl");

        Session session = SessionProvider.getSession();

        List<Echantillon> echantillons = repository.loadData(session, batchSize);
        rdfRepository.populate(echantillons);
        rdfRepository.saveOntology("ontology" + ((batchSize != -1) ? "_" + batchSize : "") + ".ttl");

        session.getTransaction().commit();
        session.close();
    }
}
