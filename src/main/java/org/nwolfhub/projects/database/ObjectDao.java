package org.nwolfhub.projects.database;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nwolfhub.projects.database.model.Project;
import org.nwolfhub.projects.database.model.Tag;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ObjectDao {
    private HibernateController controller;

    public ObjectDao(HibernateController controller) {
        this.controller = controller;
    }
    public Project getProject(Integer id) {
        Session session = controller.getSessionFactory().openSession();
        Project project = session.get(Project.class, id);
        session.close();
        return project;
    }
    public void setObject(Object o) {
        Session session = controller.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(o);
        transaction.commit();
        session.close();
    }
    public Tag getTag(Integer id) {
        Session session = controller.getSessionFactory().openSession();
        Tag tag = session.get(Tag.class, id);
        session.close();
        return tag;
    }
    public ObjectDao(AnnotationConfigApplicationContext context) {
        controller = context.getBean(HibernateController.class);
    }
}
