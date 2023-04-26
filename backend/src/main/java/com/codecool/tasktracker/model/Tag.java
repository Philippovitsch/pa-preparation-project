package com.codecool.tasktracker.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Tag {

    public Tag() {}

    public Tag(String name, Set<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}