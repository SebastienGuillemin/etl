package com.sebastienguillemin.stups;

import org.apache.logging.log4j.Logger;

import com.sebastienguillemin.stups.repository.DataRepository;
import com.sebastienguillemin.stups.repository.RDFRepository;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        // DataRepository repository = new DataRepository();
        // repository.loadData();

        new RDFRepository("STUPS.ttl");
    }
}
