package org.nwolfhub.projects.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    public Integer id;
    public String text;
    public String color;

    public Tag() {}

    public Tag(Integer id, String text, String color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public Tag setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Tag setText(String text) {
        this.text = text;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Tag setColor(String color) {
        this.color = color;
        return this;
    }
}