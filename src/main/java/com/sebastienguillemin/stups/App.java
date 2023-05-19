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
        DataRepository repository = new DataRepository();
        RDFRepository rdfRepository = new RDFRepository("STUPS.ttl");

        Session session = SessionProvider.getSession();

        List<Echantillon> echantillons = repository.loadData(session, 1);
        rdfRepository.populate(echantillons);

        session.getTransaction().commit();
        session.close();
    }
}
