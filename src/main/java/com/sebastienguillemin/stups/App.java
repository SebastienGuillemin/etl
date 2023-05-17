package com.sebastienguillemin.stups;

import java.util.List;

import com.sebastienguillemin.stups.model.Echantillon;
import com.sebastienguillemin.stups.repository.DataRepository;
import com.sebastienguillemin.stups.repository.RDFRepository;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        DataRepository repository = new DataRepository();
        RDFRepository rdfRepository = new RDFRepository("STUPS.ttl");

        List<Echantillon> echantillons = repository.loadData(1);
        rdfRepository.populate(echantillons);
    }
}
