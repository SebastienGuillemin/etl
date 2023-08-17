package com.sebastienguillemin.stups.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;

public class DataRepository {

    public List<Echantillon> loadData(Session session) {
        return this.loadData(session, -1);
    }

    public List<Echantillon> loadData(Session session, int limit) {

        if (session == null)
            return null;

        session.beginTransaction();
        System.out.println("Loading data from PostgreSQL.");

        Query<Echantillon> query = session.createQuery("FROM Echantillon WHERE composition IS NOT NULL ORDER BY id", Echantillon.class);
        
        if (limit > 0)
            query.setMaxResults(limit);

        List<Echantillon> echantillons =  query.list();
        System.out.println(echantillons.size() + " echantillon(s) loaded.");

        return echantillons;
    }
    
}
