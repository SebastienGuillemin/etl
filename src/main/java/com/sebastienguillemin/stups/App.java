package com.sebastienguillemin.stups;

import org.apache.logging.log4j.Logger;

import com.sebastienguillemin.stups.repository.DataRepository;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        DataRepository repository = new DataRepository();
        repository.loadData();
    }
}
