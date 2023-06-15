package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag getTagByName(String name);

}
