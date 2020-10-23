package org.library.repositories.jdbc;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorRepositoryJdbcTest {

    private static final String EXISTING_AUTHOR_NAME = "Zed A. Shaw";

    @Autowired
    private AuthorRepositoryJdbc authorRepositoryJdbc;

    @Test
    void shouldNotAddDuplicatedAuthorName() {
        assertThrows(DataIntegrityViolationException.class, () -> authorRepositoryJdbc.save(new Author(0, EXISTING_AUTHOR_NAME)));
    }
}