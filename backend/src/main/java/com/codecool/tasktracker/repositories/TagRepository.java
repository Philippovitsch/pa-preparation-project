package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag getTagByName(String name);

}
