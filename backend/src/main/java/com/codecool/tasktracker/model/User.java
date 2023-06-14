package com.codecool.tasktracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<String> authorities;

}
