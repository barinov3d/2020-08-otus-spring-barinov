package org.library.dao.jdbc;

import org.junit.jupiter.api.*;
import org.library.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorDaoJdbcTest {

    private static final String EXISTING_AUTHOR_NAME = "Zed A. Shaw";

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void shouldNotAddDuplicatedAuthorName() {
        assertThrows(DataIntegrityViolationException.class, () -> authorDaoJdbc.save(new Author(0, EXISTING_AUTHOR_NAME)));
    }
}