package com.sebastienguillemin.stups.repository;

import java.util.List;

import org.hibernate.Session;

import com.sebastienguillemin.stups.model.Echantillon;
import com.sebastienguillemin.stups.session.SessionProvider;

public class DataRepository {

    public void loadData() {
        Session session = SessionProvider.getSession();

        if (session == null)
            return;

        session.beginTransaction();
        System.out.println("Loading data from PostgreSQL.");
        List<Echantillon> echantillons =  session.createQuery("FROM Echantillon", Echantillon.class).list();
        session.getTransaction().commit();
        session.close();

        System.out.println(echantillons.size() + " tuples loaded.\n");
    }
    
}
