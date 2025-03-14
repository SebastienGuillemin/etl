package com.sebastienguillemin.stups.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.util.PropertiesReader;

public class EchantillonRepository {
    // private static final String END_DATE = "2022-02-28";

    private PropertiesReader propertiesReader;

    public EchantillonRepository() {
        this.propertiesReader = PropertiesReader.getInstance();
    }

    public List<Echantillon> loadData(Session session) {
        return this.loadData(session, -1, false);
    }

    public List<Echantillon> loadData(Session session, int dayCount, boolean STUPSevaluation) {
        if (session == null)
            return null;

        session.beginTransaction();
        System.out.println("Loading data from PostgreSQL.");

        String queryString;
        if (STUPSevaluation)
            queryString = this.propertiesReader.getPropertyValue("sql.evaluation_query");
        else
            queryString = this.propertiesReader.getPropertyValue("sql.query");

        System.out.println(queryString);

        Query<Echantillon> query = session.createNativeQuery(queryString, Echantillon.class);

        List<Echantillon> rows = query.list();
        System.out.println(rows.size() + " row(s) loaded.");

        return rows;
    }
}
