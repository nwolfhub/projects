package org.nwolfhub.projects.database;

import org.nwolfhub.projects.database.model.Project;

public class ObjectDao {
    private HibernateController controller;

    public ObjectDao(HibernateController controller) {
        this.controller = controller;
    }
    public Project getProject(Integer id) {
        return null;
    }
}
