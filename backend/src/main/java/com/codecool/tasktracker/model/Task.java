package com.codecool.tasktracker.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Task {

    public Task() {}

    public Task(String name, String description, Timestamp timestamp, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.tags = tags;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Timestamp timestamp;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Tag> getTags() {
        return tags;
    }

}
