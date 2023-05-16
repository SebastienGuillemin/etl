package com.sebastienguillemin.stups.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionProvider {
    private static SessionFactory sessionFactory;

    public static Session getSession () {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);


                return null;
            }
        }

        return sessionFactory.openSession();
    }
}
