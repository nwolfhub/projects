package org.nwolfhub.projects.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    public Integer id;
    public String name;
    public String shortDescription;
    public String description;
    public String tags;
    public String website;

    public Project() {

    }

    public Project(Integer id, String name, String shortDescription, String description, String tags, String website) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.tags = tags;
        this.website = website;
    }

    public Integer getId() {
        return id;
    }

    public Project setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Project setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Project setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public Project setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Project setWebsite(String website) {
        this.website = website;
        return this;
    }
}
