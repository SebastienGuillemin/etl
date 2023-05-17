package com.sebastienguillemin.stups.repository;

import java.util.List;

import org.hibernate.Session;

import com.sebastienguillemin.stups.model.Echantillon;
import com.sebastienguillemin.stups.session.SessionProvider;

import org.hibernate.query.Query;

public class DataRepository {

    public List<Echantillon> loadData(int limit) {
        Session session = SessionProvider.getSession();

        if (session == null)
            return null;

        session.beginTransaction();
        System.out.println("Loading data from PostgreSQL.");

        Query<Echantillon> query = session.createQuery("FROM Echantillon ", Echantillon.class);
        if (limit > 0)
            query.setMaxResults(limit);

        List<Echantillon> echantillons =  query.list();

        session.getTransaction().commit();
        session.close();

        System.out.println(echantillons.size() + " echantillon(s) loaded.");

        return echantillons;
    }
    
}
