package com.sebastienguillemin.stups;

import java.util.List;

import org.hibernate.Session;

import com.sebastienguillemin.stups.model.Echantillon;
import com.sebastienguillemin.stups.repository.DataRepository;
import com.sebastienguillemin.stups.repository.RDFRepository;
import com.sebastienguillemin.stups.session.SessionProvider;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        int batchSize = -1;
        if (args.length > 0)
            batchSize = Integer.parseInt(args[0]);

        DataRepository repository = new DataRepository();
        RDFRepository rdfRepository = new RDFRepository("STUPS.ttl");

        Session session = SessionProvider.getSession();

        List<Echantillon> echantillons = repository.loadData(session, batchSize);
        rdfRepository.populate(echantillons);

        session.getTransaction().commit();
        session.close();
    }
}
