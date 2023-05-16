package com.sebastienguillemin.stups;

import org.hibernate.Session;

import com.sebastienguillemin.stups.model.Echantillon;
import com.sebastienguillemin.stups.session.SessionProvider;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Session session = SessionProvider.getSession();

        if (session == null)
            return;

        session.beginTransaction();
        Echantillon echantillon =  session.createQuery("FROM Echantillon WHERE id=1", Echantillon.class).getSingleResult();
        session.getTransaction().commit();
        session.close();

        System.out.println(echantillon.getScelle().getId());
    }
}
