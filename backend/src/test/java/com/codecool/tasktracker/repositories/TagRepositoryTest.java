package com.codecool.tasktracker.repositories;

import com.codecool.tasktracker.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TagRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;

    private Tag tag1;
    private Tag tag2;

    @BeforeEach
    public void deleteDatabaseEntries() {
        if (tag1 == null || tag2 == null) {
            tag1 = new Tag();
            tag2 = new Tag();
            tag1.setName("Tag1");
            tag2.setName("Tag2");
            entityManager.persist(tag1);
            entityManager.persist(tag2);
        }
    }

    @Test
    public void getTagByNameTest() {
        assertEquals(tag1, tagRepository.getTagByName(tag1.getName()));
        assertEquals(tag2, tagRepository.getTagByName(tag2.getName()));
    }

}
