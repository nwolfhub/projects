package org.nwolfhub.projects.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.nwolfhub.projects.database.model.Project;
import org.nwolfhub.projects.database.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Component
public class HibernateController {
    private SessionFactory sessionFactory;

    @Autowired
    private Properties properties;

    public HibernateController(Properties properties) {
        this.properties = properties;
    }


    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(Project.class);
                configuration.addAnnotatedClass(Tag.class);
                configuration.setProperties(properties);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
